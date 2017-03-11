package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.BasicParser;
import com.ar.myfirstapp.obd2.response.reader.ResponseReader.Readers;


/**
 * Created by arunsoman on 04/03/17.
 */

public class AtCommands {
    public static final Command setDefault = new Command("AT", " H1\r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command resetDefault = new Command("AT", " \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command dpn = new Command("AT", " DPN \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command silentOn = new Command("AT", " CS M1 \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command silentOff = new Command("AT", " CS M0 \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command spaceOff = new Command("AT", " S0 \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command spaceOn = new Command("AT", " S1 \r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command headerON = new Command("AT", " H1\r", "", Readers.single.getResponseReader(), BasicParser.okParser);
    public static final Command canStatus = new Command("AT", " CS\r", "", Readers.single.getResponseReader(), BasicParser.okParser);


    public static final Command[] initCommands = {

            new Command("AT", " Z\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " E0\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " L1\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " I\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " H1\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " S1\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " AL\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", "SP 6\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " DPN\r", "", Readers.single.getResponseReader(), BasicParser.okParser)
    };

    public static final Command[] initCanScan={
            new Command("AT", " CA\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " CS\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " CSM1\r", "", Readers.single.getResponseReader(), BasicParser.okParser),
            new Command("AT", " MA\r", "", Readers.stream.getResponseReader(), BasicParser.okParser),
    };
}
