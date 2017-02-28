package com.ar.myfirstapp;

import android.util.Log;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class ELMStreamLogger implements ELMStreamHandler {
    private ELMDataManager dataManager = new ELMDataManager();

    @Override
    public void handle(String line) {
        Log.e("Resp", line);
        dataManager.addResponse(line);
    }
}
