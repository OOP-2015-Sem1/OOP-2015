package com.example.roxana.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Roxana on 10/28/2015.
 */
public class ButtonActionListener implements Button.OnClickListener {

    Context context;
    Game game;
    int mode;
    Button button;

    public ButtonActionListener(Context context, Game game, int mode) {

        //mode =0: human<->human
        //mode =1:computer<->human
        this.mode=mode;
        this.context = context;
        this.game = game;
    }

    public void onClick(View v) {
        button = (Button) v;
        if(mode==0){
            twoPlayersGame();
        }
        else{
            gameAgainstComputer();
        }
    }

    private void twoPlayersGame() {

        if (button.getText() == "") {
            //i should test if the button is already pressed or if the game is over :?

            String idAsString = button.getResources().getResourceEntryName(button.getId());
            int i = Integer.valueOf(idAsString.substring(6).trim());

            //find out the player
            int player = game.getTurn();

            String stringPlayer = "";
            if (player == 1) {
                stringPlayer = "X";
            } else if (player == 2) {
                stringPlayer = "O";
            }

            //makeMove
            game.makeMove(i, player);

            //set the button text
            button.setText(stringPlayer);

            //test if the game is over
            if (game.isGameOver()) {
                takeGameOverActions();
            }
        } else {
            Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
        }

    }

    private void gameAgainstComputer() {

        if (button.getText() == "") {
            //i should test if the button is already pressed

            String idAsString = button.getResources().getResourceEntryName(button.getId());
            int i = Integer.valueOf(idAsString.substring(6).trim());

            //the human always starts first => plays with X
            String stringPlayer = "X";

            //human makes move
            game.makeMove(i, 1);

            //set the button text
            button.setText(stringPlayer);

            //test if the game is over
            if (game.isGameOver()) {
                takeGameOverActions();
            } else {
                //now the computer can make its move
                AI computer = new AI();
                //chooses the move
                int move = computer.chooseBestMove(game);
                //makes it
                game.makeMove(move,2);
                Buttons buttons = new Buttons();
                buttons.setButtonText(move, "O");

                if (game.isGameOver()){
                    takeGameOverActions();
                }
            }
        } else {
            Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
        }
    }

    private void takeGameOverActions() {

        //emphasize the winning buttons
        int winningMoves[] = game.getWinningMoves();
        final Buttons buttons = new Buttons();
        buttons.emphasizeButtons(winningMoves[0], winningMoves[1], winningMoves[2]);

        //everything was happening too fast
        //i didn't know how else to deal with the problem
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                //get the winner
                int winner = game.evaluateTable();
                if (winner == 1) {
                    Toast.makeText(context, "X won", Toast.LENGTH_SHORT).show();
                } else if (winner == 2) {
                    Toast.makeText(context, "O won", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Tie", Toast.LENGTH_SHORT).show();
                }

                Score score = new Score(context);
                score.updateScore(winner);
                score.displayScore();

                //refresh game
                buttons.refreshButtons(game);
            }
        }.start();
    }
}
