package com.example.bolo.assignment1;

import android.content.Context;
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
import android.widget.Toast;
import java.lang.Math;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
     int counts=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button myButton1= (Button) findViewById(R.id.myButton1);
        final TextView myText=(TextView) findViewById(R.id.myText);

         final int[] buttonColors={Color.WHITE,Color.BLACK,Color.CYAN,Color.DKGRAY,Color.GRAY,
                 Color.GREEN,Color.LTGRAY,Color.MAGENTA,Color.RED,Color.YELLOW};
        final Random randomC=new Random();

        myButton1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Context context = getApplicationContext();
                        CharSequence text = "YOU PUSHED!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        int randomColor=randomC.nextInt(buttonColors.length);
                        int randomCol=randomC.nextInt(buttonColors.length);
                        myButton1.setBackgroundColor(buttonColors[randomColor]);
                        myButton1.setTextColor(Color.BLUE);
                        myText.setTextColor(buttonColors[Math.abs(randomColor-randomCol)]);
                    }
                }

        );
        final Button myButton2= (Button) findViewById(R.id.myButton2);

        myButton2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        counts++;

                        Toast toast = Toast.makeText(context, "I was clicked " + counts + " times", duration);
                                toast.show();
                        int randomColor=randomC.nextInt(buttonColors.length);
                        int randomCol=randomC.nextInt(buttonColors.length);
                        myButton2.setBackgroundColor(buttonColors[randomColor]);
                        myButton2.setTextColor(Color.BLUE);
                        myText.setTextColor(buttonColors[Math.abs(randomColor-randomCol)]);
                    }
                }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
