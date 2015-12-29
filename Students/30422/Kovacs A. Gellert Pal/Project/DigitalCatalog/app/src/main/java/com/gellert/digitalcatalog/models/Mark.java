package com.gellert.digitalcatalog.models;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class Mark {
    private String subject;
    private int mark;
    private boolean midterm;

    public Mark(String subject, int mark, boolean midterm) {
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
}
