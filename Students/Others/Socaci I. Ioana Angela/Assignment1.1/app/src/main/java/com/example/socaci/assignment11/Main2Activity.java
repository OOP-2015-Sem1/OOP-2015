package com.example.socaci.assignment11;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button3 = (Button) findViewById(R.id.button3);

        button3.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        TextView blackText = (TextView) findViewById(R.id.textView2);
                        blackText.setText("Yellow");
                        blackText.setTextColor(Color.YELLOW);
                    }
                }
        );
    }

}
