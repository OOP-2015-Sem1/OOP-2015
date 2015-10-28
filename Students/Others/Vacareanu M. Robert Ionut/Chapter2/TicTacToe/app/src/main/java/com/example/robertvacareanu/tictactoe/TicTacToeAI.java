package com.example.robertvacareanu.tictactoe;

/**
 * This class is an AI for Tic Tac Toe Game
 * Uses NegaMax to compute the best move
 */

public class TicTacToeAI {
    private int player_X, player_O;

    public TicTacToeAI() {
        player_X = 0;
        player_O = 0;
    }

    private int positionToBit(int position) {
        return position > 0 && position < 10 ? 1 << (position - 1) : 0;
    }

    private int bitToPositon(int bitPosition) {
        return Integer.toBinaryString(bitPosition).length();
    }

    // position > 0 set X, position < 0 set O
    private void moveUtil(int position) {
        player_X = player_X | position & -((position >> 31) + 1) & ~player_O;
        player_O = player_O | -position & (position >> 31) & ~player_X;
    }

    public void clear(int position) {
        player_O = player_O & ~position;
        player_X = player_X & ~position;
    }

    public boolean move(int position, int player) {
        int positionInBits = positionToBit(position);

        if (positionInBits != 0 && (player == 1 || player == -1) && ((player_X | player_O) & positionInBits) == 0) {
            moveUtil(player * positionInBits);
            return true;
        } else {
            return false;
        }
    }

    private boolean check(int player) {
        return (player & 7) == 7 || (player & 56) == 56 || (player & 448) == 448 || (player & 73) == 73 || (player & 146) == 146 || (player & 292) == 292 || (player & 273) == 273 || (player & 84) == 84;
    }

    private int gameOver() {
        return check(player_X) ? 2048 : check(player_O) ? 512 : ((player_O | player_X) & 511) == 511 ? 1024 : 0;
    }

    public void newGame() {
        player_X = 0;
        player_O = 0;
    }

    // 512 best score for O ( wins )
    // 2048 best score for X ( wins )
    // 1024 Draw
    private int negaMax(int player) {
        int endGame = gameOver();
        if (endGame != 0) return endGame;

        int best_value = (player == 1) ? 512 : 2048;

        for (int bit = 1; bit <= 256; bit *= 2) {

            int move = ~(player_X | player_O) & bit;

            if (move != 0) {
                moveUtil(player * move);

                int secondPlayer = negaMax(-player);
                // Get the score bits
                best_value = player * (secondPlayer & -511) > player * (best_value & -511) ? ((secondPlayer & -511) | move) : best_value;
                clear(move);
            }
        }
        return best_value;
    }

    public int generateMove(int Player) {
        return bitToPositon(negaMax(Player) & 511);
    }

    public int isGameOver() {
        return check(player_X) ? 1 : check(player_O) ? -1 : (((player_X | player_O) & 511) == 511) ? 2 : 0;
    }

}