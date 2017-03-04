package com.ar.myfirstapp.obd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by arunsoman on 04/03/17.
 */

public class ResponseHandlerUtils {
    public static final ResponseHandler okResponse = new ResponseHandler() {
        private boolean status;
        private static final String ok = "ok";

        public void parse(InputStream is) throws IOException {
            int a = is.read();
            int b = is.read();
            while (is.read() != -1) ;
        }

        @Override
        public String getResult() {
            return ok;
        }
    };

    public final static ResponseHandler singleLineHandler = new ResponseHandler() {
        String result;

        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            String line = null;
            try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                    line = bufferedReader.readLine();
                    while ((bufferedReader.readLine()) != null) ;
                    result = line;
                }
            }
        }

        @Override
        public String getResult() {
            return result;
        }
    };

    public final static ResponseHandler multiLineHandler = new ResponseHandler() {
        String result;

        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            String line = null;
            StringBuilder sb = new StringBuilder();
            try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    result = sb.toString();
                }
            }
        }

        @Override
        public String getResult() {
            return result;
        }
    };

}
