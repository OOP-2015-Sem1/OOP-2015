package com.example.t.simplefirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private static int numberOfPushes;
    private static int numberOfClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        numberOfPushes = 0;
        numberOfClicks = 0;

        final Button clickButton = (Button) findViewById(R.id.clickMeButton);
        final Button pushButton  = (Button) findViewById(R.id.pushMeButton);

        final Toast toastAfterPush = Toast.makeText(getApplicationContext(),"PushButton was pushed",Toast.LENGTH_SHORT);
        final Toast toastAfterClick = Toast.makeText(getApplicationContext(),"ClickButton was clicked",Toast.LENGTH_SHORT);

        final TextView displayedText = (TextView) findViewById(R.id.textView);

        clickButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        toastAfterPush.cancel();
                        toastAfterClick.show();
                        numberOfClicks++;
                        displayedText.setText("You have clicked " + numberOfClicks + " times");
                        displayedText.setTextColor(Color.RED);
                        clickButton.setBackgroundColor(Color.GREEN);
                        pushButton.setBackgroundColor(Color.WHITE);

                    }
                }
        );

        pushButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        toastAfterClick.cancel();
                        toastAfterPush.show();
                        numberOfPushes++;
                        displayedText.setText("You have pushed " + numberOfPushes + " times");
                        displayedText.setTextColor(Color.BLUE);
                        pushButton.setBackgroundColor(Color.GREEN);
                        clickButton.setBackgroundColor(Color.WHITE);


                    }
                }
        );




    }

    @Override
    protected void onStart()
    {
        super.onStart();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        TextView displayedText = (TextView) findViewById(R.id.textView);
        displayedText.setText("Welcome Back!");
        displayedText.setTextColor(Color.BLACK);



    }

    @Override
    protected  void onRestart()
    {
      super.onRestart();
        TextView displayedText = (TextView) findViewById(R.id.textView);
        displayedText.setText("Welcome Back!");
        displayedText.setTextColor(Color.BLACK);

    }


}
