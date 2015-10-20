package com.example.maria.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button[] theButtons; //the nine buttons
    int[] theBoard;      //the game board

    Player1 player1 = new Player1();       //player 1: X
    Player2 player2 = new Player2();       //player 2: O

    TextView score1, score2;

    private static final int X = 1;
    private static final int O = 0;

    private enum Turn {X, O}

    private Turn turn;

    int p1Score = 0;
    int p2Score = 0;

    //set score to 0-0
    private void resetScore() {
        score1 = (TextView) findViewById(R.id.player1Score);
        p1Score = 0;
        score1.setText(String.valueOf(p1Score));

        score2 = (TextView) findViewById(R.id.player2Score);
        p2Score = 0;
        score2.setText(String.valueOf(p2Score));
    }

    private void initializeBoard() {
        theBoard = new int[9];
        theButtons = new Button[9];
        turn = Turn.X;   // X always starts first

        theButtons[0] = (Button) findViewById(R.id.btn11);
        theButtons[1] = (Button) findViewById(R.id.btn12);
        theButtons[2] = (Button) findViewById(R.id.btn13);

        theButtons[3] = (Button) findViewById(R.id.btn21);
        theButtons[4] = (Button) findViewById(R.id.btn22);
        theButtons[5] = (Button) findViewById(R.id.btn23);

        theButtons[6] = (Button) findViewById(R.id.btn31);
        theButtons[7] = (Button) findViewById(R.id.btn32);
        theButtons[8] = (Button) findViewById(R.id.btn33);

        //empty the game board
        for (int i = 0; i < 9; i++) {
            theBoard[i] = 2; //2 means empty because 0 -> 'O', 1 -> 'X'
            theButtons[i].setText(" ");
            theButtons[i].setEnabled(true);
        }

        //click listeners for the nine buttons
        for (int i = 0; i < 9; i++) {
            final int pos = i;
            theButtons[i].setOnClickListener(new View.OnClickListener() {
                                                 public void onClick(View view) {
                                                     if (!checkIfGameOver()) {
                                                         changeTheTurn(pos);
                                                     }
                                                 }
                                             }

            );
        }

    }

    private boolean checkIfGameOver() {

        boolean isOver = false;

        SharedPreferences sharedPref = getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if ((theBoard[0] == 0 && theBoard[1] == 0 && theBoard[2] == 0) ||       // -
                (theBoard[3] == 0 && theBoard[4] == 0 && theBoard[5] == 0) ||   // -
                (theBoard[6] == 0 && theBoard[7] == 0 && theBoard[8] == 0) ||   // -
                (theBoard[0] == 0 && theBoard[3] == 0 && theBoard[6] == 0) ||   // |
                (theBoard[1] == 0 && theBoard[4] == 0 && theBoard[7] == 0) ||   // |
                (theBoard[2] == 0 && theBoard[5] == 0 && theBoard[8] == 0) ||   // |
                (theBoard[0] == 0 && theBoard[4] == 0 && theBoard[8] == 0) ||   // \
                (theBoard[2] == 0 && theBoard[4] == 0 && theBoard[6] == 0)) {   // /

            Toast.makeText(getApplicationContext(), "O wins!", Toast.LENGTH_SHORT).show();

            TextView score2 = (TextView) findViewById(R.id.player2Score);
            p2Score++;

            //save player 2 score in shared pref
            editor.putString("scoreP2", String.valueOf(p2Score));
            editor.apply();

            //fetch player 2 score from shared pref
            String s2 = sharedPref.getString("scoreP2", "");
            score2.setText(s2);

            isOver = true;

        } else if ((theBoard[0] == 1 && theBoard[1] == 1 && theBoard[2] == 1) ||    // -
                (theBoard[3] == 1 && theBoard[4] == 1 && theBoard[5] == 1) ||       // -
                (theBoard[6] == 1 && theBoard[7] == 1 && theBoard[8] == 1) ||       // -
                (theBoard[0] == 1 && theBoard[3] == 1 && theBoard[6] == 1) ||       // |
                (theBoard[1] == 1 && theBoard[4] == 1 && theBoard[7] == 1) ||       // |
                (theBoard[2] == 1 && theBoard[5] == 1 && theBoard[8] == 1) ||       // |
                (theBoard[0] == 1 && theBoard[4] == 1 && theBoard[8] == 1) ||       // \
                (theBoard[2] == 1 && theBoard[4] == 1 && theBoard[6] == 1)) {       // /

            Toast.makeText(getApplicationContext(), "X wins!", Toast.LENGTH_SHORT).show();

            TextView score1 = (TextView) findViewById(R.id.player1Score);
            p1Score++;

            //save player 1 score in shared pref
            editor.putString("scoreP1", String.valueOf(p1Score));
            editor.apply();

            //fetch player 1 score from shared pref
            String s1 = sharedPref.getString("scoreP1", "");
            score1.setText(s1);

            isOver = true;

        } else { //draw

            boolean emptyButton = false;
            for (int i = 0; i < 9; i++) {

                if (theBoard[i] == 2) {
                    emptyButton = true;
                }
            }

            if (!emptyButton) {
                Toast.makeText(getApplication(), "It's a draw!", Toast.LENGTH_SHORT).show();
                isOver = true;
            }
        }
        return isOver;
    }

    private void changeTheTurn(int pos) {
        if (turn == Turn.X) {
            player1.takeTurn(pos);
        } else {
            player2.takeTurn(pos);
        }
        theButtons[pos].setEnabled(false);

        //if the game is over and there are still empty buttons, then they will be disabled
        if (checkIfGameOver()) {
            for (int i = 0; i < 9; i++) {
                theButtons[i].setEnabled(false);
            }
        }
    }

    private class Player1 {

        public void takeTurn(int pos) {
            theButtons[pos].setText("X");
            theBoard[pos] = 1;
            turn = Turn.O;
        }
    }

    private class Player2 {

        public void takeTurn(int pos) {
            theButtons[pos].setText("O");
            theBoard[pos] = 0;
            turn = Turn.X;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
        resetScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.exit_app) {
            finish();
            System.exit(0);

        }

        if (id == R.id.reset_match) {
            initializeBoard();

        }

        if (id == R.id.reset_score) {
            resetScore();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
