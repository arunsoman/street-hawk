package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.BasicParserSet;
import com.ar.myfirstapp.obd2.parser.Parser;


/**
 * Created by arunsoman on 04/03/17.
 */

public class AtCommands {
    public static final Command setDefault = new Command("AT", " H1\r", "", BasicParserSet.okParser);
    public static final Command resetDefault = new Command("AT", " \r", "", BasicParserSet.okParser);
    public static final Command dpn = new Command("AT", " DPN \r", "", BasicParserSet.okParser);
    public static final Command silentOn = new Command("AT", " CS M1 \r", "", BasicParserSet.okParser);
    public static final Command silentOff = new Command("AT", " CS M0 \r", "", BasicParserSet.okParser);
    public static final Command spaceOff = new Command("AT", " S0 \r", "", BasicParserSet.okParser);
    public static final Command spaceOn = new Command("AT", " S1 \r", "", BasicParserSet.okParser);
    public static final Command headerON = new Command("AT", " H1\r", "", BasicParserSet.okParser);
    public static final Command canStatus = new Command("AT", " CS\r", "", BasicParserSet.okParser);
    public static final Command dispProtocolNumber = new Command("AT", " DPN\r", "", new Parser() {
        @Override
        public void parse(Command command) {
            byte[]rawBytes = command.getRawResp();
            if(rawBytes.length == 1){
                command.setResponseStatus(Command.ResponseStatus.Ok);
                command.setResult(""+(rawBytes[0]-48));
            }
        }
    });
    public static final Command activitMonitor = new Command("AT", " AMC\r", "", new Parser() {
        @Override
        public void parse(Command command) {
            byte[]rawBytes = command.getRawResp();
           if(rawBytes.length == 1 && rawBytes[0] == 63){
               command.setResponseStatus(Command.ResponseStatus.UnSupportedReq);
               return;
           }
            int tmp = Integer.parseInt(new String(rawBytes), 16) +1;
            double d = tmp*(.65536);
            command.setResult(Double.toString(d));
        }
    });


    public static final Command[] initCommands = {

            new Command("AT", " Z\r", "", BasicParserSet.okParser),
            new Command("AT", " E0\r", "", BasicParserSet.okParser),
            new Command("AT", " L1\r", "", BasicParserSet.okParser),
            new Command("AT", " I\r", "", BasicParserSet.okParser),
            new Command("AT", " H1\r", "", BasicParserSet.okParser),
            new Command("AT", " S1\r", "", BasicParserSet.okParser),
            new Command("AT", " AL\r", "", BasicParserSet.okParser),
            dispProtocolNumber,
            new Command("AT", "SP 6\r", "", BasicParserSet.okParser),
    };

    public static final Command[] initCanScan={
            new Command("AT", " CA\r", "", BasicParserSet.okParser),
            new Command("AT", " CS\r", "", BasicParserSet.okParser),
            new Command("AT", " CSM1\r", "", BasicParserSet.okParser),
            new Command("AT", " MA\r", "",  BasicParserSet.okParser),
    };
}
