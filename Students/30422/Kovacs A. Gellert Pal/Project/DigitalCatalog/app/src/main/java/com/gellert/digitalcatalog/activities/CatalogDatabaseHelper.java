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
        db.execSQL("CREATE TABLE ACCOUNTS (USERNAME TEXT, PASSWORD TEXT, personID INTEGER);");
        db.execSQL("CREATE TABLE PERSONS (personID INTEGER PRIMARY KEY, NAME TEXT, USERTYPE INTEGER);");
        db.execSQL("CREATE TABLE CLASSES (classID INTEGER PRIMARY KEY, NAME TEXT);");
        db.execSQL("CREATE TABLE STUDENT_CLASS (personID INTEGER, classID INTEGER);");
        db.execSQL("CREATE TABLE SUBJECTS (subjectID INTEGER PRIMARY KEY, NAME TEXT);");
        db.execSQL("CREATE TABLE TEACHER_SUBJECT_CLASS (personID INTEGER, subjectID INTEGER, classID INTEGER);");
        db.execSQL("CREATE TABLE MARKS (markID INTEGER PRIMARY KEY, personID INTEGER, subjectID INTEGER, MARK INTEGER, MIDTERM INTEGER);");

        db.execSQL("INSERT INTO ACCOUNTS VALUES('s1', 's1', 1);");
        db.execSQL("INSERT INTO ACCOUNTS VALUES('t1', 't1', 2);");
        db.execSQL("INSERT INTO ACCOUNTS VALUES('t2', 't2', 3);");
        db.execSQL("INSERT INTO ACCOUNTS VALUES('s2', 's2', 4);");

        db.execSQL("INSERT INTO PERSONS VALUES(1, 'Jack Crawford', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(2, 'Hannibal Lecter', 1);");
        db.execSQL("INSERT INTO PERSONS VALUES(3, 'Will Graham', 1);");
        db.execSQL("INSERT INTO PERSONS VALUES(4, 'Alana Bloom', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(5, 'Jimmy Price', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(6, 'Brian Zeller', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(7, 'Bedelia Du Maurier', 2);");
        db.execSQL("INSERT INTO PERSONS VALUES(8, 'Beverly Katz', 2);");

        db.execSQL("INSERT INTO CLASSES VALUES (1, 'IX.A');");
        db.execSQL("INSERT INTO CLASSES VALUES (2, 'IX.B');");

        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (1, 1);");
        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (4, 2);");
        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (5, 1);");
        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (6, 2);");
        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (7, 1);");
        db.execSQL("INSERT INTO STUDENT_CLASS VALUES (8, 2);");

        db.execSQL("INSERT INTO SUBJECTS VALUES (1, 'History');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (2, 'Mathematics');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (3, 'Physics');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (4, 'Literature');");
        db.execSQL("INSERT INTO SUBJECTS VALUES (5, 'Informatics');");

        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (2, 1, 1);");
        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (2, 4, 1);");
        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (2, 1, 2);");
        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (3, 3, 1);");
        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (3, 3, 2);");
        db.execSQL("INSERT INTO TEACHER_SUBJECT_CLASS VALUES (3, 5, 2);");

        db.execSQL("INSERT INTO MARKS VALUES (1, 1, 1, 9, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (2, 1, 2, 6, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (3, 1, 3, 8, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (4, 1, 4, 9, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (5, 1, 5, 10, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (6, 1, 5, 5, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (7, 1, 4, 6, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (8, 1, 2, 8, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (9, 1, 3, 2, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (10, 4, 1, 6, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (11, 4, 1, 7, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (12, 4, 2, 8, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (13, 4, 3, 8, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (14, 4, 4, 9, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (15, 4, 5, 6, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (16, 4, 5, 7, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (17, 4, 4, 5, 0);");
        db.execSQL("INSERT INTO MARKS VALUES (18, 4, 2, 10, 1);");
        db.execSQL("INSERT INTO MARKS VALUES (19, 4, 3, 8, 0);");
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
        Cursor result = db.rawQuery("SELECT MARKS.markID, SUBJECTS.NAME, MARKS.MARK, MARKS.MIDTERM FROM MARKS " +
                                    "JOIN SUBJECTS ON (SUBJECTS.subjectID = MARKS.subjectID) " +
                                    "WHERE personID =" + personID +
                                    " ORDER BY SUBJECTS.NAME ASC, MARKS.MARK DESC;", null);
        if (result == null || !result.moveToFirst()) return null;
        while (!result.isAfterLast()) {
            int markID = result.getInt(result.getColumnIndex("markID"));
            String subject = result.getString(result.getColumnIndex("NAME"));
            int mark = Integer.valueOf(result.getString(result.getColumnIndex("MARK")));
            boolean midterm;
            if (result.getInt(result.getColumnIndex("MIDTERM")) == 0) {
                midterm = false;
            }
            else {
                midterm = true;
            }
            marks.add(new Mark(markID, subject, mark, midterm));
            result.moveToNext();
        }

        return marks;
    }

    public ArrayList<String> getTeacherSubjects(int personID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT DISTINCT SUBJECTS.NAME FROM SUBJECTS JOIN TEACHER_SUBJECT_CLASS ON (SUBJECTS.subjectID = TEACHER_SUBJECT_CLASS.subjectID) WHERE personID =" + personID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        ArrayList<String> subjects = new ArrayList<String>();
        while (!result.isAfterLast()) {
            subjects.add(result.getString(result.getColumnIndex("NAME")));
            result.moveToNext();
        }
        return subjects;
    }

    public ArrayList<String> getTeacherClassesForGivenSubject(int personID, int subjectID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT DISTINCT CLASSES.NAME FROM CLASSES \n" +
                                    "JOIN TEACHER_SUBJECT_CLASS ON (CLASSES.classID = TEACHER_SUBJECT_CLASS.classID) \n" +
                                    "JOIN SUBJECTS ON (SUBJECTS.subjectID = TEACHER_SUBJECT_CLASS.subjectID)\n" +
                                    "WHERE personID =" + personID + " AND SUBJECTS.subjectID = " + subjectID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        ArrayList<String> classNames = new ArrayList<String>();
        while (!result.isAfterLast()) {
            classNames.add(result.getString(result.getColumnIndex("NAME")));
            result.moveToNext();
        }
        return classNames;
    }

    public int getSubjectID(String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT subjectID FROM SUBJECTS WHERE NAME = '" + subject + "';", null);
        if (result == null || !result.moveToFirst()) return -1;
        return result.getInt(result.getColumnIndex("subjectID"));
    }

    public int getClassID(String className) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT classID FROM CLASSES WHERE NAME = '" + className + "';", null);
        if (result == null || !result.moveToFirst()) return -1;
        return result.getInt(result.getColumnIndex("classID"));
    }

    public ArrayList<String> getStudentNames(int classID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT PERSONS.NAME FROM PERSONS JOIN STUDENT_CLASS ON (PERSONS.personID = STUDENT_CLASS.personID) WHERE classID =" + classID + ";", null);
        if (result == null || !result.moveToFirst()) return null;
        ArrayList<String> subjects = new ArrayList<String>();
        while (!result.isAfterLast()) {
            subjects.add(result.getString(result.getColumnIndex("NAME")));
            result.moveToNext();
        }
        return subjects;
    }
}

