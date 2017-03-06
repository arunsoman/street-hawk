package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979Response implements ResponseHandler {
    protected boolean status;
    protected int A;
    protected int B;
    protected int C;
    protected int D;

    public void parse(InputStream is)throws IOException {
        A = is.read();
        B = is.read();
        C = is.read();
        D = is.read();
    }
}
