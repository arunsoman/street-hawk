package com.ar.myfirstapp.obd2;

import com.ar.myfirstapp.obd2.parser.Parser;

import java.util.Arrays;


public class Command {
    public CommandType getCommandType() {
        return commandType;
    }

    public String getPid() {
        return id;
    }

    public enum ResponseStatus{Unknown, Ok, UnSupportedReq, BadResponse, NoData, NO_DATA, UNABLE_TO_CONNECT, NetworkError};
    public enum CommandType{Unknown, AT, MODEX, MODEX_DIS};

    public byte[] getRequest() {
        return cmd;
    }

    public String getResponse() {
        return result;
    }

    private final byte[] empty = new byte[0];
    private String name;

    public String getCommandId() {
        return commandId;
    }

    private String commandId;
    private String id;
    private byte[] cmd = empty;
    private byte[] rawResp = empty;
    private String result = "";
    private ResponseStatus responseStatus = ResponseStatus.Unknown;
    private Parser parser;
    protected CommandType commandType = CommandType.Unknown;


    private void populateReq(String mode, String id){
        commandId = mode.trim();
        this.id = id.trim();
        cmd = commandId.concat(" ").concat(this.id).concat("\r").getBytes();
    }
    public Command(String modeID, String id, String name,  boolean discovery, Parser parser) {
        this.name = name;
        this.parser = parser;
        populateReq(modeID, id);
        if(discovery)
            commandType = CommandType.MODEX_DIS;
    }
    public Command(String modeID, String id, String name,  Parser parser) {
        this.name = name;
        this.parser = parser;
        populateReq(modeID, id);
    }

    public Command(byte[] cmd, Parser parser) {
        this.commandId = null;
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

    private final static byte CR = (byte)('\r');
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
        String resp = (responseStatus== null || responseStatus != ResponseStatus.Ok) ?
                ("RAW: " + Arrays.toString(rawResp) + "\n" + " responseStatus:" + responseStatus.toString()) :
                ("Result: " + this.result + "\n");
        return "REQ:{ m:" + commandId + ", p:" + id + ", " + name + " bytes: " + Arrays.toString(cmd) + "}\n" + resp;

    }

    public String getName() {
        return name;
    }

    public void setRawResp(byte[] rawResp){this.rawResp = rawResp;}
    public void setResult(String result){
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (!commandId.equals(command.commandId)) return false;
        return id.equals(command.id);

    }

    @Override
    public int hashCode() {
        int result = commandId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
