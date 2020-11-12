package com.izavasconcelos.coreengineering.tema08.controller;

import com.izavasconcelos.coreengineering.tema08.model.Book;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class LibraryServiceTest {
    private final LibraryService libraryService = new LibraryService();

    @Test
    public void shouldMakeABookLoanTest() {
        Optional<Book> makeLoan = libraryService.makeABookLoan("Inferno", "Izabela");
        assertTrue(makeLoan.isPresent());
        assertEquals("Inferno", makeLoan.get().getTitle());
    }
    @Test
    public void shouldMakeANullBookLoanTest() {
        Optional<Book> makeNullLoan = libraryService.makeABookLoan(null, "Izabela");
        assertFalse(makeNullLoan.isPresent());
        makeNullLoan = libraryService.makeABookLoan("Title here", null);
        assertFalse(makeNullLoan.isPresent());
    }
    @Test
    public void shouldMakeARenewLoanTest() {
        Optional<Book> renewLoan = libraryService.makeARenewLoan("My Bloody Roots", "Izabela");
        assertTrue(renewLoan.isPresent());
    }
    @Test
    public void shouldMakeANullRenewLoanTest() {
        Optional<Book> renewNullLoan = libraryService.makeARenewLoan(null, "Name kkk");
        assertFalse(renewNullLoan.isPresent());
        renewNullLoan = libraryService.makeARenewLoan("", null);
        assertFalse(renewNullLoan.isPresent());
    }
    @Test
    public void shouldReturnABookTest() {
        assertTrue(libraryService.returnABook("Inferno", "Izabela"));
    }
    @Test
    public void shouldReturnANullBookTest() {
        assertFalse(libraryService.returnABook(null, "name"));
        assertFalse(libraryService.returnABook("", null));
        assertFalse(libraryService.returnABook(null, null));
        assertFalse(libraryService.returnABook("Invalid title", "Invalid name"));
    }
    @Test
    public void shouldCalculateFineForDelayTest() {
        assertTrue(libraryService.shouldCalculateFineForDelay("Izabela"));
    }

    @Test
    public void returnDelayDaysTest() {
        assertEquals(7, libraryService.returnDelayDays("O símbolo perdido","Rafael"));
        assertEquals(0, libraryService.returnDelayDays("My Bloody Roots","Izabela"));
    }

    @Test
    public void payDebtTest() {
        assertFalse(libraryService.payDebt("Origem","Izabela"));
    }

}
