package com.ar.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;
import com.ar.myfirstapp.view.ResponseViewer;
import com.ar.myfirstapp.view.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int BT_INT_REQ = 500;
    ELM327 device1 = null;
    private Button btnScan;
    private Handler mHandler;
    private ResponseViewer tvLog;
    private Terminal terminal;
    private BtManager btManager;

    public final Handler responseCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tvLog.display(msg.getData().getString("cmd"));
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLog = new ResponseViewer(this);
        terminal = new Terminal(this, device1);
        btnScan = (Button) findViewById(R.id.scan);
        btManager = new BtManager(this);
        btnScan.setActivated(false);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    device1 = new ELM327(btManager.connect(), responseCallback);
                } catch (IOException e) {
                    e.printStackTrace();
                    //TODO
                }
            }
        });
        mHandler = new Handler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            device1.resume(btManager.connect(), responseCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void readObdDevices() {
        try {
            for(Command proto: AtCommands.protoIter) {
                for(Command initC: AtCommands.initCommands)
                device1.send(initC);
                device1.send(proto);
                for (Command c: AtCommands.testCommands)
                    device1.send(c);
            }
            for(Command c: AtCommands.initCanScan){
                    device1.send(c);
            }
 //           device1.getMode1PIDs();
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
            e.printStackTrace();
        }
    }


}