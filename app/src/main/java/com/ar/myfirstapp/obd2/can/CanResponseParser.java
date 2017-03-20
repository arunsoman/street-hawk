package com.ar.myfirstapp.obd2.can;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
 * Created by arunsoman on 05/03/17.
 */

public class CanResponseParser extends Parser{

    @Override
    public void parse( Command command) {
        byte[] raw = command.getRawResp();
        //return null;
    }
}
