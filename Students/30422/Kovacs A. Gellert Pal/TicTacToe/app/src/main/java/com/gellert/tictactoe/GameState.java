package com.gellert.tictactoe;

import android.widget.TextView;

/**
 * Created by Gellért on 2015. 10. 25..
 */
public class GameState {

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private int state;
    private int scoreX;
    private int scoreO;

    private static GameState instance;

    private GameState() {
        this.state = 0;
        this.scoreX = 0;
        this.scoreO = 0;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void nextState() {
        this.state++;
    }

    public int currentCharacter() {
        if (this.state % 2 == 0)
            return CHAR_X;
           else
            return CHAR_O;
    }

    public void resetState() {
        this.state = 0;
    }

    public int getScore(int chr) {
        if (chr == CHAR_X)
            return scoreX;
        else if ( chr == CHAR_O)
            return scoreO;
        return -1;
    }

    public void setScore(int chr, int value) {
        if (chr == CHAR_X)
            this.scoreX = value;
        else if (chr == CHAR_O)
            this.scoreO = value;
    }

    public void incScore(int chr) {
        if (chr == CHAR_X)
            this.scoreX++;
        else if (chr == CHAR_O)
            this.scoreO++;
    }

    public void displayScore(TextView scoreText) {
        scoreText.setText(this.scoreX + " - " + this.scoreO);
    }

    public void resetScore() {
        this.scoreX = 0;
        this.scoreO = 0;
    }
}
