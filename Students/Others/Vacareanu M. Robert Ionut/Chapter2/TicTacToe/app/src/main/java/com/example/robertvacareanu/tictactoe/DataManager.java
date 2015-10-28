package com.example.robertvacareanu.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;

/**
 * Saves and loads the score from shared preferences
 */
public class DataManager {

    private static DataManager instance = new DataManager();

    private DataManager() {
    }

    public static DataManager getInstance() {
        return instance;
    }


    private Activity mainActivity;

    public void setActivity(Activity activity) {
        this.mainActivity = activity;
    }


    public void saveData() {
        SharedPreferences data = mainActivity.getSharedPreferences("Data", mainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        editor.putInt("X Wins", TicTacToeManager.getInstance().getNrOfXWins());
        editor.putInt("O Wins", TicTacToeManager.getInstance().getNrOfOWins());
        editor.putInt("Draws", TicTacToeManager.getInstance().getNrOfDraws());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences data = mainActivity.getSharedPreferences("Data", mainActivity.MODE_PRIVATE);

        TicTacToeManager.getInstance().setNrOfXWins(data.getInt("X Wins", 0));
        TicTacToeManager.getInstance().setNrOfOWins(data.getInt("O Wins", 0));
        TicTacToeManager.getInstance().setNrOfDraws(data.getInt("Draws", 0));
        ButtonClickListener.setMoveCounter(1);

        //Display
        TextView textViewDraws = (TextView) this.mainActivity.findViewById(R.id.textview_draws);
        TextView textViewXWins = (TextView) this.mainActivity.findViewById(R.id.textview_x_wins);
        TextView textViewOWins = (TextView) this.mainActivity.findViewById(R.id.textview_o_wins);

        textViewDraws.setText(mainActivity.getString(R.string.draws, TicTacToeManager.getInstance().getNrOfDraws()));
        textViewXWins.setText(mainActivity.getString(R.string.score_x_wins, TicTacToeManager.getInstance().getNrOfXWins()));
        textViewOWins.setText(mainActivity.getString(R.string.score_o_wins, TicTacToeManager.getInstance().getNrOfOWins()));
    }

}
