package com.gellert.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeMainActivity extends AppCompatActivity {

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private static final int X_WINS = 1;
    private static final int O_WINS = 2;
    private static final int NO_WINNER = 0;

    public static int[][] boardMatrix;
    public static int scoreX;
    public static int scoreO;

    private TextView scoreText;

    private int counter;

    Button btn00;
    Button btn01;
    Button btn02;
    Button btn10;
    Button btn11;
    Button btn12;
    Button btn20;
    Button btn21;
    Button btn22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameUtilities.loadScore(this);
        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(scoreX + " - " + scoreO);

        counter = 0;
        boardMatrix = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        btn00 = (Button) findViewById(R.id.btn00);
        btn01 = (Button) findViewById(R.id.btn01);
        btn02 = (Button) findViewById(R.id.btn02);
        btn10 = (Button) findViewById(R.id.btn10);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn20 = (Button) findViewById(R.id.btn20);
        btn21 = (Button) findViewById(R.id.btn21);
        btn22 = (Button) findViewById(R.id.btn22);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.resetMatch).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resetMatch();
                return false;
            }
        });

        menu.findItem(R.id.resetScore).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resetScore();
                return false;
            }
        });

        menu.findItem(R.id.exitApp).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    //generic button click method
    private void btnClick(View btn, int row, int col) {
        if (counter % 2 == 0) {
            ((Button) btn).setText("X");
            boardMatrix[row][col] = CHAR_X;
        } else {
            ((Button) btn).setText("O");
            boardMatrix[row][col] = CHAR_O;
        }
        ((Button) btn).setClickable(false);
        counter++;
        switch (GameUtilities.checkWin()) {
            case X_WINS:
                Toast.makeText(this, "X wins!", Toast.LENGTH_SHORT).show();
                scoreX++;
                scoreText.setText(scoreX + " - " + scoreO);
                resetMatch();
                break;
            case O_WINS:
                Toast.makeText(this, "O wins!", Toast.LENGTH_SHORT).show();
                scoreO++;
                scoreText.setText(scoreX + " - " + scoreO);
                resetMatch();
                break;
            case NO_WINNER:
                if (counter >= 9) {
                    Toast.makeText(this, "Draw match!", Toast.LENGTH_SHORT).show();
                    resetMatch();
                }
                break;
        }

        GameUtilities.saveScore(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("board_row0", boardMatrix[0]);
        outState.putIntArray("board_row1", boardMatrix[1]);
        outState.putIntArray("board_row2", boardMatrix[2]);
        outState.putInt("scoreX", scoreX);
        outState.putInt("scoreO", scoreO);
        outState.putInt("counter", counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        boardMatrix[0] = savedInstanceState.getIntArray("board_row0");
        boardMatrix[1] = savedInstanceState.getIntArray("board_row1");
        boardMatrix[2] = savedInstanceState.getIntArray("board_row2");
        scoreX = savedInstanceState.getInt("scoreX");
        scoreO = savedInstanceState.getInt("scoreO");
        counter = savedInstanceState.getInt("counter");

        GameUtilities.setButtonText(btn00, 0, 0);
        GameUtilities.setButtonText(btn01, 0, 1);
        GameUtilities.setButtonText(btn02, 0, 2);
        GameUtilities.setButtonText(btn10, 1, 0);
        GameUtilities.setButtonText(btn11, 1, 1);
        GameUtilities.setButtonText(btn12, 1, 2);
        GameUtilities.setButtonText(btn20, 2, 0);
        GameUtilities.setButtonText(btn21, 2, 1);
        GameUtilities.setButtonText(btn22, 2, 2);

        scoreText.setText(scoreX + " - " + scoreO);

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void resetMatch() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardMatrix[i][j] = 0;
            }
        }

        counter = 0;

        btn00.setClickable(true);
        btn00.setText("");
        btn01.setClickable(true);
        btn01.setText("");
        btn02.setClickable(true);
        btn02.setText("");
        btn10.setClickable(true);
        btn10.setText("");
        btn11.setClickable(true);
        btn11.setText("");
        btn12.setClickable(true);
        btn12.setText("");
        btn20.setClickable(true);
        btn20.setText("");
        btn21.setClickable(true);
        btn21.setText("");
        btn22.setClickable(true);
        btn22.setText("");
    }

    private void resetScore() {
        scoreO = 0;
        scoreX = 0;
        scoreText.setText("0 - 0");
        GameUtilities.saveScore(this);
    }

    public void btn00Click(View btn) {
        btnClick(btn, 0, 0);
    }

    public void btn01Click(View btn) {
        btnClick(btn, 0, 1);
    }

    public void btn02Click(View btn) {
        btnClick(btn, 0, 2);
    }

    public void btn10Click(View btn) {
        btnClick(btn, 1, 0);
    }

    public void btn11Click(View btn) {
        btnClick(btn, 1, 1);
    }

    public void btn12Click(View btn) {
        btnClick(btn, 1, 2);
    }

    public void btn20Click(View btn) {
        btnClick(btn, 2, 0);
    }

    public void btn21Click(View btn) {
        btnClick(btn, 2, 1);
    }

    public void btn22Click(View btn) {
        btnClick(btn, 2, 2);
    }
}