package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by arunsoman on 04/03/17.
 */

public final class Mode2 extends Mode1 {
    public Mode2() {
        super("02");
    }
    public Command[] commands={

        new Command("2", "2", "Freeze frame trouble code", new SaeJ1979ResponseParser() {
            @Override
            public String getResult() {
                throw new RuntimeException("ÿet to implement");//return "" + (BCD encoded, See below.);
            }
        })
    };
}
