package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

/**
 * Created by arunsoman on 04/03/17.
 */

public final class Mode5 extends Mode {

    private final static Command[] commands = {

            new Command("5", "100", "OBD Monitor IDs supported ($01 – $20)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("5", "101", "O2 Sensor Monitor Bank 1 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "102", "O2 Sensor Monitor Bank 1 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "103", "O2 Sensor Monitor Bank 1 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "104", "O2 Sensor Monitor Bank 1 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "105", "O2 Sensor Monitor Bank 2 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "106", "O2 Sensor Monitor Bank 2 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "107", "O2 Sensor Monitor Bank 2 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "108", "O2 Sensor Monitor Bank 2 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "109", "O2 Sensor Monitor Bank 3 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010A", "O2 Sensor Monitor Bank 3 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010B", "O2 Sensor Monitor Bank 3 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010C", "O2 Sensor Monitor Bank 3 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010D", "O2 Sensor Monitor Bank 4 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010E", "O2 Sensor Monitor Bank 4 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "010F", "O2 Sensor Monitor Bank 4 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "110", "O2 Sensor Monitor Bank 4 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Rich to lean sensor threshold voltage";
                }
            }),
            new Command("5", "201", "O2 Sensor Monitor Bank 1 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "202", "O2 Sensor Monitor Bank 1 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "203", "O2 Sensor Monitor Bank 1 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "204", "O2 Sensor Monitor Bank 1 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "205", "O2 Sensor Monitor Bank 2 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "206", "O2 Sensor Monitor Bank 2 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "207", "O2 Sensor Monitor Bank 2 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "208", "O2 Sensor Monitor Bank 2 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "209", "O2 Sensor Monitor Bank 3 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020A", "O2 Sensor Monitor Bank 3 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020B", "O2 Sensor Monitor Bank 3 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020C", "O2 Sensor Monitor Bank 3 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020D", "O2 Sensor Monitor Bank 4 Sensor 101.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020E", "O2 Sensor Monitor Bank 4 Sensor 201.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "020F", "O2 Sensor Monitor Bank 4 Sensor 301.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
            new Command("5", "210", "O2 Sensor Monitor Bank 4 Sensor 401.275Volts", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "0.005 Lean to Rich sensor threshold voltage";
                }
            }),
    };

    public Mode5() {
        super("05");
    }

    @Override
    protected Command getCommand(int index) {
        return commands[index];
    }
}
