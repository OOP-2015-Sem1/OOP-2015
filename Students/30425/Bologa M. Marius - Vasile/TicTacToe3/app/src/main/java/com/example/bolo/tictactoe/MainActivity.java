package com.example.bolo.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    boolean turn = true;
    int moves = 0;
    Button[] bArray = null;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private int xWins;
    private int oWins;

    public MainActivity() {
        oWins = 0;
        xWins = 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b2 = (Button) findViewById(R.id.button2);
        b1 = (Button) findViewById(R.id.button1);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        final TextView t1 = (TextView) findViewById(R.id.t1);
        //ActionBar actionBar=getSupportActionBar();
      //  actionBar.setDisplayShowHomeEnabled(true);
      //  actionBar.setBackgroundDrawable(R.mipmap.tic);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.tic);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        t1.setText("0 - 0");
        bArray = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9};

        for (Button b : bArray)
            b.setOnClickListener(this);
        Button breset = (Button) findViewById(R.id.buttonRS);
        breset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                xWins = 0;
                oWins = 0;
                t1.setText(xWins + " - " + oWins);
            }
        });
        Button bexit = (Button) findViewById((R.id.buttonE));
        bexit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        Button bnew = (Button) findViewById(R.id.buttonR);
        bnew.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                turn = true;
                moves = 0;
                enableOrDisable(true);
                String x = String.valueOf(xWins);
                String o = String.valueOf(oWins);
                String l = " - ";
                t1.setText(x + l + o);

            }
        });
    }

    @Override
    public void onClick(View v) {
        buttonClicked(v);
    }

    private void buttonClicked(View v) {
        Button b = (Button) v;
        if (turn) {

            b.setText("X");

        } else {

            b.setText("O");
        }
        moves++;
        b.setClickable(false);
        turn = !turn;
        b.setBackgroundColor(Color.RED);
        checkForWinner();
    }

    private void checkForWinner() {


        boolean winner = false;

        if (b1.getText() == b2.getText() && b2.getText() == b3.getText()
                && !b1.isClickable())
            winner = true;
        else if (b4.getText() == b5.getText() && b6.getText() == b5.getText()
                && !b4.isClickable())
            winner = true;
        else if (b7.getText() == b8.getText() && b8.getText() == b9.getText()
                && !b7.isClickable())
            winner = true;


        else if (b1.getText() == b4.getText() && b1.getText() == b7.getText()
                && !b1.isClickable())
            winner = true;
        else if (b2.getText() == b5.getText() && b2.getText() == b8.getText()
                && !b2.isClickable())
            winner = true;
        else if (b3.getText() == b6.getText() && b3.getText() == b9.getText()
                && !b3.isClickable())
            winner = true;


        else if (b1.getText() == b5.getText() && b1.getText() == b9.getText()
                && !b1.isClickable())
            winner = true;
        else if (b3.getText() == b5.getText() && b3.getText() == b7.getText()
                && !b3.isClickable())
            winner = true;


        if (winner) {
            if (!turn) {
                showMessage("X wins");
                xWins++;
                for (Button b : bArray) {
                    b.setClickable(false);}

            } else {
                showMessage("O wins");
                oWins++;
                for (Button b : bArray) {
                    b.setClickable(false);}
            }
        } else if (moves == 9)
            showMessage("Draw!");

    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
                .show();
    }

    private void enableOrDisable(boolean enable) {
        for (Button b : bArray) {
            b.setText("");
            b.setClickable(enable);
            if (enable) {
                b.setBackgroundColor(Color.LTGRAY);
            } else {
                b.setBackgroundColor(Color.RED);
            }

        }
        }
    }




