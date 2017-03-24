package com.ar.myfirstapp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.R;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;
import com.ar.myfirstapp.view.adapter.OBDItemAdapter;

import java.util.List;

/**
 * Created by amal.george on 24-03-2017
 */

public class LogFragment extends BaseFragment {
    private Button buttonSend;
    private EditText editTextInput;
    private TextView textViewLog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log, container, false);
        initUI();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    @Override
    protected void updateData() {
        Bundle bundle = getArguments();
        List<Command> commands = ((MainActivity) getActivity()).getCommands(bundle.getInt("position"));
        if (commands != null) {
            for (Command command : commands) {
                textViewLog.append(command.toString());
            }
        }
    }

    private void initUI() {
        editTextInput = (EditText) rootView.findViewById(R.id.editTextInput);
        textViewLog = (TextView) rootView.findViewById(R.id.textViewLog);
        buttonSend = (Button) rootView.findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextInput.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    if (message.startsWith("m1")) {
                        Command c = Mode1.getCommand(message.split(" ")[1]);
                        if (c != null)
                            device.send(c);
                        return;
                    }
                    device.send(new Command("", message + "\r", "", new Parser() {
                        @Override
                        public void parse(Command command) {
                            byte[] rawResp = command.getRawResp();
                            StringBuilder sb = new StringBuilder();
                            for (byte aByte : rawResp) {
                                sb.append(aByte).append(' ');
                            }
                            command.setResult(sb.toString());
                        }
                    }));
                    editTextInput.setText(message.split(" ")[0]);
                }
            }
        });
    }
}
