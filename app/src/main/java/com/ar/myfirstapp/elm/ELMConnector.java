    package com.ar.myfirstapp.elm;

    import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.ar.myfirstapp.async.ReadWriteAsyncTask;
import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.obd2.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

    /**
     * Created by Arun Soman on 2/28/2017.
     */

    public final class ELMConnector implements AutoCloseable {

        private BluetoothSocket bluetoothSocket;
        private Pipe pipe;
        private ReadWriteAsyncTask readWriteAsyncTask;
        private final Handler  responseCallback;

        public ELMConnector(Handler responseCallback) {
            this.responseCallback = responseCallback;
        }

        @Override
        public void close() throws Exception {
            pipe.close();
            bluetoothSocket.close();
            readWriteAsyncTask.stop();
        }

        public final class Pipe implements AutoCloseable {
            public final InputStream is;
            public final OutputStream os;

            Pipe(BluetoothSocket bs) throws IOException {
                is = bs.getInputStream();
                os = bs.getOutputStream();
            }

            @Override
            public void close() throws IOException {
                is.close();
                os.close();
            }

            public void sendNreceive(Command command) throws IOException {
                synchronized (pipe.os) {
                    os.write(command.cmd);
                    os.flush();
                }
                command.response.readResponse(is);
            }
        }
        public void interrupt() throws IOException {

        }

        public void connect(String deviceAddress) throws IOException {
            BtManager btManager = new BtManager();
            pipe = new Pipe(btManager.connect(deviceAddress));
            readWriteAsyncTask = new ReadWriteAsyncTask(pipe,  responseCallback);
            readWriteAsyncTask.execute();
        }

        public void sendNreceive(Command command) throws IOException {
            readWriteAsyncTask.submit(command);
        }

    }