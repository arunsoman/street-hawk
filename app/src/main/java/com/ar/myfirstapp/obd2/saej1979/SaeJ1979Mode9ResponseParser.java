package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979Mode9ResponseParser extends Parser {
    final String headerDelim;
    protected String respString;
    protected int argLen;

    protected SaeJ1979Mode9ResponseParser(String headerDelim) {
        this.headerDelim = headerDelim;
    }

 @Override
    protected String validate(Command command){
     String str = super.validate(command);
     if(str == null)
         return null;

     if(!str.contains(headerDelim)) {
        command.setResponseStatus(Command.ResponseStatus.Unknown);
        return null;
    }

    String str1 = str.substring(str.indexOf(headerDelim) + headerDelim.length(), str.length());
        return str1;
}

    @Override
    public abstract void parse(Command command);
}
