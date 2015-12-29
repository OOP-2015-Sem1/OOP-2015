package com.example.socaci.assignment11;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first button
        //on click the color of its text will change
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(
               new Button.OnClickListener(){
                   public void onClick(View v){

                       Button newButton = (Button) findViewById(R.id.button);
                       newButton.setTextColor(Color.RED);
                   }
               }
        );

        //second button
        //on click the color of its text will change and
        //its text would be the number of times it was pressed

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(
                new Button.OnClickListener(){

                    private int countClick;
                    private String click;

                    public void onClick(View v){

                        Button newButton = (Button) findViewById(R.id.button2);
                        newButton.setTextColor(Color.BLUE);
                        countClick++;
                        if(countClick == 1) {
                            click = String.format("I was clicked %d time", countClick);
                        }
                        else{
                            click = String.format("I was clicked %d times", countClick);
                        }
                        newButton.setText(click);
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
