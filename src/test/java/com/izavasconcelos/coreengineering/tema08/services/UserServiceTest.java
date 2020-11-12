package com.izavasconcelos.coreengineering.tema08.services;

import com.izavasconcelos.coreengineering.tema08.model.User;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService = new UserService();
    @Test
    public void shouldRegisterUserTest() {
        Optional<User> userTest = userService.searchUserName("NewUser");
        assertFalse(userTest.isPresent());
        assertTrue(userService.registerUser(new User("NewUser", "51999632459")));

        userTest = userService.searchUserName("NewUser");
        assertTrue(userTest.isPresent());
    }
    @Test
    public void shouldDeleteUserTest() {
        Optional<User> userTest = userService.searchUserName("NewUser");
        assertTrue(userTest.isPresent());
        assertTrue(userService.deleteUser("NewUser"));

        userTest = userService.searchUserName("NewUser");
        assertFalse(userTest.isPresent());

    }
    @Test
    public void shouldRegisterExistentUserTest() {
        assertFalse(userService.registerUser(new User("João", "51999632459")));
        assertFalse(userService.registerUser(new User("Marina", "51999632459")));
        assertFalse(userService.registerUser(new User("Gabriela", "51999632459")));
    }
    @Test
    public void shouldSearchUserTest() {
        Optional<User> userTest = userService.searchUserName("Izabela");
        assertTrue(userTest.isPresent());
        assertEquals("Izabela", userTest.get().getUserName());

        userTest = userService.searchUserName("Rafael");
        assertTrue(userTest.isPresent());
        assertEquals("Rafael", userTest.get().getUserName());
    }
    @Test
    public void shouldSearchNullUserTest() {
        Optional<User> userTest = userService.searchUserName(null);
        assertFalse(userTest.isPresent());

        userTest = userService.searchUserName("Invalid user");
        assertFalse(userTest.isPresent());
    }
}
