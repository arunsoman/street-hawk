package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */
public class Mode10 extends Mode {
    public Mode10(){
        super("0A");
    }
    @Override
    protected Command getCommand(int index) {
        return null;
    }
}
