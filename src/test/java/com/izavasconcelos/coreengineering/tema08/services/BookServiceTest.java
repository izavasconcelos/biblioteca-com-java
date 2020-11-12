package com.izavasconcelos.coreengineering.tema08.services;

import com.izavasconcelos.coreengineering.tema08.model.Book;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class BookServiceTest {
    private BookService bookService = new BookService();

    private Book bookTest = new Book("BookTest", "Harlan");

    @Test
    public void shouldRegisterBookTest() {
        assertTrue(bookService.registerBook(bookTest));
    }

    @Test
    public void shouldDeleteABookByTitle() {
        Optional<Book> searchBookTitle = bookService.searchBookByTitle("BookTest");
        assertTrue(searchBookTitle.isPresent());
        assertTrue(bookService.deleteBookByTitle("BookTest"));
    }
    @Test
    public void shouldDeleteANullBook() {
        assertFalse(bookService.deleteBookByTitle(null));
        assertFalse(bookService.deleteBookByTitle("A Invalid Title"));
    }

    @Test
    public void shouldSearchBookByTitleTest() {
        Optional<Book> searchBookTitle = bookService.searchBookByTitle("Eu sou Ozzy");
        assertTrue(searchBookTitle.isPresent());
        assertEquals("Eu sou Ozzy", searchBookTitle.get().getTitle());

        searchBookTitle = bookService.searchBookByTitle("Alta tensão");
        assertTrue(searchBookTitle.isPresent());
        assertEquals("Alta tensão", searchBookTitle.get().getTitle());
    }
    @Test
    public void shouldSearchBookByNullTitleTest() {
        Optional<Book> searchBookTitle = bookService.searchBookByTitle(null);
        assertFalse(searchBookTitle.isPresent());
        searchBookTitle = bookService.searchBookByTitle("A Invalid Title");
        assertFalse(searchBookTitle.isPresent());
    }

    @Test
    public void shouldSearchBookByAuthorTest() {
        List<Book> searchBookAuthor = bookService.searchBookByAuthor("Harlan Coben");
        assertFalse(searchBookAuthor.isEmpty());
        assertEquals(4, searchBookAuthor.size());
        assertEquals("Nao conte a ninguem", searchBookAuthor.get(0).getTitle());
        assertEquals("Alta tensão", searchBookAuthor.get(1).getTitle());
        assertEquals("Confie em mim", searchBookAuthor.get(2).getTitle());
        assertEquals("Seis anos depois", searchBookAuthor.get(3).getTitle());
    }
    @Test
    public void shouldSearchBookByNullAuthorTest() {
        List<Book> searchBookAuthor = bookService.searchBookByAuthor(null);
        assertTrue(searchBookAuthor.isEmpty());
        searchBookAuthor = bookService.searchBookByAuthor("Izabela");
        assertTrue(searchBookAuthor.isEmpty());
    }
    @Test
    public void shouldListBooks() {
        List<Book> listAll = bookService.listAllBooks();
        assertEquals(11, listAll.size());
        assertFalse(listAll.isEmpty());
    }
}
