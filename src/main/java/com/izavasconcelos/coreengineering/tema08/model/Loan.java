package com.izavasconcelos.coreengineering.tema08.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {
    private String bookTitle;
    private String userName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private double fineForDelay;

    public Loan(String title, String userName, LocalDate loanDate, LocalDate dueDate) {
        this.bookTitle = title;
        this.userName = userName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getFineForDelay() {
        return fineForDelay;
    }

    public void setFineForDelay(double fineForDelay) {
        this.fineForDelay = fineForDelay;
    }

    @Override
    public String toString(){
        return("\nUser: " + this.userName + "  |   Book: " + this.bookTitle +
                "\nLoan Date: " + getLoanDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " | Due date: " + getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
