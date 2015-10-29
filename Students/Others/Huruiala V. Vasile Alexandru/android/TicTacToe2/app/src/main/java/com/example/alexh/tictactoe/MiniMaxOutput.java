package com.example.alexh.tictactoe;

/**
 * Convenient little bugger
 */
public class MiniMaxOutput {
    int score;
    Move move;

    public MiniMaxOutput(int score, Move move) {
        this.score = score;
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public Move getMove() {
        return move;
    }
}
