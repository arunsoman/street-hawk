package com.ar.myfirstapp.obd2;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public interface ResponseHandler {
    enum ResponseStatus{Unknown, Ok, UnSupportedReq, BadResponse, NetworkError};
    void parse();
    void readResponse(InputStream is);
    ResponseStatus getStatus();
    String getData();
    String getResult();
}
