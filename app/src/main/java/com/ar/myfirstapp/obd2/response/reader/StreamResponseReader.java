package com.ar.myfirstapp.obd2.response.reader;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class StreamResponseReader extends ResponseReader {
    public interface LineComplete{
        void lineRead(StreamResponseReader streamResponseReader);
    }

    private LineComplete lineComplete;
    private int state;
    private List<String> serving;
    private List<String> acc;

    public StreamResponseReader(LineComplete lineComplete){
        this.lineComplete = lineComplete;
    }

    @Override
    public Command.ResponseStatus read(ELMConnector.Pipe pipe) {
        StringBuilder sb = new StringBuilder();
        boolean eom = false;
        while (!eom) {
            byte aByte = 0;//Sometimes there may be delay in response hence wait
            try {
                aByte = (byte) pipe.is.read();
            } catch (IOException e) {
                return Command.ResponseStatus.NetworkError;
            }
            if (state == 0 && aByte == 13) {
                state = 1;
            } else if (state == 1 && (aByte == 10 || aByte == 13)) {
                state = 2;
                eom = true;
            } else {
                state = 0;
            }
            sb.append(aByte).append(',');
        }
        acc.add(sb.toString());
        return Command.ResponseStatus.Ok;
    }

    @Override
    public Command.ResponseStatus setResponse(Command command){
        return Command.ResponseStatus.Ok;//TODO do deep clone and return;
    }
}
