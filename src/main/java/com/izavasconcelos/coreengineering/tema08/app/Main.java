package com.izavasconcelos.coreengineering.tema08.app;

import com.izavasconcelos.coreengineering.tema08.model.Book;
import com.izavasconcelos.coreengineering.tema08.model.User;
import com.izavasconcelos.coreengineering.tema08.services.BookService;
import com.izavasconcelos.coreengineering.tema08.services.ReportService;
import com.izavasconcelos.coreengineering.tema08.services.UserService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        BookService bookService = new BookService();
        UserService userService = new UserService();
        ReportService reportService = new ReportService();

        System.out.println("************ List Users *************");
        List<User> allUsers = userService.listAllUsers();
        System.out.println(allUsers);

        System.out.println("\n\n************ List Books *************");
        List<Book> allBooks = bookService.listAllBooks();
        System.out.println(allBooks);

        String loans = reportService.loansReport();
        System.out.println(loans);

        String topTen = reportService.topTenUsersReport();
        System.out.println(topTen);

        String overdue = reportService.overdueUserReport();
        System.out.println(overdue);

    }
}
