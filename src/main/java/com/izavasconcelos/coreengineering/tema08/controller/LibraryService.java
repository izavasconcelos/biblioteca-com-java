package com.izavasconcelos.coreengineering.tema08.controller;

import com.izavasconcelos.coreengineering.tema08.model.Book;
import com.izavasconcelos.coreengineering.tema08.model.Loan;
import com.izavasconcelos.coreengineering.tema08.model.User;
import com.izavasconcelos.coreengineering.tema08.services.BookService;
import com.izavasconcelos.coreengineering.tema08.services.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class LibraryService {
    private static final int MAX_LOANS = 5;
    private static final int TIME_LOAN_DAYS = 7;
    private static final int PRICE = 5;

    private final BookService bookService;
    private final UserService userService;
    private final List<User> userList;
    private final List<Book> bookList;

    public LibraryService() {
        this.bookService = new BookService();
        this.userService = new UserService();
        this.bookList = bookService.listAllBooks();
        this.userList = userService.listAllUsers();
    }

    public Optional<Book> makeABookLoan(String title, String userName) {
        Optional<Book> searchTitle = bookService.searchBookByTitle(title);
        Optional<User> searchUser = userService.searchUserName(userName);
        if(!searchTitle.isPresent() || !searchUser.isPresent()){
            return Optional.empty();
        }

        if(searchTitle.get().isLoanStatus() || searchUser.get().getCurrentLoans() == MAX_LOANS){
            return Optional.empty();
        }
        Loan newLoan = new Loan(title,userName, LocalDate.now(), LocalDate.now().plusDays(TIME_LOAN_DAYS));
        searchTitle.get().setLoanStatus(true);
        searchUser.get().setLoansList(newLoan);

        searchUser.get().setCurrentLoans(searchUser.get().getCurrentLoans()+1);
        searchUser.get().setTotalLoans(searchUser.get().getTotalLoans()+1);

        bookService.saveBook(bookList);
        userService.saveUser(userList);

        return bookService.searchBookByTitle(title);
    }

    public Optional<Book> makeARenewLoan(String title, String userName) {
        Optional<Book> searchTitle = bookService.searchBookByTitle(title);
        Optional<User> searchUser = userService.searchUserName(userName);
        if(!searchTitle.isPresent() || !searchUser.isPresent()){
            return Optional.empty();
        }
        List<Loan> validLoan = searchUser.get().getLoansList();
        Optional<Loan> renewLoan = validLoan.stream()
                        .filter(titleAux -> titleAux.getBookTitle().equals(title))
                        .findFirst();
        if(!renewLoan.isPresent()){
            return Optional.empty();
        }
        boolean dueLoan = renewLoan.get().getDueDate().isAfter(LocalDate.now());
        if (!dueLoan) {
            return Optional.empty();
        }
        renewLoan.get().setLoanDate(LocalDate.now());
        renewLoan.get().setDueDate(LocalDate.now().plusDays(TIME_LOAN_DAYS));
        userService.saveUser(userList);
        return bookService.searchBookByTitle(title);
    }


    public boolean returnABook(String title, String userName) {
        Optional<Book> searchTitle = bookService.searchBookByTitle(title);
        Optional<User> searchUser = userService.searchUserName(userName);
        if(!searchTitle.isPresent() || !searchUser.isPresent()){
            return false;
        }
        if (!searchTitle.get().isLoanStatus()){
            return false;
        }

        List<Loan> validLoan = searchUser.get().getLoansList();
        Optional<Loan> returnBook = validLoan.stream()
                .filter(titleAux -> titleAux.getBookTitle().equals(title))
                .findFirst();
        if(!returnBook.isPresent()){
            return false;
        }

        boolean dueLoan = returnBook.get().getDueDate().isAfter(LocalDate.now());
        if (!dueLoan) {
            return false;
        }

        validLoan.removeIf(loan -> loan.getBookTitle().equals(title));
        searchTitle.get().setLoanStatus(false);
        searchUser.get().returnLoans();
        bookService.saveBook(bookList);
        userService.saveUser(userList);
        return true;
    }

    public boolean shouldCalculateFineForDelay(String userName) {
        Optional<User> searchUser = userService.searchUserName(userName);
        if(!searchUser.isPresent()){
            return false;
        }
        List<Loan> validLoan = searchUser.get().getLoansList();
        List<Loan> delayBook = validLoan.stream()
                .filter(due -> due.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        if(delayBook.isEmpty()){
            return false;
        }
        for(Loan delayCalculator : delayBook){
            long days = ChronoUnit.DAYS.between(delayCalculator.getDueDate(), LocalDate.now());
            delayCalculator.setFineForDelay(days * PRICE);
        }
        userService.saveUser(userList);
        return true;
    }

    public long returnDelayDays(String title, String userName) {
        Optional<User> searchUser = userService.searchUserName(userName);
        if(!searchUser.isPresent()){
            return 0;
        }
        List<Loan> validLoan = searchUser.get().getLoansList();
        Optional<Loan> delayBook = validLoan.stream()
                .filter(due -> due.getDueDate().isBefore(LocalDate.now()) && due.getBookTitle().equals(title))
                .findFirst();
        if(delayBook.isPresent()){
            long days = ChronoUnit.DAYS.between(delayBook.get().getDueDate(), LocalDate.now());
            return days;
        }
        return 0;
    }

    public boolean payDebt(String title, String userName) {
        Optional<User> searchUser = userService.searchUserName(userName);
        boolean userDelay = shouldCalculateFineForDelay(userName);
        if(userDelay){
            List<Loan> validLoan = searchUser.get().getLoansList();
            Optional<Loan> payDebt = validLoan.stream()
                    .filter(bookTitle -> bookTitle.getBookTitle().equals(title))
                    .findFirst();
            if(payDebt.isPresent()){
                payDebt.get().setDueDate(LocalDate.now());
                userService.saveUser(userList);
                returnABook(title,userName);
                return true;
            }

        }
        return false;
    }

}
