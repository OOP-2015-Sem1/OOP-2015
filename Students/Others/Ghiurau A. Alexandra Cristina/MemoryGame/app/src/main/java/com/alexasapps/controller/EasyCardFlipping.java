package com.alexasapps.controller;

import android.os.Bundle;
import com.alexasapps.multiplecardsflippingdemo.R;

public class EasyCardFlipping extends CardFlipping {

    private static final String SCORE_KEY_TRIES = "EasyScoreTries";
    private static final String SCORE_KEY_TIME = "EasyScoreTime";

    final int nrOfCards = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_layout);

        setImages();
        setCards(frontImages, backImages, "fruit");
        setFrontImagesClickListeners();

        setScoreKeys(SCORE_KEY_TRIES, SCORE_KEY_TIME);
    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }
}
