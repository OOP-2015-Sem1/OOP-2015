package com.gellert.digitalcatalog.models;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public abstract class Person {

    int personID;
    String name;

    public Person(int personID, String name) {
        this.personID = personID;
        this.name = name;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
