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
    private int eom;
    private int start;
    private int end;
    private static final byte cr = (byte)('\r');
    private static final byte lf = (byte)('\n');

    public LineReader(InputStream is) throws IOException {
        byte aByte = (byte) is.read();//Sometimes there may be delay in response hence wait
        data = new byte[is.available()+1];
        data[0] = aByte;
        int available, readCnt, previousLoc = 1;
        while ((available= is.available())> 0) {
            readCnt = is.read(data, previousLoc, available);
            previousLoc += readCnt;
        }
        eom = previousLoc;
        start = (data[0] == cr ? 1 : 0);
        end = start;
        MainActivity.tvLog.append("\nRESP: "+toString());
    }

    public String nextLine() throws IOException {
        if(start == eom) return null;
        StringBuilder sb = new StringBuilder();
        for(int i = start; i < eom; i++){
            if(data[i] == cr){
                start = i+1;
                return sb.toString();
            }
            if(data[i]< 32){
                sb.append("[").append(data[i]).append("]");
            }
            else if(data[i]> 126){
                sb.append("<").append(data[i]).append(">");
            }
            else sb.append(data[i]).append(',');
        }
        start = eom;
        return sb.toString();
    }

    public void drain()  {
        eom = 0;
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
        for(int i = 0; i < eom; i++){
            sb.append(data[i]);
            sb.append(',');
        }
        return  sb.toString();
    }
}
