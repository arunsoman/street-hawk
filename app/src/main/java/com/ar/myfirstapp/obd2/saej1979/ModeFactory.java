package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class ModeFactory {
    public static String[] getSupportedPids(Command command){
        String str = command.getResult();
        return str.split(" ");
    }
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
}
