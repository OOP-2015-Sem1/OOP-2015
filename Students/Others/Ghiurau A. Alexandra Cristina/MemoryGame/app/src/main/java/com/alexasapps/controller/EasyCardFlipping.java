package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.MainActivity;
import com.alexasapps.multiplecardsflippingdemo.R;

public class EasyCardFlipping extends CardFlipping {

    final int nrOfCards = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_layout);

        super.setImages();
        setCards(frontImages, backImages, "fruit");
        setFrontImagesClickListeners();

    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }

}
