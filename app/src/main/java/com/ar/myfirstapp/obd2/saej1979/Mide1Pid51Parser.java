package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/12/2017.
 */

public class Mide1Pid51Parser extends SaeJ1979ResponseParser {
    private final static String[] values ={
            "Not available",
            "Gasoline",
            "Methanol",
            "Ethanol",
            "Diesel",
            "LPG",
            "CNG",
            "Propane",
            "Electric",
            "Bifuel running Gasoline",
            "Bifuel running Methanol",
            "Bifuel running Ethanol",
            "Bifuel running LPG",
            "Bifuel running CNG",
            "Bifuel running Propane",
            "Bifuel running Electricity",
            "Bifuel running electric and combustion engine",
            "Hybrid gasoline",
            "Hybrid Ethanol",
            "Hybrid Diesel",
            "Hybrid Electric",
            "Hybrid running electric and combustion engine",
            "Hybrid Regenerative",
            "Bifuel running diesel",

    };

    public Mide1Pid51Parser(String delim) {
        super("41 51 ");
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
