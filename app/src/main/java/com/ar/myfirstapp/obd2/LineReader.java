package com.ar.myfirstapp.obd2;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arunsoman on 05/03/17.
 */

public class LineReader {
    private byte[] data = new byte[130];
    private int eom;
    private int start;
    private int end;
    private static final byte cr = (byte)('\r');
    private static final byte lf = (byte)('\n');

    public LineReader(InputStream is) throws IOException {
        int available, readCnt, previousLoc = 0;
        while ((available= is.available())> 0) {
            readCnt = is.read(data, previousLoc, available);
            previousLoc += readCnt;
        }
        eom = previousLoc;
    }

    public String nextLine() throws IOException {
       String result = null;
        for(int i = end; i < eom; i++){
            if(data[i] == cr) {
                result = new String(data, 0, eom-1, "US-ASCII");
                end = (data[i+1] == lf)? i +2 : i+1;
                start = end;
            }
        }
        return result;
    }

    public void drain()  {
        eom = 0;
    }
}
