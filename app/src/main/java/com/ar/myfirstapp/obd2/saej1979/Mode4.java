package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */

public class Mode4 extends Mode {
    public Mode4(){
        super("4");
    }
    public Command [] commands = {
            new Command("4", "", "Clear trouble codes / Malfunction indicator lamp (MIL) / Check engine light", new SaeJ1979ResponseParser() {
        @Override
        public String getResult() {
            return "ok" ;
        }
    })};
    @Override
    protected Command getCommand(int index) {
        return null;
    }
}
