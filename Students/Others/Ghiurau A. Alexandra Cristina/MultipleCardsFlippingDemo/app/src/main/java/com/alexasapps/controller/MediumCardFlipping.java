package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.MainActivity;
import com.alexasapps.multiplecardsflippingdemo.R;

public class MediumCardFlipping extends CardFlipping{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medium_layout);


        Resources res = getResources();
        frontImages = new ImageView[30];
        backImages = new ImageView[30];

        for (int i = 0; i < 30; i++) {
            int idFrontImage = res.getIdentifier("imgBack" + (i + 1), "id", getApplicationContext().getPackageName());
            int idBackImage = res.getIdentifier("img" + (i + 1), "id", getApplicationContext().getPackageName());

            frontImages[i] = (ImageView) findViewById(idFrontImage);
            backImages[i] = (ImageView) findViewById(idBackImage);

            int result = 0;
            if (frontImages[i] == null) {
                result += 1;
            }
            if (backImages[i] == null) {
                result += 2;
            }
            Log.d("De ce nu merge?", idFrontImage + ": " + ("imgBack" + (i + 1)));
            Log.d("De ce nu merge?", idBackImage + ": " + ("img" + (i + 1)) + " = " + result);
        }


        /*frontImages = new ImageView[]{(ImageView) findViewById(R.id.imgBack1), (ImageView) findViewById(R.id.imgBack2), (ImageView)
                findViewById(R.id.imgBack3), (ImageView) findViewById(R.id.imgBack4), (ImageView) findViewById(R.id.imgBack5),
                (ImageView) findViewById(R.id.imgBack6), (ImageView) findViewById(R.id.imgBack7), (ImageView) findViewById(R.id.imgBack8),
                (ImageView) findViewById(R.id.imgBack9), (ImageView) findViewById(R.id.imgBack10), (ImageView) findViewById(R.id.imgBack11),
                (ImageView) findViewById(R.id.imgBack12), (ImageView) findViewById(R.id.imgBack13), (ImageView) findViewById(R.id.imgBack14),
                (ImageView) findViewById(R.id.imgBack15), (ImageView) findViewById(R.id.imgBack16), (ImageView) findViewById(R.id.imgBack17),
                (ImageView) findViewById(R.id.imgBack18), (ImageView) findViewById(R.id.imgBack19), (ImageView) findViewById(R.id.imgBack20),
                (ImageView) findViewById(R.id.imgBack21), (ImageView) findViewById(R.id.imgBack22), (ImageView) findViewById(R.id.imgBack23),
                (ImageView) findViewById(R.id.imgBack12)};

        backImages = new ImageView[]{(ImageView) findViewById(R.id.img1), (ImageView) findViewById(R.id.img2),
                (ImageView) findViewById(R.id.img3), (ImageView) findViewById(R.id.img4), (ImageView) findViewById(R.id.img5),
                (ImageView) findViewById(R.id.img6), (ImageView) findViewById(R.id.img7), (ImageView) findViewById(R.id.img8),
                (ImageView) findViewById(R.id.img9), (ImageView) findViewById(R.id.img10), (ImageView) findViewById(R.id.img11),
                (ImageView) findViewById(R.id.img12)};*/

        setCards(frontImages, backImages, "peng");


        Toast toast = Toast.makeText(getApplicationContext(), "size: " + cards.size(), Toast.LENGTH_SHORT);
        toast.show();

        setFrontImagesClickListeners();

    }
}
