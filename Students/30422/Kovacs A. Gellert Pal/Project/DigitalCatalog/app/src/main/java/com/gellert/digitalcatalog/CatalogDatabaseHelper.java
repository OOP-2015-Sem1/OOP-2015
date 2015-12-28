package com.gellert.digitalcatalog;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gellért on 2015. 11. 04..
 */
public class CatalogDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "catalog.db";

    public CatalogDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PERSONS (personID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USERTYPE INTEGER)");
        db.execSQL("CREATE TABLE ACCOUNTS (USERNAME TEXT, PASSWORD TEXT, personID INTEGER)");
        db.execSQL("CREATE TABLE CLASSES (classID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
        db.execSQL("CREATE TABLE STUDENT_CLASS (personID INTEGER, classID INTEGER)");
        db.execSQL("CREATE TABLE SUBJECTS (subjectID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
        db.execSQL("CREATE TABLE TEACHER_SUBJECT (personID INTEGER, subjectID INTEGER)");
        db.execSQL("CREATE TABLE CLASS_SUBJECT (classID INTEGER, subjectID INTEGER)");
        db.execSQL("CREATE TABLE MARKS (personID INTEGER, subjectID INTEGER, MARK INTEGER, MIDTERM INTEGER)");

        db.execSQL("INSERT INTO ACCOUNTS VALUES('user1', 'user1', 1)");
        db.execSQL("INSERT INTO ACCOUNTS VALUES('user2', 'user2', 2)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PERSONS");
        db.execSQL("DROP TABLE IF EXISTS ACCOUNTS");
        db.execSQL("DROP TABLE IF EXISTS CLASSES");
        db.execSQL("DROP TABLE IF EXISTS STUDENT_CLASS");
        db.execSQL("DROP TABLE IF EXISTS SUBJECTS");
        db.execSQL("DROP TABLE IF EXISTS TEACHER_SUBJECT");
        db.execSQL("DROP TABLE IF EXISTS CLASS_SUBJECT");
        db.execSQL("DROP TABLE IF EXISTS MARKS");
        onCreate(db);
    }

    public boolean insertNewMark(String name, String surname, String marks) {
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT PASSWORD FROM ACCOUNTS WHERE USERNAME = '" + username + "'", null);
        //no such username in the database
        if (result == null || !result.moveToFirst()) return false;
        return result.getString(result.getColumnIndex("PASSWORD")).equals(password);
    }

    public int getPersonID(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT personID FROM ACCOUNTS WHERE USERNAME = '" + username + "'", null);
        return result.getInt(0);
    }
}

