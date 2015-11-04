package com.alexasapps.tictactoe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean noughtsTurn = false; // false=X, true=O
    private char board[][] = new char[3][3];
    private boolean aisTurn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setupOnClickListeners();
        //resetButtons();
    }

    public void newGame(View view) {

        setNoughtsTurn(false);
        setBoard(new char[3][3]);
        setAisTurn(false);
        resetButtons();
    }

    private void resetButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < table.getChildCount(); y++) {
            if (table.getChildAt(y) instanceof TableRow) {
                TableRow row = (TableRow) table.getChildAt(y);
                for (int x = 0; x < row.getChildCount(); x++) {
                    if (row.getChildAt(x) instanceof Button) {
                        Button B = (Button) row.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                        B.setOnClickListener(new PlayOnClick(x, y));
                    }
                }
            }
        }
        TextView t = (TextView) findViewById(R.id.titleText);
        t.setText(R.string.title);
    }

    private boolean checkWin() {

        char winner = '\0';
        if (checkWinner(board, 3, 'X')) {
            winner = 'X';
        } else if (checkWinner(board, 3, 'O')) {
            winner = 'O';
        }

        if (winner == '\0') {
            return false; // nobody won
        } else {
            // display winner
            Toast toastXor0 = Toast.makeText(getApplicationContext(), winner + " wins", Toast.LENGTH_SHORT);
            toastXor0.show();
            //resetButtons();
            //TextView T = (TextView) findViewById(R.id.titleText);
            //  T.setText(winner + "  wins");
            return true;
        }
    }

    private boolean checkWinner(char[][] board, int size, char player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diag
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diag
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        return false; // everybody lost
    }

    private void setupOnClickListeners() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < table.getChildCount(); y++) {
            if (table.getChildAt(y) instanceof TableLayout) {
                TableRow row = (TableRow) table.getChildAt(y);
                for (int x = 0; x < row.getChildCount(); x++) {
                    View V = row.getChildAt(x); // <=> each button
                    V.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }

    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button B = (Button) view;
                board[y][x] = noughtsTurn ? 'O' : 'X';
                B.setText(noughtsTurn ? "O" : "X");
                B.setEnabled(false);
                noughtsTurn = !noughtsTurn;

                if (checkWin()) {
                    //  Toast.makeText(MainActivity.this, winner  "wins", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //AI
/*
    private void setUpOnClickListenerAi() {

    }

    private class AiPlaysOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public AiPlaysOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button B = (Button) view;

                board[y][x] = noughtsTurn ? 'O' : 'X';
                B.setText(noughtsTurn ? "O" : "X");
                B.setEnabled(false);
                noughtsTurn = !noughtsTurn;

                if (checkWin()) {
                    //  Toast.makeText(MainActivity.this, winner  "wins", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    public static String aiPlays(){
       return "0";
    }*/

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
        if (id == R.id.action_quit) {
            finish();
            return true;
        } else if (id == R.id.action_you_vs_computer) {
            return true;
        } else if (id == R.id.action_player_vs_player) {
            setupOnClickListeners();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //getters and setters
    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public boolean isNoughtsTurn() {
        return noughtsTurn;
    }

    public void setNoughtsTurn(boolean noughtsTurn) {
        this.noughtsTurn = noughtsTurn;
    }

    public boolean isAisTurn() {
        return aisTurn;

    }

    public void setAisTurn(boolean aisTurn) {
        this.aisTurn = aisTurn;

    }
}
