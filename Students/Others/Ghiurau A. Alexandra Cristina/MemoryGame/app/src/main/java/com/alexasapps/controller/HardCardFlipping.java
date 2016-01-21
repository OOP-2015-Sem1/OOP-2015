package com.alexasapps.controller;


import android.os.Bundle;
import com.alexasapps.multiplecardsflippingdemo.R;

public class HardCardFlipping extends CardFlipping {

    private static final String SCORE_KEY_TRIES = "HardScoreTries";
    private static final String SCORE_KEY_TIME = "HardScoreTime";

    final int nrOfCards = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hard_layout);

        setImages();
        setCards(frontImages, backImages, "peng");
        setFrontImagesClickListeners();

        setScoreKeys(SCORE_KEY_TRIES, SCORE_KEY_TIME);
    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }
}
