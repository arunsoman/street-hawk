package com.ar.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;
import com.ar.myfirstapp.utils.Utils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public ELM327 device1;
    private BtManager btManager;

    private Button buttonScan;
    private Button buttonSend;
    private EditText editTextInput;
    private TextView textViewLog;

    public static Handler responseCallback;

    private BroadcastReceiver btReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                if (!Utils.isBluetoothEnabled(MainActivity.this)) {
                    buttonScan.setActivated(false);
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, Utils.BT_INT_REQ);
                } else {
                    buttonScan.setActivated(true);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        btManager = new BtManager();

        responseCallback = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String command = "cmd";
                textViewLog.append(command);
                display(msg.getData().getString(command));
                super.handleMessage(msg);
            }
        };

        btReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    private void initUI() {
        editTextInput = (EditText) findViewById(R.id.editTextInput);
        textViewLog = (TextView) findViewById(R.id.textViewLog);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonSend = (Button) findViewById(R.id.buttonSend);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextInput.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    if (message.startsWith("m1")) {
                        Command c = Mode1.getCommand(message.split(" ")[1]);
                        if (c != null)
                            device1.send(c);
                        return;
                    }
                    device1.send(new Command("", message + "\r", "", new Parser() {
                        @Override
                        public void parse(Command command) {
                            byte[] rawResp = command.getRawResp();
                            StringBuilder sb = new StringBuilder();
                            for (byte aByte : rawResp) {
                                sb.append(aByte).append(' ');
                            }
                            command.setResult(sb.toString());
                        }
                    }));
                    editTextInput.setText(message.split(" ")[0]);
                }
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonScan.setActivated(false);
                new Handler().post(new Runnable() {
                    BluetoothSocket bs;

                    @Override
                    public void run() {
                        try {
                            if (btManager == null || !btManager.isConnected()) {
                                bs = btManager.connect();
                            }
                            if (btManager.isConnected()) {
                                device1 = new ELM327(bs, responseCallback);
                                fireTasks();
                            } else {
                                //TODO
                                buttonScan.setActivated(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            //TODO
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(btReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(btReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.BT_INT_REQ) {
            btReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fireTasks() {
        try {
            for (Command proto : AtCommands.protoIter) {
                for (Command initC : AtCommands.initCommands)
                    device1.send(initC);
                device1.send(proto);
                //             device1.send(ModeFactory.getCommand("m1", "20"));
                for (String str : ModeFactory.getSupportedModes()) {
                    device1.send(ModeFactory.getDiscoveryCommand(str));
                }
                //for (Command c: AtCommands.testCommands)
                //    device1.send(c);
            }
//            for(Command c: AtCommands.initCanScan){
//                    device1.send(c);
//            }
// //           device1.getMode1PIDs();
            //           device1.sendCommand(AtCommands.activitMonitor);
            /*
            Command command = Mode1.getCommand("00");
            device1.sendCommand(command);
            command = Mode1.getCommand("1C");
            device1.sendCommand(command);
            command = Mode1.getCommand("12");
            device1.sendCommand(command);
            command = Mode1.getCommand("51");
            device1.sendCommand(command);
            command = Mode1.getCommand("05");
            device1.sendCommand(command);
            device1.sendCommand(Mode1.getCommand("00"));
            device1.sendCommand(Mode1.getCommand("01"));
            device1.sendCommand(Mode1.getCommand("04"));
            device1.sendCommand(Mode1.getCommand("0C"));
            device1.sendCommand(Mode1.getCommand("0D"));
            device1.initSequence();
            //device1.queryCan((byte) 1, (byte) (0x7DF));
            */

        } catch (Exception e) {
            Log.e("MActivity", "NPE", e);
        }
    }

    public void display(final String str) {
        if (str.contains(", p:00") || str.contains("m01, p:20")) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    String mode = null;
                    if (str.contains("m:01")) {
                        mode = "m1";
                    } else if (str.contains("m:09")) {
                        mode = "m9";
                    } else return;
                    String tmp = str.split("Result: ")[1];
                    for (String pid : tmp.split(" ")) {
                        if (pid.contains("00"))
                            continue;
                        Command c = ModeFactory.getCommand(mode, pid);
                        if (c != null) {
                            device1.send(c);
                        }
                    }
                }
            });
        }
    }
}