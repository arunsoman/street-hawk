package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;

import java.io.IOException;


/**
 * Created by arunsoman on 04/03/17.
 */

public class Command {
    final String name;
    final String modeID;
    final String id;
    public final byte[] cmd;
    public final ResponseHandler response;

    public Command(String modeID, String id, String name, ResponseHandler response) {
        this.modeID = modeID;
        this.id = id;
        this.name = name;
        this.response = response;
        this.cmd = (modeID + " " + id).getBytes();
    }
    protected Command(byte[] cmd, ResponseHandler response){
        this.modeID = null;
        this.id = null;
        this.name = null;
        this.cmd = cmd;
        this.response = response;
    }

    @Override
    public String toString() {

        return "Command:{type: " + modeID + " id: " + id + ", name'" + name + "}\n"+
                "Response:{status: "+response.getStatus() + ", data: " + response.getData()+"}\n"+
                "Result: "+ response.getResult();
    }
}
