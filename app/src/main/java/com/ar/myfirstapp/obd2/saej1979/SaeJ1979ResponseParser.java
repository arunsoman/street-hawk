package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979ResponseParser extends Parser {
    protected int A;
    protected int B;
    protected int C;
    protected int D;

    @Override
    public void parse(Command command) {
        //String str = rawData[0];
        command.setResult(getResult());
    }

    public abstract String getResult();
}
