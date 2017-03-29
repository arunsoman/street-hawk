package com.ar.myfirstapp.utils;

import android.util.Log;

/**
 * Created by amal.george on 29-03-2017
 */

public class Logger {
    private static boolean isDebug() {
        return true;
    }

    public static void e(String TAG, String e) {
        if (isDebug()) Log.e(TAG, e);
    }

    public static void e(String TAG, String e, Exception exception) {
        if (isDebug()) Log.e(TAG, e, exception);
    }

    public static void d(String TAG, String d) {
        if (isDebug()) Log.d(TAG, d);
    }
}
