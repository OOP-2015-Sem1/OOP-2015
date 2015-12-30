package com.gellert.digitalcatalog.models;

import java.util.ArrayList;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class Teacher extends Person {
    ArrayList<String> subjects;
    public Teacher(int personID, String name, ArrayList<String> subjects) {

        super(personID, name);
        this.subjects = subjects;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }
}
