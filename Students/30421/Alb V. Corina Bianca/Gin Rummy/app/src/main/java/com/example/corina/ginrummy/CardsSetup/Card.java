package com.example.corina.ginrummy.CardsSetup;

/**
 * Created by Corina on 1/22/2016.
 */
public class Card {

    private String mSuit;
    private String mValue;
    private int cardImage;
    private int mCost;
    private int mCardIndex;
    private int mOrder;

    private Boolean mUsed;

    public void setUsed(Boolean used) {
        this.mUsed = used;
    }

    public String getSuit() {
        return mSuit;
    }

    public void setSuit(String mSuit) {
        this.mSuit = mSuit;
    }

    public String getValue() {
        return mValue;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        this.mOrder = order;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public int getCost() {
        return mCost;
    }

    public void setCost(int mCost) {
        this.mCost = mCost;
    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }

    public int getCardIndex() {
        return mCardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.mCardIndex = cardIndex;
    }
}