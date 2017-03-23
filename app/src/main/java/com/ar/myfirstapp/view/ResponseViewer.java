package com.ar.myfirstapp.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ar.myfirstapp.MainActivity;
import com.ar.myfirstapp.R;
import com.ar.myfirstapp.obd2.Command;
import com.ar.myfirstapp.obd2.saej1979.ModeFactory;

/**
 * Created by Arun Soman on 3/22/2017.
 */

public class ResponseViewer {
    private TextView view;

    public ResponseViewer(AppCompatActivity activity){
        view= (TextView)activity.findViewById(R.id.log);
    }

    public void display(final String str){
        if(str.contains(", p:00")||
                str.contains("m01, p:20")) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    String mode = null;
                    if(str.contains("m:01")){
                        mode = "m1";
                    }
                    else if(str.contains("m:09")){
                        mode = "m9";
                    }
                    else return;
                    String tmp = str.split("Result: ")[1];

                    for(String pid: tmp.split(" ")){
                        if(pid.contains("00")) continue;
                        Command c = ModeFactory.getCommand(mode, pid);
                        if(c != null){
                            MainActivity.device1.send(c);
                        }
                    }
                }
            });
        }
        view.append(str);
    }
}
