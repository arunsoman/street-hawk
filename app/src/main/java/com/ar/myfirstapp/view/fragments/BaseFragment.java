package com.ar.myfirstapp.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * Created by amal.george on 24-03-2017
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    public String title;

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
