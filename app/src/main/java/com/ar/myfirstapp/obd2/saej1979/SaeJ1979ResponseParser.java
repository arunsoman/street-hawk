package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.ASCIIUtils;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979ResponseParser extends Parser {
    int A;
    int B;
    int C;
    int D;
    final String headerDelim;

    protected SaeJ1979ResponseParser(String headerDelim) {
        this.headerDelim = headerDelim;
    }

    protected String validate(Command command){
        String str = ASCIIUtils.toString(command.getRawResp());
        str = str.replace("SEARCHING...","");
        str = str.replace("\r\n>...","");
        str = str.replace("\r>...","");

        if(str.contains("NO DATA")){
            command.setResponseStatus(Command.ResponseStatus.NO_DATA);
            return null;
        }
        if(str.contains("?")){
            if(str.replace( " ", "").replace("?","").trim().length() == 0){
                command.setResponseStatus(Command.ResponseStatus.UnSupportedReq);
                return null;
            }
        }
        if(!str.contains(headerDelim)){
            command.setResponseStatus(Command.ResponseStatus.Unknown);
            return null;
        }
        String str1 = str.substring(str.indexOf(headerDelim)+headerDelim.length(), str.length());
        return  str1;
    }

    @Override
    public void parse(Command command) {
        String str1 = validate(command);
        if(str1 == null)
            return;
        String splits[] = str1.split(" ");
        int len = splits.length;
        if(len >0) {
            A = (int) Long.parseLong(splits[0], 16);
            if(len> 1){
                B = (int) Long.parseLong(splits[1], 16);
                if(len >2 ) {
                    C = (int) Long.parseLong(splits[2], 16);
                    if (len > 3) {
                        D = (int) Long.parseLong(splits[3], 16);
                    }
                }
            }
        }
        setResult(command, len);
    }

    public abstract void setResult(Command command, int argLen);
}
