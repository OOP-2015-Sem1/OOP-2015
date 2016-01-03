package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.R;

public class HardCardFlipping extends CardFlipping {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hard_layout);

        Resources res = getResources();
        frontImages = new ImageView[30];
        backImages = new ImageView[30];

        for (int i = 0; i < 30; i++) {
            int idFrontImage = res.getIdentifier("imgBack" + (i + 1), "id", getApplicationContext().getPackageName());
            int idBackImage = res.getIdentifier("img" + (i + 1), "id", getApplicationContext().getPackageName());

            frontImages[i] = (ImageView) findViewById(idFrontImage);
            backImages[i] = (ImageView) findViewById(idBackImage);

        }

        setCards(frontImages, backImages, "peng");

        setFrontImagesClickListeners();

    }
}
