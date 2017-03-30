package com.ar.myfirstapp.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.bt.DeviceManager;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;
import com.ar.myfirstapp.utils.Utils;
import com.ar.myfirstapp.view.fragments.BaseFragment;
import com.ar.myfirstapp.view.fragments.FragmentFactory;
import com.ar.myfirstapp.view.fragments.LogFragment;
import com.ar.myfirstapp.view.fragments.OBDFragment;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    //public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    public static final String UUID = "fa87c0d0-afac-11de-8a39-0800200c9a66";

    private DeviceManager deviceManager;
    public ResponseHandler responseHandler;

    private ViewPager viewPager;
    private TextView textViewTitle;

    private Map<Integer, Command>[] fragmentData = new HashMap[FragmentFactory.getLength()];

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


    void initiateConnection() {
        if (deviceManager == null) {
            deviceManager = DeviceManager.getInstance();
            deviceManager.setHandler(responseHandler);
            deviceManager.initialize();
            String deviceAddress = deviceManager.getELM327Address("OBDII");
            if (!TextUtils.isEmpty(deviceAddress)) {
                BluetoothDevice device = deviceManager.getBluetoothAdapter().getRemoteDevice(deviceAddress);
                deviceManager.connect(device);
                fireTasks();
            }
        }
    }

    void terminateConnection() {
        deviceManager.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        terminateConnection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        responseHandler = new ResponseHandler(this);
        bluetoothStateReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));

        initiateConnection();
    }

    @Override
    public void onBackPressed() {
        /*
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
        */
        super.onBackPressed();
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
        switch (requestCode) {
            case Utils.BT_INT_REQ:
                if (resultCode == Activity.RESULT_OK)
                    bluetoothStateReceiver.onReceive(this, new Intent(BluetoothAdapter.ACTION_STATE_CHANGED));
                else {
                    Toast.makeText(MainActivity.this, R.string.error_bluetooth, Toast.LENGTH_SHORT).show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fireTasks() {
        try {
            for (Command proto : AtCommands.protoIter) {
                for (Command initC : AtCommands.initCommands)
                    deviceManager.send(initC);
                deviceManager.send(proto);
                //             deviceManager.send(ModeFactory.getCommand("m1", "20"));
                for (String str : ModeFactory.getSupportedModes()) {
                    deviceManager.send(ModeFactory.getDiscoveryCommand(str));
                }
                //for (Command c: AtCommands.testCommands)
                //    deviceManager.send(c);
            }
//            for(Command c: AtCommands.initCanScan){
//                    deviceManager.send(c);
//            }
// //           deviceManager.getMode1PIDs();
            //           deviceManager.sendCommand(AtCommands.activitMonitor);
            /*
            Command command = Mode1.getCommand("00");
            deviceManager.sendCommand(command);
            command = Mode1.getCommand("1C");
            deviceManager.sendCommand(command);
            command = Mode1.getCommand("12");
            deviceManager.sendCommand(command);
            command = Mode1.getCommand("51");
            deviceManager.sendCommand(command);
            command = Mode1.getCommand("05");
            deviceManager.sendCommand(command);
            deviceManager.sendCommand(Mode1.getCommand("00"));
            deviceManager.sendCommand(Mode1.getCommand("01"));
            deviceManager.sendCommand(Mode1.getCommand("04"));
            deviceManager.sendCommand(Mode1.getCommand("0C"));
            deviceManager.sendCommand(Mode1.getCommand("0D"));
            deviceManager.initSequence();
            //deviceManager.queryCan((byte) 1, (byte) (0x7DF));
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
            fragmentData[index].put(Integer.parseInt(command.getPid(), 16), command);
        } catch (NullPointerException e) {
            fragmentData[index] = new HashMap<>();
            try {
                fragmentData[index].put(Integer.parseInt(command.getPid(), 16), command);
            } catch (Exception ignored) {
            }
        } catch (IndexOutOfBoundsException ignored) {
        } catch (NumberFormatException ignored) {

        }
    }

    public Map<Integer, Command> getCommands(int index) {
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