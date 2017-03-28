package com.ar.myfirstapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.view.DeviceService;

import java.util.List;

/**
 * Created by amal.george on 24-03-2017
 */

public class OBDItemAdapter extends RecyclerView.Adapter<OBDItemAdapter.ViewHolder> {
    private List<Command> commands;

    public OBDItemAdapter(List<Command> commands) {
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
        holder.textViewOBDKey.setText(command.getName());
        holder.linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceService.getInstance().send(command);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOBDKey, textViewOBDValue;
        LinearLayout linearLayoutItem;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayoutItem = (LinearLayout) itemView.findViewById(R.id.linearLayoutItem);
            textViewOBDKey = (TextView) itemView.findViewById(R.id.textViewOBDKey);
            textViewOBDValue = (TextView) itemView.findViewById(R.id.textViewOBDValue);
        }
    }
}
