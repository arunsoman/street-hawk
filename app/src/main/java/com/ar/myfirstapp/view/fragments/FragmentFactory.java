package com.ar.myfirstapp.view.fragments;

/**
 * Created by amal.george on 24-03-2017
 */

public class FragmentFactory {
    public static String[] getTitle() {
        return new String[
                ]{"Show current data",
                "Show freeze frame data",
                "Show stored Diagnostic Trouble Codes",
                "Clear Diagnostic Trouble Codes and stored values",
                "Test results, oxygen sensor monitoring (non CAN only)",
                "Test results, other component/system monitoring (Test results, oxygen sensor monitoring for CAN only",
                "Show pending Diagnostic Trouble Codes (detected during current or last driving cycle)",
                "Control operation of on-board component/system",
                "Request vehicle information",
                "Permanent Diagnostic Trouble Codes (DTCs) (Cleared DTCs)",
                "Log"
        };
    }

    public static int getLength() {
        return getTitle().length;
    }

    public static int getLastIndex() {
        return getTitle().length - 1;
    }
}
