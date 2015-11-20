package com.example.alexh.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexh on 20.11.2015.
 */
public class TTTSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TicTacToe.db";
    private static final String TABLE_NAME = "tictactoe_table";
    private static final String ID_COL = "ID";
    private static final String SCORE_X_WINS_COL = "X_WINS";
    private static final String SCORE_O_WINS_COL = "O_WINS";
    private static final String SCORE_TIES_COL = "TIES";
    private static final String GAMESTATE_COL = "GAME_STATE";
    private static final String AI_STATUS_COL = "AI";


    public TTTSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER,%s INTEGER, %s INTEGER, %s TEXT, %s TEXT)",
                TABLE_NAME, ID_COL, SCORE_X_WINS_COL, SCORE_O_WINS_COL, SCORE_TIES_COL, GAMESTATE_COL, AI_STATUS_COL));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String xWins, String oWins, String ties, String gameState, String ai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORE_X_WINS_COL, xWins);
        contentValues.put(SCORE_O_WINS_COL, oWins);
        contentValues.put(SCORE_TIES_COL, ties);
        contentValues.put(GAMESTATE_COL, gameState);
        contentValues.put(AI_STATUS_COL, ai);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public int updateData(String xWins, String oWins, String ties, String gameState, String ai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String id = "1";
        contentValues.put(ID_COL, id);
        contentValues.put(SCORE_X_WINS_COL, xWins);
        contentValues.put(SCORE_O_WINS_COL, oWins);
        contentValues.put(SCORE_TIES_COL, ties);
        contentValues.put(GAMESTATE_COL, gameState);
        contentValues.put(AI_STATUS_COL, ai);
        return db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
    }
}
