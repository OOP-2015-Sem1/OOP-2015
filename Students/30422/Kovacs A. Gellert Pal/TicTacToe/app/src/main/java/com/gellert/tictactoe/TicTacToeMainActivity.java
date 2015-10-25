package com.gellert.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private int scoreX;
    private int scoreO;

    private TextView scoreText;

    private int counter;

    private TicTacToeButton[][] buttonMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadScore();
        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(scoreX + " - " + scoreO);

        counter = 0;

        buttonMatrix = new TicTacToeButton[3][3];
        buttonMatrix[0][0] = (TicTacToeButton) findViewById(R.id.btn00);
        buttonMatrix[0][1] = (TicTacToeButton) findViewById(R.id.btn01);
        buttonMatrix[0][2] = (TicTacToeButton) findViewById(R.id.btn02);
        buttonMatrix[1][0] = (TicTacToeButton) findViewById(R.id.btn10);
        buttonMatrix[1][0] = (TicTacToeButton) findViewById(R.id.btn11);
        buttonMatrix[1][0] = (TicTacToeButton) findViewById(R.id.btn12);
        buttonMatrix[2][0] = (TicTacToeButton) findViewById(R.id.btn20);
        buttonMatrix[2][1] = (TicTacToeButton) findViewById(R.id.btn21);
        buttonMatrix[2][2] = (TicTacToeButton) findViewById(R.id.btn22);

        for (int i =0; i< 3; i++) {
            for (int j = 0; j<3;j++) {
                buttonMatrix[i][j].resetContent();
                buttonMatrix[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View btn) {
                        if (counter % 2 == 0) {
                            ((TicTacToeButton)btn).updateContent(CHAR_X);
                        } else {
                            ((TicTacToeButton)btn).updateContent(CHAR_O);
                        }
                        counter++;
                        switch (GameUtilities.checkWin(buttonMatrix)) {
                            case X_WINS:
                                Toast.makeText(getApplicationContext(), "X wins!", Toast.LENGTH_SHORT).show();
                                scoreX++;
                                scoreText.setText(scoreX + " - " + scoreO);
                                resetMatch();
                                break;
                            case O_WINS:
                                Toast.makeText(getApplicationContext(), "O wins!", Toast.LENGTH_SHORT).show();
                                scoreO++;
                                scoreText.setText(scoreX + " - " + scoreO);
                                resetMatch();
                                break;
                            case NO_WINNER:
                                if (counter >= 9) {
                                    Toast.makeText(getApplicationContext(), "Draw match!", Toast.LENGTH_SHORT).show();
                                    resetMatch();
                                }
                                break;
                        }

                        saveScore();
                    }
                });
            }
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int[][] boardMatrix = GameUtilities.getValueMatrix(buttonMatrix);
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
        int[][] boardMatrix = new int[3][3];

        boardMatrix[0] = savedInstanceState.getIntArray("board_row0");
        boardMatrix[1] = savedInstanceState.getIntArray("board_row1");
        boardMatrix[2] = savedInstanceState.getIntArray("board_row2");
        scoreX = savedInstanceState.getInt("scoreX");
        scoreO = savedInstanceState.getInt("scoreO");
        counter = savedInstanceState.getInt("counter");

        for (int i =0; i< 3; i++) {
            for (int j = 0; j<3;j++) {
                buttonMatrix[i][j].updateContent(boardMatrix[i][j]);
            }
        }

        scoreText.setText(scoreX + " - " + scoreO);

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void resetMatch() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonMatrix[i][j].resetContent();
            }
        }

        counter = 0;
    }

    private void resetScore() {
        scoreO = 0;
        scoreX = 0;
        scoreText.setText("0 - 0");
        saveScore();
    }

    public void saveScore() {
        SharedPreferences sP = this.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("scoreX",scoreX);
        editor.putInt("scoreO",scoreO);
        editor.apply();
    }

    public void loadScore() {
        SharedPreferences sP = this.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        scoreX = sP.getInt("scoreX", 0);
        scoreO = sP.getInt("scoreO", 0);
    }
}