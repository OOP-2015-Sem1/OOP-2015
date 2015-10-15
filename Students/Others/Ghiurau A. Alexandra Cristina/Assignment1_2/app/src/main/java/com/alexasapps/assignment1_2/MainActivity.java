package com.alexasapps.assignment1_2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int clickCounter = 1;
    private int pushCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //refferance to the button
        Button clickMeButton = (Button) findViewById(R.id.clickMeId);
        Button pushMeButton = (Button) findViewById(R.id.pushMeId);

        //click listener
        pushMeButton.setOnClickListener(
                new Button.OnClickListener() {
                    //callBack method, inside an interface^^
                    public void onClick(View v) {
                        TextView alexastext = (TextView) findViewById(R.id.SecondTestId);
                        alexastext.setText("Thank you for clicking softly");
                        Toast toastP = Toast.makeText(getApplicationContext(), "I was puched " + pushCounter + " times", Toast.LENGTH_SHORT);
                        toastP.show();
                        pushCounter++;
                    }
                }
        );

        //holding the button
        pushMeButton.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        TextView alexastext = (TextView) findViewById(R.id.SecondTestId);
                        alexastext.setText("Auch. Click softely, please.");
                        Toast toastP = Toast.makeText(getApplicationContext(), "I was puched " + pushCounter + " times", Toast.LENGTH_SHORT);
                        toastP.show();
                        pushCounter++;
                        return true;
                    }
                }
        );

        clickMeButton.setOnClickListener(
                new Button.OnClickListener() {
                    //callBack method, inside an interface^^
                    public void onClick(View v) {
                        Toast toastC = Toast.makeText(getApplicationContext(), "I was clicked " + clickCounter + " times", Toast.LENGTH_SHORT);
                        toastC.show();
                        clickCounter++;
                    }
                }
        );
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
