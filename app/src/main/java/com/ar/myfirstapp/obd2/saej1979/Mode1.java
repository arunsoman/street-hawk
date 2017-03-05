package com.ar.myfirstapp.obd2.saej1979;

/*"
 " Created by Arun Soman on 3/3/2017.
 "*/

import com.ar.myfirstapp.obd2.BadResponseException;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.ResponseHandler;
import com.ar.myfirstapp.obd2.ResponseHandlerUtils;
import com.ar.myfirstapp.obd2.SaeJ1979Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;

import static com.ar.myfirstapp.obd2.ResponseHandlerUtils.m1Pid1;
import static java.lang.String.valueOf;

public class Mode1 extends Mode {


    public final static Command commands[] = {
            new Command("01", "00", "PIDs supported [01 - 20]", new ResponseHandler(){
                String result=null;
                @Override
                public void parse(InputStream is) throws IOException, BadResponseException {
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                        try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                            while ((line = bufferedReader.readLine()) != null) {
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
                    }
                }

                @Override
                public String getResult() {
                    return result;
                }
            }),
            new Command("01", "1",
                    "Monitor status since DTCs cleared. status and number of DTCs.)", m1Pid1 ),
            new Command("01", "2", "Freeze DTC", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3", "Fuel system status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4", "Calculated engine load", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5", "Engine coolant temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6", "Short term fuel trim—Bank 1", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7", "Long term fuel trim—Bank 1", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "8", "Short term fuel trim—Bank 2", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "9", "Long term fuel trim—Bank 2", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0A", "Fuel pressure (gauge pressure)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0B", "Intake manifold absolute pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0C", "Engine RPM", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0D", "Vehicle speed", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0E", "Timing advance", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "0F", "Intake air temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "10", "MAF air flow rate", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "11", "Throttle position", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "12", "Commanded secondary air status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "13", "Oxygen sensors present (in 2 banks)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "14", "Oxygen Sensor 1 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "15", "Oxygen Sensor 2 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "16", "Oxygen Sensor 3 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "17", "Oxygen Sensor 4 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "18", "Oxygen Sensor 5 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "19", "Oxygen Sensor 6 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1A", "Oxygen Sensor 7 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1B", "Oxygen Sensor 8 A: Voltage B: Short term fuel trim", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1C", "OBD standards this vehicle conforms to", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1D", "Oxygen sensors present (in 4 banks)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1E", "Auxiliary input status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "1F", "Run time since engine start", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "20", "PIDs supported [21 - 40]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "21", "Distance traveled with malfunction indicator lamp (MIL) on", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "22", "Fuel Rail Pressure (relative to manifold vacuum)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "23", "Fuel Rail Gauge Pressure (diesel, or gasoline direct injection)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "24", "Oxygen Sensor 1 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "25", "Oxygen Sensor 2 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "26", "Oxygen Sensor 3 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "27", "Oxygen Sensor 4 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "28", "Oxygen Sensor 5 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "29", "Oxygen Sensor 6 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2A", "Oxygen Sensor 7 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2B", "Oxygen Sensor 8 AB: Fuel–Air Equivalence Ratio CD: Voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2C", "Commanded EGR", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2D", "EGR Error", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2E", "Commanded evaporative purge", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "2F", "Fuel Tank Level Input", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "30", "Warm-ups since codes cleared", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "31", "Distance traveled since codes cleared", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "32", "Evap. System Vapor Pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "33", "Absolute Barometric Pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "34", "Oxygen Sensor 1    AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "35", "Oxygen Sensor 2 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "36", "Oxygen Sensor 3 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "37", "Oxygen Sensor 4 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "38", "Oxygen Sensor 5 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "39", "Oxygen Sensor 6 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3A", "Oxygen Sensor 7 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3B", "Oxygen Sensor 8 AB: Fuel–Air Equivalence Ratio CD: Current", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3C", "Catalyst Temperature: Bank 1, Sensor 1", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3D", "Catalyst Temperature: Bank 2, Sensor 1", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3E", "Catalyst Temperature: Bank 1, Sensor 2", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "3F", "Catalyst Temperature: Bank 2, Sensor 2", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "40", "PIDs supported [41 - 60]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "41", "Monitor status this drive cycle", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "42", "Control module voltage", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "43", "Absolute load value", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "44", "Fuel–Air commanded equivalence ratio", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "45", "Relative throttle position", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "46", "Ambient air temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "47", "Absolute throttle position B", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "48", "Absolute throttle position C", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "49", "Accelerator pedal position D", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4A", "Accelerator pedal position E", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4B", "Accelerator pedal position F", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4C", "Commanded throttle actuator", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4D", "Time run with MIL on", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4E", "Time since trouble codes cleared", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "4F", "Maximum value for Fuel–Air equivalence ratio, oxygen sensor voltage, oxygen sensor current, and intake manifold absolute pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "50", "Maximum value for air flow rate from mass air flow sensor", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "51", "Fuel Type", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "52", "Ethanol fuel %", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "53", "Absolute Evap system Vapor Pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "54", "Evap system vapor pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "55", "Short term secondary oxygen sensor trim, A: bank 1, B: bank 3", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "56", "Long term secondary oxygen sensor trim, A: bank 1, B: bank 3", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "57", "Short term secondary oxygen sensor trim, A: bank 2, B: bank 4", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "58", "Long term secondary oxygen sensor trim, A: bank 2, B: bank 4", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "59", "Fuel rail absolute pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5A", "Relative accelerator pedal position", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5B", "Hybrid battery pack remaining life", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5C", "Engine oil temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5D", "Fuel injection timing", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5E", "Engine fuel rate", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "5F", "Emission requirements to which vehicle is designed", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "60", "PIDs supported [61 - 80]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "61", "Driver's demand engine - percent torque", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "62", "Actual engine - percent torque", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "63", "Engine reference torque", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "64", "Engine percent torque data", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "65", "Auxiliary input / output supported", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "66", "Mass air flow sensor", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "67", "Engine coolant temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "68", "Intake air temperature sensor", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "69", "Commanded EGR and EGR Error", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6A", "Commanded Diesel intake air flow control and relative intake air flow position", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6B", "Exhaust gas recirculation temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6C", "Commanded throttle actuator control and relative throttle position", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6D", "Fuel pressure control system", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6E", "Injection pressure control system", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "6F", "Turbocharger compressor inlet pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "70", "Boost pressure control", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "71", "Variable Geometry turbo (VGT) control", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "72", "Wastegate control", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "73", "Exhaust pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "74", "Turbocharger RPM", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "75", "Turbocharger temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "76", "Turbocharger temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "77", "Charge air cooler temperature (CACT)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "78", "Exhaust Gas temperature (EGT) Bank 1", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "79", "Exhaust Gas temperature (EGT) Bank 2", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7A", "Diesel particulate filter (DPF)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7B", "Diesel particulate filter (DPF)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7C", "Diesel Particulate filter (DPF) temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7D", "NOx NTE (Not-To-Exceed) control area status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7E", "PM NTE (Not-To-Exceed) control area status", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "7F", "Engine run time", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "80", "PIDs supported [81 - A0]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "81", "Engine run time for Auxiliary Emissions Control Device(AECD)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "82", "Engine run time for Auxiliary Emissions Control Device(AECD)", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "83", "NOx sensor", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "84", "Manifold surface temperature", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "85", "NOx reagent system", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "86", "Particulate matter (PM) sensor", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "87", "Intake manifold absolute pressure", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "A0", "PIDs supported [A1 - C0]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "C0", "PIDs supported [C1 - E0]", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "C3", "?", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            }),
            new Command("01", "C4", "?", new SaeJ1979Response() {
                @Override
                public String getResult() {
                    return null;
                }
            })
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
