package com.izavasconcelos.coreengineering.tema08.model;

public class Book {

    private String title;
    private String author;
    private int id;
    boolean loanStatus;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.loanStatus = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(boolean loanStatus) {
        this.loanStatus = loanStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "\nTitle: " + title + ", Author: " + author + ", Book Id: " + id;
    }
}
