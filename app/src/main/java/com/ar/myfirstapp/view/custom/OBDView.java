package com.ar.myfirstapp.view.custom;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by Arun Soman on 3/24/2017.
 */

public interface OBDView {
    String display(Command c);
}
