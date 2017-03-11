package com.ar.myfirstapp.obd2;

import android.util.Log;

import com.ar.myfirstapp.MainActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by arunsoman on 05/03/17.
 */

public class LineReader {
    private String resp;
    private int state;

    public void read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean eom = false;
        while (!eom) {
            byte aByte = (byte) is.read();//Sometimes there may be delay in response hence wait
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
    }

    public byte[] getRawData(){
        return null;
    }

    public static String toString(byte[] data){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            sb.append(data[i]);
            sb.append(',');
        }
        return  sb.toString();
    }

    public static String[] toMultilineStringArray(String resp){
        if(resp.matches(".*13\\,13\\,.*"))
            return resp.split(".*13\\,13\\,.*");
        else
            return resp.split(".*13\\,10\\,.*");
    }
    public String toString(){
        return  resp;
    }
}
