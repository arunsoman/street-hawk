package com.ar.myfirstapp.elm;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.ar.myfirstapp.async.ReadWriteAsyncTask;
import com.ar.myfirstapp.obd2.Command;

import java.io.IOException;


public final class ELM327{

    public static long waitTime = 200;
    private ReadWriteAsyncTask readWriteAsyncTask;

    private BluetoothSocket bs;
    private Handler  responseCallback;

    public ELM327(BluetoothSocket bs, Handler responseCallback) {
        readWriteAsyncTask = new ReadWriteAsyncTask(bs,responseCallback);
        readWriteAsyncTask.execute();
        this.responseCallback = responseCallback;
        this.bs = bs;
    }

    public void send(Command command) {
        readWriteAsyncTask.submit(command);
    }
    public void interrupt() throws IOException {
        readWriteAsyncTask.interrupt();
    }


    public void resume(BluetoothSocket bs, Handler responseCallback) {
        this.responseCallback = responseCallback;
        this.bs = bs;
    }
}