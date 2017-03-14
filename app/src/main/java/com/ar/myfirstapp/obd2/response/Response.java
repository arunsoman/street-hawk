package com.ar.myfirstapp.obd2.response;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class Response {
    protected String[] respList;

    public void set(Command.ResponseStatus status, String[] responses){
        this.respList = responses;
    }
}
