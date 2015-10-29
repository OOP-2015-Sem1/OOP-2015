package com.example.maria.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class TicTacToeButton extends Button {

    private enum Turn {X, O}

    private Turn turn;

    public TicTacToeButton(Context context) {
        super(context);
    }

    public TicTacToeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicTacToeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buttonClick(final int[] theBoard, final TicTacToeButton[] theButtons, final Activity activity) {
        turn = Turn.X;
        for (int i = 0; i < 9; i++) {
            final int pos = i;
            theButtons[i].setOnClickListener(new View.OnClickListener() {
                                                 public void onClick(View view) {
                                                     onButtonClick(theBoard, view, activity, pos, theButtons);


                                                 }
                                             }

            );
        }
    }

    public void onButtonClick(int[] theBoard, View v, Activity activity, int pos, TicTacToeButton[] theButtons) {

        if (!GameOver.checkIfGameOver(theBoard, v.getContext(), activity)) {
            changeTheTurn(pos, theBoard, theButtons, activity);
        }
    }

    private void changeTheTurn(int pos, int[] theBoard, TicTacToeButton[] theButtons, Activity activity) {
        if (turn == Turn.X) {
            theButtons[pos].setText("X");
            theBoard[pos] = 1;
            turn = Turn.O;
        } else {
            theButtons[pos].setText("O");
            theBoard[pos] = 0;
            turn = Turn.X;
        }
        theButtons[pos].setEnabled(false);

        //if the game is over and there are still empty buttons, then they will be disabled
        if (GameOver.checkIfGameOver(theBoard, getContext().getApplicationContext(), activity)) {
            for (int i = 0; i < 9; i++) {
                theButtons[i].setEnabled(false);
            }
        }
    }
}
