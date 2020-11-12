package com.izavasconcelos.coreengineering.tema08.services;

import com.izavasconcelos.coreengineering.tema08.controller.LibraryService;
import com.izavasconcelos.coreengineering.tema08.dao.UserDAO;
import com.izavasconcelos.coreengineering.tema08.model.Loan;
import com.izavasconcelos.coreengineering.tema08.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private UserDAO userDao = new UserDAO();
    private List<User> listUsers = userDao.listAllUsers();
    LibraryService libraryService = new LibraryService();

    public List<Loan> listAllLoans() {
        List<Loan> allLoans = new ArrayList<>();

        for(User user : listUsers){
            allLoans.addAll(user.getLoansList());
        }
        return allLoans;
    }

    public String loansReport() {
        StringBuilder listLoansBuilder = new StringBuilder();

        listLoansBuilder.append("\n------------- Loans Report -------------");
        listAllLoans().forEach(loan -> listLoansBuilder
                .append(loan)
                .append("\n"));

        return listLoansBuilder.toString();
    }

    public String topTenUsersReport() {
        StringBuilder topUsersBuilder = new StringBuilder();

        List<User> topTen = listUsers.stream()
                .sorted(Comparator.comparing(User::getTotalLoans).reversed())
                .limit(10)
                .collect(Collectors.toList());

        topUsersBuilder.append("\n*********    Top 10 Readers   ********\n");
        topTen.forEach(user -> topUsersBuilder
                .append(user)
                .append("\n"));

        return topUsersBuilder.toString();
    }

    public String overdueUserReport() {
        StringBuilder overdueBuilder = new StringBuilder();
        List<Loan> overdueList = listAllLoans().stream()
                .filter(due -> due.getDueDate().isBefore(LocalDate.now()) && libraryService.shouldCalculateFineForDelay(due.getUserName()))
                .collect(Collectors.toList());

        overdueBuilder.append("\n------------ Overdue Report -----------\n");
        overdueList.forEach(due -> overdueBuilder
                .append("Days Late: " + ChronoUnit.DAYS.between(due.getDueDate(), LocalDate.now()) )
                .append("  |  Book Title: " + due.getBookTitle())
                .append("\nUser: " + due.getUserName() + "   |   Due date: " + due.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .append("\nFine for Delay: R$"+ due.getFineForDelay()+ "0")
                .append("\n\n"));

        return overdueBuilder.toString();
    }
}
