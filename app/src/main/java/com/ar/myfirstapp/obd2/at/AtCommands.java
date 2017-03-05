package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.Command;

import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.multiLineHandler;
import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.okResponse;
import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.singleLineHandler;


/**
 * Created by arunsoman on 04/03/17.
 */

public class AtCommands {
    public static final Command setDefault = new Command("AT", " H1\r", "", okResponse);
    public static final Command resetDefault = new Command("AT", " \r", "", okResponse);
    public static final Command dpn = new Command("AT", " DPN \r", "", singleLineHandler);
    public static final Command silentOn = new Command("AT", " CS M1 \r", "", okResponse);
    public static final Command silentOff = new Command("AT", " CS M0 \r", "", okResponse);
    public static final Command spaceOff = new Command("AT", " S0 \r", "", okResponse);
    public static final Command spaceOn = new Command("AT", " S1 \r", "", okResponse);
    public static final Command[] initCommands = {

            new Command("AT", " Z\r", "", okResponse),
            new Command("AT", " E0\r", "", okResponse),
            new Command("AT", " L1\r", "", okResponse),
            new Command("AT", " I\r", "", singleLineHandler),
            new Command("AT", " H1\r", "", singleLineHandler),
            new Command("AT", " S1\r", "", singleLineHandler),
            new Command("AT", " AL\r", "", singleLineHandler),
            new Command("AT", "SP 0\r", "", singleLineHandler),
            new Command("AT", " DPN\r", "", singleLineHandler),
            //"AT SH 7DF",
    };

    public static final Command[] initCanScan={
            new Command("AT", " CA\r", "", singleLineHandler),
            new Command("AT", " CS\r", "", singleLineHandler),
            new Command("AT", " CSM1\r", "", okResponse),
            new Command("AT", " CA\r", "", okResponse),
            new Command("AT", " CA\r", "", okResponse),
            new Command("AT", " MA\r", "", multiLineHandler),
    };
}
