package com.example.andreea.assignment1;

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
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button pushButton=(Button) findViewById(R.id.PUSH);
        Button clickButton=(Button) findViewById(R.id.CLICK);
        Button textButton=(Button) findViewById(R.id.TEXTCOLOR);
        textButton.setBackgroundColor(Color.GREEN);
        clickButton.setBackgroundColor(Color.GREEN);
        pushButton.setBackgroundColor(Color.GREEN);
        pushButton.setOnClickListener(

                new Button.OnClickListener() {
                    int n = 0;

                    public void onClick(View v) {
                        n += 1;
                        View button = (View) findViewById(R.id.PUSH);
                        button.setBackgroundColor(Color.RED);
                        TextView buttonText = (TextView) findViewById(R.id.PUSH);
                        buttonText.setTextColor(Color.BLUE);
                        buttonText.setText("I was clicked " + n + " times");

                    }
                }
        );

        clickButton.setOnClickListener(

                new Button.OnClickListener(){
                    int n=0;
                    public  void onClick(View v){
                        n += 1;
                        View button=(View) findViewById(R.id.CLICK);
                        button.setBackgroundColor(Color.BLUE);
                        TextView buttonText=(TextView) findViewById(R.id.CLICK);
                        buttonText.setTextColor(Color.RED);
                        buttonText.setText("I was clicked "+ n +" times");

                    }
                }
        );

        textButton.setOnClickListener(

                new Button.OnClickListener(){
                    public  void onClick(View v){
                        View button=(View) findViewById(R.id.TEXTCOLOR);
                        TextView buttonText=(TextView) findViewById(R.id.TEXT);
                        buttonText.setTextColor(Color.RED);

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
