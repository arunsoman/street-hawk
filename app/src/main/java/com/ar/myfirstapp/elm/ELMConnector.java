package com.ar.myfirstapp.elm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public final class ELMConnector implements AutoCloseable {

    private BluetoothSocket bluetoothSocket;
    private Pipe pipe;
    private String[] initSeqCmds = {"ATL1\r", "ATH1\r", "ATS1\r", "ATAL\r", "ATSPO\r"};
    private String streamCmd = "ATMA\n\r";
    private String deviceID = "ATI\r";

    @Override
    public void close() throws Exception {
        pipe.close();
    }

    private final class Pipe implements AutoCloseable {
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
        //sreset();
        String resp = sendNreceiveCmd(deviceID);
        /*if (!resp.toUpperCase().contains("ELM327")) {
            throw new IOException("Unknown device:" + resp);
        }*/
        for (String cmd : initSeqCmds) {
            resp = sendNreceiveCmd(cmd);
            Log.e("Response for "+cmd,resp);
        }
        return true;
    }

    public void scan(ELMStreamHandler handler) throws IOException {
        pipe.os.write(streamCmd.getBytes());
        pipe.os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(pipe.is));

        String line = br.readLine();
        StringBuilder res = new StringBuilder();

        // read until '>' arrives OR end of stream reached
        char c;
        byte b;
        // -1 if the end of the stream is reached
        while (((b = (byte) pipe.is.read()) > -1)) {
            c = (char) b;
            if (c == '\n') // read until '>' arrives
            {
                handler.handle(res.toString());
                res.delete(0,res.length());
            }
            res.append(c);
        }
    }

    public boolean reset() throws IOException {
        String resp = sendNreceiveCmd("AT Z\r");
        Log.d("reset", resp);
        return true;
    }

    private byte[] resp = new byte[30];

    public String sendNreceiveCmd(String cmd) throws IOException {
        pipe.os.write(cmd.getBytes());
        pipe.os.flush();

        StringBuilder res = new StringBuilder();

        // read until '>' arrives OR end of stream reached
        char c;
        byte b;
        // -1 if the end of the stream is reached
        while (((b = (byte) pipe.is.read()) > -1)) {
            c = (char) b;
            if (c == '>') // read until '>' arrives
            {
                break;
            }
            res.append(c);
        }
        return res.toString();

    }

}
