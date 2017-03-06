    package com.ar.myfirstapp.elm;

    import android.bluetooth.BluetoothSocket;

    import com.ar.myfirstapp.bt.BtManager;
    import com.ar.myfirstapp.obd2.BadResponseException;
    import com.ar.myfirstapp.obd2.Command;
    import com.ar.myfirstapp.obd2.UnknownCommandException;
    import com.ar.myfirstapp.obd2.at.AtCommands;
    import com.ar.myfirstapp.obd2.saej1979.Mode1;

    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;

    /**
     * Created by Arun Soman on 2/28/2017.
     */

    public final class ELMConnector implements AutoCloseable {

        private BluetoothSocket bluetoothSocket;
        private Pipe pipe;
        @Override
        public void close() throws Exception {
            pipe.close();
            bluetoothSocket.close();
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

            public String sendNreceive(Command command) throws IOException, BadResponseException, UnknownCommandException {
                synchronized (pipe.os) {
                    os.write(command.cmd);
                    os.flush();
                }
                command.response.parse(is);
                return command.response.getResult();
            }
        }
        public void interrupt() throws IOException {
            synchronized (pipe.os) {
                pipe.os.write((byte) '!');
                pipe.os.flush();
            }
        }

        public void connect(String deviceAddress) throws IOException {
            BtManager btManager = new BtManager();
            pipe = new Pipe(btManager.connect(deviceAddress));

        }
        public String sendNreceive(Command command) throws IOException, BadResponseException, UnknownCommandException {
            return pipe.sendNreceive(command);
        }

    }