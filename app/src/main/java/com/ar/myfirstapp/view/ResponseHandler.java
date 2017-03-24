package com.ar.myfirstapp.view;

import android.os.Handler;
import android.os.Message;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.obd2.Command;

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
}
