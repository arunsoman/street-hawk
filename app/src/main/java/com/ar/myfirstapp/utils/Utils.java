package com.ar.myfirstapp.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

/**
 * Created by amal.george on 24-03-2017
 */

public class Utils {
    public static final int BT_INT_REQ = 500;

    public static boolean isBluetoothEnabled(Activity activity) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(activity, "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
            return false;
        }
        return (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) || (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON);
    }
}
