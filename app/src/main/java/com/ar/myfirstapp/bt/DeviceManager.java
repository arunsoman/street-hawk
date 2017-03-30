package com.ar.myfirstapp.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ar.myfirstapp.view.MainActivity;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.utils.Constants;
import com.ar.myfirstapp.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class DeviceManager {
    private static final String TAG = "DeviceManager";

    private final BluetoothAdapter bluetoothAdapter;
    private Handler handler;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private int currentState, newState;

    public static final class BLUETOOTH_STATE {
        public static final int NONE = 0;       // Initialization state.
        public static final int CONNECTING = 1; // Attempting an outgoing connection
        public static final int CONNECTED = 2;  // Active in connection with a remote device
    }

    public static final class MESSAGE_TYPE {
        public static final int STATE_CHANGE = 1;
        public static final int READ = 2;
        public static final int WRITE = 3;
        public static final int DEVICE_NAME = 4;
        public static final int NOTIFICATION = 5;
    }

    /**
     * Constructor. Prepares a new DeviceManager instance
     */
    private DeviceManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        newState = currentState = BLUETOOTH_STATE.NONE;
    }

    /**
     * Instance loader
     */
    private static class InstanceLoader {
        static DeviceManager INSTANCE = new DeviceManager();
    }

    public static DeviceManager getInstance() {
        return InstanceLoader.INSTANCE;
    }

    /**
     * @param handler A Handler to send messages back to the UI Activity
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Return the active bluetooth adapter
     */
    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    /**
     * Return the state of active connection
     */
    private synchronized int getCurrentState() {
        return currentState;
    }


    /**
     * Send response to UI when state changes
     */
    private synchronized void onStateChange() {
        currentState = getCurrentState();
        if (newState != currentState)
            Log.d(TAG, "#onStateChange() " + newState + " -> " + currentState);

        newState = currentState;
        handler.obtainMessage(MESSAGE_TYPE.STATE_CHANGE, newState, -1).sendToTarget();
    }


    /**
     * Initialize
     */
    public synchronized void initialize() {
        Log.d(TAG, "#initialize()");

        // Cancel any thread attempting to make a connection
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        // Cancel any thread currently running a connection
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (handler == null) {
            Logger.e(TAG, "#initialize() -> Handler not set before initialize()");
            return;
        }

        // Update UI title
        onStateChange();
    }

    /**
     * Initiate the connection
     *
     * @param device The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        Log.d(TAG, "#connect() to: " + device);

        // Cancel any thread attempting to make a connection
        if (currentState == BLUETOOTH_STATE.CONNECTING) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        // Start the thread to connect with the given device
        connectThread = new ConnectThread(device);
        connectThread.start();
        // Update UI title
        onStateChange();
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MainActivity.UUID));
            } catch (IOException e) {
                Logger.e(TAG, "ConnectThread#ConnectThread() createRfcommSocketToServiceRecord Excpetion", e);
            }
            mmSocket = tmp;
            currentState = BLUETOOTH_STATE.CONNECTING;
        }

        public void run() {
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            bluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "ConnectThread#run() close socket failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (DeviceManager.this) {
                connectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread#cancel() of connect socket failed", e);
            }
        }
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    private synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.d(TAG, "connected, Socket");

        // Cancel the thread that completed the connection
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        // Cancel any thread currently running a connection
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = handler.obtainMessage(MESSAGE_TYPE.DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_DEVICE_NAME, device.getName());
        msg.setData(bundle);
        handler.sendMessage(msg);

        onStateChange();
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param OBDII The name of device
     */
    public String getELM327Address(String OBDII) {
        Set<BluetoothDevice> pairedDevices = getBluetoothAdapter().getBondedDevices();
        String deviceAddress = null;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(OBDII)) {
                    deviceAddress = device.getAddress();
                    break;
                }
            }
        }
        return deviceAddress;
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param command The command to write
     * @see ConnectedThread#send(Command)
     */
    public void send(Command command) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            //if (currentState != BLUETOOTH_STATE.CONNECTED) return;
            sendToQueue(command);
        }
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        Log.d(TAG, "stop");

        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        currentState = BLUETOOTH_STATE.NONE;
        // Update UI title
        onStateChange();
    }


    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        // Send a failure message back to the Activity
        Message msg = handler.obtainMessage(MESSAGE_TYPE.NOTIFICATION);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_TOAST, "Unable to connect device");
        msg.setData(bundle);
        handler.sendMessage(msg);

        currentState = BLUETOOTH_STATE.NONE;
        // Update UI title
        onStateChange();

        // Start the service over to restart listening mode
        DeviceManager.this.initialize();
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = handler.obtainMessage(MESSAGE_TYPE.NOTIFICATION);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_TOAST, "Device connection was lost");
        msg.setData(bundle);
        handler.sendMessage(msg);

        currentState = BLUETOOTH_STATE.NONE;
        // Update UI title
        onStateChange();

        // Start the service over to restart listening mode
        DeviceManager.this.initialize();
    }

    private final Queue<Command> inQ = new ConcurrentLinkedQueue<>();

    void sendToQueue(final Command c) {
        synchronized (inQ) {
            inQ.add(c);
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {

        private final class Pipe extends ByteArrayOutputStream implements AutoCloseable {
            final InputStream is;
            final OutputStream os;
            private int state;

            void reset(int cnt) {
                this.count = count - cnt;
                if (this.count < 0)
                    count = 0;
            }

            byte[] readAll() throws IOException {
                reset();
                boolean eom = false;
                while (!eom) {
                    byte aByte = (byte) is.read();
                    //Sometimes there may be delay in response hence wait
                    if (state == 0 && aByte == 13) {
                        state = 1;
                    } else if (state == 1 && (aByte == 10 || aByte == 13)) {
                        state = 2;
                        //Log.e("pip", "line "+ ASCIIUtils.toString(toByteArray()));
                    } else if (state == 2 && aByte == 62) {
                        state = 3;
                        eom = true;
                    } else {
                        state = 0;
                    }
                    /*
                    if (interrupt) {
                        writeOs("!".getBytes());
                        interrupt = false;
                    }
                    */
                    write(aByte);
                }
                reset(3);
                return toByteArray();
            }

            Pipe(InputStream is, OutputStream os) throws IOException {
                this.is = is;
                this.os = os;
            }

            @Override
            public void close() throws IOException {
                is.close();
                os.close();
            }

            private Command sendRequestForResponse(Command command) throws IOException {
                writeOs(command.getRequest());
                handler.obtainMessage(MESSAGE_TYPE.WRITE, -1, -1, command).sendToTarget();
                Logger.e(TAG, "Request Sent :" + command.toString());

                int avail = is.available();
                if (avail == 0) {
                    synchronized (is) {
                        try {
                            is.wait(Constants.ELMWaitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    if ((is.available()) == 0) {
                        command.setRawResp(null);
                        command.setResponseStatus(Command.ResponseStatus.NO_DATA);
                    } else {
                        byte[] rawResp = readAll();
                        command.setRawResp(rawResp);
                    }
                } else {
                    byte[] rawResp = readAll();
                    command.setRawResp(rawResp);
                }
                handler.obtainMessage(MESSAGE_TYPE.READ, -1, -1, command).sendToTarget();
                return command;
            }

            private void writeOs(byte[] data) throws IOException {
                synchronized (os) {
                    os.write(data);
                    os.flush();
                }
            }
        }

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Logger.e(TAG, "ConnectedThread#", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            currentState = BLUETOOTH_STATE.CONNECTED;
        }

        public void run() {
            try (Pipe pipe = new Pipe(mmInStream, mmOutStream)) {
                while (currentState == BLUETOOTH_STATE.CONNECTED) {
                    Command command;
                    synchronized (inQ) {
                        if (inQ.size() == 0)
                            continue;
                        command = inQ.remove();
                    }
                    try {
                        pipe.sendRequestForResponse(command);
                        command.parse();
                    } catch (IOException exception) {
                        command.setResponseStatus(Command.ResponseStatus.NetworkError);
                        connectionLost();
                    }
                    Log.e(TAG, "Response Received : " + command.toString());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Logger.e(TAG, "ConnectedThread#close() failed", e);
            }
        }
    }
}
