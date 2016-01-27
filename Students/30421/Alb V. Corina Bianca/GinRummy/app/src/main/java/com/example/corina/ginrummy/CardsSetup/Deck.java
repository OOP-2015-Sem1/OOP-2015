package com.example.corina.ginrummy.CardsSetup;

import com.example.corina.ginrummy.CardsSetup.Card;
import com.example.corina.ginrummy.Constants;
import com.example.corina.ginrummy.R;

import java.util.HashMap;

/**
 * Created by Corina on 1/22/2016.
 */
public class Deck {

    private String[] mSuits = new String[]{"Diamond", "Clubs", "Heart", "Spade"};
    private String[] mValue = new String[]{"A", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "J", "Q", "K"};
    private int[] mImages = new int[]{
            R.drawable.acediamonds, R.drawable.diamonds2, R.drawable.diamonds3, R.drawable.diamonds4, R.drawable.diamonds5, R.drawable.diamonds6,
            R.drawable.diamonds7, R.drawable.diamonds8, R.drawable.diamonds9, R.drawable.diamonds10, R.drawable.jackdiamonds, R.drawable.queendiamonds,
            R.drawable.kingdiamonds,

            R.drawable.aceclubs, R.drawable.clubs2, R.drawable.clubs3, R.drawable.clubs4, R.drawable.clubs5, R.drawable.clubs6,
            R.drawable.clubs7, R.drawable.clubs8, R.drawable.clubs9, R.drawable.clubs10, R.drawable.jackclubs, R.drawable.queenclubs,
            R.drawable.kingclubs,

            R.drawable.acehearts, R.drawable.hearts2, R.drawable.hearts3, R.drawable.hearts4, R.drawable.hearts5, R.drawable.hearts6,
            R.drawable.hearts7, R.drawable.hearts8, R.drawable.hearts9, R.drawable.hearts10, R.drawable.jackhearts, R.drawable.queenhearts,
            R.drawable.kinghearts,

            R.drawable.acespades, R.drawable.spades2, R.drawable.spades3, R.drawable.spades4, R.drawable.spades5, R.drawable.spades6,
            R.drawable.spades7, R.drawable.spades8, R.drawable.spades9, R.drawable.spades10, R.drawable.jackspades, R.drawable.queenspades,
            R.drawable.kingspades};
    private HashMap<Integer, Card> mDeck = new HashMap<>();

    public Deck() {
        for (int i=0; i< Constants.DECK; i++) {
            Card card = new Card();
            card.setValue(mValue[i % Constants.COMPLETE_SUIT]);
            card.setCardImage(mImages[i]);
            if (i >= Constants.DIAMOND_SUIT*Constants.COMPLETE_SUIT && i <= Constants.COMPLETE_SUIT - 1) {
                card.setSuit(mSuits[Constants.DIAMOND_SUIT]);
            }
            if (i >= Constants.CLUBS_SUIT*Constants.COMPLETE_SUIT && i <= 2 * Constants.COMPLETE_SUIT - 1) {
                card.setSuit(mSuits[Constants.CLUBS_SUIT]);
            }
            if (i >= Constants.HEART_SUIT*Constants.COMPLETE_SUIT && i <= 3 * Constants.COMPLETE_SUIT - 1) {
                card.setSuit(mSuits[Constants.HEART_SUIT]);
            }
            if (i >= Constants.SPADE_SUIT*Constants.COMPLETE_SUIT && i <= 4 * Constants.COMPLETE_SUIT - 1) {
                card.setSuit(mSuits[Constants.SPADE_SUIT]);
            }
            card.setUsed(false);
            card.setOrder(i % Constants.COMPLETE_SUIT);

            if ((i % Constants.COMPLETE_SUIT) >= 0 && (i % Constants.COMPLETE_SUIT) < 10) {
                card.setCost(1 + (i % Constants.COMPLETE_SUIT));
            } else {
                card.setCost(Constants.MAX_COST);
            }

            mDeck.put(i,card);
        }
    }

    public Card getCardAtIndex(int index) {
        return mDeck.get(index);
    }

    public void removeCardFromIndex(int index) {
        mDeck.remove(index);
    }
}