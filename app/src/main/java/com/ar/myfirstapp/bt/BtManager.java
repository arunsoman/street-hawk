package com.ar.myfirstapp.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.elm.ELMStreamLogger;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class BtManager {
    public static String getELMAddres(){
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        String deviceAddress = null;
        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice device : pairedDevices)
            {
                if (device.getName().equals("OBDII")) {
                    deviceAddress = device.getAddress();
                    try {
                        try(ELMConnector connector = new ELMConnector()) {
                            connector.connect(deviceAddress);
                            connector.initSequence();
//                            try(ELMStreamLogger logger =new ELMStreamLogger()) {
//                                connector.scan(logger,null);
//                            }

                            connector.getSupportedJ1979PIDs();
                        }
                    }catch (Exception e){
                        boolean connected = false;
                    }
                }
            }
        }
        return deviceAddress;
    }

    public static String getBTDevieType(BluetoothDevice d){
        String type = "";

        switch (d.getType()){
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                type = "DEVICE_TYPE_CLASSIC";
                break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                type = "DEVICE_TYPE_DUAL";
                break;
            case BluetoothDevice.DEVICE_TYPE_LE:
                type = "DEVICE_TYPE_LE";
                break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                type = "DEVICE_TYPE_UNKNOWN";
                break;
            default:
                type = "unknown...";
        }

        return type;
    }

}
