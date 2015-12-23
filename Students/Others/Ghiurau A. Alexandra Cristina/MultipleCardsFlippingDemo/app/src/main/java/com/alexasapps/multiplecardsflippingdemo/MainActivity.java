package com.alexasapps.multiplecardsflippingdemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    Button portalForNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        portalForNewActivity = (Button) findViewById(R.id.button);

        portalForNewActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               // Intent i = new Intent(getApplicationContext(),CardFlipping.class);
                //startActivity(i);
                startActivity(new Intent(MainActivity.this, CardFlipping.class));
            }
        });
        /*

            frontImages[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isBackVisible) {
                        setRightOut.setTarget(frontImages[index]);
                        setLeftIn.setTarget(backImages[index]);
                        setRightOut.start();
                        setLeftIn.start();
                        isBackVisible = true;
                    } else {
                        setRightOut.setTarget(backImages[index]);
                        setLeftIn.setTarget(frontImages[index]);
                        setRightOut.start();
                        setLeftIn.start();
                        isBackVisible = false;
                    }
                }
            });
        }*/

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
