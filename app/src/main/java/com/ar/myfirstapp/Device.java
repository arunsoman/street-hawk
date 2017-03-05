package com.ar.myfirstapp;

import android.util.Log;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.UnknownCommandException;
import com.ar.myfirstapp.obd2.at.AtCommands;
import com.ar.myfirstapp.obd2.can.CanRequest8;
import com.ar.myfirstapp.obd2.can.CanResponseHandlers;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import java.io.IOException;

/**
 * Created by arunsoman on 05/03/17.
 */

public class Device {
    private  ELMConnector connector;
    private boolean spaceOff;
    private State state;

    private enum State {Connected, Disconnected, Initialized, Reset, Interrupted, Scanning};


    public Device(String deviceAddress) throws Exception {
        connector = new ELMConnector();
            connector.connect(deviceAddress);

    }
    public String sendCommand(Command command) throws IOException, BadResponseException, UnknownCommandException {
        return connector.sendNreceive(command);
    }

    public void initSequence() throws IOException {
        for (Command c : AtCommands.initCommands) {
            try {
                connector.sendNreceive(c);
            } catch (BadResponseException e) {
                Log.e(c.toString(), "BadResponse");
            } catch (UnknownCommandException e) {
                Log.e(c.toString(), "unknownCommand");
            }
        }
    }

    public void setToDefault() throws IOException, BadResponseException, UnknownCommandException {
        connector.sendNreceive(AtCommands.setDefault);
    }




    public void reset() throws IOException, BadResponseException, UnknownCommandException {
        connector.sendNreceive(AtCommands.resetDefault);
        state = State.Reset;
    }

    public String getMode1PIDs() throws IOException, BadResponseException, UnknownCommandException {

        if(state== State.Initialized){
            String result = connector.sendNreceive(Mode1.commands[0]);
            return result;
        }
        return null;
    }

    public String queryCan(byte mode, byte pid) throws IOException, BadResponseException, UnknownCommandException {
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

    public void initScan() throws IOException, BadResponseException, UnknownCommandException {
        for (Command c : AtCommands.initCommands) {
            connector.sendNreceive(c);
        }
    }
}
