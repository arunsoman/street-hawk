package com.ar.myfirstapp.obd2;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

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
    }

    public String nextLine() throws IOException {
       String result = null;
        for(int i = end; i < eom; i++){

            if(data[i] == cr) {
                end = i-start;
                try {
                    if(end == 0){
                        return "";
                    }
                    result = new String(data, start, end, "US-ASCII");
                } catch(Throwable e){
                    Log.e("Error",e.getMessage());
                }
                end = (data[i+1] == lf)? i +2 : i+1;
                start = end;
                return result;
            }
        }

        if(eom == start){
            return  "";
        }

        result = new String(data, start, eom, "US-ASCII");
        return result;
    }

    public void drain()  {
        eom = 0;
    }
}
