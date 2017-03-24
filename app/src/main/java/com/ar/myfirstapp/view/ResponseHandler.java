package com.ar.myfirstapp.view;

import android.os.Handler;
import android.os.Message;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.obd2.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public class ResponseHandler extends Handler {
    private final Queue<Command> outQ = new ConcurrentLinkedQueue<Command>();
    private MainActivity mainActivity;
    private Map<String, OBDView> viewMap = new HashMap<>();

    public ResponseHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void registerDissplay(OBDView view, String commandId){
        viewMap.put(commandId, view);
    }
    @Override
    public void handleMessage(Message msg) {
        if(!outQ.isEmpty()){
            Command c = outQ.remove();
            String cId = c.getCommandId();
            OBDView view = viewMap.get(cId);
            if(view == null)
                view = viewMap.get("*");
            if(view != null)
                view.display(c);
        }
    //    tvLog.display(msg.getData().getString("cmd"));
        super.handleMessage(msg);
    }

    public void add(Command command) {
        outQ.add(command);
    }
}
