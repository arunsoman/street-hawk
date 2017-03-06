package com.ar.myfirstapp.obd2.saej1979;

/*"
 " Created by Arun Soman on 3/3/2017.
 "*/

import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.LineReader;
import com.ar.myfirstapp.obd2.ResponseHandler;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.m1Pid1ResponseHandler;

public class Mode1 extends Mode {


    public static final Command commands[] = {
            new Command("1", "0", "PIDs supported [01 - 20]",  new ResponseHandler(){
                String result=null;
                @Override
                public void parse(InputStream is) throws IOException, BadResponseException {
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    LineReader lineReader = new LineReader(is);
                    while ((line = lineReader.nextLine()) != null) {
                        line.replaceAll("\\s+", "");
                        BitSet bitSet = BitSet.valueOf(new long[]{Long.parseLong(line, 16)});
                        for (int i = 0; i < bitSet.length(); i++) {
                            if (bitSet.get(i))
                                sb.append(i).append(',');
                        }
                        sb.append('\n');
                    }
                    result = sb.toString();
                }

                @Override
                public String getResult() {
                    return result;
                }
            }),
            new Command("1", "1", "Monitor status since DTCs cleared. (Includes malfunction indicator lamp (MIL) status and number of DTCs.)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (Bit encoded. See below.);
                }
            }),
            new Command("1", "2", "Freeze DTC", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "";
                }
            }),
            new Command("1", "3", "Fuel system status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (Bit encoded. See below.);
                }
            }),
            new Command("1", "4", "Calculated engine load value0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "5", "Engine coolant temperature-40215°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A - 40);
                }
            }),
            new Command("1", "6", "Short term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A - 128) * 100 / 128);
                }
            }),
            new Command("1", "7", "Long term fuel % trim—Bank 1-100 (Rich)99.22 (Lean)%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A - 128) * 100 / 128);
                }
            }),
            new Command("1", "8", "Short term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A - 128) * 100 / 128);
                }
            }),
            new Command("1", "9", "Long term fuel % trim—Bank 2-100 (Rich)99.22 (Lean)%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A - 128) * 100 / 128);
                }
            }),
            new Command("1", "0A", "Fuel pressure0765kPa (gauge)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 3);
                }
            }),
            new Command("1", "0B", "Intake manifold absolute pressure0255kPa (absolute)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A);
                }
            }),
            new Command("1", "0C", "Engine RPM016383.75rpm", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 4);
                }
            }),
            new Command("1", "0D", "Vehicle speed0255km/h", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A);
                }
            }),
            new Command("1", "0E", "Timing advance-6463.5° relative to #1 cylinder", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A / 2) -64);
                }
            }),
            new Command("1", "0F", "Intake air temperature-40215°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A - 40);
                }
            }),
            new Command("1", "10", "MAF air flow rate0655.35g/s", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 100);
                }
            }),
            new Command("1", "11", "Throttle position0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "12", "Commanded secondary air status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (Bit encoded. See below.);
                }
            }),
            new Command("1", "13", "Oxygen sensors present", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + ([A0..A3] ==Bank 1, Sensors 1 - 4.[A4..A7] ==Bank 2…);
                }
            }),
            new Command("1", "14", "Bank 1, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005 *(B - 128) * 100 / 128);
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "15", "Bank 1, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005 *(B - 128) * 100 / 128); 
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "16", "Bank 1, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005 * (B - 128) * 100 / 128);
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "17", "Bank 1, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005* (B - 128) * 100 / 128); 
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "18", "Bank 2, Sensor 1:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005 *(B - 128) * 100 / 128);
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "19", "Bank 2, Sensor 2:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)return "" + (A * 0.005 *(B - 128) * 100 / 128 );
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "1A", "Bank 2, Sensor 3:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)
                    return "" + (A * 0.005 *(B - 128) * 100 / 128 );
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "1B", "Bank 2, Sensor 4:Oxygen sensor voltage,0-100(lean)1.27599.2(rich)Volts%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    if (B == 0xFF)
                    return "" + (A * 0.005 *(B - 128) * 100 / 128 );
                    return "";
                }
            }),
            new Command("", "", "Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "1C", "OBD standards this vehicle conforms to", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (Bit encoded. See below.);
                }
            }),
            new Command("1", "1D", "Oxygen sensors present", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");// return "" + (Similar to PID 13, but[A0..A7] == [
                    //B1S1, B1S2, B2S1, B2S2, B3S1, B3S2, B4S1, B4S2]);
                }
            }),
            new Command("1", "1E", "Auxiliary input status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (A0 == Power Take Off (PTO) status(1 == active)[A1..A7]not used);
                }
            }),
            new Command("1", "1F", "Run time since engine start065535seconds", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A * 256) + B);
                }
            }),
            new Command("1", "20", "PIDs supported 21-40", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");//return "" + (Bit encoded[A7..D0] == [PID 0x21..PID 0x40]);
                }
            }),
            new Command("1", "21", "Distance traveled with malfunction indicator lamp (MIL) on065535km", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A * 256) + B);
                }
            }),
            new Command("1", "22", "Fuel Rail Pressure (relative to manifold vacuum)05177.265kPa", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((((A * 256) + B) * 10) / 128);
                }
            }),
            new Command("1", "23", "Fuel Rail Pressure (diesel)0655350kPa (gauge)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) * 10);
                }
            }),
            new Command("1", "24", "O2S1_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "25", "O2S2_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "26", "O2S3_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768* ((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "27", "O2S4_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "28", "O2S5_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "29", "O2S6_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "2A", "O2S7_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "2B", "O2S8_WR_lambda(1):Equivalence Ratio028N/AV", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768 *((C * 256) + D) / 8192);
                }
            }),
            new Command("", "", "Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "2C", "Commanded EGR0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (100 * A / 255);
                }
            }),
            new Command("1", "2D", "EGR Error-10099.22%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A - 128) * 100 / 128);
                }
            }),
            new Command("1", "2E", "Commanded evaporative purge0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (100 * A / 255);
                }
            }),
            new Command("1", "2F", "Fuel Level Input0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (100 * A / 255);
                }
            }),
            new Command("1", "30", "# of warm-ups since codes cleared0255N/A", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A);
                }
            }),
            new Command("1", "31", "Distance traveled since codes cleared065535km", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A * 256) + B);
                }
            }),
            new Command("1", "32", "Evap. System Vapor Pressure-81928192Pa", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 4 );
                }
            }),
            new Command("1", "33", "Barometric pressure0255kPa (Absolute)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A);
                }
            }),
            new Command("1", "34", "O2S1_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "35", "O2S2_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "36", "O2S3_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 327685)* (((C * 256) + D) / 256 ))-128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "37", "O2S4_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "38", "O2S5_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "39", "O2S6_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" +(((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "3A", "O2S7_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "3B", "O2S8_WR_lambda(1):Equivalence Ratio0-1282128N/AmA", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((((A * 256) + B) / 32768)* ((C * 256) + D) / 256) - 128);
                }
            }),
            new Command("", "", "Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("yet to implement");
                }
            }),
            new Command("1", "3C", "Catalyst TemperatureBank 1, Sensor 1-406513.5°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((((A * 256) + B) / 10)-40);
                }
            }),
            new Command("1", "3D", "Catalyst TemperatureBank 2, Sensor 1-406513.5°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((((A * 256) + B) / 10)-40);
                }
            }),
            new Command("1", "3E", "Catalyst TemperatureBank 1, Sensor 2-406513.5°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((((A * 256) + B) / 10) -40);
                }
            }),
            new Command("1", "3F", "Catalyst TemperatureBank 2, Sensor 2-406513.5°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((((A * 256) + B) / 10)-40);
                }
            }),
            new Command("1", "40", "PIDs supported 41-60", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (Bit encoded[A7..D0] == [PID 0x41..PID 0x60]);
                }
            }),
            new Command("1", "41", "Monitor status this drive cycle", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (Bit encoded. See below.);
                }
            }),
            new Command("1", "42", "Control module voltage065.535V", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 1000);
                }
            }),
            new Command("1", "43", "Absolute load value025700%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) * 100 / 255);
                }
            }),
            new Command("1", "44", "Command equivalence ratio02N/A", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (((A * 256) + B) / 32768);
                }
            }),
            new Command("1", "45", "Relative throttle position0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "46", "Ambient air temperature-40215°C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A - 40);
                }
            }),
            new Command("1", "47", "Absolute throttle position B0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "48", "Absolute throttle position C0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "49", "Accelerator pedal position D0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "4A", "Accelerator pedal position E0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "4B", "Accelerator pedal position F0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "4C", "Commanded throttle actuator0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "4D", "Time run with MIL on065535minutes", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A * 256) + B);
                }
            }),
            new Command("1", "4E", "Time since trouble codes cleared065535minutes", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + ((A * 256) + B);
                }
            }),
            new Command("1", "51", "Fuel Type", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (From fuel type table see below);
                }
            }),
            new Command("1", "52", "Ethanol fuel %0100%", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return "" + (A * 100 / 255);
                }
            }),
            new Command("1", "53", "Absoulute Evap system Vapour Pressure0327675kpa", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (1 / 200 per bit);
                }
            }),
            new Command("1", "C3", "????", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    //return "" + (Returns numerous data, including Drive Condition ID and Engine Speed*);
                    throw new RuntimeException("ÿet to implement");
                }
            }),
            new Command("1", "C4", "????", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    throw new RuntimeException("ÿet to implement");//return "" + (B5 is Engine Idle RequestB6 is Engine Stop Request*);
                }
            }),
    };

    public Mode1() {
        super("1");
    }

    protected Mode1(String id) {
        super(id);
    }

    @Override
    protected Command getCommand(int index) {
        return commands[index];
    }
}
