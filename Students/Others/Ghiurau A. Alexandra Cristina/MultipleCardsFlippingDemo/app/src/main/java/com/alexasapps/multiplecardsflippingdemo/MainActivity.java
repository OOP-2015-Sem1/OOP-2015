package com.alexasapps.multiplecardsflippingdemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgFront;// = (ImageView) findViewById(R.id.imgBack1);
    ImageView imgBack;// = (ImageView) findViewById(R.id.imgApples);

    //ImageView[] frontImages = {(ImageView) findViewById(R.id.imgBack1), (ImageView) findViewById(R.id.imgBack2)};
    //ImageView[] backImages = {(ImageView) findViewById(R.id.imgApples), (ImageView) findViewById(R.id.imgBannana)};

    boolean isBackVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imgFront = frontImages[0];
       // imgBack = backImages[0];

        imgFront = (ImageView)findViewById(R.id.imgApples);
        imgBack = (ImageView)findViewById(R.id.imgBack1);

        final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flip_right_out);


        final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_left_in);


        imgFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBackVisible){
                    setRightOut.setTarget(imgFront);
                    setLeftIn.setTarget(imgBack);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = true;
                }
                else{
                    setRightOut.setTarget(imgBack);
                    setLeftIn.setTarget(imgFront);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;
                }
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
