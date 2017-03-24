package com.ar.myfirstapp.obd2.parser;

import com.ar.myfirstapp.obd2.ASCIIUtils;
import com.ar.myfirstapp.obd2.Command;

public abstract class Parser {
    protected String validate(Command command){
        String str = ASCIIUtils.toString(command.getRawResp());
        str = str.replace("SEARCHING...","");
        str = str.replace("\r\n>...","");
        str = str.replace("\r>...","");

        if(str.contains("NO DATA")){
            command.setResponseStatus(Command.ResponseStatus.NO_DATA);
            return null;
        }
        if(str.contains("UNABLE TO CONNECT")){
            command.setResponseStatus(Command.ResponseStatus.UNABLE_TO_CONNECT);
            return null;
        }
//TODO fix ?
//
//        if(str.contains("?")){
//            if(str.replace( " ", "").replace("?","").trim().length() == 0){
//                command.setResponseStatus(Command.ResponseStatus.UnSupportedReq);
//                return null;
//            }
//        }
        return str;
    }

    public abstract void parse( Command command);
}
