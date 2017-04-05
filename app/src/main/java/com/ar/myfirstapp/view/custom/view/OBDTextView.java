package com.ar.myfirstapp.view.custom.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ar.myfirstapp.obd2.Command;

/**
 * Created by amal.george on 29-03-2017
 */

public class OBDTextView extends AppCompatTextView implements OBDView {
    public OBDTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void display(Command c) {
        setText(c.getName());
    }
}
