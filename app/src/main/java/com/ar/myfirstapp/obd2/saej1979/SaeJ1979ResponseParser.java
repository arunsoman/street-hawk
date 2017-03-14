package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.ASCIIUtils;
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

        String str = ASCIIUtils.toString(command.getRawResp());
        String str1 = str.substring(str.indexOf("00")+3, str.length());
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
