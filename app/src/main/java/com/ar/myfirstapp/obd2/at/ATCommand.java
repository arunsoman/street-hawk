package com.ar.myfirstapp.obd2.at;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public class ATCommand extends Command {
    public ATCommand(String pid, String discription, Parser parser){
        super("AT",pid,discription,parser);
        commandType = CommandType.AT;
    }
}
