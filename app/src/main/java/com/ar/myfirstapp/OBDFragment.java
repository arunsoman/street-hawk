package com.ar.myfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.view.adapter.OBDItemAdapter;
import com.ar.myfirstapp.view.fragments.BaseFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by amal.george on 24-03-2017
 */

public class OBDFragment extends BaseFragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_obd, container, false);
        initUI();
        return rootView;
    }

    @Override
    protected void updateData() {
        Bundle bundle = getArguments();
        Map<Integer,Command> commands = ((MainActivity) getActivity()).getCommands(bundle.getInt("position"));
        if (commands != null) {
            OBDItemAdapter obdItemAdapter = new OBDItemAdapter(commands);
            recyclerView.setAdapter(obdItemAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    private void initUI() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
