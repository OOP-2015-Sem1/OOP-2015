package com.gellert.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class TicTacToeMainActivity extends AppCompatActivity {

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private TextView scoreText;

    private GameState gameStateInstance;

    private TicTacToeButton[][] buttonMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStateInstance = GameState.getInstance(); //state and scores are initialized with value 0
        loadScore();
        scoreText = (TextView) findViewById(R.id.scoreText);
        gameStateInstance.displayScore(scoreText);

        buttonMatrix = new TicTacToeButton[3][3];
        buttonMatrix[0][0] = (TicTacToeButton) findViewById(R.id.btn00);
        buttonMatrix[0][1] = (TicTacToeButton) findViewById(R.id.btn01);
        buttonMatrix[0][2] = (TicTacToeButton) findViewById(R.id.btn02);
        buttonMatrix[1][0] = (TicTacToeButton) findViewById(R.id.btn10);
        buttonMatrix[1][1] = (TicTacToeButton) findViewById(R.id.btn11);
        buttonMatrix[1][2] = (TicTacToeButton) findViewById(R.id.btn12);
        buttonMatrix[2][0] = (TicTacToeButton) findViewById(R.id.btn20);
        buttonMatrix[2][1] = (TicTacToeButton) findViewById(R.id.btn21);
        buttonMatrix[2][2] = (TicTacToeButton) findViewById(R.id.btn22);

        for (int i =0; i< 3; i++) {
            for (int j = 0; j<3;j++) {
                buttonMatrix[i][j].resetContent();
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
        outState.putInt("scoreX", gameStateInstance.getScore(CHAR_X));
        outState.putInt("scoreO", gameStateInstance.getScore(CHAR_O));
        outState.putInt("gameState", gameStateInstance.getState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int[][] boardMatrix = new int[3][3];

        boardMatrix[0] = savedInstanceState.getIntArray("board_row0");
        boardMatrix[1] = savedInstanceState.getIntArray("board_row1");
        boardMatrix[2] = savedInstanceState.getIntArray("board_row2");
        gameStateInstance.setScore(CHAR_X, savedInstanceState.getInt("scoreX"));
        gameStateInstance.setScore(CHAR_O, savedInstanceState.getInt("scoreO"));
        gameStateInstance.setState(savedInstanceState.getInt("gameState"));

        for (int i =0; i< 3; i++) {
            for (int j = 0; j<3;j++) {
                buttonMatrix[i][j].updateContent(boardMatrix[i][j]);
            }
        }
        gameStateInstance.displayScore(scoreText);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void resetMatch() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonMatrix[i][j].resetContent();
            }
        }
        gameStateInstance.resetState();
    }

    private void resetScore() {
        gameStateInstance.resetScore();
        gameStateInstance.displayScore(scoreText);
        saveScore();
    }

    public void saveScore() {
        SharedPreferences sP = this.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("scoreX",gameStateInstance.getScore(CHAR_X));
        editor.putInt("scoreO",gameStateInstance.getScore(CHAR_O));
        editor.apply();
    }

    public void loadScore() {
        SharedPreferences sP = this.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        gameStateInstance.setScore(CHAR_X, sP.getInt("scoreX", 0));
        gameStateInstance.setScore(CHAR_O, sP.getInt("scoreO", 0));
    }

    public TicTacToeButton[][] getButtonMatrix() {
        return buttonMatrix;
    }

    public TextView getScoreText() {
        return scoreText;
    }
}