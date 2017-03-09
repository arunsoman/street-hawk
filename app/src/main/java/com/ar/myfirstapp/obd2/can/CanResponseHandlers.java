package com.ar.myfirstapp.obd2.can;

import android.util.Log;

import com.ar.myfirstapp.obd2.AbstractResponseHandler;
import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.LineReader;
import com.ar.myfirstapp.obd2.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 05/03/17.
 */

public class CanResponseHandlers {
    public static class Can8QueryHandler extends AbstractResponseHandler {
        byte pid;

        public Can8QueryHandler(byte pid) {
            this.pid = pid;
        }

        @Override
        public void parse() {

        }
    }
}
