package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.UnknownCommandException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by arunsoman on 04/03/17.
 */

public abstract class Mode {
    final String modeId;

    public Mode(String modeId) {
        this.modeId = modeId;
    }

    void send(Command command, ELMConnector.Pipe pipe) throws IOException, UnknownCommandException, BadResponseException {
         pipe.sendNreceive(command);
    }

    abstract protected Command getCommand(int index);

}
