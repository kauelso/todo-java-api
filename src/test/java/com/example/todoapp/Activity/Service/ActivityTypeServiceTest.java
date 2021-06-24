package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.Models.ActivityType;
import com.example.todoapp.Activity.Repository.Database.ActivityTypeRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ActivityTypeServiceTest {
    @Mock
    private ActivityTypeRepository repository;
    @Mock
    private ActivityTypeService service;

    private AutoCloseable autoCloseable;

    private ActivityType type1;

    private ActivityType type2;

    @BeforeEach
    void SetUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        service = new ActivityTypeService(repository);
        type1 = new ActivityType(
                1L,
                "OUTROS"
        );
        type2 = new ActivityType(
                1L,
                "PROFISSIONAL"
        );
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getAllActivityTypes() {
        List<ActivityType> list = new ArrayList<ActivityType>();
        list.add(type1);
        list.add(type2);
        when(repository.findAll()).thenReturn(list);
        assertEquals(list,service.getAllActivityTypes());
    }
    @Test
    void getAllActivityTypesEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(),service.getAllActivityTypes());
    }

    @Test
    void getActivityTypeById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(type1));
        assertEquals(Optional.of(type1),service.getActivityTypeById(anyLong()));
    }

    @Test
    void getActivityTypeByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(),service.getActivityTypeById(anyLong()));
    }

    @Test
    void addNewActivityTypeAlreadyNotExists() throws IllegalAccessException {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(repository.save(type1)).thenReturn(type1);
        assertEquals(type1,service.addNewActivityType(type1));
    }

    @Test
    void addNewActivityTypeAlreadyExists(){
        when(repository.findByName(anyString())).thenReturn(Optional.of(type1));
        assertThrows(IllegalAccessException.class,() ->service.addNewActivityType(type1));
    }

    @Test
    void removeActivityType() throws IllegalAccessException {
        when(repository.findByName(anyString())).thenReturn(Optional.of(type1));
        assertEquals(type1,service.removeActivityType(anyString()));
    }

    @Test
    void removeActivityTypeAlreadyExists(){
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,() ->service.removeActivityType(anyString()));
    }

    @Test
    void updateActivityType() throws IllegalAccessException {
        when(repository.findById(anyLong())).thenReturn(Optional.of(type1));
        when(repository.save(any(ActivityType.class))).thenReturn(type2);
        assertEquals(type2,service.updateActivityType(type2,anyLong()));
    }

    @Test
    void updateActivityTypeNotFound(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,()->service.updateActivityType(type2,anyLong()));
    }
}