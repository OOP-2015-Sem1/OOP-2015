package com.example.alexh.tictactoe;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Random;

/**
 * Board is the top class that holds all game data
 * it holds save/load methods using preferences
 */
public class Board {
    protected Activity activity;
    protected GameBoard gameBoard;
    public SharedPreferencesSaveManager sharedPreferencesSaveManager;
    public SQLiteDatabaseSaveManager sqLiteDatabaseSaveManager;

    public Board(Activity activity, LinkedList<Button> buttonLinkedList, TextView scoreTextView, TextView aiTextView) {
        this.activity = activity;
        this.gameBoard = new GameBoard(this, buttonLinkedList, scoreTextView, aiTextView);

        this.sharedPreferencesSaveManager = new SharedPreferencesSaveManager(this.activity, this.gameBoard);
        this.sqLiteDatabaseSaveManager = new SQLiteDatabaseSaveManager(this.activity, this.gameBoard);
        load();
    }

    public void save() {
        this.sharedPreferencesSaveManager.save();
        this.sqLiteDatabaseSaveManager.save();
    }

    public void load() {
        if (Math.random() >= 0.5) {
            this.sharedPreferencesSaveManager.load();
            Log.d("DEBUG", "pref");
        } else {
            this.sqLiteDatabaseSaveManager.load();
            Log.d("DEBUG", "SQLite");
        }
    }
}
