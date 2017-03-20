package com.ar.myfirstapp.async;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Arun Soman on 3/7/2017.
 */

public class ReadWriteAsyncTask extends AsyncTask<Void, Void, Void> {
    private ELMConnector connector;
    private Queue<Command> commands = new ConcurrentLinkedQueue<Command>();
    private Handler responseCallback;
    private boolean stop;
    private String text;

    public ReadWriteAsyncTask(ELMConnector connector, Handler responseCallback){
        this.connector = connector;
        this.responseCallback = responseCallback;
    }

    public void submit(Command c){
        synchronized (commands){
            commands.add(c);
        }
    }

    public void interruptScan() throws IOException {
        synchronized (commands){
            connector.interrupt();
        }
    }
    @Override
    protected Void doInBackground(Void... params) {
        while (!stop){
            Command command;
            synchronized (commands){
                if(commands.size() ==  0)
                    continue;
                command = commands.remove();
            }
            try {
                try {
                    connector.sendNreceive(command);
                }catch (IOException e){
                    Log.e("readeWritethread","IOException: "+command.toString(), e);
                    command.setResponseStatus(Command.ResponseStatus.NetworkError);
                    throw e;
                }
                Bundle bundle = new Bundle(2);
                bundle.putString("cmd", command.toString());
                Message message = responseCallback.obtainMessage();
                message.what = 1;
                message.setData(bundle);
                responseCallback.sendMessage(message);
            } catch (Throwable tr){
                Log.e("readeWritethread","Exception: "+command.toString(), tr);
                if(tr instanceof InterruptedIOException)
                    break;
            }
            //Log.e("readeWritethread","Command: "+command.toString());
        }
        Log.e("readeWritethread","stopped");
        return null;
    }


    public void stop() {
        stop = true;
    }
}
