package com.ar.myfirstapp.view;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ar.myfirstapp.R;
import com.ar.myfirstapp.elm.ELM327;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.parser.Parser;
import com.ar.myfirstapp.obd2.saej1979.Mode1;

import static com.ar.myfirstapp.MainActivity.device1;

/**
 * Created by Arun Soman on 3/22/2017.
 */

public class Terminal implements View.OnClickListener {
    private Button btnScan, buttonSend;
    private EditText editTextInput;

    public Terminal(AppCompatActivity activity, ELM327 device1){
        buttonSend = (Button) activity.findViewById(R.id.buttonSend);
        editTextInput = (EditText) activity.findViewById(R.id.editTextInput);
        buttonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String message = editTextInput.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            if (message.startsWith("m1")) {
                Command c = Mode1.getCommand(message.split(" ")[1]);
                if (c != null)
                    device1.send(c);
                return;
            }
            device1.send(new Command("", message + "\r", "", new Parser() {
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
}
