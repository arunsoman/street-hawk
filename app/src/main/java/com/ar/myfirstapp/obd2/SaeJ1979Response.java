package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979Response implements ResponseHandler {
    private boolean status;
    private int a;
    private int b;
    private int c;
    private int d;

    public void parse(InputStream is)throws IOException {
        a = is.read();
        b = is.read();
        c = is.read();
        d = is.read();
    }
}
