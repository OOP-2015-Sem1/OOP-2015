package com.example.roxana.tictactoe;

import android.util.Log;

/**
 * Created by Roxana on 10/28/2015.
 */
public class AI {

    int bestMove;

    ///a class that deals with the moves made by the computer

    //I used(or at least attempted to) the min-max algorithm

    /**** Method setting the bestMove field ****/
    public void setBestMove(int move){
        bestMove = move;
    }
    /**** Method getting the bestMove field ****/
    public int getBestMove(){
        return bestMove;
    }

    public int chooseBestMove(Game game) {

        int v = max_value(game);
        int bestMove = getBestMove();
        return bestMove;
    }

    public int max_value(Game game) {
        if (game.isGameOver()) {
            return game.utility();
        }
        int value = -11;

        int moves[] = game.getMoves();

        for (int move = 0; move < 9; move++) {
            //try and see if move available
            if (moves[move] == 0) {
                game.makeMove(move, 2);
                int minValue = min_value(game);
                if (value < minValue) {
                    value = minValue;//value is the maximum of minvalues
                    setBestMove(move);
                }
                game.deleteMove(move);
            }
        }
        return value;
    }

    public int min_value(Game game) {
        if (game.isGameOver()) {
            return game.utility();
        }
        int value = 11;
        int moves[] = game.getMoves();

        for (int move = 0; move < 9; move++) {
            //try move
            if (moves[move] == 0) {//move available

                game.makeMove(move, 1);//1 as X makes the move
                int maxValue = max_value(game);
                if (value > maxValue) {
                    value = maxValue;//value is the minimum of maxvalues
                }
                game.deleteMove(move);
            }
        }
        return value;
    }
}

