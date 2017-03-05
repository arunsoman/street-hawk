package com.ar.myfirstapp.obd2.can;

import com.ar.myfirstapp.obd2.BadResponseException;
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

        private String process(InputStream is) throws IOException{
            byte a0 = (byte) is.read();
            if(a0 == -1)
                return null;
            byte a1 = (byte) is.read();
            byte a2 = (byte) is.read();
            byte a3 = (byte) is.read();
            byte a4 = (byte) is.read();
            byte a5 = (byte) is.read();
            byte a6 = (byte) is.read();
            byte a7 = (byte) is.read();
            if(a0 == 3 && a3 == 0x31){

            }
            else if(a0 >=3 && a0 <= 6 && a3 ==pid){

            }
            else if(a0 >=4 && a0 <= 7){

            }
            return null;
        }
        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            process(is);
            is.read();//cr
            is.read();//lf
            parse(is);
        }

        @Override
        public String getResult() {
            return null;
        }
    }
}
