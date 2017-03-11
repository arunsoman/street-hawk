    package com.ar.myfirstapp.elm;

    import android.bluetooth.BluetoothSocket;
    import android.content.Context;
    import android.os.Handler;

import com.ar.myfirstapp.async.ReadWriteAsyncTask;
import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.obd2.Command;
    import com.ar.myfirstapp.obd2.response.ResponseReader;

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

        public void send(Command command) {
            readWriteAsyncTask.submit(command);
        }

        public final class Pipe implements AutoCloseable {
            public final InputStream is;
            public final OutputStream os;
            private final ResponseReader reader = new ResponseReader();

            Pipe(BluetoothSocket bs) throws IOException {
                is = bs.getInputStream();
                os = bs.getOutputStream();
            }

            @Override
            public void close() throws IOException {
                is.close();
                os.close();
            }

            public byte[] sendNreceive(byte[] request) throws IOException {
                synchronized (os) {
                    pipe.os.write(request);
                    pipe.os.flush();
                }
                byte []rawResp = reader.read(pipe.is);
                return rawResp;
            }
            public void interrupt() throws IOException {
                synchronized (os) {
                    pipe.os.write((byte)'!');
                    pipe.os.flush();
                }
            }
        }

        public void interrupt() throws IOException {
            pipe.interrupt();
        }

        public void connect(String deviceAddress) throws IOException {
            BtManager btManager = new BtManager();
            pipe = new Pipe(btManager.connect(deviceAddress));
            readWriteAsyncTask = new ReadWriteAsyncTask(this,  responseCallback);
            readWriteAsyncTask.execute();
        }

        public byte[] sendNreceive(byte[]data) throws IOException {
            return pipe.sendNreceive(data);
        }
    }