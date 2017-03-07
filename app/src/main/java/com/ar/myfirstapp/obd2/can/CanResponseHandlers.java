package com.ar.myfirstapp.obd2.can;

import android.util.Log;

import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.LineReader;
import com.ar.myfirstapp.obd2.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 05/03/17.
 */

public class CanResponseHandlers {
    public static class Can8QueryHandler implements ResponseHandler {
        byte pid;

        public Can8QueryHandler(byte pid) {
            this.pid = pid;
        }

        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            LineReader lineReader = new LineReader(is);
            String line;
                while ((line = lineReader.nextLine()) != null){
                Log.e("CAN", line);
            }
        }

        @Override
        public String getResult() {
            return null;
        }
    }
}
