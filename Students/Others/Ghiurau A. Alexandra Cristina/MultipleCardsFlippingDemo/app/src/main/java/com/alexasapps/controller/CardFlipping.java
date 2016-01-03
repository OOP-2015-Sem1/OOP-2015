package com.alexasapps.controller;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexasapps.model.Card;
import com.alexasapps.multiplecardsflippingdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class CardFlipping extends Activity {

    private Context context;

    boolean isBackVisible = false;

    public ImageView[] frontImages;
    public ImageView[] backImages;

    int count = 0;
    int nrOfTries = 0;

    int index1 = -1;
    int index2 = -1;


    public ArrayList<Card> cards = new ArrayList<Card>();

    List<String> backDrawables = new ArrayList<>();


    public void setCards(ImageView[] frontImages, ImageView[] backImages, String imageName) {

        for (int i = 1; i <= frontImages.length / 2; i++) {
            backDrawables.add("@drawable/" + imageName + i);
            backDrawables.add("@drawable/" + imageName + i);
        }
        Collections.shuffle(backDrawables);

        for (int i = 0; i < frontImages.length; i++) {

            Card card = new Card();

            int imageResource = getResources().getIdentifier(backDrawables.get(i), null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            backImages[i].setImageDrawable(res);

            card.setId(i);
            card.setFrontImage(frontImages[i]);
            card.setBackImage(backImages[i]);
            cards.add(card);
        }

    }

    public void doTurn(Card card) {

        final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flip_right_out);

        final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_left_in);

        if (!card.isSelected()) {

            setRightOut.setTarget(card.getFrontImage());
            setLeftIn.setTarget(card.getBackImage());
            setRightOut.start();
            setLeftIn.start();

            isBackVisible = true;
            card.setSelected(true);

        } else {

            setRightOut.setTarget(card.getBackImage());
            setLeftIn.setTarget(card.getFrontImage());
            setRightOut.start();
            setLeftIn.start();

            isBackVisible = false;
            card.setSelected(false);
        }

    }

    public void checkCards(int index1, int index2) {
        if (cards.get(index1).getBackImage().getDrawable().getConstantState().equals(cards.get(index2).getBackImage().getDrawable().getConstantState())) {

            cards.get(index1).setMatched(true);
            cards.get(index2).setMatched(true);
            cards.get(index1).setSelected(false);
            cards.get(index2).setSelected(false);

        } else {
            doTurn(cards.get(index1));
            doTurn(cards.get(index2));
        }
        if (this.isGameWon()) {

            Toast toast = Toast.makeText(getApplicationContext(), "YOU WON", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean isGameWon() {
        for (Card c : this.cards) {
            if (c.isMatched() == false) {
                return false;
            }
        }
        return true;
    }

    public void setFrontImagesClickListeners() {
        for (int i = 0; i < cards.size(); i++) {
            frontImages[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < frontImages.length; i++)
                        if (v.getId() == frontImages[i].getId()) {
                            nrOfTries = nrOfTries + 1;
                            if (!cards.get(i).isMatched() && !cards.get(i).isSelected())
                                doTurn(cards.get(i));

                        }
                    count = 0;
                    for (int i = 0; i < cards.size(); i++) {
                        if (cards.get(i).isSelected()) {
                            count++;
                        }
                    }

                    if (count == 2) {
                        for (int i = 0; i < cards.size(); i++) {
                            if (cards.get(i).isSelected()) {
                                if (index1 == -1) {
                                    index1 = i;
                                } else {
                                    index2 = i;
                                }
                            }
                        }
                        checkCards(index1, index2);
                        index1 = -1;
                        index2 = -1;

                    }
                }
            });

        }
    }
}
