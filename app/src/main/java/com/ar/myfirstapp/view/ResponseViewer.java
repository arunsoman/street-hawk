package com.ar.myfirstapp.view;

import android.os.Handler;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;
import com.ar.myfirstapp.view.custom.OBDView;

/**
 * Created by Arun Soman on 3/22/2017.
 */

public class ResponseViewer implements OBDView {
    public String display(final Command command) {
        if (command.getCommandType() == Command.CommandType.MODEX_DIS) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Command[] cmds = ModeFactory.getSupportedPidCommands(command);
                    for (Command c : cmds)
                        if (c != null)
                            DeviceService.getInstance().send(c);
                }
            });
        }
        return (command.toString());
    }
}
