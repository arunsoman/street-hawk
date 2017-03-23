package com.ar.myfirstapp.async;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ar.myfirstapp.obd2.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Arun Soman on 3/7/2017.
 */

public class ReadWriteAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private final Queue<Command> commands = new ConcurrentLinkedQueue<Command>();
    private Handler responseCallback;
    private BluetoothSocket bluetoothSocket;
    private boolean stop;
    private boolean interrupt;

    public ReadWriteAsyncTask(BluetoothSocket bluetoothSocket, Handler responseCallback){
        this.responseCallback = responseCallback;
        this.bluetoothSocket = bluetoothSocket;
    }

    public void submit(final Command c){
        synchronized (commands){
            commands.add(c);
        }
    }

    public void interrupt() throws IOException {
        this.interrupt = true;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        try (Pipe pipe = new Pipe(bluetoothSocket)){
            Throwable tr = null;
            while (!stop){
                Command command;
                synchronized (commands){
                    if(commands.size() ==  0)
                        continue;
                    command = commands.remove();
                }
                try {
                    pipe.sendNreceive(command);
                    command.parse();
                    Bundle bundle = new Bundle(2);
                    bundle.putString("cmd", command.toString());
                    Message message = responseCallback.obtainMessage();
                    message.what = 1;
                    message.setData(bundle);
                    responseCallback.sendMessage(message);
                    tr = null;
                } catch (Throwable t){
                    tr = t;
                    if(tr instanceof InterruptedIOException) {
                        command.setResponseStatus(Command.ResponseStatus.NetworkError);
                        return true;
                    }
                    if(tr instanceof IOException) {
                        command.setResponseStatus(Command.ResponseStatus.NetworkError);
                        return false;
                    }
                }
                Log.e("readeWritethread",command.toString(), tr);
            }
            Log.e("readeWritethread","stopped");
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public void stop() {
        stop = true;
    }

    private final class Pipe extends ByteArrayOutputStream implements AutoCloseable {
        final InputStream is;
        final OutputStream os;
        private int state;

        void reset(int cnt){
            this.count = count- cnt;
            if(this.count <0)
                count = 0;
        }

        byte[] readAll() throws IOException {
            reset();
            boolean eom = false;
            while (!eom) {
                byte aByte = (byte) is.read();//Sometimes there may be delay in response hence wait
                if (state == 0 && aByte == 13) {
                    state = 1;
                } else if (state == 1 && (aByte == 10 || aByte == 13)) {
                    state = 2;
                    //Log.e("pip", "line "+ ASCIIUtils.toString(toByteArray()));
                } else if (state == 2 && aByte == 62) {
                    state = 3;
                    eom = true;
                } else {
                    state = 0;
                }
                if(interrupt){
                    writeOs("!".getBytes());
                    interrupt = false;
                }
                write(aByte);
            }
            reset(3);
            return toByteArray();
        }


        Pipe(BluetoothSocket bs) throws IOException {
            is = bs.getInputStream();
            os = bs.getOutputStream();
        }

        @Override
        public void close() throws IOException {
            is.close();
            os.close();
        }

        private Command sendNreceive(Command command) throws IOException {
            writeOs(command.getCmd());
            int avail = is.available();
            if(avail == 0){
                synchronized (is){
                    try {
                        is.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                if(( is.available()) == 0){
                    command.setRawResp(null);
                    command.setResponseStatus(Command.ResponseStatus.NO_DATA);
                }else{
                    byte[] rawResp = readAll();
                    command.setRawResp(rawResp);
                }
            }
            return command;
        }

        private void writeOs(byte[]data) throws IOException {
            synchronized (os) {
                os.write(data);
                os.flush();
            }
        }
    }
}
