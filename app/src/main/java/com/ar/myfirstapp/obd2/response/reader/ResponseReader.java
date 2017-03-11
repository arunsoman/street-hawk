package com.ar.myfirstapp.obd2.response.reader;

import com.ar.myfirstapp.elm.ELMConnector;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by arunsoman on 05/03/17.
 */

public abstract class ResponseReader {

    public enum Readers {
        single(new SingleLineResponseReader()),
        multi(new MultiLineResponseReader()),
        stream(new StreamResponseReader(null));

        private ResponseReader responseReader;

        Readers(ResponseReader responseReader) {
            this.responseReader = responseReader;
        }

        public ResponseReader getResponseReader(){
            return this.responseReader;
        }
    };

    protected String resp;
    private int state;

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
            } else if (state == 2 && aByte == 62) {
                state = 3;
                eom = true;
            } else {
                state = 0;
            }
            sb.append(aByte).append(',');
        }
        resp = sb.toString();
        return Command.ResponseStatus.Ok;
    }

    public String toString(){
        return  "resp:{raw: "+ resp +" };";
    }

    public abstract Command.ResponseStatus setResponse(Command command);
}
