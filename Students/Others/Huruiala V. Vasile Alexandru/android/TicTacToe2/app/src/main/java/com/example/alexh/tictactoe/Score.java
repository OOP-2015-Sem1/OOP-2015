package com.example.alexh.tictactoe;

/**
 * Created by alexh on 21.10.2015.
 */
public class Score {

    private int xScore;
    private int oScore;
    private int ties;

    public Score() {
        this.xScore = 0;
        this.oScore = 0;
        this.ties = 0;
    }

    public Score(int oScore, int xScore, int ties) {
        this.oScore = oScore;
        this.xScore = xScore;
        this.ties = ties;
    }

    public void reset() {
        this.xScore = 0;
        this.oScore = 0;
        this.ties = 0;
    }

    public void set(int x, int o, int t) {
        this.xScore = x;
        this.oScore = o;
        this.ties = t;
    }

    public void xWon() {
        this.xScore++;
    }

    public void oWon() {
        this.oScore++;
    }

    public void tie() {
        this.ties++;
    }

    @Override
    public String toString() {
        return xScore + " - " + ties + " - " + oScore;
    }
}
