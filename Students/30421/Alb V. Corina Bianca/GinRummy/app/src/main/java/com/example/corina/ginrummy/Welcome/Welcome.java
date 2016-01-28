package com.example.corina.ginrummy.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.corina.ginrummy.Activities.Instructions;
import com.example.corina.ginrummy.Activities.Play;
import com.example.corina.ginrummy.R;

public class Welcome extends AppCompatActivity {

//    private Deck gameDeck;
//    private ArrayList<Integer> exclude = new ArrayList<>();
//    private CardPiles mComputerCards;
//    private CardPiles mPlayerCards;
//    private CardPiles mStackPile;
//    private CardPiles mDiscardPile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final TextView instruction = (TextView) findViewById(R.id.instructions);
        TextView play = (TextView) findViewById(R.id.play);

        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructionsIntent = new Intent(Welcome.this, Instructions.class);
                Welcome.this.startActivity(instructionsIntent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(Welcome.this, Play.class);
                Welcome.this.startActivity(playIntent);
            }
        });
    }

//    private void gameSetup(Deck gameDeck) {
//
//        //set cards
//        mComputerCards = new CardPiles();
//        mPlayerCards = new CardPiles();
//        mStackPile = new CardPiles();
//        mDiscardPile = new CardPiles();
//
//        for (int i=0; i<10; i++) {
//            mComputerCards.addCard(getRandom());
//            mPlayerCards.addCard(getRandom());
//        }
//
//        for (int i=0; i<30; i++) {
//            mStackPile.addCard(getRandom());
//        }
//
//        mDiscardPile.addCard(getRandom());
//
//    }
//
//    public Card getRandom() {
//
//        int randomNumber;
//
//        do {
//            Random random = new Random();
//            randomNumber = random.nextInt() % 52;
//        }
//        while (exclude.contains(randomNumber));
//
//        exclude.add(randomNumber);
//
//        return gameDeck.getCardAtIndex(randomNumber);
//    }
}