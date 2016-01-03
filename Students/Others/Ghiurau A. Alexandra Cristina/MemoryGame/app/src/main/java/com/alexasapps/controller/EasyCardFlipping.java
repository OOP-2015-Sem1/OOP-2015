package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.MainActivity;
import com.alexasapps.multiplecardsflippingdemo.R;

public class EasyCardFlipping extends CardFlipping {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_layout);

        Resources res = MainActivity.mainContext.getResources();
        frontImages = new ImageView[12];
        backImages = new ImageView[12];

        for (int i = 0; i < 12; i++) {
            int idFrontImage = res.getIdentifier("imgBack" + (i + 1), "id", MainActivity.mainContext.getPackageName());
            int idBackImage = res.getIdentifier("img" + (i + 1), "id", MainActivity.mainContext.getPackageName());

            frontImages[i] = (ImageView) findViewById(idFrontImage);
            backImages[i] = (ImageView) findViewById(idBackImage);
        }

        setCards(frontImages, backImages, "fruit");
        setFrontImagesClickListeners();

    }
}
