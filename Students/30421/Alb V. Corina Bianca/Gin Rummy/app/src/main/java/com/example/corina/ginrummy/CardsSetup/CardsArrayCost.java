package com.example.corina.ginrummy.CardsSetup;

import android.util.Log;

import com.example.corina.ginrummy.CardsSetup.Card;

import java.util.ArrayList;

/**
 * Created by Corina on 1/27/2016.
 */
public class CardsArrayCost {

    private ArrayList<Card> mCardsArray = new ArrayList<>();
    private int mSum;

    public CardsArrayCost() {
        this.mSum = 0;
    }

    public void addCrad(Card card){
        mCardsArray.add(card);
    }

    public ArrayList<Card> getCardsArray() {
        return mCardsArray;
    }

    public boolean isValid(ArrayList<Card> list){
        if (list.size() >= 3) {
            Log.e("TAG", " ---- ------- -- - - " + list.size());
            for (int i=0; i<list.size(); i++) {
                Log.e("TAG", " order " + list.get(i).getOrder());
                Log.e("TAG"," o " + mCardsArray.get(i).getValue() + " " + mCardsArray.get(i).getSuit() + " " + mCardsArray.get(i).getCost());
                for(int j=i+1; j<mCardsArray.size(); j++) {
                    if (mCardsArray.get(i).getOrder() > mCardsArray.get(j).getOrder()) {
                        int aux = mCardsArray.get(i).getOrder();
                        mCardsArray.get(i).setOrder(mCardsArray.get(j).getOrder());
                        mCardsArray.get(j).setOrder(aux);
                    }
                }
            }
            for (int i=0; i<list.size()-1; i++) {
                Log.e("TAG"," ----------------------------------------------");
                if (list.get(i+1).getOrder() - list.get(i).getOrder() != 1) {
                    Log.e("TAG","FALSE");
                    return false;
                }
            }
        }
        Log.e("TAG","TRUE");
        return true;
    }

    public void setCardsArray(ArrayList<Card> mCardsArray) {
        this.mCardsArray = mCardsArray;
    }

    public int getSum() {
        for (int i=0; i < mCardsArray.size(); i++) {
            mSum = mSum + mCardsArray.get(i).getCost();
        }
        return mSum;
    }

    public void setSum(int sum) {
        this.mSum = sum;
    }
}
