package com.ar.myfirstapp.bt;

import android.os.Handler;
import android.os.Message;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.utils.Constants;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public class DeviceResponseHandler extends Handler {

    public interface DeviceResponseListener {
        void onStateChanged(int state);

        void onConnected(String connectedDeviceName);

        void onWriteCommand(Command command);

        void onReadCommand(Command command);

        void onNotification(String notificationText);
    }

    private DeviceResponseListener deviceResponseListener;

    public void setDeviceResponseListener(DeviceResponseListener deviceResponseListener) {
        this.deviceResponseListener = deviceResponseListener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case DeviceManager.MESSAGE_TYPE.STATE_CHANGE:
                if (deviceResponseListener != null)
                    deviceResponseListener.onStateChanged(msg.arg1);
                break;
            case DeviceManager.MESSAGE_TYPE.WRITE:
                if (deviceResponseListener != null)
                    deviceResponseListener.onWriteCommand((Command) msg.obj);
                break;
            case DeviceManager.MESSAGE_TYPE.READ:
                if (deviceResponseListener != null) {
                    DeviceManager.CommandParcel parcel = (DeviceManager.CommandParcel) msg.obj;
                    deviceResponseListener.onReadCommand(parcel.getCommand());
                    if (parcel.getRequestResponseListener() != null)
                        parcel.getRequestResponseListener().onResponseReceived(parcel.getCommand());
                }
                break;
            case DeviceManager.MESSAGE_TYPE.DEVICE_NAME:
                String deviceName = msg.getData().getString(Constants.TAG_DEVICE_NAME);
                if (deviceResponseListener != null)
                    deviceResponseListener.onConnected(deviceName);
                break;
            case DeviceManager.MESSAGE_TYPE.NOTIFICATION:
                if (deviceResponseListener != null)
                    deviceResponseListener.onNotification(msg.getData().getString(Constants.TAG_TOAST));
                break;
        }

    }

    //private static final String TAG = "RESPONSE HANDLER";

    // private final Queue<Command> outQ = new ConcurrentLinkedQueue<Command>();
    //private Map<String, OBDView> viewMap = new HashMap<>();

    /*
    public void registerDisplay(OBDView view, String commandId) {
        viewMap.put(commandId, view);
    }

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
    public void add(Command command) {
        outQ.add(command);
    }
    */
}
