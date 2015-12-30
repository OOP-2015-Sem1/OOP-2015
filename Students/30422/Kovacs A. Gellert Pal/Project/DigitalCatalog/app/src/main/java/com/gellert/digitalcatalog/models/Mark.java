package com.gellert.digitalcatalog.models;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class Mark {
    private int markID;
    private String subject;
    private int mark;
    private boolean midterm;

    public Mark(int markID, String subject, int mark, boolean midterm) {
        this.markID = markID;
        this.subject = subject;
        this.mark = mark;
        this.midterm = midterm;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isMidterm() {
        return midterm;
    }

    public void setMidterm(boolean midterm) {
        this.midterm = midterm;
    }

    public int getMarkID() {
        return markID;
    }
}
