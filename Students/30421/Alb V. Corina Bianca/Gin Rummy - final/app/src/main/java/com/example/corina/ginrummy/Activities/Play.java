package com.example.corina.ginrummy.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corina.ginrummy.CardsSetup.Card;
import com.example.corina.ginrummy.CardsSetup.CardPiles;
import com.example.corina.ginrummy.CardsSetup.CardsArrayCost;
import com.example.corina.ginrummy.CardsSetup.Deck;
import com.example.corina.ginrummy.Constants;
import com.example.corina.ginrummy.R;
import com.example.corina.ginrummy.Welcome.Welcome;

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

    private TextView score;

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

        initializePiles();

        score = (TextView) findViewById(R.id.score);
        score.setText(" 0 ");

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

                score.setText(Constants.YOU + mSumPlayer + Constants.COMPUTER + mSumComputer);

                aux1.setEnabled(true);
                aux2.setEnabled(true);
            }
        });
    }

    private void initializePiles() {
        ImageButton topDiscard = (ImageButton) findViewById(R.id.discard_pile);
        topDiscard.setBackgroundDrawable(getResources().getDrawable(mDiscardPile.getCard(0).getCardImage()));
        discardCard(topDiscard);

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

                    switchGetFromStack(sp.getInt(Constants.FIRST_CARD_SELECTED, Constants.INVALID));
                    disablePiles();
                }
            }
        });
    }

    private void switchGetFromStack(int previousId) {

        ImageButton aux1 = (ImageButton) findViewById(previousId);
        ImageButton discard = (ImageButton) findViewById(R.id.discard_pile);

        Card current = mStackPile.getCard(mStackPile.getSize() - 1);
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
    private void discardCard(final ImageButton topDiscard) {

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

    private void switchGetCards(int currentId, int previousId) {

        ImageButton aux1 = (ImageButton) findViewById(previousId);
        ImageButton aux2 = (ImageButton) findViewById(currentId);

        Card current = mDiscardPile.getCard(mDiscardPile.getSize() - 1);
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
                if (sp.getBoolean(Constants.IS_CARD_SELECTED, Boolean.parseBoolean(null))) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(Constants.IS_CARD_SELECTED, false);
                    editor.putInt(Constants.SECOND_CARD_SELECTED, card.getId());
                    editor.commit();

                    switchCards(sp.getInt(Constants.SECOND_CARD_SELECTED, Constants.INVALID),
                            sp.getInt(Constants.FIRST_CARD_SELECTED, Constants.INVALID));
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(Constants.IS_CARD_SELECTED, true);

                    editor.putInt(Constants.FIRST_CARD_SELECTED, card.getId());
                    editor.commit();
                }
            }
        });
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

    // computer cards
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

    // game algorithm
    private void verifyWinner() {

        if (mSumComputer >= Constants.MIN_SCORE_TO_WIN) {
            if (verifyCards(mComputerCards) >= Constants.MIN_SCORE_TO_WIN) {
                Toast.makeText(this, R.string.computer_wins, Toast.LENGTH_SHORT).show();
            }
        } else if (mSumPlayer >= Constants.MIN_SCORE_TO_WIN) {
            int constant = verifyCards(mPlayerCards);
            if (constant >= 40) {
                new AlertDialog.Builder(this)
                        .setTitle("You are the winner !!!")
                        .setMessage("Game over")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent stopGameIntent = new Intent(Play.this, Welcome.class);
                                Play.this.startActivity(stopGameIntent);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.btn_star)
                        .show();
            }
        } else {
            if (mStackPile.getSize() == 0) {
                Toast.makeText(this, R.string.no_winner, Toast.LENGTH_SHORT).show();
                ImageButton aux1 = (ImageButton) findViewById(R.id.discard_pile);
                ImageButton aux2 = (ImageButton) findViewById(R.id.stack_pile);
                Button aux3 = (Button) findViewById(R.id.player_done);

                aux1.setEnabled(false);
                aux2.setEnabled(false);
                aux3.setEnabled(false);
            }
        }
    }

    private int verifyCards(CardPiles cards) {
        int sum = 0;
        HashMap<String, CardsArrayCost> sameValue = new HashMap<>();
        HashMap<String, CardsArrayCost> sameSuit = new HashMap<>();

        initValues(sameValue, sameSuit);

        //verify for cost and suit
        for (int i = 0; i < Constants.PLAYERS_PILE; i++) {
            sameValue.get(cards.getCard(i).getValue()).getCardsArray().add(cards.getCard(i));
            sameSuit.get(cards.getCard(i).getSuit()).getCardsArray().add(cards.getCard(i));
        }

        for (int i = 0; i < cards.getCards().size(); i++) {
            if (sameSuit.get(cards.getCards().get(i).getSuit()).getCardsArray().size() >= Constants.MIN_MATCH &&
                    sameSuit.get(cards.getCards().get(i).getSuit()).cardIsValid(cards.getCards().get(i), sameSuit.get(cards.getCards().get(i).getSuit()).getCardsArray())) {
                sum = sum + cards.getCard(i).getCost();
                if (sameValue.get(cards.getCards().get(i).getValue()).getCardsArray().size() == Constants.MIN_MATCH) {
                    if (sameValue.get(cards.getCards().get(i).getValue()).getSum() > sameSuit.get(cards.getCards().get(i).getSuit()).getSum()) {
                        sameSuit.get(cards.getCards().get(i).getSuit()).getCardsArray().remove(cards.getCard(i));
                    } else {
                        sameValue.get(cards.getCards().get(i).getValue()).getCardsArray().remove(cards.getCard(i));
                    }
                } else if (sameValue.get(cards.getCards().get(i).getValue()).getCardsArray().size() == Constants.VALUE_MAX_MATCH) {
                    if (sameValue.get(cards.getCard(i).getValue()).getSum() > sameValue.get(cards.getCard(i).getValue()).getSum() - cards.getCard(i).getCost() +
                            sameSuit.get(cards.getCard(i).getValue()).getSum()) {
                        sameSuit.get(cards.getCards().get(i).getSuit()).getCardsArray().remove(cards.getCard(i));
                    } else {
                        sameValue.get(cards.getCards().get(i).getValue()).getCardsArray().remove(cards.getCard(i));
                    }
                }
            } else if (sameValue.get(cards.getCard(i).getValue()).getCardsArray().size() >= Constants.MIN_MATCH) {
                sum = sum + cards.getCard(i).getCost();
            }
        }

        return sum;
    }

    private void initValues(HashMap<String, CardsArrayCost> sameValue, HashMap<String, CardsArrayCost> sameSuit) {
        sameValue.put(Constants.A, new CardsArrayCost());
        sameValue.put(Constants.TWO, new CardsArrayCost());
        sameValue.put(Constants.THREE, new CardsArrayCost());
        sameValue.put(Constants.FOUR, new CardsArrayCost());
        sameValue.put(Constants.FIVE, new CardsArrayCost());
        sameValue.put(Constants.SIX, new CardsArrayCost());
        sameValue.put(Constants.SEVEN, new CardsArrayCost());
        sameValue.put(Constants.EIGHT, new CardsArrayCost());
        sameValue.put(Constants.NINE, new CardsArrayCost());
        sameValue.put(Constants.TEN, new CardsArrayCost());
        sameValue.put(Constants.J, new CardsArrayCost());
        sameValue.put(Constants.Q, new CardsArrayCost());
        sameValue.put(Constants.K, new CardsArrayCost());

        sameSuit.put(Constants.H, new CardsArrayCost());
        sameSuit.put(Constants.C, new CardsArrayCost());
        sameSuit.put(Constants.D, new CardsArrayCost());
        sameSuit.put(Constants.S, new CardsArrayCost());
    }

    // useful
    private CardPiles getPileForId(int id) {

        switch (id) {
            case R.id.stack_pile:
                return mStackPile;
            default:
                return mDiscardPile;
        }
    }

    private int getPositionForId(int id) {

        switch (id) {
            case R.id.card1:
                return 0;
            case R.id.card2:
                return 1;
            case R.id.card3:
                return 2;
            case R.id.card4:
                return 3;
            case R.id.card5:
                return 4;
            case R.id.card6:
                return 5;
            case R.id.card7:
                return 6;
            case R.id.card8:
                return 7;
            case R.id.card9:
                return 8;
            default:
                return 9;
        }
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

    // game setup
    private void gameSetup() {
        //set cards in piles
        mComputerCards = new CardPiles();
        mPlayerCards = new CardPiles();
        mStackPile = new CardPiles();
        mDiscardPile = new CardPiles();

        mDiscardPile.setSize(Constants.DISCARD_DEFAULT_PILE);
        mStackPile.setSize(Constants.STACK_DEFAULT_PILE);

        for (int i = 0; i < Constants.PLAYERS_PILE; i++) {
            Card auxCard = getRandom();
            mComputerCards.addCard(auxCard, i);
            mSumComputer = mSumComputer + auxCard.getCost();
            auxCard = getRandom();
            mPlayerCards.addCard(auxCard, i);
            mSumPlayer = mSumPlayer + auxCard.getCost();
        }
        for (int i = 0; i < Constants.STACK_DEFAULT_PILE; i++) {
            mStackPile.addCard(getRandom(), i);
        }
        mDiscardPile.addCard(getRandom(), 0);
    }

    public Card getRandom() {
        int randomNumber;
        Random random = new Random();
        do {
            randomNumber = random.nextInt() % Constants.DECK;
        }
        while (exclude.contains(randomNumber) || randomNumber < 0);
        exclude.add(randomNumber);
        return gameDeck.getCardAtIndex(randomNumber);
    }
}