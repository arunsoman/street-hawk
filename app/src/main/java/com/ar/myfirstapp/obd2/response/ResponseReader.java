package com.ar.myfirstapp.obd2.response;

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

public class ResponseReader extends ByteArrayOutputStream{
    private boolean containsNonPrintable = false;
    private byte[] resp;
    private int state;
    private FlyReader reader = new FlyReader();
    private class FlyReader extends ByteArrayOutputStream{
        public void reset(int cnt){
            this.count = count- cnt;
            if(this.count <0)
                count = 0;
        }
    }
    public byte[] read(InputStream is) throws IOException {
        reader.reset();
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
            reader.write(aByte);
        }
        Log.e("RespReader", Arrays.toString(reader.toByteArray()));
        if(eom) reader.reset(3);
        resp = reader.toByteArray();
        Log.e("RespReader1", Arrays.toString(reader.toByteArray()));
        return resp;
    }

    public byte[] getRawData(){
        return resp;
    }


    public String toString(){
        try{
            return new String(resp);
        }catch (Throwable e){
            return "Non printable chars";
        }
    }
}
