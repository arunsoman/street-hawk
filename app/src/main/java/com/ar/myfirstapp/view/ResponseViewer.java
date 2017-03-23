package com.ar.myfirstapp.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ar.myfirstapp.R;

/**
 * Created by Arun Soman on 3/22/2017.
 */

public class ResponseViewer {
    private TextView view;

    public ResponseViewer(AppCompatActivity activity){
        view= (TextView)activity.findViewById(R.id.log);
    }

    public void display(String str){
        view.append(str);
    }
}
