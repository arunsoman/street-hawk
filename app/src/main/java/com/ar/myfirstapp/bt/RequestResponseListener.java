package com.ar.myfirstapp.bt;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by amal.george on 04-04-2017
 */

public interface RequestResponseListener {
    void onResponseReceived(Command command);
}