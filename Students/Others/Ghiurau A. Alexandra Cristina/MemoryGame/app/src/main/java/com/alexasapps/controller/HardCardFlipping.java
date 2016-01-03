package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.R;

public class HardCardFlipping extends CardFlipping {

    final int nrOfCards = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hard_layout);

        super.setImages();
        setCards(frontImages, backImages, "peng");
        setFrontImagesClickListeners();

    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }
}
