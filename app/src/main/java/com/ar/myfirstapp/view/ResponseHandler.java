package com.ar.myfirstapp.view;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.utils.Constants;
import com.ar.myfirstapp.view.custom.OBDView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public class ResponseHandler extends Handler {
    private final Queue<Command> outQ = new ConcurrentLinkedQueue<Command>();
    private Map<String, OBDView> viewMap = new HashMap<>();

    private final WeakReference<MainActivity> activityReference;


    public ResponseHandler(MainActivity activityReference) {
        this.activityReference = new WeakReference<>(activityReference);
    }

    public void registerDisplay(OBDView view, String commandId) {
        viewMap.put(commandId, view);
    }

    /*
    @Override
    public void handleMessage(Message msg) {
        MainActivity activity = activityReference.get();
        if (activity != null) {
            if (!outQ.isEmpty()) {
                Command c = outQ.remove();
                String cId = c.getCommandId();
                OBDView view = viewMap.get(cId);
                if (view == null)
                    view = viewMap.get("*");
                if (view != null) {
                    view.display(c);
                    activity.show(c);
                }
            }
            //    tvLog.display(msg.getData().getString("cmd"));
        }
    }
    */
    @Override
    public void handleMessage(Message msg) {
        MainActivity activity = activityReference.get();

        switch (msg.what) {
            case DeviceService.MESSAGE_TYPE.STATE_CHANGE:
                switch (msg.arg1) {
                    case DeviceService.BLUETOOTH_STATE.CONNECTED:
                        //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                        //mConversationArrayAdapter.clear();
                        break;
                    case DeviceService.BLUETOOTH_STATE.CONNECTING:
                        //setStatus(R.string.title_connecting);
                        break;
                    case DeviceService.BLUETOOTH_STATE.NONE:
                        //setStatus(R.string.title_not_connected);
                        break;
                }
                break;
            case DeviceService.MESSAGE_TYPE.WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                //mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case DeviceService.MESSAGE_TYPE.READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                break;
            case DeviceService.MESSAGE_TYPE.DEVICE_NAME:
                // save the connected device's name
                //mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                //Toast.makeText(this, "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case DeviceService.MESSAGE_TYPE.TOAST:
                Toast.makeText(activity, msg.getData().getString(Constants.TAG_TOAST), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void add(Command command) {
        outQ.add(command);
    }
}
