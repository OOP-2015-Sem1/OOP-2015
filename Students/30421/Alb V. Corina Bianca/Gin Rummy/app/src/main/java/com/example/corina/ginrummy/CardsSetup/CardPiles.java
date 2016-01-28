package com.example.corina.ginrummy.CardsSetup;

import com.example.corina.ginrummy.CardsSetup.Card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Corina on 1/22/2016.
 */
public class CardPiles implements Serializable {

    private int mSize;

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        this.mSize = size;
    }

    private ArrayList<Card> mCards = new ArrayList<>();

    public void addCard(Card card, int position) {
        mCards.add(position, card);
    }

    public void deleteCard(Card card) {
        mCards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return mCards;
    }

    public Card getCard(int index) {
        return mCards.get(index);
    }

    public void setCard(int index, Card card) {
        mCards.set(index, card);
    }
}