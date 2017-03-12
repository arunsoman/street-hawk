package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
 * Created by Arun Soman on 3/12/2017.
 */

public class Mide1Pid1CParser extends Parser {
    private final static String[] values ={
            "OBD-II as defined by the CARB",
            "OBD as defined by the EPA",
            "OBD and OBD-II",
            "OBD-I",
            "Not OBD compliant",
            "EOBD (Europe)",
            "EOBD and OBD-II",
            "EOBD and OBD",
            "EOBD, OBD and OBD II",
            "JOBD (Japan)",
            "JOBD and OBD II",
            "JOBD and EOBD",
            "JOBD, EOBD, and OBD II",
            "Reserved",
            "Reserved",
            "Reserved",
            "Engine Manufacturer Diagnostics (EMD)",
            "Engine Manufacturer Diagnostics Enhanced (EMD+)",
            "Heavy Duty On-Board Diagnostics (Child/Partial) (HD OBD-C)",
            "Heavy Duty On-Board Diagnostics (HD OBD)",
            "World Wide Harmonized OBD (WWH OBD)",
            "Reserved",
            "Heavy Duty Euro OBD Stage I without NOx control (HD EOBD-I)",
            "Heavy Duty Euro OBD Stage I with NOx control (HD EOBD-I N)",
            "Heavy Duty Euro OBD Stage II without NOx control (HD EOBD-II)",
            "Heavy Duty Euro OBD Stage II with NOx control (HD EOBD-II N)",
            "Reserved",
            "Brazil OBD Phase 1 (OBDBr-1)",
            "Brazil OBD Phase 2 (OBDBr-2)",
            "Korean OBD (KOBD)",
            "India OBD I (IOBD I)",
            "India OBD II (IOBD II)",
            "Heavy Duty Euro OBD Stage VI (HD EOBD-IV)",
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
