package com.ar.myfirstapp.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.support.v4.app.Fragment;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.utils.Utils;
import com.ar.myfirstapp.view.OBDView;

import java.util.List;


/**
 * Created by amal.george on 24-03-2017
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected ELM327 device;
    public String title;

    public void setDevice(ELM327 device) {
        this.device = device;
    }

    public BaseFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(dataUpdateReceiver, new IntentFilter("myfirstapp.refresh"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(dataUpdateReceiver);
    }

    protected abstract void updateData();

    protected BroadcastReceiver dataUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("myfirstapp.refresh")) {
                updateData();
            }
        }
    };

}
