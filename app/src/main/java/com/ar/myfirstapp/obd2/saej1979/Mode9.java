package com.ar.myfirstapp.obd2.saej1979;

import com.ar.myfirstapp.obd2.ASCIIUtils;
import com.ar.myfirstapp.obd2.Command;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;


public class Mode9 implements Mode {
    public static final Map < String, Command > commands = new HashMap < > ();
    static {
        commands.put("00", new Command("09 ", "00", "List of PIDs supported (range 01h to 20h)", true, new SaeJ1979Mode9ResponseParser("91 00 ") {
            @Override public void parse(Command command) {
//                String str = validate(command);
                String str = ASCIIUtils.toString(command.getRawResp());
                str = str.replace("SEARCHING...","");
                str = str.replace("\r\n>...","");
                str = str.replace("\r>...","");
                if(str == null || str.trim().length() == 0) {
                    return;
                }
                if(str.endsWith(" 00 00 "))
                    str = str.substring(0, str.length()-7);
                    argLen = str.split(" ").length;
                if (argLen != 4) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
                if(str == null)
                    return;
                String str2 = str.replace(" ", "");
                BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(str2, 16)});
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < bitSet.length(); j++) {
                    if (bitSet.get(j))
                        sb.append(Integer.toHexString(j)).append(' ');
                }
                command.setResult(sb.toString());
                command.setResponseStatus(Command.ResponseStatus.Ok);
            }
        }));
        commands.put("1", new Command("09 ", "01", "VIN message count", new SaeJ1979Mode9ResponseParser("91 01 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 1) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("2", new Command("09 ", "02", "VIN (vehicle identification number)", new SaeJ1979Mode9ResponseParser("91 02 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen <= 17 && argLen >= 20) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("3", new Command("09 ", "03", "Calibration ID message count", new SaeJ1979Mode9ResponseParser("91 03 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 1) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("4", new Command("09 ", "04", "Calibration IDs", new SaeJ1979Mode9ResponseParser("91 04 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 16) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));;
        commands.put("5", new Command("09 ", "05", "CALIB verification numbers message count", new SaeJ1979Mode9ResponseParser("91 05 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 1) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("6", new Command("09 ", "06", "Calibration verification number", new SaeJ1979Mode9ResponseParser("91 06 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    return;
                }
                    argLen = str.split(" ").length;
                if (argLen != 4) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("7", new Command("09 ", "07", "IPT message count", new SaeJ1979Mode9ResponseParser("91 00") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 1) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("8", new Command("09 ", "08", "In-use performance tracking (IPT)", new SaeJ1979Mode9ResponseParser("91 07 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 4) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("9", new Command("09 ", "09", "ECU name message count", new SaeJ1979Mode9ResponseParser("91 09 ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 1) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("A", new Command("09 ", "0A", "ECU name", new SaeJ1979Mode9ResponseParser("91 0A ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 20) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));
        commands.put("B", new Command("09 ", "0B", "In-use performance tracking", new SaeJ1979Mode9ResponseParser("91 0B ") {
            @Override public void parse(Command command) {
                String str = validate(command);
                if(str == null) {
                    argLen = str.split(" ").length;
                    return;
                }
                if (argLen != 4) {
                    command.setResponseStatus(Command.ResponseStatus.Unknown);
                    return;
                }
            }
        }));

    }

    public Command getCommand(String index) {
        Command c = commands.get(index);
        return c;
    }

    @Override
    public Command[] getDiscoveryCommands() {
        return new Command[]{commands.get("00")};
    }
}