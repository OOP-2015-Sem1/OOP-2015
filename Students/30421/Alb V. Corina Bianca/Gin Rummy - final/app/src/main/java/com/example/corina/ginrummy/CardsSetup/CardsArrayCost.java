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

    public ArrayList<Card> getCardsArray() {
        return mCardsArray;
    }

    public boolean cardIsValid(Card card, ArrayList<Card> list) {
        ArrayList<Card> myList = list;
        int index = 0;

        for (int i = 0; i < myList.size() - 1; i++) {
            if (list.get(i) == card) {
                index = i;
            }
            for (int j = i + 1; j < myList.size(); j++) {
                if (myList.get(i).getOrder() > myList.get(j).getOrder()) {
                    Card aux = myList.get(i);
                    myList.set(i, myList.get(j));
                    myList.set(j, aux);
                }
            }
        }

        if (index - 1 >= 0 && list.get(index).getOrder() - list.get(index - 1).getOrder() == 1 &&
                index + 1 < list.size() && list.get(index + 1).getOrder() - list.get(index).getOrder() == 1) {
            return true;
        } else if (index - 2 >= 0 && list.get(index).getOrder() - list.get(index - 1).getOrder() == 1 &&
                list.get(index - 1).getOrder() - list.get(index - 2).getOrder() == 1) {
            return true;
        } else if (index + 2 < list.size() && list.get(index + 2).getOrder() - list.get(index + 1).getOrder() == 1 &&
                list.get(index + 1).getOrder() - list.get(index).getOrder() == 1) {
            return true;
        }

        return false;
    }

    public void setCardsArray(ArrayList<Card> mCardsArray) {
        this.mCardsArray = mCardsArray;
    }

    public int getCardIndex(Card card) {
        int index = 0;
        for (int i = 0; i < mCardsArray.size(); i++) {
            if (mCardsArray.get(i) == card) {
                index = i;
            }
        }
        return index;
    }

    public int getSum() {
        for (int i = 0; i < mCardsArray.size() - 1; i++) {
            if (mCardsArray.get(i).getOrder() - mCardsArray.get(i + 1).getOrder() == 1)
                mSum = mSum + mCardsArray.get(i).getCost();
        }
        return mSum;
    }

    public void setSum(int sum) {
        this.mSum = sum;
    }
}
