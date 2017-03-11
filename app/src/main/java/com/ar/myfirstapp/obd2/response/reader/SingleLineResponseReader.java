package com.ar.myfirstapp.obd2.response.reader;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class SingleLineResponseReader extends ResponseReader {
    private String eom = String.valueOf((char)((byte)('>')));




    @Override
    public Command.ResponseStatus setResponse(Command command) {
        String[] result;
        String tmp = resp;
        if(resp.endsWith(",13,13,62,")||resp.endsWith(",13,13,62,")){
            tmp = tmp.substring(0, resp.length()-10);
        }
        if(tmp.matches("13,13,"))
            result= tmp.split("13,13,");
        else
            result= tmp.split("13,10,");
        result[result.length-1] = result[result.length-1].replace(eom,"");
        command.setResponse( result);
        return Command.ResponseStatus.Ok;
    }
}
