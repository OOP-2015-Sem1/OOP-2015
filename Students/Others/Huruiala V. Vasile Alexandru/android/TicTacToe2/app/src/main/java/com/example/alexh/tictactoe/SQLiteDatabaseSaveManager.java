package com.example.alexh.tictactoe;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by alexh on 20.11.2015.
 */
public class SQLiteDatabaseSaveManager implements GameStateSaveManager{
    private Activity activity;
    private GameBoard gameBoard;

    private TTTSQLiteHelper db;

    public SQLiteDatabaseSaveManager(Activity activity, GameBoard gameBoard) {
        this.activity = activity;
        this.gameBoard = gameBoard;

        this.db = new TTTSQLiteHelper(this.activity.getApplicationContext());
    }

    @Override
    public void save() {
        String xWins = gameBoard.getScore().getXScore() + "";
        String oWins = gameBoard.getScore().getOScore() + "";
        String ties = gameBoard.getScore().getTies() + "";
        String gameState = gameBoard.getGridButtonPanel().toString();
        String ai = gameBoard.getAi().isAiOn() + "";

        if (this.db.updateData(xWins, oWins, ties, gameState, ai) == 0) {
            this.db.insertData(xWins, oWins, ties, gameState, ai);
        }
    }

    @Override
    public void load() {
        Cursor res = this.db.getAllData();
        if (res.getCount() != 1) {
            Log.d("Error", "Database error");
        } else {
            res.moveToFirst();
            int xWins = res.getInt(1);
            int oWins = res.getInt(2);;
            int ties = res.getInt(3);
            String gameState = res.getString(4);
            String ai = res.getString(5);

            this.gameBoard.getScore().set(xWins, oWins, ties);
            this.gameBoard.gridButtonPanel.setValue(gameState);
            this.gameBoard.getAi().setAiOn(Boolean.parseBoolean(ai));
        }
    }
}
