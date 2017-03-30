package com.ar.myfirstapp.view;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ar.myfirstapp.bt.DeviceManager;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;
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
    private static final String TAG = "RESPONSE HANDLER";

    private final Queue<Command> outQ = new ConcurrentLinkedQueue<Command>();
    private Map<String, OBDView> viewMap = new HashMap<>();

    private final WeakReference<MainActivity> activityReference;


    public ResponseHandler(MainActivity activityReference) {
        this.activityReference = new WeakReference<>(activityReference);
    }

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
    */
    public void add(Command command) {
        outQ.add(command);
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity activity = activityReference.get();
        switch (msg.what) {
            case DeviceManager.MESSAGE_TYPE.STATE_CHANGE:
                switch (msg.arg1) {
                    case DeviceManager.BLUETOOTH_STATE.CONNECTED:
                        break;
                    case DeviceManager.BLUETOOTH_STATE.CONNECTING:
                        Toast.makeText(activity, "Connecting", Toast.LENGTH_SHORT).show();
                        break;
                    case DeviceManager.BLUETOOTH_STATE.NONE:
                        break;
                }
                break;
            case DeviceManager.MESSAGE_TYPE.WRITE:
                //Logger.d(TAG, "Sent " + (Command) msg.obj);
                break;
            case DeviceManager.MESSAGE_TYPE.READ:
                final Command command = (Command) msg.obj;
                if (command.getCommandType() == Command.CommandType.MODEX_DIS) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Command[] commands = ModeFactory.getSupportedPidCommands(command);
                            if (commands != null) {
                                for (Command c : commands)
                                    if (c != null)
                                        DeviceManager.getInstance().send(c);
                            }
                        }
                    });
                }
                activity.show(command);
                //Logger.d(TAG, "Received " + (Command) msg.obj);
                break;
            case DeviceManager.MESSAGE_TYPE.DEVICE_NAME:
                String deviceName = msg.getData().getString(Constants.TAG_DEVICE_NAME);
                Toast.makeText(activity, "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
                break;
            case DeviceManager.MESSAGE_TYPE.NOTIFICATION:
                Toast.makeText(activity, msg.getData().getString(Constants.TAG_TOAST), Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
