package com.example.roxy_.assignment1;

import android.view.View;
import android.widget.Button;

/**
 * Created by roxy_ on 13.10.2015.
 */
public class ButtonListener implements View.OnClickListener {

    int count=0;
    public void onClick(View v) {

        count++;
        Button button = (Button)v;
        String text = String.valueOf(count);
        button.setText("I was pressed "+text+" times");

    }
}
