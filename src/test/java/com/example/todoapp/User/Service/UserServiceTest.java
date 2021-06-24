package com.example.todoapp.User.Service;

import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private UserService service;

    private AutoCloseable autoCloseable;

    private User user1;

    private User user2;

    @BeforeEach
    void SetUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        service = new UserService(repository);
        user1 = new User("1",
                "username",
                "password",
                false);
        user2 = new User("1",
                "username",
                "password",
                true);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getUsers() {
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        when(repository.findAll()).thenReturn(list);
        assertEquals(list,service.getUsers());
    }

    @Test
    void getUsersEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(),service.getUsers());
    }

    @Test
    void getUser() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.of(user1));
        assertEquals(user1,service.getUser(anyString()));
    }

    @Test
    void getUserNotFound(){
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(Exception.class,()->service.getUser(anyString()));
    }

    @Test
    void createUser() throws IllegalAccessException {
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(User.class))).thenReturn(user1);
        assertEquals(user1,service.createUser(user1));
    }

    @Test
    void createUserAlreadyExists(){
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user1));
        assertThrows(Exception.class,()->service.createUser(user1));
    }

    @Test
    void updateUser() throws IllegalAccessException {
        when(repository.findById(anyString())).thenReturn(Optional.of(user1));
        User updated = user1;
        updated.setUsername(user2.getUsername());
        updated.setPassword(user2.getPassword());
        when(repository.save(any(User.class))).thenReturn(updated);
        assertEquals(updated,service.updateUser(user2,anyString()));
    }

    @Test
    void updateUserNotExists(){
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,()->service.updateUser(user2,anyString()));
    }

    @Test
    void deleteUser() throws IllegalAccessException {
        when(repository.findById(anyString())).thenReturn(Optional.of(user1));
        assertEquals(user1,service.deleteUser(anyString()));
    }

    @Test
    void deleteUserNotExists(){
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,()->service.deleteUser(anyString()));
    }
}