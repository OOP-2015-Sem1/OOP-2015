package com.example.maria.thetwobuttons;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myFirstButton = (Button)findViewById(R.id.myFirstButton);


        myFirstButton.setOnClickListener(
                new Button.OnClickListener() {

                    int count1 = 1;

                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Fantastic!", Toast.LENGTH_SHORT).show();
                        myFirstButton.setTextColor(Color.BLUE);

                        final int i = count1++;
                        myFirstButton.setText("I was clicked " + i + " times.");

                    }
                }
        );

        final Button mySecondButton = (Button)findViewById(R.id.mySecondButton);

        mySecondButton.setOnClickListener(
                new Button.OnClickListener() {

                    int count2 = 1;

                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Allons-y!", Toast.LENGTH_SHORT).show();
                        mySecondButton.setTextColor(Color.RED);

                        final int i = count2++;
                        mySecondButton.setText("I was clicked " + i + " times.");
                    }
                }
        );

        final TextView myText = (TextView)findViewById(R.id.myText);

        myFirstButton.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        myText.setTextColor(Color.BLUE);
                        return true;
                    }
                }
        );

        mySecondButton.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        myText.setTextColor(Color.RED);
                        return true;
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
