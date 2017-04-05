package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public interface Mode {

    Command[] getDiscoveryCommands();
    Command getCommand(String pid);
}
