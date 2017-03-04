package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */

public final class Mode5 extends Mode {

    private final static Command[] commands = {
            new Command("05 ","00","Supported", response),
            new Command("05 ","100","OBD Monitor IDs supported ($01 – $20)", response),
            new Command("05 ","101","O2 Sensor Monitor Bank 1 Sensor 1", response),
            new Command("05 ","102","O2 Sensor Monitor Bank 1 Sensor 2", response),
            new Command("05 ","103","O2 Sensor Monitor Bank 1 Sensor 3", response),
            new Command("05 ","104","O2 Sensor Monitor Bank 1 Sensor 4", response),
            new Command("05 ","105","O2 Sensor Monitor Bank 2 Sensor 1", response),
            new Command("05 ","106","O2 Sensor Monitor Bank 2 Sensor 2", response),
            new Command("05 ","107","O2 Sensor Monitor Bank 2 Sensor 3", response),
            new Command("05 ","108","O2 Sensor Monitor Bank 2 Sensor 4", response),
            new Command("05 ","109","O2 Sensor Monitor Bank 3 Sensor 1", response),
            new Command("05 ","010A","O2 Sensor Monitor Bank 3 Sensor 2", response),
            new Command("05 ","010B","O2 Sensor Monitor Bank 3 Sensor 3", response),
            new Command("05 ","010C","O2 Sensor Monitor Bank 3 Sensor 4", response),
            new Command("05 ","010D","O2 Sensor Monitor Bank 4 Sensor 1", response),
            new Command("05 ","010E","O2 Sensor Monitor Bank 4 Sensor 2", response),
    new Command("05 ","010F","O2 Sensor Monitor Bank 4 Sensor 3", response),
    new Command("05 ","110","O2 Sensor Monitor Bank 4 Sensor 4", response),
    new Command("05 ","201","O2 Sensor Monitor Bank 1 Sensor 1", response),
    new Command("05 ","202","O2 Sensor Monitor Bank 1 Sensor 2", response),
    new Command("05 ","203","O2 Sensor Monitor Bank 1 Sensor 3", response),
    new Command("05 ","204","O2 Sensor Monitor Bank 1 Sensor 4", response),
    new Command("05 ","205","O2 Sensor Monitor Bank 2 Sensor 1", response),
    new Command("05 ","206","O2 Sensor Monitor Bank 2 Sensor 2", response),
    new Command("05 ","207","O2 Sensor Monitor Bank 2 Sensor 3", response),
    new Command("05 ","208","O2 Sensor Monitor Bank 2 Sensor 4", response),
    new Command("05 ","209","O2 Sensor Monitor Bank 3 Sensor 1", response),
    new Command("05 ","020A","O2 Sensor Monitor Bank 3 Sensor 2", response),
    new Command("05 ","020B","O2 Sensor Monitor Bank 3 Sensor 3", response),
    new Command("05 ","020C","O2 Sensor Monitor Bank 3 Sensor 4", response),
    new Command("05 ","020D","O2 Sensor Monitor Bank 4 Sensor 1", response),
    new Command("05 ","020E","O2 Sensor Monitor Bank 4 Sensor 2", response),
    new Command("05 ","020F","O2 Sensor Monitor Bank 4 Sensor 3", response),
    new Command("05 ","210","O2 Sensor Monitor Bank 4 Sensor 4", response),
    };
    public Mode5() {
        super("05");
    }

    @Override
    protected Command getCommand(int index) {
        return commands[index];
    }
}
