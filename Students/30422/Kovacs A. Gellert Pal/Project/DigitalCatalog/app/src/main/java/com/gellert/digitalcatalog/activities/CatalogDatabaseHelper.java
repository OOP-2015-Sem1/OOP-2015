package com.gellert.digitalcatalog.activities;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import com.gellert.digitalcatalog.models.Mark;

        import java.util.ArrayList;

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
        db.execSQL("CREATE TABLE PERSONS (personID INTEGER PRIMARY KEY, NAME TEXT, USERTYPE INTEGER);");
        db.execSQL("CREATE TABLE ACCOUNTS (USERNAME TEXT, PASSWORD TEXT, personID INTEGER);");
        db.execSQL("CREATE TABLE CLASSES (classID INTEGER PRIMARY KEY, NAME TEXT);");
        db.execSQL("CREATE TABLE STUDENT_CLASS (personID INTEGER, classID INTEGER);");
        db.execSQL("CREATE TABLE SUBJECTS (subjectID INTEGER PRIMARY KEY, NAME TEXT);");
        db.execSQL("CREATE TABLE TEACHER_SUBJECT_CLASS (personID INTEGER, subjectID INTEGER, classID INTEGER);");
        db.execSQL("CREATE TABLE MARKS (personID INTEGER, subjectID INTEGER, MARK INTEGER, MIDTERM INTEGER);");

        db.execSQL("INSERT INTO ACCOUNTS VALUES('stud1', 'stud1', 1);");
        db.execSQL("INSERT INTO ACCOUNTS VALUES('teach1', 'teach1', 2);");

        db.execSQL("INSERT INTO PERSONS VALUES(1, 'John Student', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(2, 'Jane Teacher', 1);");

        db.execSQL("INSERT INTO CLASSES VALUES (1, 'IX.A');");

        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (1, 1);");

        db.execSQL("INSERT INTO SUBJECTS VALUES (1, 'History');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (2, 'Mathematics');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (3, 'Physics');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (4, 'Literature');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (5, 'Informatics');");

        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (2, 1, 1);");

        db.execSQL("INSERT INTO MARKS VALUES (1, 1, 9, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 2, 6, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 3, 8, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 4, 9, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 5, 10, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 5, 5, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 4, 6, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 2, 8, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (1, 3, 2, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PERSONS;");
        db.execSQL("DROP TABLE IF EXISTS ACCOUNTS;");
        db.execSQL("DROP TABLE IF EXISTS CLASSES;");
        db.execSQL("DROP TABLE IF EXISTS STUDENT_CLASS;");
        db.execSQL("DROP TABLE IF EXISTS SUBJECTS;");
        db.execSQL("DROP TABLE IF EXISTS TEACHER_SUBJECT;");
        db.execSQL("DROP TABLE IF EXISTS CLASS_SUBJECT;");
        db.execSQL("DROP TABLE IF EXISTS MARKS;");
        onCreate(db);
    }

    public boolean insertNewMark(String name, String surname, String marks) {
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT PASSWORD FROM ACCOUNTS WHERE USERNAME = '" + username + "';", null);
        //no such username in the database
        if (result == null || !result.moveToFirst()) return false;
        return result.getString(result.getColumnIndex("PASSWORD")).equals(password);
    }

    public int getPersonID(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT personID FROM ACCOUNTS WHERE USERNAME = '" + username + "';", null);
        if (result == null || !result.moveToFirst()) return -1;
        return result.getInt(result.getColumnIndex("personID"));
    }

    public int getUserType(int personID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT USERTYPE FROM PERSONS WHERE personID = " + personID + ";", null);
        if (result == null || !result.moveToFirst()) return -1;
        return result.getInt(result.getColumnIndex("USERTYPE"));
    }

    public String getPersonName(int personID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT NAME FROM PERSONS WHERE personID = " + personID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        return result.getString(result.getColumnIndex("NAME"));
    }

    public String getStudentClassName(int personID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT CLASSES.NAME FROM CLASSES JOIN STUDENT_CLASS ON (CLASSES.classID = STUDENT_CLASS.classID) WHERE personID =" + personID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        return result.getString(result.getColumnIndex("NAME"));
    }

    public ArrayList<Mark> getStudentMarks(int personID) {
        ArrayList<Mark> marks = new ArrayList<Mark>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT SUBJECTS.NAME, MARKS.MARK, MARKS.MIDTERM FROM MARKS JOIN SUBJECTS ON (SUBJECTS.subjectID = MARKS.subjectID) WHERE personID =" + personID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        while (!result.isAfterLast()) {
            String subject = result.getString(result.getColumnIndex("NAME"));
            int mark = Integer.valueOf(result.getString(result.getColumnIndex("MARK")));
            boolean midterm;
            if (result.getInt(result.getColumnIndex("MIDTERM")) == 0) {
                midterm = false;
            }
            else {
                midterm = true;
            }
            marks.add(new Mark(subject, mark, midterm));
            result.moveToNext();
        }

        return marks;
    }
}

