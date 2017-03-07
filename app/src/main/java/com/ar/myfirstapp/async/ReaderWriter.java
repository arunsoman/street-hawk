package com.ar.myfirstapp.async;

import android.os.AsyncTask;
import android.util.Log;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.ResponseHandler;
import com.ar.myfirstapp.obd2.UnknownCommandException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Arun Soman on 3/7/2017.
 */

public class ReaderWriter extends AsyncTask<Void, Void, Boolean> {
    private ELMConnector.Pipe pipe;
    private Queue<Command> commands = new ArrayBlockingQueue<Command>(10);
    private ResponseHandler responseHandler;

    public ReaderWriter(ELMConnector.Pipe pipe){
        this.pipe = pipe;
    }

    public void submit(Command c){
        synchronized (commands){
            commands.add(c);
            Log.e("Submit:", c.toString());
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
    protected Boolean doInBackground(Void... params) {
        while (true){

            Command command;
            synchronized (commands){
                if(commands.size() ==  0)
                    continue;
                command = commands.remove();
            }
            try {
                pipe.sendNreceive(command);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadResponseException e) {
                e.printStackTrace();
            } catch (UnknownCommandException e) {
                e.printStackTrace();
            }catch (Throwable tr){
                if(tr instanceof InterruptedIOException)
                    throw tr;
            }
        }
    }
}
