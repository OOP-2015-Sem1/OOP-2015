package com.alexasapps.controller;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.multiplecardsflippingdemo.MainActivity;
import com.alexasapps.multiplecardsflippingdemo.R;

public class MediumCardFlipping extends CardFlipping{

    final int nrOfCards = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medium_layout);

        super.setImages();
        setCards(frontImages, backImages, "cartoon");
        setFrontImagesClickListeners();

    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }
}
