package com.example.robertvacareanu.currencyconverter.business.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "converter";
    private static final int DATABASE_VERSION = 1;

    private static final String JSON = "name";

    public static final String CURRENCY_TABLE_PROVIDER_ONE = "currency_one";
    public static final String CURRENCY_TABLE_PROVIDER_TWO = "currency_two";


    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";


    private Context context;
    //Querry
    private static final String COLUMNS_QUERY = "SELECT" + " " + JSON + " " + "FROM" + " ";


    private static final String CREATE_CURRENCY_TABLE_PROVIDER_ONE = "CREATE" + " " + "TABLE" + " " + CURRENCY_TABLE_PROVIDER_ONE + " " + "(" +
            JSON + " " + "TEXT" + " " + "NOT NULL" + " " + "UNIQUE" + " " +
            ")" + ";";

    private static final String CREATE_CURRENCY_TABLE_PROVIDER_TWO = "CREATE" + " " + "TABLE" + " " + CURRENCY_TABLE_PROVIDER_TWO + " " + "(" +
            JSON + " " + "TEXT" + " " + "NOT NULL" + " " + "UNIQUE" + " " +
            ")" + ";";


    public CurrencyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENCY_TABLE_PROVIDER_ONE);
        db.execSQL(CREATE_CURRENCY_TABLE_PROVIDER_TWO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + CURRENCY_TABLE_PROVIDER_ONE);
        db.execSQL(DROP_TABLE + CURRENCY_TABLE_PROVIDER_TWO);
        onCreate(db);
    }


    public int delete(String provider) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(provider, null, null);
    }

    public void save(JSONObject json, String provider) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JSON, json.toString());

        delete(provider);
        database.insert(provider, null, contentValues);

    }

    public JSONObject load(String provider) {
        SQLiteDatabase database = getWritableDatabase();
        JSONObject result = null;
        Cursor cursor = database.rawQuery(COLUMNS_QUERY + provider, null);

        if (cursor.moveToNext()) {
            try {
                result = new JSONObject(cursor.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return result;
    }
}
