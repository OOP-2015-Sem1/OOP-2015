package com.example.roxy_.assignment1;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * Created by roxy_ on 13.10.2015.
 */
public class ButtonListener implements View.OnClickListener {

    private TextView textView;
    private int textColor;

    public ButtonListener(TextView textView,int textColor){
        this.textView = textView;
        this.textColor=textColor;
    }
    public void onClick(View v) {

        textView.setTextColor(textColor);

    }
}
