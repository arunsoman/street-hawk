package com.ar.myfirstapp.obd2.parser;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.response.Response;

import java.sql.Array;
import java.util.Arrays;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public abstract class Parser extends Response {
    protected String[] result;

    public String toString(){ return Arrays.toString(result);}
    public abstract Command.ResponseStatus parse( String [] rawData, Command command);
}
