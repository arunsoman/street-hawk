package com.ar.myfirstapp.obd2.saej1979;

import android.util.Log;

import com.ar.myfirstapp.obd2.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class ModeFactory {

    public static String[] getSupportedModes() {
        return new String[]{"m1", "m9"};
    }

    public static Command getCommand(String mode, String id) {
        String pid = id.toUpperCase();
        switch (mode) {
            case "m1":
                return Mode1.getCommand(pid);
            case "m9":
                return Mode9.getCommand(pid);
            default:
                return null;
        }
    }

    public static Command getDiscoveryCommand(String str) {
        return getCommand(str, "00");
    }

    public static Command[] getSupportedPidCommands(Command command) {
        if (command.getCommandType() == Command.CommandType.MODEX_DIS) {
            String ids[] = command.getResponse().split(" ");
            List<Command> commands = new ArrayList<>();
            String modeId = command.getCommandId();
            switch (modeId) {
                case "01":
                    for (int i = 0; i < ids.length; i++) {
                        Command thisCommand = Mode1.getCommand(ids[i]);
                        if (thisCommand != null) {
                            commands.add(thisCommand);
                            Log.d("ModelFac", "could not find:" + ids[i]);
                        }
                    }
                    return commands.toArray(new Command[commands.size()]);
                case "09":
                    for (int i = 0; i < ids.length; i++) {
                        Command thisCommand = Mode9.getCommand(ids[i]);
                        if (thisCommand != null) {
                            commands.add(thisCommand);
                            Log.d("ModelFac", "could not find:" + ids[i]);
                        }
                    }
                    return commands.toArray(new Command[commands.size()]);
            }
        }
        return null;
    }
}
