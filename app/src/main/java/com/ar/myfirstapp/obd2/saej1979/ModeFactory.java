package com.ar.myfirstapp.obd2.saej1979;

import android.util.Log;

import com.ar.myfirstapp.obd2.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class ModeFactory {
    static private final Map<String, Mode> Modes = new HashMap<>();
    static {
        Modes.put("01", new Mode1());
        Modes.put("02", new Mode1());
        Modes.put("09", new Mode9());
    }
    public static String[] getSupportedModes() {
        return Modes.keySet().toArray(new String[Modes.size()]);
    }

    public static Command getCommand(String mode, String id) {
        String pid = id.toUpperCase();
        try {
            return Modes.get(mode).getCommand(pid);
        }catch (NullPointerException e){
            return null;
        }
    }

    public static Command[] getDiscoveryCommand(String str) {
        try{
            return Modes.get(str).getDiscoveryCommands();
        }catch (NullPointerException e){
            return null;
        }
    }

    public static Command[] getSupportedPidCommands(Command command) {
        if (command.getCommandType() == Command.CommandType.MODEX_DIS) {
            String ids[] = command.getResponse().split(" ");
            List<Command> commands = new ArrayList<>();
            String modeId = command.getCommandId();
            for (String id : ids) {
                Command thisCommand = getCommand(command.getCommandId(), id);
                if (thisCommand != null) {
                    commands.add(thisCommand);
                    Log.d("ModelFac", "could not find:" + id);
                }
            }
            return commands.toArray(new Command[commands.size()]);
        }
        return null;
    }
}
