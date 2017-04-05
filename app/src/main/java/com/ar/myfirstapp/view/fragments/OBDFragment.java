package com.ar.myfirstapp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.view.MainActivity;
import com.ar.myfirstapp.view.adapter.OBDItemAdapter;

import java.util.Map;

/**
 * Created by amal.george on 24-03-2017
 */

public class OBDFragment extends BaseFragment {
    RecyclerView recyclerView;
    int position;
    OBDItemAdapter obdItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_obd, container, false);
        initUI();
        return rootView;
    }

    @Override
    protected void updateData(int index, int pId) {
        if (index != position) return;
        obdItemAdapter.add(((MainActivity) getActivity()).getCommands(position).get(pId));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void loadData() {
        Map<Integer, Command> commands = ((MainActivity) getActivity()).getCommands(position);
        obdItemAdapter = new OBDItemAdapter(commands);
        recyclerView.setAdapter(obdItemAdapter);
    }

    private void initUI() {
        position = getArguments().getInt("position");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
