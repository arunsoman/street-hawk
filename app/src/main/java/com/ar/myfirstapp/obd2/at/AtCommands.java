package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

            new Command("AT", " I\r", "", singleLineHandler),
            new Command("AT", " H1\r", "", okResponse),
            new Command("AT", " S1\r", "", okResponse),
            new Command("AT", " L1\r", "", okResponse),
            new Command("AT", " AL\r", "", okResponse),
            new Command("AT", "SP 0\r", "", okResponse),
            new Command("AT", " DPN\r", "", singleLineHandler),
            //"AT SH 7DF",
    };
}
