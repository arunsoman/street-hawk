package com.ar.myfirstapp.obd2.parser;

import com.ar.myfirstapp.obd2.Command;

import java.util.Arrays;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class BasicParserSet{

    public static final Parser okParser = new Parser() {
        @Override
        public void parse( Command command) {
            String str = validate(command);
            if(str == null)
                return;
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(str);
        }
    };

}
