package com.ar.myfirstapp.elm;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.ar.myfirstapp.async.ReadWriteAsyncTask;
import com.ar.myfirstapp.bt.BtManager;
import com.ar.myfirstapp.obd2.ASCIIUtils;
import com.ar.myfirstapp.obd2.Command;

import java.io.ByteArrayOutputStream;
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
            private int state;
//
//            public void reset(int cnt){
//                this.count = count- cnt;
//                if(this.count <0)
//                    count = 0;
//            }
            public byte[] read() throws IOException {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                boolean eom = false;
                while (!eom) {
                    byte aByte = (byte) is.read();//Sometimes there may be delay in response hence wait
                    if (state == 0 && aByte == 13) {
                        state = 1;
                    } else if (state == 1 && (aByte == 10 || aByte == 13)) {
                        state = 2;
                    } else if (state == 2 && aByte == 62) {
                        state = 3;
                        eom = true;
                    } else {
                        state = 0;
                    }
                    bos.write(aByte);
                }
                //if(eom) reset(3);
                byte[] resp = bos.toByteArray();
                return resp;
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

            private Command sendNreceive(Command command, boolean readLineByLine) throws IOException {
                synchronized (os) {
                    pipe.os.write(command.getCmd());
                    pipe.os.flush();
                    Log.e("pip", "pushed "+ ASCIIUtils.toString(command.getCmd()));
                }
                if(readLineByLine){}
                else {
                    byte[] rawResp = read();
                    byte[] result = new byte[rawResp.length];
                    System.arraycopy(rawResp,0,result,0, result.length);
                    Log.e("pip", "received "+ ASCIIUtils.toString(result));
                    command.setRawResp(result);
                }
                return command;
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

        public void sendNreceive(Command command) throws IOException {
             pipe.sendNreceive(command, false).parse();
        }
    }