package com.alexasapps.multiplecardsflippingdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.alexasapps.controller.EasyCardFlipping;
import com.alexasapps.controller.HardCardFlipping;
import com.alexasapps.controller.MediumCardFlipping;

public class MainActivity extends AppCompatActivity {

    public static Context mainContext;

    Button easyLevel;
    Button mediumLevel;
    Button hardLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        easyLevel = (Button) findViewById(R.id.button);
        mediumLevel = (Button) findViewById(R.id.button2);
        hardLevel = (Button) findViewById(R.id.button3);

        mainContext = getApplicationContext();

        easyLevel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, EasyCardFlipping.class));
            }
        });

        mediumLevel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MediumCardFlipping.class));
            }
        });

        hardLevel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HardCardFlipping.class));
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
