package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.BasicParserSet;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import static com.ar.myfirstapp.obd2.parser.BasicParserSet.okParser;


/**
 * Created by arunsoman on 04/03/17.
 */

public class AtCommands {
    public static final Command ATSP0= new Command("AT ", "0\r", "Automatic protocol detection", okParser);
    public static final Command ATSP1= new Command("AT ", "1\r", "SAE J1850 PWM (41.6 kbaud)", okParser);
    public static final Command ATSP2= new Command("AT ", "2\r", "SAE J1850 VPW (10.4 kbaud)", okParser);
    public static final Command ATSP3= new Command("AT ", "3\r", "ISO 9141-2 (5 baud init, 10.4 kbaud)", okParser);
    public static final Command ATSP4= new Command("AT ", "4\r", "ISO 14230-4 KWP (5 baud init, 10.4 kbaud)", okParser);
    public static final Command ATSP5= new Command("AT ", "5\r", "ISO 14230-4 KWP (fast init, 10.4 kbaud)", okParser);
    public static final Command ATSP6= new Command("AT ", "6\r", "ISO 15765-4 CAN (11 bit ID, 500 kbaud)", okParser);
    public static final Command ATSP7= new Command("AT ", "7\r", "ISO 15765-4 CAN (29 bit ID, 500 kbaud)", okParser);
    public static final Command ATSP8= new Command("AT ", "8\r", "ISO 15765-4 CAN (11 bit ID, 250 kbaud)", okParser);
    public static final Command ATSP9= new Command("AT ", "9\r", "ISO 15765-4 CAN (29 bit ID, 250 kbaud)", okParser);


    public static final Command ATHI = new Command("AT", " H1\r", "", okParser);
    public static final Command resetDefault = new Command("AT", " \r", "", okParser);
    public static final Command dpn = new Command("AT", " DPN \r", "", okParser);
    public static final Command silentOn = new Command("AT", " CS M1 \r", "", okParser);
    public static final Command silentOff = new Command("AT", " CS M0 \r", "", okParser);
    public static final Command spaceOff = new Command("AT", " S0 \r", "", okParser);
    public static final Command spaceOn = new Command("AT", " S1 \r", "", okParser);
    public static final Command headerON = new Command("AT", " H1\r", "", okParser);
    public static final Command canStatus = new Command("AT", " CS\r", "", okParser);
    public static final Command dispProtocolNumber = new Command("AT", " DPN\r", "", okParser);

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
            new Command("AT", " Z\r", "", okParser),
            new Command("AT", " E0\r", "", okParser),
            new Command("AT", " L1\r", "", okParser),
            new Command("AT", " I\r", "", okParser),
            new Command("AT", " H0\r", "", okParser),
            new Command("AT", " S1\r", "", okParser),
            new Command("AT", " AL\r", "", okParser),
            new Command("AT", "ST 250\r", "", okParser),
    };

    public static final Command[] protoIter= {
            ATSP0, ATSP6
    };

    public static final Command[] testCommands= {
            Mode1.getCommand("00"),
            Mode1.getCommand("0C"),
            Mode1.getCommand("0D"),
    };

    public static final Command[] initCanScan={
            new Command("AT", " CA\r", "", okParser),
            new Command("AT", " L1\r", "", okParser),
            new Command("AT", " H1\r", "", okParser),
            new Command("AT", " CAF0\r", "", okParser),
            new Command("AT", " STFF\r", "", okParser),
            new Command("AT", " SH7E2\r", "", okParser),
    };
}
