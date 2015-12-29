package com.gellert.digitalcatalog.models;

import java.util.ArrayList;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class Student extends Person {

    private String className;
    private ArrayList<Mark> marks;

    public Student(int personID, String name, String className, ArrayList<Mark> marks) {
        super(personID,name);
        this.className = className;
        this.marks = marks;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }
}
