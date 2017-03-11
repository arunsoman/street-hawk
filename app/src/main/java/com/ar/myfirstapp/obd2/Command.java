package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.response.ResponseReader;
import com.ar.myfirstapp.obd2.saej1979.SaeJ1979ResponseParser;

import java.io.IOException;
import java.util.Arrays;


/**
 * Created by arunsoman on 04/03/17.
 */

public class Command {
    public static final byte CR = (byte)'\r';

    final String name;
    final String modeID;
    final String id;
    public final byte[] cmd;
    private byte[] rawResp;
    private String result;
    private ResponseStatus responseStatus;
    private Parser parser;

    public byte[] getRawResp() {
        return rawResp;
    }

    public enum ResponseStatus{Unknown, Ok, UnSupportedReq, BadResponse, NoData, NetworkError};

    public Command(String s, String s1, String s2, SaeJ1979ResponseParser saeJ1979ResponseParser) {
        modeID = s;
        id = s1;
        name = "";
        this.cmd = populateCmd(s, s1);
    }
    public Parser getParser(){
        return parser;
    }

    public Command(String modeID, String id, String name,  Parser parser) {
        this.modeID = modeID;
        this.id = id;
        this.name = name;
        this.cmd = populateCmd(modeID,id);
        this.parser = parser;
    }

    private byte[] populateCmd(String modeID, String id){
        String str = modeID + " " + id;
        if(!str.endsWith("\r"))
            str = modeID + " " + id+'\r';
        return (str).getBytes();
    }
    private byte[] populateCmd(byte[] cmd) {
       byte[] res;
        if (cmd[cmd.length-1] != CR) {
            byte[] tmp = new byte[cmd.length + 1];
            System.arraycopy(cmd, 9, tmp, 0, cmd.length);
            tmp[tmp.length] = CR;
            res = tmp;
        } else {
            res = cmd;
        }
        return res;
    }
    public Command(byte[] cmd, Parser parser) {
        this.modeID = null;
        this.id = null;
        this.name = null;
        this.parser = parser;
        this.cmd = populateCmd(cmd);
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
    @Override
    public String toString() {
        return "REQ:{ " + modeID + ", " + id + ", " + name + "}\n"+
                "RAW: "+Arrays.toString(rawResp) + "\n"+
                "Result: "+ this.result +"\n";
    }
    public void setRawResp(byte[] rawResp){this.rawResp = rawResp;}
    public void setResult(String result){
        this.result = result;
    }
    private ResponseReader reader = new ResponseReader();


}
