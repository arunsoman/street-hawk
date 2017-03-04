package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;

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

    List<Command> getSupportedPIDs(ELMConnector.Pipe pipe) throws IOException{
        String response = pipe.sendNreceive((modeId+" 00\r").getBytes());

        String resultArray[] = response.split(" ");
        StringBuilder hexString = new StringBuilder();
        for(int i=4;i<resultArray.length;i++){
            hexString.append(resultArray[i]);
        }
        BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(hexString.toString(),16)});
        List<Command> list = new ArrayList<>();

        for (int i = 0; i < bitSet.length(); i++){
            if(bitSet.get(i) == true)
                list.add(this.getCommand(i));
        }
        return list;

    }
    String send(Command command, ELMConnector.Pipe pipe) throws IOException{
        return pipe.sendNreceive(command.cmd);
    }

    abstract protected Command getCommand(int index);

}
