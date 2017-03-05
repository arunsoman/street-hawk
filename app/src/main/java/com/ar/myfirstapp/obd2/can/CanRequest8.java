package com.ar.myfirstapp.obd2.can;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.ResponseHandler;

/**
 * Created by arunsoman on 05/03/17.
 */

public class CanRequest8 extends Command{

    public  CanRequest8(byte mode, byte pid, ResponseHandler responseHandler) {
        super(new byte[9], responseHandler);
        this.cmd[0] = 2;
        this.cmd[1] = mode;
        this.cmd[2] = pid;
        this.cmd[8] = (byte)('\r');
    }
}
