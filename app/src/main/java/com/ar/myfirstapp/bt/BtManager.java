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
    private BluetoothAdapter mBluetoothAdapter;
    private boolean connected;

    public BtManager() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isBtAdaptorEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    public boolean isConnected() {
        return connected;
    }

    private String getELM327Address(String OBDII) {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
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
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(ELM327Address);

            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
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
