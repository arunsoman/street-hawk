package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class SaeJ1979Response extends AbstractResponseHandler {
    protected boolean status;
    protected int A;
    protected int B;
    protected int C;
    protected int D;

    public void parse(){
    }
}
