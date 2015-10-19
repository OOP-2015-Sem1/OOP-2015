package com.gellert.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gellért on 2015. 10. 18..
 */
public class GameUtilities {

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private static final int X_WINS = 1;
    private static final int O_WINS = 2;
    private static final int NO_WINNER = 0;

    public static int checkWin() {

        //check rows
        for (int i = 0; i<3; i++) {
            int rowSum = 0;
            for (int j = 0; j<3; j++) {
                rowSum += TicTacToeMainActivity.boardMatrix[i][j];
            }
            switch (rowSum) {
                case 3*CHAR_X: return X_WINS;
                case 3*CHAR_O: return O_WINS;
            }
        }

        //check columns
        for (int i = 0; i<3; i++) {
            int colSum = 0;
            for (int j = 0; j<3; j++) {
                colSum += TicTacToeMainActivity.boardMatrix[j][i];
            }
            switch (colSum) {
                case 3*CHAR_X: return X_WINS;
                case 3*CHAR_O: return O_WINS;
            }
        }

        //check main diagonal
        int mainDiagSum = 0;
        for (int i = 0; i<3; i++) {
            mainDiagSum += TicTacToeMainActivity.boardMatrix[i][i];
        }
        switch (mainDiagSum) {
            case 3*CHAR_X: return X_WINS;
            case 3*CHAR_O: return O_WINS;
        }

        //check secondary diagonal
        int secondaryDiagSum = 0;
        for (int i = 0; i<3; i++) {
            secondaryDiagSum += TicTacToeMainActivity.boardMatrix[i][2 - i];
        }
        switch (secondaryDiagSum) {
            case 3*CHAR_X: return X_WINS;
            case 3*CHAR_O: return O_WINS;
        }

        //no one is in winning position at the moment
        return NO_WINNER;
    }

    public static void setButtonText(Button btn, int i, int j) {
        switch (TicTacToeMainActivity.boardMatrix[i][j]) {
            case CHAR_O:
                btn.setText("O");
                btn.setClickable(false);
                break;
            case CHAR_X:
                btn.setText("X");
                btn.setClickable(false);
                break;
        }
    }

    public static void saveScore(Context context) {
        SharedPreferences sP = context.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("scoreX", TicTacToeMainActivity.scoreX);
        editor.putInt("scoreO", TicTacToeMainActivity.scoreO);
        editor.apply();
    }

    public static void loadScore(Context context) {
        SharedPreferences sP = context.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);
        TicTacToeMainActivity.scoreX = sP.getInt("scoreX", 0);
        TicTacToeMainActivity.scoreO = sP.getInt("scoreO", 0);
    }
}
