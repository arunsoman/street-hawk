package com.ar.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.view.ViewPager;
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
import com.ar.myfirstapp.view.OBDView;
import com.ar.myfirstapp.view.ResponseHandler;
import com.ar.myfirstapp.view.ResponseViewer;
import com.ar.myfirstapp.view.fragments.BaseFragment;
import com.ar.myfirstapp.view.fragments.FragmentFactory;
import com.ar.myfirstapp.view.fragments.LogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static ELM327 device1;
    private BtManager btManager;

    public ResponseHandler responseHandler;

    private ViewPager viewPager;
    private TextView textViewTitle;

    private BaseFragment[] fragments = new BaseFragment[FragmentFactory.getTitle().length];

    private List<Command>[] fragmentData = new List[FragmentFactory.getTitle().length];

    private BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                if (!Utils.isBluetoothEnabled(MainActivity.this)) {
                    //buttonScan.setActivated(false);
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, Utils.BT_INT_REQ);
                } else {
                    //buttonScan.setActivated(true);
                }
            }
        }
    };

    void startFire() {
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
                    } else {
                        //TODO
                        //buttonScan.setActivated(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //TODO
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        btManager = new BtManager();
        responseHandler = new ResponseHandler(this);
        bluetoothStateReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));

        ResponseViewer tvLog = new ResponseViewer();
        responseHandler.registerDisplay(tvLog, "*");

        startFire();
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textViewTitle.setText(FragmentFactory.getTitle()[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(FragmentFactory.getTitle().length);

        /*
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
                                device1 = new ELM327(bs, responseHandler);
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
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bluetoothStateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bluetoothStateReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.BT_INT_REQ) {
            bluetoothStateReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));
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


    public void show(Command command) {
        try {
            int index = Integer.parseInt(command.getCommandId(), 16);
            addData(index, command);
        } catch (NumberFormatException ignored) {
        } finally {
            addData(FragmentFactory.getTitle().length - 1, command);
            sendBroadcast(new Intent("myfirstapp.refresh"));
        }
    }

    private void addData(int index, Command command) {
        try {
            fragmentData[index].add(command);
        } catch (NullPointerException e) {
            fragmentData[index] = new LinkedList<>();
            fragmentData[index].add(command);
        }
    }

    public List<Command> getCommands(int index) {
        return fragmentData[index];
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment;
            if (position == FragmentFactory.getTitle().length - 1) {
                fragment = new LogFragment();
            } else {
                fragment = new OBDFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 11;
        }
    }
}