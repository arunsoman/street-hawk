package com.ar.myfirstapp.obd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;

/**
 * Created by arunsoman on 04/03/17.
 */

public class ResponseHandlerUtils {
    public static final ResponseHandler okResponse = new ResponseHandler() {
        private boolean status;
        private static final String ok = "ok";

        public void parse(InputStream is) throws IOException {
            int a = is.read();
            int b = is.read();
            while (is.read() != -1) ;
        }

        @Override
        public String getResult() {
            return ok;
        }
    };

    public final static ResponseHandler singleLineHandler = new ResponseHandler() {
        String result;

        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            String line = null;
            try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                    line = bufferedReader.readLine();
                    while ((bufferedReader.readLine()) != null) ;
                    result = line;
                }
            }
        }

        @Override
        public String getResult() {
            return result;
        }
    };

    public final static ResponseHandler multiLineHandler = new ResponseHandler() {
        String result;

        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            String line = null;
            StringBuilder sb = new StringBuilder();
            try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    result = sb.toString();
                }
            }
        }

        @Override
        public String getResult() {
            return result;
        }
    };

    enum diesel {
        EGRandorVVTSystem(false, false),
        PMfiltermonitoring(false, false),
        ExhaustGasSensor(false, false),
        Reserved1(false, false),
        BoostPressure(false, false),
        Reserved2(false, false),
        NOxSCRMonitor(false, false),
        NMHCCatalyst(false, false);

        boolean available;
        boolean test;

        diesel(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }

        public void set(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }
        }

    enum petrol {
        OxygenSensorHeater(false, false),
        EGRSystem(false, false),
        OxygenSensor(false, false),
        AcRefrigerant(false, false),
        SecondaryAirSystem(false, false),
        EvaporativeSystem(false, false),
        HeatedCatalyst(false, false),
        Catalyst(false, false);

        boolean available;
        boolean test;

        petrol(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }

        public void set(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }
    }

    ;

    public enum basicTest{
        Components(false, false),
        FuelSystem(false, false),
        Misfire(false, false);

        boolean available;
        boolean test;

        basicTest(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }

        public void set(boolean available, boolean test) {
            this.available = available;
            this.test = test;
        }
    }
    enum  EngineType{ petrolType, dieselType}
    public static final ResponseHandler m1Pid1 = new ResponseHandler() {
        private final static byte msbSet = (byte) 0x80;
        private final static byte allSet6Till0 = 0x7f;
        private final static byte b3Set = 0x4;
        int counter;
        petrol petrolEngStatus;
        diesel dieselEngStatus;
        EngineType engineType;
        basicTest test;
        boolean CIL;


        @Override
        public void parse(InputStream is) throws IOException, BadResponseException {
            String line;
            try (InputStreamReader bufferedInputStream = new InputStreamReader(is);) {
                try (BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);) {
                    while ((line = bufferedReader.readLine()) != null) {
                        String abcd[] = line.split(" ");
                        CIL = (((byte)Integer.parseInt(abcd[0],16))&msbSet) == (byte)1;
                        counter = (((byte)Integer.parseInt(abcd[0],16)) | allSet6Till0);
                        BitSet bBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[2], 16)});
                        int i = 0;
                        for(basicTest b : basicTest.values()){
                            b.set(bBitSet.get(i),bBitSet.get(i+4));
                        }
                        engineType = (((byte)Integer.parseInt(abcd[1],16))& b3Set) == (byte)1
                                ?EngineType.dieselType:EngineType.petrolType;
                        /*
                        0 = Spark ignition monitors supported (e.g. Otto or Wankel engines)
                        1 = Compression ignition monitors supported (e.g. Diesel engines)
                         */
                        BitSet cBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[2], 16)});
                        BitSet dBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[3], 16)});
                        i = 0;
                        if(engineType == EngineType.petrolType) {
                            for (petrol p : petrol.values()) {
                                p.set(cBitSet.get(i), dBitSet.get(i));
                                i++;
                            }
                        }else{
                            for (diesel p : diesel.values()) {
                                p.set(cBitSet.get(i), dBitSet.get(i));
                                i++;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public String getResult() {
            return toString();
        }

        @Override
        public String toString() {
            return "$classname{" +
                    ", counter=" +counter+
                    ", basicTest" +test+
                    ", petrolEngStatus=" + petrolEngStatus +
                    ", dieselEngStatus=" + dieselEngStatus +
                    ", engineType=" + engineType +
                    ", CIL=" + CIL +
                    '}';
        }
    };

}
