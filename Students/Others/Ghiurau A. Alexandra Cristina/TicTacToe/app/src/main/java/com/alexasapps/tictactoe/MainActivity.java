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

import java.util.Random;
//TO DO: Disable buttons after a game is finished

public class MainActivity extends AppCompatActivity {

    private boolean noughtsTurn = false; // false=X, true=O
    private char board[][] = new char[3][3];

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
            return false;
        } else {
            Toast.makeText(getApplicationContext(), winner + " wins", Toast.LENGTH_SHORT).show();
            //resetButtons();
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

    private void disableButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < table.getChildCount(); y++) {
            if (table.getChildAt(y) instanceof TableRow) {
                TableRow row = (TableRow) table.getChildAt(y);
                for (int x = 0; x < row.getChildCount(); x++) {
                    if (row.getChildAt(x) instanceof Button) {
                        Button B = (Button) row.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
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
                board[y][x] = isNoughtsTurn() ? 'O' : 'X';
                B.setText(isNoughtsTurn() ? "O" : "X");
                B.setEnabled(false);
                noughtsTurn = !noughtsTurn;

                if (checkWin()) {
                    disableButtons();
                }
            }
        }
    }


    //AI
/*
    private void setUpOnClickListenerAi() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < table.getChildCount(); y++) {
            if (table.getChildAt(y) instanceof TableLayout) {
                TableRow row = (TableRow) table.getChildAt(y);
                for (int x = 0; x < row.getChildCount(); x++) {
                    View V = row.getChildAt(x); // <=> each button
                    V.setOnClickListener(new AiPlaysOnClick(x, y));
                }
            }
        }
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

                if ((!noughtsTurn)) {
                    board[y][x] = 'X';
                    B.setEnabled(false);
                } else { //else get randomly unocuppied position
                    Random r = new Random();

                    TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
                    TableRow row = (TableRow) table.getChildAt(y);

                    int x = r.nextInt(row.getChildCount());
                    int y = r.nextInt(table.getChildCount());
                    board[y][x] = 'O';
                    B.setEnabled(false);
                }
                noughtsTurn = !noughtsTurn;
            }

            if (checkWin()) {
                //  Toast.makeText(MainActivity.this, winner  "wins", Toast.LENGTH_SHORT).show();
                disableButtons();
            }
        }
    }
*/
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
            setupOnClickListeners();
            return true;
        } else if (id == R.id.action_player_vs_player) {
            //setUpOnClickListenerAi();
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
}
