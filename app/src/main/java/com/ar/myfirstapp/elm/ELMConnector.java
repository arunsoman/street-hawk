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
    private static final byte LINEFEED = (byte)('\n'); //\n
    private static final byte CR = 0x0D; //\r
    private static final byte DONE = 0x3E; //'>'
    private static final int SENDERID = 0xF1;
    private String[] initSeqCmds = {"ATH1", "ATS1", "ATAL", "ATSP0","ATSH7DF","ATDPN"};
    private String streamCmd = "ATMA";
    private String deviceID = "ATI";
    private State state;
    private enum State  {Connected, Disconnected, Initialized, Reset, Interrupted, Scanning};

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

    public void connect(String deviceAddress) throws IOException {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothSocket sockFallback = null;

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            pipe = new Pipe(bluetoothSocket);
            state = State.Connected;
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
                state = State.Connected;
            } catch (Exception e2) {
                Log.e(TAG, "Couldn't fallback while establishing Bluetooth connection.", e2);
                throw new IOException(e2.getMessage());
            }
        }
    }

    public void initSequence() throws IOException {
        //sreset();
        String resp = sendNreceive(deviceID);
        /*if (!resp.toUpperCase().contains("ELM327")) {
            throw new IOException("Unknown device:" + resp);
        }*/
        for (String cmd : initSeqCmds) {
            resp = sendNreceive(cmd);
            Log.e("Response for "+cmd,resp);
        }
        state = State.Initialized;
    }

    public void setToDefault() throws IOException{
        sendNreceive("AT D");
    }

    public int describeCurrentProtocolByNumber() throws IOException{
        return Integer.parseInt(sendNreceive("AT DPN").trim());
    }

    public String describeCurrentProtocol() throws IOException{
        return sendNreceive("AT DP").trim();
    }

    public void interruptScan()throws IOException{
        synchronized (pipe.os){
            pipe.os.write((byte)'!');
            pipe.os.flush();
            state = State.Interrupted;
        }
    }
    public void scan(ELMStreamHandler handler, String filter) throws IOException {
        if(filter == null)
            sendNreceive( "AT CRA XX XX XX XX");
        else sendNreceive("AT CRA "+filter);
        pipe.os.write(streamCmd.getBytes());
        pipe.os.write(CR);
        pipe.os.flush();
        readResponse();
        state = State.Scanning;
        String response = readResponse();
        while (response != null) {
            handler.handle(response);
            response = readResponse();
        }
    }

    private final StringBuilder sb = new StringBuilder();
    private String readResponse() throws IOException{
        String rep = null;
        byte b;
        // -1 if the end of the stream is reached
        while (((b = (byte) pipe.is.read()) > -1)) {
            if(b == LINEFEED || b == CR ) {
                //Ignore
            }
            else if (b == DONE){
                rep = sb.toString();
                break;
            }
            else{
                sb.append((char) b);
            }

        }
        sb.delete(0,sb.length());
        return rep;
    }
    public void reset() throws IOException {
        String resp = sendNreceive("AT Z");
        Log.d("reset", resp);
        state = State.Reset;
    }

    private byte[] resp = new byte[30];

    public String sendNreceive(String cmd) throws IOException {
        pipe.os.write(cmd.getBytes());
        pipe.os.write(CR);
        pipe.os.flush();

        return readResponse();
    }

    public String getSupportedJ1979PIDs() throws IOException {
        String result = sendNreceive("01 00");
        String resultArray[] = result.split(" ");
        StringBuilder hexString = new StringBuilder();
        for(int i=4;i<resultArray.length;i++){
            hexString.append(resultArray[i]);
        }
        Mode0Pids.printSupportedPIDs(hexString.toString());
        return null;
    }
}
