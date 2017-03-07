    package com.ar.myfirstapp.elm;

    import android.bluetooth.BluetoothSocket;

    import com.ar.myfirstapp.MainActivity;
    import com.ar.myfirstapp.async.ReaderWriter;
    import com.ar.myfirstapp.bt.BtManager;
    import com.ar.myfirstapp.obd2.BadResponseException;
    import com.ar.myfirstapp.obd2.Command;
    import com.ar.myfirstapp.obd2.LineReader;
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
        private ReaderWriter readerWriter;

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
                MainActivity.tvLog.append("\nREQ: " + LineReader.toString(command.cmd));
                command.response.parse(is);
                return command.response.getResult();
            }
        }
        public void interrupt() throws IOException {

        }

        public void connect(String deviceAddress) throws IOException {
            BtManager btManager = new BtManager();
            pipe = new Pipe(btManager.connect(deviceAddress));
            readerWriter = new ReaderWriter(pipe);
            readerWriter.execute();
        }
        public void sendNreceive(Command command) throws IOException, BadResponseException, UnknownCommandException {
            readerWriter.submit(command);
        }

    }