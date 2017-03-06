package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

/**
 * Created by arunsoman on 04/03/17.
 */

public class Mode9 extends Mode {
    public static final Command[] commands = {

            new Command("9", "0", "mode 9 supported PIDs 01 to 20", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (Bit encoded);
                }
            }),
            new Command("9", "2", "Vehicle identification number (VIN)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (Returns 5 lines, A is line ordering flag, B -E ASCII coded VIN digits.);
                }
            }),
            new Command("9", "4", "calibration ID", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (Returns multiple lines, ASCII coded);
                }
            }),
            new Command("9", "6", "calibration", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),

    };

    public Mode9() {
        super("09 ");
    }

    @Override
    protected Command getCommand(int index) {
        return commands[index];
    }
}
