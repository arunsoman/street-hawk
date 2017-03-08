package com.ar.myfirstapp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.ResponseHandlerUtils;
import com.ar.myfirstapp.obd2.UnknownCommandException;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.can.CanRequest8;
import com.ar.myfirstapp.obd2.can.CanResponseHandlers;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import java.io.IOException;

import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.singleLineHandler;

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
        connector.sendNreceive(command);
    }

    public void initSequence() throws IOException {
        for (Command c : AtCommands.initCommands) {
            connector.sendNreceive(c);
        }
    }

    public void setToDefault() throws IOException{
        connector.sendNreceive(AtCommands.setDefault);
    }

    public void reset() throws IOException{
        connector.sendNreceive(AtCommands.resetDefault);
        state = State.Reset;
    }

    public void getMode1PIDs() throws IOException{

        //if(state== State.Initialized){
            connector.sendNreceive(Mode1.commands[0]);
            //return result;
        //}
        ///return null;
    }

    public void queryCan(byte mode, byte pid) throws IOException{
        boolean flip = false;
        if(!spaceOff) {
            connector.sendNreceive(AtCommands.spaceOff);
            spaceOff = true;
            flip = true;
        }
            CanRequest8 request8 = new CanRequest8(mode, pid, new CanResponseHandlers.Can8QueryHandler(pid));
        connector.sendNreceive(request8);
        if(flip){
            connector.sendNreceive(AtCommands.spaceOn);
            spaceOff = true;
        }
    }

    public void initScan() throws IOException{
        for (Command c : AtCommands.initCommands) {
            connector.sendNreceive(c);
        }
    }
    public void scan(String id) throws BadResponseException, UnknownCommandException, IOException {
        connector.sendNreceive(new Command("AT", " CRA"+id+"\r", "", singleLineHandler));
        connector.sendNreceive(new Command("AT", " MA\r", "", new ResponseHandlerUtils.StreamHandler(this)));
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
