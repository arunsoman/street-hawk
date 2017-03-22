package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.obd2.parser.Parser;

import java.util.Arrays;


/**
 * Created by arunsoman on 04/03/17.
 */

public class Command {
    private static final byte CR = (byte)'\r';

    public byte[] getCmd() {
        return cmd;
    }

    public static enum ResponseStatus{Unknown, Ok, UnSupportedReq, BadResponse, NoData, NO_DATA, UNABLE_TO_CONNECT, NetworkError};

    private String name;
    private String modeID;
    private String id;
    private byte[] cmd;
    private byte[] rawResp;
    private String result;
    private ResponseStatus responseStatus;
    private Parser parser;


    public Command(String modeID, String id, String name,  Parser parser) {
        this(populateCmd(modeID,id), parser);
        this.modeID = modeID;
        this.id = id;
        this.name = name;
        this.parser = parser;
    }

    public Command(byte[] cmd, Parser parser) {
        this.modeID = null;
        this.id = null;
        this.name = null;
        this.parser = parser;
        this.cmd = populateCmd(cmd);
    }

    public byte[] getRawResp() {
        return rawResp;
    }


    public void parse(){
        parser.parse(this);
    }

    private static byte[] populateCmd(String modeID, String id){
        StringBuilder sb = new StringBuilder().append(modeID).append(" ");
        if(id.endsWith("\r"))
            sb.append(id);
        else
            sb.append(id).append("\r");
        return sb.toString().getBytes();
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

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        String resp = (responseStatus != ResponseStatus.Ok) ?
                ("RAW: " + Arrays.toString(rawResp) + "\n" +" responseStatus:" + responseStatus.toString()) :
                ("Result: " + this.result + "\n");
        return "REQ:{ " + modeID + ", " + id + ", " + name + " bytes: " + Arrays.toString(cmd) + "}\n" + resp;

    }
    public void setRawResp(byte[] rawResp){this.rawResp = rawResp;}
    public void setResult(String result){
        this.result = result;
    }
}
