package com.ar.myfirstapp.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Arun Soman on 3/7/2017.
 */

public class ReadWriteAsyncTask extends AsyncTask<Void, Void, Void> {
    private ELMConnector.Pipe pipe;
    private Queue<Command> commands = new ConcurrentLinkedQueue<Command>();
    private Handler responseCallback;
    private boolean stop;
    private String text;
    public ReadWriteAsyncTask(ELMConnector.Pipe pipe, Handler responseCallback){
        this.pipe = pipe;
        this.responseCallback = responseCallback;
    }

    public void submit(Command c){
        synchronized (commands){
            commands.add(c);
            Log.e("ReadWriteAsyncTask:", c.toString());
        }
    }

    public void interruptScan() throws IOException {
        synchronized (commands){
            //commands.clear();
            synchronized (pipe.os) {
                pipe.os.write((byte) '!');
                pipe.os.flush();
            }
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
                command.sendNreceive(pipe);
                Log.e("RWAT",command.toString());
                Bundle bundle = new Bundle(2);
                bundle.putString("cmd", command.toString());
                Message message = responseCallback.obtainMessage();
                message.what = 1;
                message.setData(bundle);
                responseCallback.sendMessage(message);
            } catch (Throwable tr){
                if(tr instanceof InterruptedIOException)
                    break;
            }
        }
        Log.e("readeWritethread","stopped");
        return null;
    }


    public void stop() {
        stop = true;
    }
}
