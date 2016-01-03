package com.example.alexh.tictactoe;

import android.widget.TextView;
import android.widget.Toast;


/**
 * Holds the TextView for score
 * contains the record of who won
 * updates the label
 * and some extra logic for convenience
 */
public class Score {

    private GameBoard gameBoard;

    private TextView scoreTextView;

    private int xScore;
    private int oScore;
    private int ties;

    public Score(GameBoard gameBoard, TextView scoreTextView) {

        this.gameBoard = gameBoard;

        this.scoreTextView = scoreTextView;

        this.reset();
    }

    public void reset() {
        this.setXScore(0);
        this.setOScore(0);
        this.setTies(0);
    }

    public void set(int xWins, int oWins, int ties) {
        this.setXScore(xWins);
        this.setOScore(oWins);
        this.setTies(ties);
    }

    public int getXScore() {
        return xScore;
    }

    public void setXScore(int xScore) {
        this.xScore = xScore;
        this.updateLabel();
    }

    public int getOScore() {
        return oScore;
    }

    public void setOScore(int oScore) {
        this.oScore = oScore;
        this.updateLabel();
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
        this.updateLabel();
    }

    public void xWon() {
        this.setXScore(this.getXScore() + 1);
    }

    public void oWon() {
        this.setOScore(this.getOScore() + 1);
    }

    public void tie() {
        this.setTies(this.getTies() + 1);
    }

    public void sWon(GridButtonState symbol) {
        if (symbol.equals(GridButtonState.X_STATE)) {
            this.xWon();
            Toast.makeText(this.gameBoard.board.activity, "X won", Toast.LENGTH_SHORT).show();
        } else if (symbol.equals(GridButtonState.O_STATE)) {
            this.oWon();
            Toast.makeText(this.gameBoard.board.activity, "O won", Toast.LENGTH_SHORT).show();
        } else if (symbol.equals(GridButtonState.BLANK_STATE)) {
            this.tie();
            Toast.makeText(this.gameBoard.board.activity, "You are all losers", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateLabel() {
        this.scoreTextView.setText(this.toString());
    }

    @Override
    public String toString() {
        return xScore + " - " + ties + " - " + oScore;
    }
}
