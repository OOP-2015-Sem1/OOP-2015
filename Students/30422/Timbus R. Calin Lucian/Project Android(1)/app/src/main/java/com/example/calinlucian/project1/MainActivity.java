package com.example.calinlucian.project1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;

public class MainActivity extends AppCompatActivity {
    private int nrOfClicks = 0;
    private int nrOfPushes = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView myMediumText =(TextView)findViewById(R.id.textView);
        final Button clickButton = (Button)findViewById(R.id.clickButton);
        clickButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        myMediumText.setTextColor(Color.MAGENTA);
                        myMediumText.setBackgroundColor(Color.GREEN);
                        Context context = getApplicationContext();
                        clickButton.setTextColor(Color.YELLOW);
                        nrOfClicks++;
                        CharSequence text = "You pressed the click button for " + nrOfClicks + " times";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
        );
        final Button pushButton = (Button) findViewById(R.id.pushButton);
        pushButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Context context = getApplicationContext();
                        nrOfPushes++;
                        pushButton.setTextColor(BLUE);
                        myMediumText.setTextColor(Color.DKGRAY);
                        myMediumText.setBackgroundColor(Color.RED);
                        CharSequence text = "You pressed the push button for " +nrOfPushes +  "times";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
