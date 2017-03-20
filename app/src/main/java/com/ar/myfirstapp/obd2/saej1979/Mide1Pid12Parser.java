    package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

    /**
 * Created by Arun Soman on 3/12/2017.
 */

public class Mide1Pid12Parser extends SaeJ1979ResponseParser {
    private final static String[] values ={
            "Upstream",
            "Downstream of catalytic converter",
            "From the outside atmosphere or off",
            "Pump commanded on for diagnostics",

};

    public Mide1Pid12Parser() {
        super("41 12 ");
    }

    @Override
    public void setResult(Command command, int argLen) {
        int index = A;//getIndex(getRespValue(str));
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
    private int getIndex(String rawResp){
        return 0;//TODO
    }
}
