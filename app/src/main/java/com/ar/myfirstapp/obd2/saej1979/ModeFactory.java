package com.ar.myfirstapp.obd2.saej1979;

import android.util.Log;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class ModeFactory {

    public static String[] getSupportedModes(){
        return new String[]{"m1","m9"};
    }
    public static Command getCommand(String mode, String id){
        String pid = id.toUpperCase();
        switch (mode){
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

    public static Command[] getSupportedPidCommands(Command command){
        if(command.getCommandType() == Command.CommandType.MODEX_DIS){
            String ids[] = command.getResponse().split(" ");
            Command[] commands = new Command[ids.length];
            String modeId = command.getCommandId();
            switch (modeId){
                case "01":
                    for (int i = 0; i < ids.length; i++){
                        commands[i] = Mode1.getCommand(ids[i]);
                        if(commands[i] == null)
                        Log.d("ModelFac","could not find:"+ids[i]);
                    }
                    return commands;
                case "09":

                    for (int i = 0; i < ids.length; i++){
                        commands[i] = Mode9.getCommand(ids[i]);
                        if(commands[i] == null)
                        Log.d("ModelFac","could not find:"+ids[i]);
                    }
                    return commands;
            }
        }
        return null;
    }
}
