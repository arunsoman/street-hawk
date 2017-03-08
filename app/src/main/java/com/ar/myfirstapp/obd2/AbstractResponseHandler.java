package com.ar.myfirstapp.obd2;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Arun Soman on 3/8/2017.
 */

public abstract class AbstractResponseHandler implements ResponseHandler {
    protected ResponseStatus status;
    protected String dataStr;
    protected String resultStr;

    public void readResponse(InputStream is) {
        byte read = 0;
        try {
//            read = (byte) is.read();
            LineReader lineReader = new LineReader(is);
            dataStr = lineReader.toString();
            status = ResponseStatus.Ok;
        } catch (IOException e) {
            status = ResponseStatus.NetworkError;
            return;
        }
//        if(read != (byte)('\r') )
//            status = ResponseStatus.BadResponse;
    }
    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public String getData() {
        return dataStr;
    }

    @Override
    public String getResult() {
        return resultStr;
    }
}
