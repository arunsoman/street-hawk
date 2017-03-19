package com.ar.myfirstapp;

import android.os.Handler;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.UnknownCommandException;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.can.CanResponseParser;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import java.io.IOException;


/**
 * Created by arunsoman on 05/03/17.
 */

public class Device {
    private  ELMConnector connector;
    private boolean spaceOff;
    private State state;
    //private final  ResponseHandlerUtils.StreamHandler streamHandler;
    private enum State {Connected, Disconnected, Initialized, Reset, Interrupted, Scanning}

    public Device(String deviceAddress, Handler responseCallback) throws IOException {
        //streamHandler = new ResponseHandlerUtils.StreamHandler(this);
        connector = new ELMConnector(responseCallback);
        connector.connect(deviceAddress);

    }
    public void sendCommand(Command command) throws IOException{
        connector.send(command);
    }

    public void initSequence() throws IOException {
        for (Command c : AtCommands.initCommands) {
            connector.send(c);
        }
    }

    public void setToDefault() throws IOException{
        connector.send(AtCommands.setDefault);
    }

    public void reset() throws IOException{
        connector.send(AtCommands.resetDefault);
        state = State.Reset;
    }

    public void getMode1PIDs() throws IOException{

        //if(state== State.Initialized){
        connector.send(Mode1.getCommand("00"));
            //return result;
        //}
        ///return null;
    }


    public void queryCan(byte mode, byte pid) throws IOException{
        boolean flip = false;
        /*
        if(!spaceOff) {
            connector.send(AtCommands.spaceOff);
            spaceOff = true;
            flip = true;
        }
        */
        byte[] cmd = new byte[9];
        cmd[0] = 2;
        cmd[1] = mode;
        cmd[2] = pid;
        cmd[8] = (byte)('\r');

        Command request8 = new Command(cmd,  new CanResponseParser());
        connector.send(request8);
        /*
        if(flip){
            connector.send(AtCommands.spaceOn);
            spaceOff = true;
        }
         */
    }

    public void initScan() throws IOException{
        for (Command c : AtCommands.initCommands) {
            connector.send(c);
        }
    }
    public void scan(String id) throws BadResponseException, UnknownCommandException, IOException {
//        connector.sendNreceive(new Command("AT", " CRA"+id+"\r", "", singleLineHandler));
//        connector.sendNreceive(new Command("AT", " MA\r", "", new ResponseHandlerUtils.StreamHandler(this)));
        state = State.Scanning;
    }

    public void killScan(){
        if(state == State.Scanning){
            try {
                connector.interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
