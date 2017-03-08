package com.ar.myfirstapp.obd2;

import android.util.Log;

import com.ar.myfirstapp.Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by arunsoman on 04/03/17.
 */

public class ResponseHandlerUtils {
        private static final String ok = new StringBuilder().append((byte)'o').append((byte)'k').toString();
        private static final String OK = new StringBuilder().append((byte)'O').append((byte)'K').toString();
        private static final String CR = new StringBuilder().append((byte)'\r').toString();

    public static final ResponseHandler crResponse = new AbstractResponseHandler() {

        @Override
        public void parse() {
            if(dataStr.contains(CR)){
                status = ResponseStatus.Ok;
                resultStr = "ÖK";
            }
            else {
                status = ResponseStatus.BadResponse;
            }
        }
    };

    public static final ResponseHandler okResponse = new AbstractResponseHandler() {
        public void parse( ) {
            if (dataStr.contains(ok) || dataStr.contains(OK)){
                status = ResponseStatus.Ok;
            }
            else {
                status = ResponseStatus.BadResponse;
            }
        }
    };

    public static final ResponseHandler singleLineHandler = new AbstractResponseHandler() {
        @Override
        public void parse() {

        }
    };

    public static final class StreamHandler extends AbstractResponseHandler {
        List<String> result = new ArrayList<>();
        private Device device;
        public StreamHandler(Device device) {
            this.device = device;
        }

        @Override
        public void parse() {

        }
    }

    public static final ResponseHandler multiLineHandler = new AbstractResponseHandler() {

        @Override
        public void parse() {

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

    public enum basicTest {
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

    private enum EngineType {petrolType, dieselType}

    public static final ResponseHandler m1Pid1ResponseHandler = new AbstractResponseHandler() {
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
        public void parse()  {
            /*
            String abcd[] = line.split(" ");
                CIL = (((byte) Integer.parseInt(abcd[0], 16)) & msbSet) == (byte) 1;
                counter = (((byte) Integer.parseInt(abcd[0], 16)) | allSet6Till0);
                BitSet bBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[2], 16)});
                int i = 0;
                for (basicTest b : basicTest.values()) {
                    b.set(bBitSet.get(i), bBitSet.get(i + 4));
                }
                engineType = (((byte) Integer.parseInt(abcd[1], 16)) & b3Set) == (byte) 1
                        ? EngineType.dieselType : EngineType.petrolType;
                //
                //0 = Spark ignition monitors supported (e.g. Otto or Wankel engines)
                //1 = Compression ignition monitors supported (e.g. Diesel engines)
              //
                BitSet cBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[2], 16)});
                BitSet dBitSet = BitSet.valueOf(new long[]{Long.parseLong(abcd[3], 16)});
                i = 0;
                if (engineType == EngineType.petrolType) {
                    for (petrol p : petrol.values()) {
                        p.set(cBitSet.get(i), dBitSet.get(i));
                        i++;
                    }
                } else {
                    for (diesel p : diesel.values()) {
                        p.set(cBitSet.get(i), dBitSet.get(i));
                        i++;
                    }
                }
                */
        }

        @Override
        public String getResult() {
            return toString();
        }

        @Override
        public String toString() {
            return "$classname{" +
                    ", counter=" + counter +
                    ", basicTest" + test +
                    ", petrolEngStatus=" + petrolEngStatus +
                    ", dieselEngStatus=" + dieselEngStatus +
                    ", engineType=" + engineType +
                    ", CIL=" + CIL +
                    '}';
        }
    };

}