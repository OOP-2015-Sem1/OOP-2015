package com.example.robertvacareanu.tictactoe;

import android.view.View;
import android.widget.Button;

/**
 * Take care of the behaviour of every button
 */
public class ButtonClickListener implements View.OnClickListener {

    private Button source;
    private int nrOfButton;
    private static TicTacToeAI TTT = TicTacToeManager.getInstance().getTTT();
    private static int counter = 1;

    public ButtonClickListener(Button source, int nrOfButton) {
        this.source = source;
        this.nrOfButton = nrOfButton;
    }

    public static void setMoveCounter(int counter) {
        ButtonClickListener.counter = counter;
    }

    @Override
    public void onClick(View v) {

        int gameOver = TTT.isGameOver();
        if (!TicTacToeManager.getInstance().getTypeOfMatch()) {
            if (TTT.move(nrOfButton, 1) && gameOver == 0) {
                source.setText("X");
                TicTacToeManager.getInstance().computerMove(-1, 0);
                if ((gameOver = TTT.isGameOver()) != 0) {
                    TicTacToeManager.getInstance().setCounters(gameOver);
                }
            }
        } else {
            int player = counter % 2 == 1 ? 1 : -1;
            String signature = player == 1 ? "X" : "O";
            if ((TTT.move(nrOfButton, player)) && gameOver == 0) {
                counter++;
                source.setText(signature);
                if ((gameOver = TTT.isGameOver()) != 0) {
                    TicTacToeManager.getInstance().setCounters(gameOver);
                }
            }
        }
    }
}
