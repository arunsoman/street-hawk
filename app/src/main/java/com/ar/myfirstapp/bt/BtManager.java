package com.ar.myfirstapp.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class BtManager {
    private static final String TAG = "BtManager";
    private static BluetoothAdapter bluetoothAdapter;
    private boolean connected;

    public static BluetoothAdapter getAdapter() {
        if (bluetoothAdapter != null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter;
    }

    public BtManager() {
    }

    public boolean isBtAdaptorEnabled() {
        return getAdapter().isEnabled();
    }

    public boolean isConnected() {
        return connected;
    }

    public static String getELM327Address(String OBDII) {
        Set<BluetoothDevice> pairedDevices = getAdapter().getBondedDevices();
        String deviceAddress = null;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(OBDII)) {
                    deviceAddress = device.getAddress();
                    return deviceAddress;
                }
            }
        }
        return deviceAddress;
    }

    public BluetoothSocket connect() throws IOException {
        BluetoothSocket bluetoothSocket = null, sockFallback = null;
        String ELM327Address = getELM327Address("OBDII");
        if (!TextUtils.isEmpty(ELM327Address)) {
            BluetoothDevice device = getAdapter().getRemoteDevice(ELM327Address);

            //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            UUID uuid = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
                connected = bluetoothSocket.isConnected();
                return bluetoothSocket;
            } catch (IOException e) {
                Class<?> clazz = bluetoothSocket.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
                try {
                    Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                    Object[] params = new Object[]{Integer.valueOf(1)};
                    sockFallback = (BluetoothSocket) m.invoke(bluetoothSocket.getRemoteDevice(), params);
                    sockFallback.connect();
                    bluetoothSocket = sockFallback;
                    connected = bluetoothSocket.isConnected();
                    return bluetoothSocket;
                } catch (Exception e2) {
                    connected = false;
                    Log.e(TAG, "Couldn't fallback while establishing Bluetooth connection.", e2);
                    throw new IOException(e2.getMessage());
                    //TODO
                }
            }
        } else {
            Log.e(TAG, "No Paired OBDII Devices");
        }
        return bluetoothSocket;
    }
}
