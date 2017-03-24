package com.ar.myfirstapp.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import static com.ar.myfirstapp.MainActivity.BT_INT_REQ;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class BtManager  {

    private BluetoothAdapter mBluetoothAdapter;
    private boolean connected;

    public BtManager(Activity activity){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            Toast.makeText(activity.getApplicationContext(),"Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableBtIntent, BT_INT_REQ);
        }
    }

    public boolean isBtAdaptorEnabled(){
        return mBluetoothAdapter.isEnabled();
    }

    public boolean isConnected(){
        return connected;
    }
    private String getELM327Addres(String OBDII){
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

    public  BluetoothSocket connect() throws IOException {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(getELM327Addres("OBDII"));
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothSocket bluetoothSocket = null ,sockFallback = null;

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            connected = bluetoothSocket.isConnected();
            return bluetoothSocket;
        } catch (IOException e) {
            String TAG = BluetoothManager.class.getName();
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
    }
}
