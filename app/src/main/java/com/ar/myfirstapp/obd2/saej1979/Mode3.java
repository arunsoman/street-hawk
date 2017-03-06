package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

/**
 * Created by arunsoman on 04/03/17.
 */

public class Mode3 extends Mode {
   public Mode3(){
       super("03");
   }
    public Command[] commands = {new Command("3", "", "Request trouble codes", new SaeJ1979Response() {
        @Override
        public String getResult() {
            throw new RuntimeException("ÿet to implement");//return "" + (3 codes per message frame, BCD encoded. See below.);
        }
    })
    };
    @Override
    protected Command getCommand(int index) {
        return null;
    }
}
