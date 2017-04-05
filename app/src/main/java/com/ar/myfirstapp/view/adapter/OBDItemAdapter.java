package com.ar.myfirstapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.bt.DeviceManager;
import com.ar.myfirstapp.bt.RequestResponseListener;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.utils.Constants;
import com.ar.myfirstapp.view.custom.RepeatListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by amal.george on 24-03-2017
 */

public class OBDItemAdapter extends RecyclerView.Adapter<OBDItemAdapter.ViewHolder> {
    private Map<Integer, Command> commands;
    private List<Integer> keyList;

    public OBDItemAdapter(Map<Integer, Command> commands) {
        if (commands != null) {
            this.commands = commands;
            this.keyList = new ArrayList<>(commands.keySet());
        } else {
            this.commands = new LinkedHashMap<>();
            this.keyList = new ArrayList<>();
        }
    }


    public void add(Command command) {
        int key = Integer.parseInt(command.getPid(), 16);
        commands.put(key, command);
        if (keyList.contains(key)) {
            //notifyItemChanged(keyList.indexOf(key));
        } else {
            keyList = new ArrayList<>(commands.keySet());
            notifyItemInserted(keyList.indexOf(key));
        }
    }

    @Override
    public OBDItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obd_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OBDItemAdapter.ViewHolder holder, int position) {
        final Command command = commands.get(keyList.get(position));
        holder.textViewOBDKey.setText(command.getName());
        holder.textViewOBDValue.setText(command.toString());
        holder.itemView.setOnTouchListener(new RepeatListener(0, Constants.ELMTimeDelay, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  notifyItemChanged(holder.getAdapterPosition());
                DeviceManager.getInstance().send(command, new RequestResponseListener() {
                    @Override
                    public void onResponseReceived(Command command) {
                        holder.textViewOBDKey.setText(command.getName());
                        holder.textViewOBDValue.setText(command.toString());
                    }
                });

            }
        }));
    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOBDKey;
        TextView textViewOBDValue;

        ViewHolder(View itemView) {
            super(itemView);
            textViewOBDKey = (TextView) itemView.findViewById(R.id.textViewOBDKey);
            textViewOBDValue = (TextView) itemView.findViewById(R.id.textViewOBDValue);
        }
    }


}
