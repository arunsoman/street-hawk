package com.ar.myfirstapp.obd2.saej1979;

/*"
 " Created by Arun Soman on 3/3/2017.
 "*/

import com.ar.myfirstapp.obd2.*;
import com.ar.myfirstapp.obd2.Mode1Pid01Parser;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Mode1 implements Mode {


    public static final Map<String, Command> commands = new HashMap<>();
    static {
    commands.put("00", new Command("01", "00\r", "PIDs supported [01 - 20]", true,  new SaeJ1979ResponseParser("41 00 "){

                @Override
                public void parse(Command command) {
                    String str = validate(command);
                    if(str == null)
                        return;
                    String str2 = str.replace(" ", "");
                    BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(str2, 16)});
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < bitSet.length(); j++) {
                        if (bitSet.get(j)) {
                            sb.append(Integer.toHexString(j)).append(' ');
                        }
                    }
                    command.setResult(sb.toString());
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                }

        @Override
        public void setResult(Command command, int argLen) {

        }
    }));
            commands.put("1", new Command("01", "01",
                    "Monitor status since DTCs cleared. (Includes malfunction indicator lamp (MIL) status and number of DTCs.)",
                    new Mode1Pid01Parser()));

            commands.put("2", new Command("01", "02", "Freeze DTC", new SaeJ1979ResponseParser("41 02 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResult("yet to implement");//";
                }
            }));
            commands.put("3", new Command("01", "03", "Fuel system status", new SaeJ1979ResponseParser("41 03 ") {

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
            commands.put("4", new Command("01", "04", "Calculated engine load value0100%", new Aby2point55Parser("41 04 ")));
            commands.put("5", new Command("01", "05", "Engine coolant temperature-40215°C", new SaeJ1979ResponseParser("41 05 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+(A - 40));
                }
            }));
            commands.put("6", new Command("01", "06", "Short term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser("41 06 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A - 128) * 100 / 128));
                }
            }));
            commands.put("7", new Command("01", "07", "Long term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser("41 07 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A - 128) * 100 / 128));
                }
            }));
            commands.put("8", new Command("01", "08", "Short term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser("41 08") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A - 128) * 100 / 128));
                }
            }));
            commands.put("9", new Command("01", "09", "Long term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979ResponseParser("41 09 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A - 128) * 100 / 128));
                }
            }));
            commands.put("A", new Command("01", "0A", "Fuel pressure0765kPa (gauge)", new SaeJ1979ResponseParser("41 0A ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ (A * 3));
                }
            }));
            commands.put("B", new Command("01", "0B", "Intake manifold absolute pressure0255kPa (absolute)", new SaeJ1979ResponseParser("41 0B ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ (A));
                }
            }));
            commands.put("C", new Command("01", "0C\r", "Engine RPM016383.75rpm", new SaeJ1979ResponseParser("41 0C ") {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResult(""+((A * 256) + B) / 4);
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                }
            }));
            commands.put("D", new Command("01", "0D\r", "Vehicle speed0255km/h", new SaeJ1979ResponseParser("41 0D ") {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 1) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResult(""+A);
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                }
            }));
            commands.put("E", new Command("01", "0E", "Timing advance-6463.5° relative to #1 cylinder", new SaeJ1979ResponseParser("41 0E ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A / 2) -64));
                }
            }));
            commands.put("F", new Command("01", "0F", "Intake air temperature-40215°C", new SaeJ1979ResponseParser("41 0F ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ (A - 40));
                }
            }));
            commands.put("10", new Command("01", "10", "MAF air flow rate0655.35g/s", new SaeJ1979ResponseParser("41 10 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ (((A * 256) + B) / 100));
                }
            }));
            commands.put("11", new Command("01", "11", "Throttle position0100%", new Aby2point55Parser("41 11 ")));
            commands.put("12", new Command("01", "12", "Commanded secondary air status", new Mode1Pid12Parser()));
            commands.put("13", new Command("01", "13", "Oxygen sensors present", new SaeJ1979ResponseParser("41 13 ") {
                @Override
                public void parse(Command command) {
                    String str = validate(command);
                    if(str == null)
                        return;
                    command.setResult(str);
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                }

                @Override
                public void setResult(Command command, int argLen) {
//                    command.setResult("yet to implement");//command.setResult(""+ ([A0..A3] ==Bank 1, Sensors 1 - 4.[A4..A7] ==Bank 2…);
                }
            }));
            commands.put("14", new Command("01", "14", "Bank 1, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 14 ")));
            commands.put("15", new Command("01", "15", "Bank 1, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 15 ")));
            commands.put("16", new Command("01", "16", "Bank 1, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 16 ")));
            commands.put("17", new Command("01", "17", "Bank 1, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 17 ")));
            commands.put("18", new Command("01", "18", "Bank 2, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 18 ")));
            commands.put("19", new Command("01", "19", "Bank 2, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 19 ")));
            commands.put("1A", new Command("01", "1A", "Bank 2, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 1A ")));
            commands.put("1B", new Command("01", "1B", "Bank 2, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new O2SensorParser("41 1B ")));
            commands.put("1C", new Command("01", "1C", "OBD standards this vehicle conforms to", new Mode1Pid1CParser()));
            commands.put("1D", new Command("01", "1D", "Oxygen sensors present", new SaeJ1979ResponseParser("41 1D ") {
                @Override
                public void parse(Command command) {
                    String str = validate(command);

                }
                
                @Override
                public void setResult(Command command, int argLen) {
                //    command.setResult("yet to implement");// command.setResult(""+ (Similar to PID 13, but[A0..A7] == [
                    //B1S1, B1S2, B2S1, B2S2, B3S1, B3S2, B4S1, B4S2]);
                }
            }));
            commands.put("1E", new Command("01", "1E", "Auxiliary input status", new SaeJ1979ResponseParser("41 1E ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResult("yet to implement");//command.setResult(""+ (A0 == Power Take Off (PTO) status(1 == active)[A1..A7]not used);
                }
            }));
            commands.put("1F", new Command("01", "1F", "Run time since engine start065535seconds", new SaeJ1979ResponseParser("41 1F ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A * 256) + B));
                }
            }));
            commands.put("20", new Command("01", "20", "PIDs supported 21-40", true, new SaeJ1979ResponseParser("41 20 ") {
                @Override
                public void parse(Command command) {
                    String str = validate(command);
                    if(str == null)
                        return;
                    String str2 = str.replace(" ", "");
                    BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(str2, 16)});
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < bitSet.length(); j++) {
                        if(j <= 9 )
                            sb.append('0');
                        if (bitSet.get(j)) {
                            sb.append(Integer.toHexString(0x20+j)).append(' ');
                        }
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(sb.toString());
                }
                
                @Override
                public void setResult(Command command, int argLen) {
                //    command.setResult("yet to implement");//command.setResult(""+ (Bit encoded[A7..D0] == [PID 0x21..PID 0x40]);
                }
            }));
            commands.put("21", new Command("01", "21", "Distance traveled with malfunction indicator lamp (MIL) on065535km", new SaeJ1979ResponseParser("41 21 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((A * 256) + B));
                }
            }));
            commands.put("22", new Command("01", "22", "Fuel Rail Pressure (relative to manifold vacuum)05177.265kPa", new SaeJ1979ResponseParser("41 22 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult(""+ ((((A * 256) + B) * 10) / 128));
                }
            }));
            commands.put("23", new Command("01", "23", "Fuel Rail Pressure (diesel)0655350kPa (gauge)", new SaeJ1979ResponseParser("41 23 ") {
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
            commands.put("24", new Command("01", "24", "O2S1_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 24 ")));
            commands.put("25", new Command("01", "25", "O2S2_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 25 ")));
            commands.put("26", new Command("01", "26", "O2S3_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 26 ")));
            commands.put("27", new Command("01", "27", "O2S4_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 27 ")));
            commands.put("28", new Command("01", "28", "O2S5_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 28 ")));
            commands.put("29", new Command("01", "29", "O2S6_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 29 ")));
            commands.put("2A", new Command("01", "2A", "O2S7_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 2A ")));
            commands.put("2B", new Command("01", "2B", "O2S8_WR_lambda(1):Equivalence Ratio028N/AV", new O2Parser("41 2B ")));
            commands.put("2C", new Command("01", "2C", "Commanded EGR0100%", new Aby2point55Parser("41 2C ")));
            commands.put("2D", new Command("01", "2D", "EGR Error-10099.22%", new SaeJ1979ResponseParser("41 2D ") {
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
            commands.put("2E", new Command("01", "2E", "Commanded evaporative purge0100%", new Aby2point55Parser("41 2E ")));
            commands.put("2F", new Command("01", "2F", "Fuel Level Input0100%", new Aby2point55Parser("41 2F ")));
            commands.put("30", new Command("01", "30", "# of warm-ups since codes cleared0255N/A", new SaeJ1979ResponseParser("41 30 ") {
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
            commands.put("31", new Command("01", "31", "Distance traveled since codes cleared065535km", new SaeJ1979ResponseParser("41 31 ") {
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
            commands.put("32", new Command("01", "32", "Evap. System Vapor Pressure-81928192Pa", new SaeJ1979ResponseParser("41 32 ") {
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
            commands.put("33", new Command("01", "33", "Barometric pressure0255kPa (Absolute)", new SaeJ1979ResponseParser("41 33 ") {
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
            commands.put("34", new Command("01", "34", "O2S1_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 34 ")));
            commands.put("35", new Command("01", "35", "O2S2_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 35 ")));
            commands.put("36", new Command("01", "36", "O2S3_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 36 ")));
            commands.put("37", new Command("01", "37", "O2S4_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 37 ")));
            commands.put("38", new Command("01", "38", "O2S5_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 38 ")));
            commands.put("39", new Command("01", "39", "O2S6_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 39 ")));
            commands.put("3A", new Command("01", "3A", "O2S7_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 3A ")));
            commands.put("3B", new Command("01", "3B", "O2S8_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new ParserCplusDby256minus128("41 3B ")));
            commands.put("3C", new Command("01", "3C", "Catalyst TemperatureBank 1, Sensor 1-406513.5°C", new ParserAtimes256plusBby10minus40("41 3C ")));
            commands.put("3D", new Command("01", "3D", "Catalyst TemperatureBank 2, Sensor 1-406513.5°C", new ParserAtimes256plusBby10minus40("41 3D ")));
            commands.put("3E", new Command("01", "3E", "Catalyst TemperatureBank 1, Sensor 2-406513.5°C", new ParserAtimes256plusBby10minus40("41 3E ")));
            commands.put("3F", new Command("01", "3F", "Catalyst TemperatureBank 2, Sensor 2-406513.5°C", new ParserAtimes256plusBby10minus40("41 3F ")));
            commands.put("40", new Command("01", "40", "PIDs supported 41-60", true, new SaeJ1979ResponseParser("41 40 ") {

                @Override
                public void parse(Command command) {
                    String str = validate(command);
                    if(str == null)
                        return;
                    String str2 = str.replace(" ", "");
                    BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(str2, 16)});
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < bitSet.length(); j++) {
                        if (bitSet.get(j)) {
                            sb.append(Integer.toHexString(0x40+j)).append(' ');
                        }
                    }
                    command.setResult(sb.toString());
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                }
                @Override
                public void setResult(Command command, int argLen) {
                }
            }));
            commands.put("41", new Command("01", "41", "Monitor status this drive cycle", new SaeJ1979ResponseParser("41 41 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    command.setResult("ÿet to implement");//command.setResult(""+ (Bit encoded. See below.);
                }
            }));
            commands.put("42", new Command("01", "42", "Control module voltage065.535V", new SaeJ1979ResponseParser("41 42 ") {
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
            commands.put("43", new Command("01", "43", "Absolute load value025700%", new SaeJ1979ResponseParser("41 43 ") {
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
            commands.put("44", new Command("01", "44", "Command equivalence ratio02N/A", new SaeJ1979ResponseParser("41 44 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 2) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (((A * 256) + B) / 32768));
                    //command.setResult(""+ ;
                }
            }));
            commands.put("45", new Command("01", "45", "Relative throttle position0100%", new Aby2point55Parser("41 45 ")));
            commands.put("46", new Command("01", "46", "Ambient air temperature-40215°C", new SaeJ1979ResponseParser("41 46 ") {
                @Override
                public void setResult(Command command, int argLen) {
                    if (argLen != 1) {
                        command.setResponseStatus(Command.ResponseStatus.Unknown);
                        return;
                    }
                    command.setResponseStatus(Command.ResponseStatus.Ok);
                    command.setResult("" + (A -40));//command.setResult(""+ (A - 40);
                }
            }));
            commands.put("47", new Command("01", "47", "Absolute throttle position B0100%", new Aby2point55Parser("41 47 ")));
            commands.put("48", new Command("01", "48", "Absolute throttle position C0100%", new Aby2point55Parser("41 48 ")));
            commands.put("49", new Command("01", "49", "Accelerator pedal position D0100%", new Aby2point55Parser("41 49 ")));
            commands.put("4A", new Command("01", "4A", "Accelerator pedal position E0100%", new Aby2point55Parser("41 4A ")));
            commands.put("4B", new Command("01", "4B", "Accelerator pedal position F0100%", new Aby2point55Parser("41 4B ")));
            commands.put("4C", new Command("01", "4C", "Commanded throttle actuator0100%", new Aby2point55Parser("41 4C ")));
            commands.put("4D", new Command("01", "4D", "Time run with MIL on065535minutes", new Parser256AplusB("41 4D ")));
            commands.put("4E", new Command("01", "4E", "Time since trouble codes cleared065535minutes", new Parser256AplusB("41 4E ")));
            commands.put("51", new Command("01", "51", "Fuel Type", new Mode1Pid51Parser("41 51 ")));
            commands.put("52", new Command("01", "52", "Ethanol fuel %0100%", new Aby2point55Parser("41 52 ")));
            commands.put("53", new Command("01", "53", "Absoulute Evap system Vapour Pressure0327675kpa", null));
            commands.put("C3", new Command("01", "C3", "????", null));
            commands.put("C4", new Command("01", "C4", "????", null));
    };


    public Command getCommand(String index) {
        return commands.get(index);
    }

    @Override
    public Command[] getDiscoveryCommands() {
        return new Command[]{commands.get("00"),
                commands.get("20"),
                commands.get("40"),
                commands.get("60"),
                commands.get("80")};
    }

    private static class Aby2point55Parser extends SaeJ1979ResponseParser{
        private Aby2point55Parser(String delim) {
            super( delim);
        }

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
        private O2Parser(String delim) {
            super( delim);
        }

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
        private O2SensorParser(String delim) {
            super( delim);
        }

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
        private ParserCplusDby256minus128(String delim) {
            super( delim);
        }

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
        private Parser256AplusB(String delim) {
            super( delim);
        }

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
        private ParserAtimes256plusBby10minus40(String delim) {
            super( delim);
        }

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
