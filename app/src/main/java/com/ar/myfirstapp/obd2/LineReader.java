package com.ar.myfirstapp.obd2;

import android.util.Log;

import com.ar.myfirstapp.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by arunsoman on 05/03/17.
 */

public class LineReader {
    private byte[] data ;

    public LineReader(InputStream is) throws IOException {
        read(is);
    }

    public LineReader(InputStream is, long wait) throws InterruptedException, IOException {
        this.wait(wait);
        read(is);
    }

    private void read(InputStream is) throws IOException {
        byte aByte = (byte) is.read();//Sometimes there may be delay in response hence wait
        data = new byte[is.available()+1];
        data[0] = aByte;
        int available, readCnt, previousLoc = 1;
        while ((available= is.available())> 0) {
            readCnt = is.read(data, previousLoc, available);
            previousLoc += readCnt;
        }
    }

    public byte[] getRawData(){
        return data;
    }
    public static String toString(byte[] data){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            sb.append(data[i]);
            sb.append(',');
        }
        return  sb.toString();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            sb.append(data[i]);
            sb.append(',');
        }
        return  sb.toString();
    }
}
