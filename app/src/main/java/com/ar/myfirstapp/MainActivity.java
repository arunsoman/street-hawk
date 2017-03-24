package com.ar.myfirstapp;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;
import com.ar.myfirstapp.view.ResponseHandler;
import com.ar.myfirstapp.view.ResponseViewer;
import com.ar.myfirstapp.view.Terminal;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static final int BT_INT_REQ = 500;
    public static ELM327 device1;
    private Button btnScan;
    private ResponseViewer tvLog;
    private BtManager btManager;

    public ResponseHandler responseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        responseHandler = new ResponseHandler(this);
        tvLog = new ResponseViewer(this);
        responseHandler.registerDissplay(tvLog, "*");
        new Terminal(this, device1);
        btnScan = (Button) findViewById(R.id.scan);
        btManager = new BtManager(this);
        btnScan.setActivated(false);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnScan.setActivated(false);
                new Handler().post(new Runnable() {
                    BluetoothSocket bs;

                    @Override
                    public void run() {
                        try {
                            if (btManager == null || !btManager.isConnected()) {
                                bs = btManager.connect();
                            }
                            if (btManager.isConnected()) {
                                device1 = new ELM327(bs, responseHandler);
                                fireTasks();
                            }else {
                                //TODO
                                btnScan.setActivated(true);
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
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BT_INT_REQ) {
            if(btManager.isBtAdaptorEnabled()){
                btnScan.setActivated(true);
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fireTasks() {
        try {
            for(Command proto: AtCommands.protoIter) {
                for(Command initC: AtCommands.initCommands)
                device1.send(initC);
                device1.send(proto);
                device1.send(ModeFactory.getCommand("m9", "00"));
                device1.send(ModeFactory.getCommand("m1", "00"));
                device1.send(ModeFactory.getCommand("m1", "20"));
                device1.send(ModeFactory.getCommand("m1", "40"));
//                for(String str: ModeFactory.getSupportedModes()){
//                    device1.send(ModeFactory.getDiscoveryCommand(str));
//                }
//                //for (Command c: AtCommands.testCommands)
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

        }  catch (Exception e) {
            Log.e("MActivity","NPE",e);
        }
    }


}