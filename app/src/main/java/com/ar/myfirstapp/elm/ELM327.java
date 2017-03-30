package com.ar.myfirstapp.elm;

import android.bluetooth.BluetoothSocket;

import com.ar.myfirstapp.async.ReadWriteAsyncTask;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.bt.ResponseHandler;

import java.io.IOException;


public final class ELM327 {

    private ReadWriteAsyncTask readWriteAsyncTask;

    private BluetoothSocket bs;
    private ResponseHandler responseCallback;

    public ELM327(BluetoothSocket bs, ResponseHandler responseCallback) {
        readWriteAsyncTask = new ReadWriteAsyncTask(bs, responseCallback);
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


    public void resume(BluetoothSocket bs, ResponseHandler responseCallback) {
        this.responseCallback = responseCallback;
        this.bs = bs;
    }
}