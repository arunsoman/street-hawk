package com.ar.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class ELMConnector {

    private BluetoothSocket bluetoothSocket;
    private Pipe pipe;
    private String[] initSeqCmds = {"ATL1", "ATH1", "ATS1", "ATAL"};
    private String streamCmd = "ATSP0";
    private String deviceID = "AT1";

    class Pipe implements Closeable {
        InputStream is;
        OutputStream os;

        Pipe(BluetoothSocket bs) throws IOException {
            is = bs.getInputStream();
            os = bs.getOutputStream();
        }

        @Override
        public void close() throws IOException {
            is.close();
            os.close();
        }
    }

    public boolean connect(String deviceAddress) throws IOException {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothSocket sockFallback = null;

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            pipe = new Pipe(bluetoothSocket);
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
                pipe = new Pipe(bluetoothSocket);
            } catch (Exception e2) {
                Log.e(TAG, "Couldn't fallback while establishing Bluetooth connection.", e2);
                throw new IOException(e2.getMessage());
            }
        }
        return bluetoothSocket != null;
    }

    public boolean initSequence() throws IOException {
        String resp = sendNreceiveCmd(deviceID);
        if (!resp.toUpperCase().contains("ELM327")) {
            throw new IOException("Unknown device:" + resp);
        }
        for (String cmd : initSeqCmds) {
            resp = sendNreceiveCmd(cmd);
            if (!resp.toUpperCase().contains("ELM327")) {
                throw new IOException("Unexpected res:" + resp + " for cmd:" + cmd);
            }
        }
        return true;
    }

    public void scan(ELMStreamHandler handler) throws IOException {
        pipe.os.write(streamCmd.getBytes());
        pipe.os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(pipe.is));
        String line;
        while ((line = br.readLine()) != null) {
            handler.handle(line);
        }
    }

    public boolean reset() throws IOException {
        String resp = sendNreceiveCmd("ATZ");
        Log.d("reset", resp);
        return true;
    }

    private byte[] resp = new byte[30];

    public String sendNreceiveCmd(String cmd) throws IOException {
        pipe.os.write(cmd.getBytes());
        pipe.os.flush();
        int readBytes = pipe.is.read(resp);
        return new String(resp, 0, readBytes);

    }

}
