package com.ar.myfirstapp.obd2.parser;

import com.ar.myfirstapp.obd2.Command;

import java.util.Arrays;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class BasicParserSet{

    private static final String ok = new StringBuilder().append((byte)'o').append((byte)'k').toString();
    private static final String OK = new StringBuilder().append((byte)'O').append((byte)'K').toString();
    private static final String CR = new StringBuilder().append((byte)'\r').toString();
    public static final String ko = new StringBuilder().append((byte)'?').toString();
    public static final String NO_DATA = new StringBuilder()
            .append((byte)'N')
            .append((byte)'O')
            .append((byte)' ')
            .append((byte)'D')
            .append((byte)'A')
            .append((byte)'T')
            .append((byte)'A').toString();

    public static final Parser okParser = new Parser() {
        @Override
        public void parse( Command command) {
            byte[] rawResp = command.getRawResp();
            int from = 0;
            while ((from = Arrays.binarySearch(rawResp, from, rawResp.length, (byte)(79))) > 0) {
                if(rawResp[from+1] != (byte)(75)){
                    from++;
                }
                else{
                    break;
                }
            }
            if(from < 0 ) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
            }
            else {
                command.setResponseStatus(Command.ResponseStatus.Ok);
            }
        }
    };


}
