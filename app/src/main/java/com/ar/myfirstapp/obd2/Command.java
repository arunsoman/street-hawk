package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.response.reader.ResponseReader;
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
    protected final byte[] cmd;
    private String rawResp;
    private String[] responses;
    private String[] result;
    private ResponseStatus responseStatus;
    private ResponseReader responseReader;
    private Parser parser;

    public void setResponse(String[] response) {
        this.responses = response;
    }

    public enum ResponseStatus{Unknown, Ok, UnSupportedReq, BadResponse, NoData, NetworkError};

    public Command(String s, String s1, String s2, SaeJ1979ResponseParser saeJ1979ResponseParser) {
        modeID = s;
        id = s1;
        name = "";
        this.cmd = populateCmd(s, s1);
        responseReader = ResponseReader.Readers.single.getResponseReader();
    }


    public ResponseReader getReader() {
        return responseReader;
    }
    public Parser getParser(){
        return parser;
    }

    public Command(String modeID, String id, String name, ResponseReader responseReader, Parser parser) {
        this.modeID = modeID;
        this.id = id;
        this.name = name;
        this.responseReader = responseReader;
        this.cmd = populateCmd(modeID,id);
        this.parser = parser;
    }

    private byte[] populateCmd(String modeID, String id){
        String str = modeID + " " + id;
        if(!str.endsWith("\r"))
            str = modeID + " " + id+'\r';
        return (str).getBytes();
    }
    protected Command(byte[] cmd, ResponseReader responseReader, Parser parser) {
        this.responseReader = responseReader;
        this.modeID = null;
        this.id = null;
        this.name = null;
        if (cmd[cmd.length] != CR) {
            byte[] tmp = new byte[cmd.length + 1];
            System.arraycopy(cmd, 9, tmp, 0, cmd.length);
            tmp[tmp.length] = CR;
            this.cmd = tmp;
        } else {
            this.cmd = cmd;
        }
        this.parser = parser;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
    @Override
    public String toString() {
        return "REQ:{ " + modeID + ", " + id + ", " + name + "}\n"+
                "RAW: "+rawResp + "\n"+
                "RES: "+Arrays.toString(responses) + "\n"+
                "Result: "+ Arrays.toString(this.result)+"\n";
    }

    public void setResult(String[] result){
        this.result = result;
    }

    public void sendNreceive(ELMConnector.Pipe pipe) throws IOException {
        synchronized (pipe.os) {
            pipe.os.write(cmd);
            pipe.os.flush();
        }
            this.responseStatus = responseReader.read(pipe);
            if(responseStatus == ResponseStatus.Ok) {
                this.rawResp = responseReader.toString();
                this.responseStatus = responseReader.setResponse(this);
                if (responseStatus == ResponseStatus.Ok) {
                    this.responseStatus = parser.parse(responses, this);
                }
            }
     }
}
