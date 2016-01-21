package com.alexasapps.controller;

import android.os.Bundle;
import com.alexasapps.multiplecardsflippingdemo.R;

public class MediumCardFlipping extends CardFlipping{

    private static final String SCORE_KEY_TRIES = "MediumScoreTries";
    private static final String SCORE_KEY_TIME = "MediumScoreTime";

    final int nrOfCards = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medium_layout);

        setImages();
        setCards(frontImages, backImages, "cartoon");
        setFrontImagesClickListeners();

        setScoreKeys(SCORE_KEY_TRIES, SCORE_KEY_TIME);
    }

    @Override
    int getNumberOfCards() {
        return nrOfCards;
    }
}
