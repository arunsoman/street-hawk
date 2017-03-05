package com.ar.myfirstapp;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.can.CanRequest8;
import com.ar.myfirstapp.obd2.can.CanResponseHandlers;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import java.io.IOException;

/**
 * Created by arunsoman on 05/03/17.
 */

public class Device {
    private final ELMConnector connector;
    private boolean spaceOff;
    private State state;

    private enum State {Connected, Disconnected, Initialized, Reset, Interrupted, Scanning};


    public Device(String deviceAddress) throws Exception {
        try(ELMConnector connector = new ELMConnector()) {
            connector.connect(deviceAddress);
            initSequence();
            this.connector = connector;
        }
    }
    public String sendCommand(Command command) throws IOException, BadResponseException {
        return connector.sendNreceive(command);
    }

    public void initSequence() throws IOException, BadResponseException {
        for (Command c : AtCommands.initCommands) {
            connector.sendNreceive(c);
        }
    }

    public void setToDefault() throws IOException, BadResponseException {
        connector.sendNreceive(AtCommands.setDefault);
    }




    public void reset() throws IOException, BadResponseException {
        connector.sendNreceive(AtCommands.resetDefault);
        state = State.Reset;
    }

    public String getMode1PIDs() throws IOException, BadResponseException {
        StringBuilder sb = new StringBuilder();
        if(state== State.Initialized){
            String result = connector.sendNreceive(Mode1.commands[0]);
            String[] lines = result.split("\n");
            String[] splits;
            for(String aLine: lines){
                splits = aLine.split(" ");
                if(splits.length == 8){
                    sb.append(splits[0]);
                    sb.append(" ");
                }else if(splits.length == 11){
                    sb.append(splits[0]);
                    sb.append(splits[1]);
                    sb.append(splits[2]);
                    sb.append(splits[3]);
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    public String queryCan(byte mode, byte pid) throws IOException, BadResponseException {
        boolean flip = false;
        if(!spaceOff) {
            connector.sendNreceive(AtCommands.spaceOff);
            spaceOff = true;
            flip = true;
        }
        CanRequest8 request8 = new CanRequest8(mode, pid, new CanResponseHandlers.Can8QueryHandler(pid));
        String result =  connector.sendNreceive(request8);
        if(flip){
            connector.sendNreceive(AtCommands.spaceOn);
            spaceOff = true;
        }
        return result;
    }
}
