package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;

import java.io.IOException;


/**
 * Created by arunsoman on 04/03/17.
 */

public class Command {
    final String name;
    public final byte[] cmd;
    public final ResponseHandler response;

    public Command(String modeID, String id, String name, ResponseHandler response) {
        this.name = name;
        this.response = response;
        this.cmd = (modeID + " " + id).getBytes();
    }
    protected Command(byte[] cmd, ResponseHandler response){
        this.name = null;
        this.cmd = cmd;
        this.response = response;
    }

    String send(ELMConnector.Pipe pipe) throws IOException, BadResponseException {
        return pipe.sendNreceive(this);
    }
}
