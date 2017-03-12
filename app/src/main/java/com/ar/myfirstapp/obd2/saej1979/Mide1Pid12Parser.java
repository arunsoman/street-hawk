    package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
 * Created by Arun Soman on 3/12/2017.
 */

public class Mide1Pid12Parser extends Parser {
    private final static String[] values ={
            "Upstream",
            "Downstream of catalytic converter",
            "From the outside atmosphere or off",
            "Pump commanded on for diagnostics",

};
    @Override
    public void parse(Command command) {
        byte[] rawResp = command.getRawResp();
        int index = getIndex(rawResp);
        try {
            command.setResult(values[index]);
        }catch (ArrayIndexOutOfBoundsException e){
            if(index>=34 && index <= 250)
                command.setResult("Reserved");
            else if(index>=251 && index <= 255)
                command.setResult("Not available for assignment (SAE J1939 special meaning)");
            else
                command.setResponseStatus(Command.ResponseStatus.Unknown);
        }
    }
    private int getIndex(byte[] rawResp){
        return 0;//TODO
    }
}