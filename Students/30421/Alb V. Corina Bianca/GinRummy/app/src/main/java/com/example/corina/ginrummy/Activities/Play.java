package com.example.corina.ginrummy.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.corina.ginrummy.CardsSetup.Card;
import com.example.corina.ginrummy.CardsSetup.CardPiles;
import com.example.corina.ginrummy.CardsSetup.CardsArrayCost;
import com.example.corina.ginrummy.CardsSetup.Deck;
import com.example.corina.ginrummy.Constants;
import com.example.corina.ginrummy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Corina on 1/22/2016.
 */
public class Play extends Activity {

    private Deck gameDeck;
    private ArrayList<Integer> exclude = new ArrayList<>();
    private CardPiles mComputerCards;
    private CardPiles mPlayerCards;
    private CardPiles mStackPile;
    private CardPiles mDiscardPile;
    private SharedPreferences sp;
    private int mSumComputer = 0;
    private int mSumPlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sp = getSharedPreferences(Constants.PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constants.IS_CARD_SELECTED, false);
        editor.putInt(Constants.FIRST_CARD_SELECTED, Constants.INVALID);
        editor.putInt(Constants.SECOND_CARD_SELECTED, Constants.INVALID);
        editor.commit();

        gameDeck = new Deck();
        gameSetup();

        ImageButton topDiscard = (ImageButton) findViewById(R.id.discard_pile);
        topDiscard.setBackgroundDrawable(getResources().getDrawable(mDiscardPile.getCard(0).getCardImage()));
        discardCrad(topDiscard);

        ImageButton card1 = (ImageButton) findViewById(R.id.card1);
        card1.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(0).getCardImage()));
        playerCard(card1);
        ImageButton card2 = (ImageButton) findViewById(R.id.card2);
        card2.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(1).getCardImage()));
        playerCard(card2);
        ImageButton card3 = (ImageButton) findViewById(R.id.card3);
        card3.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(2).getCardImage()));
        playerCard(card3);
        ImageButton card4 = (ImageButton) findViewById(R.id.card4);
        card4.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(3).getCardImage()));
        playerCard(card4);
        ImageButton card5 = (ImageButton) findViewById(R.id.card5);
        card5.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(4).getCardImage()));
        playerCard(card5);
        ImageButton card6 = (ImageButton) findViewById(R.id.card6);
        card6.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(5).getCardImage()));
        playerCard(card6);
        ImageButton card7 = (ImageButton) findViewById(R.id.card7);
        card7.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(6).getCardImage()));
        playerCard(card7);
        ImageButton card8 = (ImageButton) findViewById(R.id.card8);
        card8.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(7).getCardImage()));
        playerCard(card8);
        ImageButton card9 = (ImageButton) findViewById(R.id.card9);
        card9.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(8).getCardImage()));
        playerCard(card9);
        ImageButton card10 = (ImageButton) findViewById(R.id.card10);
        card10.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(9).getCardImage()));
        playerCard(card10);

        ImageButton topStack = (ImageButton) findViewById(R.id.stack_pile);
        topStack.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
        stackCard(topStack);

        ImageButton topComputer = (ImageButton) findViewById(R.id.computer_player);
        topComputer.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));

        final Button computer = (Button) findViewById(R.id.player_done);
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerPlays(R.id.discard_pile);
                ImageButton aux1 = (ImageButton) findViewById(R.id.discard_pile);
                ImageButton aux2 = (ImageButton) findViewById(R.id.stack_pile);

                computer.setBackgroundColor(Color.GRAY);
                computer.setEnabled(false);

                verifyWinner();

                aux1.setEnabled(true);
                aux2.setEnabled(true);
            }
        });
    }

    // stack pile
    private void stackCard(final ImageButton topStack) {
        topStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean(Constants.IS_CARD_SELECTED, Boolean.parseBoolean(null))) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(Constants.IS_CARD_SELECTED, false);
                    editor.putInt(Constants.SECOND_CARD_SELECTED, topStack.getId());
                    editor.commit();

                    switchGetFromStack(sp.getInt(Constants.SECOND_CARD_SELECTED, Constants.INVALID),
                            sp.getInt(Constants.FIRST_CARD_SELECTED, Constants.INVALID));
                    disablePiles();
                }
            }
        });
    }

    private void switchGetFromStack(int currentId, int previousId) {

        ImageButton aux1 = (ImageButton) findViewById(previousId);
//        ImageButton aux2 = (ImageButton) findViewById(currentId);
        ImageButton discard = (ImageButton) findViewById(R.id.discard_pile);

        Card current = mStackPile.getCard(mStackPile.getSize()-1);
        current.setUsed(false);

        mDiscardPile.setSize(mDiscardPile.getSize() + 1);

        aux1.setBackgroundDrawable(getResources().getDrawable(mStackPile.getCard(mStackPile.getSize() - 1).getCardImage()));
        mDiscardPile.addCard(mPlayerCards.getCard(getPositionForId(previousId)), mDiscardPile.getSize() - 1);
        discard.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(getPositionForId(previousId)).getCardImage()));
        mSumPlayer = mSumPlayer - mPlayerCards.getCard(getPositionForId(previousId)).getCost();
        mPlayerCards.getCard(getPositionForId(previousId)).setUsed(false);
        mPlayerCards.setCard(getPositionForId(previousId), current);
        mStackPile.deleteCard(current);
        mSumPlayer = mSumPlayer + current.getCost();

        mStackPile.setSize(mStackPile.getSize() - 1);
    }

    // discard pile
    private void discardCrad(final ImageButton topDiscard) {

        topDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean(Constants.IS_CARD_SELECTED, Boolean.parseBoolean(null))) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(Constants.IS_CARD_SELECTED, false);
                    editor.putInt(Constants.SECOND_CARD_SELECTED, topDiscard.getId());
                    editor.commit();

                    switchGetCards(sp.getInt(Constants.SECOND_CARD_SELECTED, Constants.INVALID),
                            sp.getInt(Constants.FIRST_CARD_SELECTED, Constants.INVALID));
                    disablePiles();
                }
            }
        });
    }

    // game algorithm
    private void verifyWinner() {

        if (mSumComputer >= Constants.MIN_SCORE_TO_WIN && mSumPlayer < Constants.MIN_SCORE_TO_WIN) {
            if (verifyComputerCards() >= Constants.MIN_SCORE_TO_WIN){
                Toast.makeText(this, "Computer wins!", Toast.LENGTH_SHORT).show();
            }
        } else if (mSumPlayer >= Constants.MIN_SCORE_TO_WIN && mSumComputer < Constants.MIN_SCORE_TO_WIN) {
            if (verifyPlayerCards() >= 40){
                Toast.makeText(this, "You win! Congratulations!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (mStackPile.getSize() == 0 ) {
                Toast.makeText(this, "No winner", Toast.LENGTH_SHORT).show();
                ImageButton aux1 = (ImageButton) findViewById(R.id.discard_pile);
                ImageButton aux2 = (ImageButton) findViewById(R.id.stack_pile);
                Button aux3 = (Button) findViewById(R.id.player_done);

                aux1.setEnabled(false);
                aux2.setEnabled(false);
                aux3.setEnabled(false);
            }
        }
    }

    private int verifyPlayerCards() {

        int sum = 0;

        HashMap<String, CardsArrayCost> sameValue = new HashMap<>();
        HashMap<String, CardsArrayCost> sameSuit = new HashMap<>();

        sameValue.put("A", new CardsArrayCost());
        sameValue.put("2", new CardsArrayCost());
        sameValue.put("3", new CardsArrayCost());
        sameValue.put("4", new CardsArrayCost());
        sameValue.put("5", new CardsArrayCost());
        sameValue.put("6", new CardsArrayCost());
        sameValue.put("7", new CardsArrayCost());
        sameValue.put("8", new CardsArrayCost());
        sameValue.put("9", new CardsArrayCost());
        sameValue.put("10", new CardsArrayCost());
        sameValue.put("J", new CardsArrayCost());
        sameValue.put("Q", new CardsArrayCost());
        sameValue.put("K", new CardsArrayCost());

        sameSuit.put("Heart", new CardsArrayCost());
        sameSuit.put("Clubs", new CardsArrayCost());
        sameSuit.put("Diamond", new CardsArrayCost());
        sameSuit.put("Spade", new CardsArrayCost());

        //verify for cost and suit
        for (int i=0; i<Constants.PLAYERS_PILE; i++) {
            sameValue.get(mPlayerCards.getCard(i).getValue()).getCardsArray().add(mPlayerCards.getCard(i));
            sameSuit.get(mPlayerCards.getCard(i).getSuit()).getCardsArray().add(mPlayerCards.getCard(i));
        }

        ArrayList<Card> usedCards = new ArrayList<>();

        for (int i=0; i<Constants.PLAYERS_PILE; i++) {
            if (sameValue.get(mPlayerCards.getCard(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH ||
                    sameSuit.get(mPlayerCards.getCard(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH) {
                usedCards.add(mPlayerCards.getCard(i));
            }
        }

        //determine how to use cards
        for (int i=0; i<usedCards.size(); i++) {

            if (sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() == Constants.MIN_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH) {
                    if (sameValue.get(usedCards.get(i).getValue()).getSum() >= sameSuit.get(usedCards.get(i).getSuit()).getSum()) {
                        sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(usedCards.get(i));
                    } else {
                        sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mPlayerCards.getCard(i));
                    }
                }
            }
            if (sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() == Constants.VALUE_MAX_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH) {
                    if (sameValue.get(usedCards.get(i).getValue()).getSum() >
                            sameValue.get(usedCards.get(i).getValue()).getSum() - usedCards.get(i).getCost() +
                                    sameSuit.get(usedCards.get(i).getValue()).getSum()) {
                        sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mPlayerCards.getCard(i));
                    }
                } else {
                    sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(mPlayerCards.getCard(i));
                }
            }
            if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH &&
                    sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getSum() > sameValue.get(usedCards.get(i).getValue()).getSum()) {
                    sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mPlayerCards.getCard(i));
                } else {
                    sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(mPlayerCards.getCard(i));
                }
            }
        }
        for (int i=0; i<usedCards.size(); i++) {
            if (sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                sum = sum + usedCards.get(i).getCost();
            } else if ((sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH )) {
                if (sameSuit.get(usedCards.get(i).getSuit()).isValid(sameSuit.get(usedCards.get(i).getSuit()).getCardsArray())) {
                    sum = sum + usedCards.get(i).getCost();
                }
            }
        }
        return sum;
    }

    private int verifyComputerCards() {

        int sum = 0;

        HashMap<String, CardsArrayCost> sameValue = new HashMap<>();
        HashMap<String, CardsArrayCost> sameSuit = new HashMap<>();

        sameValue.put("A", new CardsArrayCost());
        sameValue.put("2", new CardsArrayCost());
        sameValue.put("3", new CardsArrayCost());
        sameValue.put("4", new CardsArrayCost());
        sameValue.put("5", new CardsArrayCost());
        sameValue.put("6", new CardsArrayCost());
        sameValue.put("7", new CardsArrayCost());
        sameValue.put("8", new CardsArrayCost());
        sameValue.put("9", new CardsArrayCost());
        sameValue.put("10", new CardsArrayCost());
        sameValue.put("J", new CardsArrayCost());
        sameValue.put("Q", new CardsArrayCost());
        sameValue.put("K", new CardsArrayCost());

        sameSuit.put("Heart", new CardsArrayCost());
        sameSuit.put("Clubs", new CardsArrayCost());
        sameSuit.put("Diamond", new CardsArrayCost());
        sameSuit.put("Spade", new CardsArrayCost());

        //verify for cost and suit
        for (int i=0; i<10; i++) {
            sameValue.get(mComputerCards.getCard(i).getValue()).getCardsArray().add(mComputerCards.getCard(i));
            sameSuit.get(mComputerCards.getCard(i).getSuit()).getCardsArray().add(mComputerCards.getCard(i));
        }

        ArrayList<Card> usedCards = new ArrayList<>();

        for (int i=0; i<10; i++) {
            if (sameValue.get(mComputerCards.getCard(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH ||
                    sameSuit.get(mComputerCards.getCard(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                usedCards.add(mComputerCards.getCard(i));
            }
        }

        //determine how to use cards
        for (int i=0; i<usedCards.size(); i++) {
            if (sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() == Constants.MIN_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH) {
                    if (sameValue.get(usedCards.get(i).getValue()).getSum() >= sameSuit.get(usedCards.get(i).getSuit()).getSum()) {
                        sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(usedCards.get(i));
                    } else {
                        sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mComputerCards.getCard(i));
                    }
                }
            }
            if (sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() == Constants.VALUE_MAX_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH) {
                    if (sameValue.get(usedCards.get(i).getValue()).getSum() >
                            sameValue.get(usedCards.get(i).getValue()).getSum() - usedCards.get(i).getCost() +
                                    sameSuit.get(usedCards.get(i).getValue()).getSum()) {
                        sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mComputerCards.getCard(i));
                    }
                } else {
                    sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(mComputerCards.getCard(i));
                }
            }
            if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH &&
                    sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                if (sameSuit.get(usedCards.get(i).getSuit()).getSum() > sameValue.get(usedCards.get(i).getValue()).getSum()) {
                    sameValue.get(usedCards.get(i).getValue()).getCardsArray().remove(mComputerCards.getCard(i));
                } else {
                    sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().remove(mComputerCards.getCard(i));
                }
            }
        }
        for (int i=0; i<usedCards.size(); i++) {
            if (sameSuit.get(usedCards.get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH ||
                    sameValue.get(usedCards.get(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                sum = sum + usedCards.get(i).getCost();
            }
        }
        return sum;
    }

    private void switchGetCards(int currentId, int previousId) {

        ImageButton aux1 = (ImageButton) findViewById(previousId);
        ImageButton aux2 = (ImageButton) findViewById(currentId);

        Card current = mDiscardPile.getCard(mDiscardPile.getSize()-1);
        current.setUsed(false);

        aux1.setBackgroundDrawable(getResources().getDrawable(mDiscardPile.getCard(mDiscardPile.getSize() - 1).getCardImage()));
        mDiscardPile.setCard(mDiscardPile.getSize() - 1, mPlayerCards.getCard(getPositionForId(previousId)));
        mSumPlayer = mSumPlayer - mPlayerCards.getCard(getPositionForId(previousId)).getCost();
        mPlayerCards.getCard(getPositionForId(previousId)).setUsed(false);
        aux2.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(getPositionForId(previousId)).getCardImage()));
        mPlayerCards.setCard(getPositionForId(previousId), current);
        mSumPlayer = mSumPlayer + current.getCost();

    }

    // player cards
    private void playerCard(final ImageButton card) {

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean("card_selected", Boolean.parseBoolean(null))) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("card_selected", false);
                    editor.putInt("card_id_c", card.getId());
                    editor.commit();

                    switchCards(sp.getInt("card_id_c", Constants.INVALID), sp.getInt("card_id_p", Constants.INVALID));
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("card_selected", true);

                    editor.putInt("card_id_p", card.getId());
                    editor.commit();
                }
            }
        });
    }

    private void disablePiles() {
        ImageButton aux1 = (ImageButton) findViewById(R.id.discard_pile);
        ImageButton aux2 = (ImageButton) findViewById(R.id.stack_pile);

        Button aux3 = (Button) findViewById(R.id.player_done);
        aux3.setBackgroundColor(Color.CYAN);
        aux3.setEnabled(true);

        aux1.setEnabled(false);
        aux2.setEnabled(false);
    }

    private void computerPlays(int id) {

        int randomNumber;
        Random random = new Random();
        do {
            randomNumber = random.nextInt() % Constants.PLAYERS_PILE;
        } while (randomNumber < 0);

        Card current = mComputerCards.getCard(randomNumber);

        mSumComputer = mSumComputer - current.getCost();

        mComputerCards.setCard(randomNumber, getPileForId(id).getCard(getPileForId(id).getSize() - 1));
        mSumComputer = mSumComputer + mComputerCards.getCard(randomNumber).getCost();
        if (id == R.id.stack_pile) {
            mDiscardPile.setSize(mDiscardPile.getSize() + 1);
            mDiscardPile.addCard(current, mDiscardPile.getSize() - 1);
            mStackPile.deleteCard(getPileForId(id).getCard(getPileForId(id).getSize() - 1));
            mStackPile.setSize(mStackPile.getSize() - 1);
        } else {
            mDiscardPile.setCard(mDiscardPile.getSize() - 1, current);
        }

        ImageButton aux = (ImageButton) findViewById(R.id.discard_pile);
        aux.setBackgroundDrawable(getResources().getDrawable(current.getCardImage()));
    }

    private void switchCards(int currentId, int previousId) {

        ImageButton aux1 = (ImageButton) findViewById(previousId);
        ImageButton aux2 = (ImageButton) findViewById(currentId);

        Card current = mPlayerCards.getCard(getPositionForId(currentId));

        aux1.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(getPositionForId(currentId)).getCardImage()));
        mPlayerCards.setCard(getPositionForId(currentId), mPlayerCards.getCard(getPositionForId(previousId)));
        aux2.setBackgroundDrawable(getResources().getDrawable(mPlayerCards.getCard(getPositionForId(previousId)).getCardImage()));
        mPlayerCards.setCard(getPositionForId(previousId), current);

    }

    private CardPiles getPileForId(int id) {

        switch (id) {
            case R.id.stack_pile : return mStackPile;
            default : return mDiscardPile;
        }
    }

    private int getPositionForId(int id) {

        switch (id) {
            case R.id.card1 : return 0;
            case R.id.card2 : return 1;
            case R.id.card3 : return 2;
            case R.id.card4 : return 3;
            case R.id.card5 : return 4;
            case R.id.card6 : return 5;
            case R.id.card7 : return 6;
            case R.id.card8 : return 7;
            case R.id.card9 : return 8;
            default : return 9;
        }
    }

    // game setup
    private void gameSetup() {
        //set cards in piles
        mComputerCards = new CardPiles();
        mPlayerCards = new CardPiles();
        mStackPile = new CardPiles();
        mDiscardPile = new CardPiles();

        mDiscardPile.setSize(Constants.DISCARD_DEFAULT_PILE);
        mStackPile.setSize(Constants.STACK_DEFAULT_PILE);

        for (int i=0; i<Constants.PLAYERS_PILE; i++) {
            Card auxCard = getRandom();
            mComputerCards.addCard(auxCard, i);
            mSumComputer = mSumComputer + auxCard.getCost();
            auxCard = getRandom();
            mPlayerCards.addCard(auxCard, i);
            mSumPlayer = mSumPlayer + auxCard.getCost();
        }
        for (int i=0; i<Constants.DISCARD_DEFAULT_PILE; i++) {
            mStackPile.addCard(getRandom(),i);
        }
        mDiscardPile.addCard(getRandom(),0);
    }

    // TODO - random -- !!! duplicates !!!
    public Card getRandom() {
        int randomNumber;
        Random random = new Random();
        do {
            randomNumber = random.nextInt() % Constants.DECK;
        }
        while (exclude.contains(randomNumber) && randomNumber < 0);
        exclude.add(randomNumber);
        return gameDeck.getCardAtIndex(randomNumber);
//        return gameDeck.getCardAtIndex(randomI++);
    }
}