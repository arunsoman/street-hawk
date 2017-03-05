package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

/**
 * Created by arunsoman on 04/03/17.
 */

public class Mode9 extends Mode {
    public static final Command[] commands = {
            new Command("09 ", "0", "Mode 9 supported PIDs (01 to 20)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "1", "VIN Message Count in PID 02. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "2", "Vehicle Identification Number (VIN)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "3", "Calibration ID message count for PID 04. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "4", "Calibration ID", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "5", "Calibration verification numbers (CVN) message count for PID 06. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "6", "Calibration Verification Numbers (CVN)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "7", "In-use performance tracking message count for PID 08 and 0B. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "8", "In-use performance tracking for spark ignition vehicles", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "9", "ECU name message count for PID 0A", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "0A", "ECU name", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("09 ", "0B", "In-use performance tracking for compression ignition vehicles", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            })
    };

    public Mode9() {
        super("09 ");
    }

    @Override
    protected Command getCommand(int index) {
        return commands[index];
    }
}
