package com.ar.myfirstapp.obd2.saej1979;

/*"
 " Created by Arun Soman on 3/3/2017.
 "*/

import com.ar.myfirstapp.obd2.*;
import com.ar.myfirstapp.obd2.Mode1Pid01Parser;
import com.ar.myfirstapp.obd2.parser.Parser;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Mode1 extends Mode {


    public static final Map<String, Command> commands = new HashMap<>();
    static {
    commands.put("00", new Command("1", "0\r", "PIDs supported [01 - 20]",  new Parser(){

                @Override
                public void parse(Command command) {
                    byte[] rawResp = command.getRawResp();
                    String str = ASCIIUtils.toString(rawResp);
                    header = "41 00";
                    String str2 = getRespValue(str).replace(" ", "");
                    BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(str2, 16)});
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < bitSet.length(); j++) {
                        if (bitSet.get(j))
                            sb.append(Integer.toHexString(j)).append(' ');
                    }
                    command.setResult(sb.toString());
                }
            }));
            commands.put("01", new Command("1", "1",
                    "Monitor status since DTCs cleared. (Includes malfunction indicator lamp (MIL) status and number of DTCs.)",
                    new Mode1Pid01Parser()));

            commands.put("02", new Command("1", "2", "Freeze DTC", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//";
                }
            }));
            commands.put("03", new Command("1", "3", "Fuel system status", new SaeJ1979ResponseParser() {

                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    String str = null;
                    switch (A) {
                        case 1:
                            str = " Open loop due to insufficient engine temperature";
                            break;
                        case 2:
                            str = "  Closed loop, using oxygen sensor feedback to determine fuel mix";
                            break;
                        case 4:
                            str = "  Open loop due to engine load OR fuel cut due to deceleration";
                            break;
                        case 8:
                            str = "  Open loop due to system failure";
                            break;
                        case 16:
                            str = " Closed loop, using at least one oxygen sensor but there is a fault in the feedback system";
                            break;
                        default:
                            command.setResponseStatus(Command.ResponseStatus.BadResponse);
                            return;

                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(str);
                }
            }));
            commands.put("04", new Command("1", "4", "Calculated engine load value0100%", new Aby2point55Parser()));
            commands.put("05", new Command("1", "5", "Engine coolant temperature-40215°C", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (A - 40);
                }
            }));
            commands.put("06", new Command("1", "6", "Short term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A - 128) * 100 / 128);
                }
            }));
            commands.put("07", new Command("1", "7", "Long term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A - 128) * 100 / 128);
                }
            }));
            commands.put("08", new Command("1", "8", "Short term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A - 128) * 100 / 128);
                }
            }));
            commands.put("09", new Command("1", "9", "Long term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A - 128) * 100 / 128);
                }
            }));
            commands.put("0A", new Command("1", "0A", "Fuel pressure0765kPa (gauge)", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (A * 3);
                }
            }));
            commands.put("0B", new Command("1", "0B", "Intake manifold absolute pressure0255kPa (absolute)", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (A);
                }
            }));
            commands.put("0C", new Command("1", "0C", "Engine RPM016383.75rpm", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (((A * 256) + B) / 4);
                }
            }));
            commands.put("0D", new Command("1", "0D", "Vehicle speed0255km/h", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (A);
                }
            }));
            commands.put("0E", new Command("1", "0E", "Timing advance-6463.5° relative to #1 cylinder", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A / 2) -64);
                }
            }));
            commands.put("0F", new Command("1", "0F", "Intake air temperature-40215°C", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (A - 40);
                }
            }));
            commands.put("10", new Command("1", "10", "MAF air flow rate0655.35g/s", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + (((A * 256) + B) / 100);
                }
            }));
            commands.put("11", new Command("1", "11", "Throttle position0100%", new Aby2point55Parser()));
            commands.put("12", new Command("1", "12", "Commanded secondary air status", new Mide1Pid12Parser()));
            commands.put("13", new Command("1", "13", "Oxygen sensors present", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//throw new RuntimeException("yet to implement");//" + ([A0..A3] ==Bank 1, Sensors 1 - 4.[A4..A7] ==Bank 2…);
                }
            }));
            commands.put("14", new Command("1", "14", "Bank 1, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("15", new Command("1", "15", "Bank 1, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("16", new Command("1", "16", "Bank 1, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("17", new Command("1", "17", "Bank 1, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("18", new Command("1", "18", "Bank 2, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("19", new Command("1", "19", "Bank 2, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("1A", new Command("1", "1A", "Bank 2, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("1B", new Command("1", "1B", "Bank 2, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser()));
            commands.put("1C", new Command("1", "1C", "OBD standards this vehicle conforms to", new Mide1Pid1CParser()));
            commands.put("1D", new Command("1", "1D", "Oxygen sensors present", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");// throw new RuntimeException("yet to implement");//" + (Similar to PID 13, but[A0..A7] == [
                    //B1S1, B1S2, B2S1, B2S2, B3S1, B3S2, B4S1, B4S2]);
                }
            }));
            commands.put("1E", new Command("1", "1E", "Auxiliary input status", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//throw new RuntimeException("yet to implement");//" + (A0 == Power Take Off (PTO) status(1 == active)[A1..A7]not used);
                }
            }));
            commands.put("1F", new Command("1", "1F", "Run time since engine start065535seconds", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A * 256) + B);
                }
            }));
            commands.put("20", new Command("1", "20", "PIDs supported 21-40", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//throw new RuntimeException("yet to implement");//" + (Bit encoded[A7..D0] == [PID 0x21..PID 0x40]);
                }
            }));
            commands.put("21", new Command("1", "21", "Distance traveled with malfunction indicator lamp (MIL) on065535km", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((A * 256) + B);
                }
            }));
            commands.put("22", new Command("1", "22", "Fuel Rail Pressure (relative to manifold vacuum)05177.265kPa", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("yet to implement");//" + ((((A * 256) + B) * 10) / 128);
                }
            }));
            commands.put("23", new Command("1", "23", "Fuel Rail Pressure (diesel)0655350kPa (gauge)", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {

                    if(argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (((A * 256) + B) * 10));
                }
            }));
            commands.put("24", new Command("1", "24", "O2S1_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("25", new Command("1", "25", "O2S2_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("26", new Command("1", "26", "O2S3_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("27", new Command("1", "27", "O2S4_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("28", new Command("1", "28", "O2S5_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("29", new Command("1", "29", "O2S6_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("2A", new Command("1", "2A", "O2S7_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("2B", new Command("1", "2B", "O2S8_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser()));
            commands.put("2C", new Command("1", "2C", "Commanded EGR0100%", new Aby2point55Parser()));
            commands.put("2D", new Command("1", "2D", "EGR Error-10099.22%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if(argLen != 1) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + ((A - 128) * 100 / 128));
                }
            }));
            commands.put("2E", new Command("1", "2E", "Commanded evaporative purge0100%", new Aby2point55Parser()));
            commands.put("2F", new Command("1", "2F", "Fuel Level Input0100%", new Aby2point55Parser()));
            commands.put("30", new Command("1", "30", "# of warm-ups since codes cleared0255N/A", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 1) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (A));
                }}
            ));
            commands.put("31", new Command("1", "31", "Distance traveled since codes cleared065535km", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (((A * 256) + B)));
                }
            }));
            commands.put("32", new Command("1", "32", "Evap. System Vapor Pressure-81928192Pa", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (((A * 256) + B) / 4 ));
                }
            }));
            commands.put("33", new Command("1", "33", "Barometric pressure0255kPa (Absolute)", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (A));
                }
            }));
            commands.put("34", new Command("1", "34", "O2S1_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("35", new Command("1", "35", "O2S2_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("36", new Command("1", "36", "O2S3_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("37", new Command("1", "37", "O2S4_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("38", new Command("1", "38", "O2S5_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("39", new Command("1", "39", "O2S6_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("3A", new Command("1", "3A", "O2S7_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("3B", new Command("1", "3B", "O2S8_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128()));
            commands.put("3C", new Command("1", "3C", "Catalyst TemperatureBank 1, Sensor 1-406513.5°C", new ParserAtimes256plusBby10minus40()));
            commands.put("3D", new Command("1", "3D", "Catalyst TemperatureBank 2, Sensor 1-406513.5°C", new ParserAtimes256plusBby10minus40()));
            commands.put("3E", new Command("1", "3E", "Catalyst TemperatureBank 1, Sensor 2-406513.5°C", new ParserAtimes256plusBby10minus40()));
            commands.put("3F", new Command("1", "3F", "Catalyst TemperatureBank 2, Sensor 2-406513.5°C", new ParserAtimes256plusBby10minus40()));
            commands.put("40", new Command("1", "40", "PIDs supported 41-60", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("ÿet to implement");//throw new RuntimeException("yet to implement");//" + (Bit encoded[A7..D0] == [PID 0x41..PID 0x60]);
                }
            }));
            commands.put("41", new Command("1", "41", "Monitor status this drive cycle", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    throw new RuntimeException("ÿet to implement");//throw new RuntimeException("yet to implement");//" + (Bit encoded. See below.);
                }
            }));
            commands.put("42", new Command("1", "42", "Control module voltage065.535V", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" +(((A * 256) + B) / 1000));
                }
            }));
            commands.put("43", new Command("1", "43", "Absolute load value025700%", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + ((((A * 256) + B) * 100 / 255)));
                }
            }));
            commands.put("44", new Command("1", "44", "Command equivalence ratio02N/A", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (((A * 256) + B) / 32768));
                    //throw new RuntimeException("yet to implement");//" + ;
                }
            }));
            commands.put("45", new Command("1", "45", "Relative throttle position0100%", new Aby2point55Parser()));
            commands.put("46", new Command("1", "46", "Ambient air temperature-40215°C", new SaeJ1979ResponseParser() {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 1) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (A -40));//throw new RuntimeException("yet to implement");//" + (A - 40);
                }
            }));
            commands.put("47", new Command("1", "47", "Absolute throttle position B0100%", new Aby2point55Parser()));
            commands.put("48", new Command("1", "48", "Absolute throttle position C0100%", new Aby2point55Parser()));
            commands.put("49", new Command("1", "49", "Accelerator pedal position D0100%", new Aby2point55Parser()));
            commands.put("4A", new Command("1", "4A", "Accelerator pedal position E0100%", new Aby2point55Parser()));
            commands.put("4B", new Command("1", "4B", "Accelerator pedal position F0100%", new Aby2point55Parser()));
            commands.put("4C", new Command("1", "4C", "Commanded throttle actuator0100%", new Aby2point55Parser()));
            commands.put("4D", new Command("1", "4D", "Time run with MIL on065535minutes", new Parser256AplusB()));
            commands.put("4E", new Command("1", "4E", "Time since trouble codes cleared065535minutes", new Parser256AplusB()));
            commands.put("51", new Command("1", "51", "Fuel Type", new Mide1Pid51Parser()));
            commands.put("52", new Command("1", "52", "Ethanol fuel %0100%", new Aby2point55Parser()));
            commands.put("53", new Command("1", "53", "Absoulute Evap system Vapour Pressure0327675kpa", null));
            commands.put("C3", new Command("1", "C3", "????", null));
            commands.put("C4", new Command("1", "C4", "????", null));
    };

    public Mode1(String modeId) {
        super(modeId);
    }


    public static Command getCommand(String index) {
        return commands.get(index);
    }

    private static class Aby2point55Parser extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 1) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
                return;
            }
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(""+(A/2.55));
        }
    }

    private static class O2Parser extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 4) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
                return;
            }
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(""+(((A * 256) + B) *2/ 65536 *((C * 256) + D)*2 / 65536 ));
        }
    }
    private static class O2SensorParser extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 2) {
                if (B == 0xFF){
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
                command.setResult(""+((A/100)*(B/1.28) - 100));
                command.setResponseStatus(Command.ResponseStatus.Ok);
            }
        }
    }
    private static class ParserCplusDby256minus128 extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 4) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
                return;
            }
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(""+(C+(D/256)-128));
        }
    }

    private static class Parser256AplusB extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 2) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
                return;
            }
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(""+(256*A+B));
        }
    }
    private static class ParserAtimes256plusBby10minus40 extends SaeJ1979ResponseParser{
        @Override
        public void setResult(Command command, int argLen) {
            if(argLen != 2) {
                command.setResponseStatus(Command.ResponseStatus.Unknown);
                return;
            }
            command.setResponseStatus(Command.ResponseStatus.Ok);
            command.setResult(""+((((A * 256) + B) / 10) -40));
        }
    }
}
