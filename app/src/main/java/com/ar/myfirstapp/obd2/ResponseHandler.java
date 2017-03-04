package com.ar.myfirstapp.obd2;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 04/03/17.
 */

public interface ResponseHandler {
    void parse(InputStream is) throws IOException, BadResponseException;
    String getResult();
}
