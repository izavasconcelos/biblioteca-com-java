package com.izavasconcelos.coreengineering.tema08.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String userPhone;
    private int currentLoans;
    private List<Loan> loanList;
    private int totalLoans;


    public User(String name, String phone) {
        this.userName = name;
        this.userPhone = phone;
        this.loanList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public List<Loan> getLoansList() {
        return loanList;
    }

    public int getCurrentLoans() {
        return currentLoans;
    }

    public void setCurrentLoans(int loans) {
        this.currentLoans = loans;
    }

    public void returnLoans() {
        this.currentLoans--;
    }

    public void setLoansList(Loan loansList) {
        this.loanList.add(loansList);
    }

    public int getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(int total) {
        this.totalLoans = total;
    }

    @Override
    public String toString() {
        return "\nUser: " + userName + "  |  Total Loans: " + totalLoans;
    }
}
