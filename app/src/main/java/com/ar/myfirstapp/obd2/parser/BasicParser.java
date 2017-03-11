package com.ar.myfirstapp.obd2.parser;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/10/2017.
 */

public class BasicParser {

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
        public Command.ResponseStatus parse(String[] rawData, Command command) {
            if(rawData[0].startsWith(ok)||
                    rawData[0].startsWith(OK)) {
                return(Command.ResponseStatus.Ok);
            } else if(rawData[0].startsWith(OK)||
                    rawData[0].startsWith(OK)){
                return (Command.ResponseStatus.Ok);
            }else if(rawData[0].startsWith(ko)){
                return (Command.ResponseStatus.UnSupportedReq);
            }
            else{
                return(Command.ResponseStatus.Unknown);

            }
        }
    };
    public static final Parser koParser = new Parser() {
        @Override
        public Command.ResponseStatus parse(String[] rawData, Command command) {
            if(rawData[0].startsWith(ko)) {
                command.setResponseStatus(Command.ResponseStatus.Ok);
                return Command.ResponseStatus.Ok;
            }
            else{
                return Command.ResponseStatus.UnSupportedReq;

            }
        }
    };
    public static final Parser crParser = new Parser() {
        @Override
        public Command.ResponseStatus parse(String[] rawData, Command command) {
            if(rawData[0].startsWith(CR)) {
                return Command.ResponseStatus.Ok;
            }
            else{
                return Command.ResponseStatus.UnSupportedReq;

            }
        }
    };
}
