package com.izavasconcelos.coreengineering.tema08.services;

import com.izavasconcelos.coreengineering.tema08.dao.BookDAO;
import com.izavasconcelos.coreengineering.tema08.model.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class BookService {
    private BookDAO bookDao = new BookDAO();
    private List<Book> listBooks = bookDao.listAllBooks();
    private int counterBookId = 0;


    public boolean registerBook(Book book) {
        Optional<Book> searchTitle = searchBookByTitle(book.getTitle());
        if(searchTitle.isPresent()) {
            return false;
        }else{
            counterBookId = (listAllBooks().size() == 0) ? 0 : listAllBooks().get(listAllBooks().size()-1).getId() + 1;
            book.setId(counterBookId);
            listBooks.add(book);
            bookDao.saveBook(listBooks);
            return true;
        }
    }

    public boolean deleteBookByTitle(String title) {
        Optional<Book> bookToRemove = searchBookByTitle(title);
        if(bookToRemove.isPresent()) {
            listBooks.removeIf(book -> book.getTitle().equals(title) && !book.isLoanStatus());
            bookDao.saveBook(listBooks);
            return true;
        }
        return false;
    }

    public Optional<Book> searchBookByTitle(String title) {
        Optional nullPointerTitle = Optional.ofNullable(title);
        if(!nullPointerTitle.isPresent()) {
            return Optional.empty();
        }
        return listBooks.stream().filter(books -> books.getTitle().equals(title)).findFirst();
    }

    public List<Book> searchBookByAuthor(String author) {
        Optional nullPointerAuthor = Optional.ofNullable(author);
        if(!nullPointerAuthor.isPresent()) {
            return Collections.emptyList();
        }
        return listBooks.stream().filter(books -> books.getAuthor().contains(author)).collect(toList());
    }

    public List<Book> listAllBooks() {
        return listBooks;
    }


    public boolean saveBook(List<Book> book) {
        return bookDao.saveBook(book);
    }
}
