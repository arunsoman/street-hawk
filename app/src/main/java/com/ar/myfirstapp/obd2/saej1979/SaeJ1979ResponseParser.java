package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979ResponseParser extends Parser {
    protected int A;
    protected int B;
    protected int C;
    protected int D;

    @Override
    public Command.ResponseStatus parse(String[] rawData, Command command) {
        String str = rawData[0];
        command.setResult(new String[]{getResult()});
        return null;
    }

    public abstract String getResult();
}
