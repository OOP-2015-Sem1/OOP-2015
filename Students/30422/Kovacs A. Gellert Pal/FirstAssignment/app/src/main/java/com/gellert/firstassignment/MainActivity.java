package com.gellert.firstassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android. widget.TextView;
import android. widget.Toast;
import android.graphics.Color;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int countBtnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPush = (Button)findViewById(R.id.btnPush);
        Button btnClick = (Button)findViewById(R.id.btnClick);
        final TextView textView = (TextView)findViewById(R.id.textView);

        countBtnClick = 1;
        final int[] colorArray = {Color.RED, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA, Color.WHITE};
        final Random rand  = new Random();

        btnPush.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        int textColor = rand.nextInt(8);
                        textView.setTextColor(colorArray[textColor]);
                        textView.setText("Button PUSH pressed.");


                    }
                }
        );

        btnClick.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        textView.setText("Button CLICK pressed.");

                        Toast toast = Toast.makeText(getApplicationContext(), "Button CLICK pressed " + countBtnClick + " times.", Toast.LENGTH_SHORT);
                        toast.show();
                        countBtnClick++;
                    }
                }
        );
    }
}
