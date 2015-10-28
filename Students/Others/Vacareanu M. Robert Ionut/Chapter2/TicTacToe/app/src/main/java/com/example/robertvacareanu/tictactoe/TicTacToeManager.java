package com.example.robertvacareanu.tictactoe;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Singleton manages things like score, moves and all the logic behind a TicTacToe game
 */
public class TicTacToeManager {

    private static TicTacToeManager instance = new TicTacToeManager();

    public static TicTacToeManager getInstance(){
        return instance;
    }

    private TicTacToeManager(){
    }


    private int nrOfXWins = 0;
    private int nrOfOWins = 0;
    private int nrOfDraws = 0;
    private TicTacToeAI TTT = new TicTacToeAI();
    private Activity mainActivity ;
    private boolean typeOfMatch;

    public void setTypeOfMatch(boolean typeOfMatch){
        this.typeOfMatch = typeOfMatch;
    }

    // vs Computer or vs Player
    public boolean getTypeOfMatch(){
        return this.typeOfMatch;
    }

    public void setActivity(Activity activity){
        this.mainActivity = activity;
    }

    public int getNrOfXWins() {
        return nrOfXWins;
    }

    public void setNrOfXWins(int nrOfXWins) {
        this.nrOfXWins = nrOfXWins;
    }

    public int getNrOfOWins() {
        return nrOfOWins;
    }

    public void setNrOfOWins(int nrOfOWins) {
        this.nrOfOWins = nrOfOWins;
    }

    public int getNrOfDraws() {
        return nrOfDraws;
    }

    public void setNrOfDraws(int nrOfDraws) {
        this.nrOfDraws = nrOfDraws;
    }

    public TicTacToeAI getTTT() {
        return TTT;
    }

    public void computerMove(int player, int move) {
        //setButtons();
        if (move == 0) {
            move = TTT.isGameOver() == 0 ? TTT.generateMove(player) : 0;
        }
        String playerText = (player == 1) ? "X" : "O";
        switch (move) {
            case 1:
                buttons[0].setText(playerText);
                TTT.move(move, player);
                break;
            case 2:
                buttons[1].setText(playerText);
                TTT.move(move, player);
                break;
            case 3:
                buttons[2].setText(playerText);
                TTT.move(move, player);
                break;
            case 4:
                buttons[3].setText(playerText);
                TTT.move(move, player);
                break;
            case 5:
                buttons[4].setText(playerText);
                TTT.move(move, player);
                break;
            case 6:
                buttons[5].setText(playerText);
                TTT.move(move, player);
                break;
            case 7:
                buttons[6].setText(playerText);
                TTT.move(move, player);
                break;
            case 8:
                buttons[7].setText(playerText);
                TTT.move(move, player);
                break;
            case 9:
                buttons[8].setText(playerText);
                TTT.move(move, player);
                break;
        }
    }

    Button[] buttons = new Button[9];

    private void getButtons() {
        buttons[0] = (Button) this.mainActivity.findViewById(R.id.button_1);
        buttons[1] = (Button) this.mainActivity.findViewById(R.id.button_2);
        buttons[2] = (Button) this.mainActivity.findViewById(R.id.button_3);
        buttons[3] = (Button) this.mainActivity.findViewById(R.id.button_4);
        buttons[4] = (Button) this.mainActivity.findViewById(R.id.button_5);
        buttons[5] = (Button) this.mainActivity.findViewById(R.id.button_6);
        buttons[6] = (Button) this.mainActivity.findViewById(R.id.button_7);
        buttons[7] = (Button) this.mainActivity.findViewById(R.id.button_8);
        buttons[8] = (Button) this.mainActivity.findViewById(R.id.button_9);
    }

    public void setButtons() {

        getButtons();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(new ButtonClickListener(buttons[i], i + 1));
        }
    }

    public void resetScore() {
        setCounters(0);
    }

    public void setCounters(int number) {
        switch (number) {
            case 1:
                nrOfXWins++;
                break;
            case -1:
                nrOfOWins++;
                break;
            case 2:
                nrOfDraws++;
                break;
            case 0:
                nrOfXWins = 0;
                nrOfOWins = 0;
                nrOfDraws = 0;
            default:
                break;
        }

        final TextView textViewXWins, textViewOWins, textViewDraws;

        textViewDraws = (TextView) this.mainActivity.findViewById(R.id.textview_draws);
        textViewXWins = (TextView) this.mainActivity.findViewById(R.id.textview_x_wins);
        textViewOWins = (TextView) this.mainActivity.findViewById(R.id.textview_o_wins);

        textViewDraws.setText(this.mainActivity.getString(R.string.draws, nrOfDraws));
        textViewXWins.setText(this.mainActivity.getString(R.string.score_x_wins, nrOfXWins));
        textViewOWins.setText(this.mainActivity.getString(R.string.score_o_wins, nrOfOWins));

    }

    public void newGame() {
        setButtons();
        for (Button b : buttons) {
            b.setText(this.mainActivity.getString(R.string.empty_text));
        }
        TTT.newGame();
        ButtonClickListener.setMoveCounter(1);
    }

}
