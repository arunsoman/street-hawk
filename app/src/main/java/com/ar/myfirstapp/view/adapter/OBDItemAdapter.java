package com.ar.myfirstapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.bt.DeviceManager;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.view.custom.OBDTextView;

import java.util.Map;

/**
 * Created by amal.george on 24-03-2017
 */

public class OBDItemAdapter extends RecyclerView.Adapter<OBDItemAdapter.ViewHolder> {
    private Map<Integer, Command> commands;

    public OBDItemAdapter(Map<Integer, Command> commands) {
        this.commands = commands;
    }

    @Override
    public OBDItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obd_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OBDItemAdapter.ViewHolder holder, int position) {
        final Command command = commands.get(position);
        holder.textViewOBDKey.display(command);
        holder.textViewOBDKey.setText(command.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager.getInstance().send(command);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        OBDTextView textViewOBDKey;
        TextView textViewOBDValue;

        ViewHolder(View itemView) {
            super(itemView);
            textViewOBDKey = (OBDTextView) itemView.findViewById(R.id.textViewOBDKey);
            textViewOBDValue = (TextView) itemView.findViewById(R.id.textViewOBDValue);
        }
    }
}
