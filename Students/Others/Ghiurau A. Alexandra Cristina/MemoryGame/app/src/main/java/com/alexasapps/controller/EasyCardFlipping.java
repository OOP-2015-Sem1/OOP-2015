package com.alexasapps.controller;


import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
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

/*
        frontImages = new ImageView[]{(ImageView) findViewById(R.id.imgBack1), (ImageView) findViewById(R.id.imgBack2), (ImageView)
                findViewById(R.id.imgBack3), (ImageView) findViewById(R.id.imgBack4), (ImageView) findViewById(R.id.imgBack5),
                (ImageView) findViewById(R.id.imgBack6), (ImageView) findViewById(R.id.imgBack7), (ImageView) findViewById(R.id.imgBack8),
                (ImageView) findViewById(R.id.imgBack9), (ImageView) findViewById(R.id.imgBack10), (ImageView) findViewById(R.id.imgBack11),
                (ImageView) findViewById(R.id.imgBack12)};

        backImages = new ImageView[]{(ImageView) findViewById(R.id.img1), (ImageView) findViewById(R.id.img2),
                (ImageView) findViewById(R.id.img3), (ImageView) findViewById(R.id.img4), (ImageView) findViewById(R.id.img5),
                (ImageView) findViewById(R.id.img6), (ImageView) findViewById(R.id.img7), (ImageView) findViewById(R.id.img8),
                (ImageView) findViewById(R.id.img9), (ImageView) findViewById(R.id.img10), (ImageView) findViewById(R.id.img11),
                (ImageView) findViewById(R.id.img12)};
*/
        setCards(frontImages, backImages, "fruit");


        Toast toast = Toast.makeText(getApplicationContext(), "size: " + cards.size(), Toast.LENGTH_SHORT);
        toast.show();

        setFrontImagesClickListeners();

    }
}
