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

    public static int checkWin(TicTacToeButton[][] buttonMatrix) {

        //check rows
        for (int i = 0; i<3; i++) {
            int rowSum = 0;
            for (int j = 0; j<3; j++) {
                rowSum += buttonMatrix[i][j].getValue();
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
                colSum += buttonMatrix[j][i].getValue();
            }
            switch (colSum) {
                case 3*CHAR_X: return X_WINS;
                case 3*CHAR_O: return O_WINS;
            }
        }

        //check main diagonal
        int mainDiagSum = 0;
        for (int i = 0; i<3; i++) {
            mainDiagSum += buttonMatrix[i][i].getValue();
        }
        switch (mainDiagSum) {
            case 3*CHAR_X: return X_WINS;
            case 3*CHAR_O: return O_WINS;
        }

        //check secondary diagonal
        int secondaryDiagSum = 0;
        for (int i = 0; i<3; i++) {
            secondaryDiagSum += buttonMatrix[i][2 - i].getValue();
        }
        switch (secondaryDiagSum) {
            case 3*CHAR_X: return X_WINS;
            case 3*CHAR_O: return O_WINS;
        }

        //no one is in winning position at the moment
        return NO_WINNER;
    }

    public static int[][] getValueMatrix(TicTacToeButton[][] buttonMatrix) {
        int[][] boardMatrix = new int[3][3];
        for (int i =0; i< 3; i++) {
            for (int j = 0; j<3;j++) {
                boardMatrix[i][j] = buttonMatrix[i][j].getValue();
            }
        }
        return boardMatrix;
    }
}
