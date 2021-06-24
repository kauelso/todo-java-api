package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.DTOs.ActivityDTO;
import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Models.ActivityType;
import com.example.todoapp.Activity.Repository.API.ActivityApiRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityTypeRepository;
import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;


class ActivityServiceTest {
    @Mock
    private ActivityRepository repository;
    @Mock
    private ActivityApiRepository apiRepository;
    @Mock
    private ActivityTypeRepository typeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityService service;

    private AutoCloseable autoCloseable;

    private Activity activity;

    private User localUser;

    private User remoteUser;

    private ActivityType activityType;

    @BeforeEach
    void SetUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        activity  = new Activity("1",
                "teste",
                "descricao",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "1",
                "OUTROS"
        );
        localUser = new User("1",
                "username",
                "password",
                false);
        remoteUser = new User("1",
                "username",
                "password",
                true);
        activityType = new ActivityType(
                1L,
                "OUTROS"
        );
        service = new ActivityService(repository,userRepository,typeRepository,apiRepository);
    }
    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getActivitiesEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(apiRepository.getAllActivity()).thenReturn(new ResponseEntity<List<Activity>>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(Collections.emptyList(),service.getActivities());
    }
    @Test
    void getActivitiesBDListAndAPIEmptyList() {
        List<Activity> list = new ArrayList<Activity>();
        list.add(activity);
        when(repository.findAll()).thenReturn(list);
        when(apiRepository.getAllActivity()).thenReturn(new ResponseEntity<List<Activity>>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(list,service.getActivities());
    }

    @Test
    void getActivitiesBDEmptyListAndAPIList() {
        List<Activity> list = new ArrayList<Activity>();
        list.add(activity);
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(apiRepository.getAllActivity()).thenReturn(new ResponseEntity<List<Activity>>(list,HttpStatus.OK));
        assertEquals(list,service.getActivities());
    }

    @Test
    void getActivitiesBDListAndAPIList() {
        List<Activity> list = new ArrayList<Activity>();
        list.add(activity);
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(apiRepository.getAllActivity()).thenReturn(new ResponseEntity<List<Activity>>(list,HttpStatus.OK));
        assertEquals(list,service.getActivities());
    }

    @Test
    void addNewBDActivity() throws IllegalAccessException {
        when(typeRepository.findByName(activity.getType())).thenReturn(Optional.of(activityType));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(localUser));
        when(repository.save(any(Activity.class))).thenReturn(activity);
        assertEquals(activity,service.addNewActivity(activity));
    }

    @Test
    void addNewAPIActivity() throws IllegalAccessException {
        when(typeRepository.findByName(activity.getType())).thenReturn(Optional.of(activityType));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(remoteUser));
        when(apiRepository.saveActivity(any(ActivityDTO.class))).thenReturn(new ResponseEntity<Activity>(activity,HttpStatus.OK));
        assertEquals(activity,service.addNewActivity(activity));
    }

    @Test
    void addNewActivityTypeMissing(){
        when(typeRepository.findByName(activity.getType())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,() -> service.addNewActivity(activity));
    }

    @Test
    void addNewActivityUserMissing(){
        when(typeRepository.findByName(activity.getType())).thenReturn(Optional.of(activityType));
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalAccessException.class,() -> service.addNewActivity(activity));
    }

    @Test
    void getActivity() {
        when(repository.findById("1")).thenReturn(java.util.Optional.of(activity));
        when(apiRepository.getActivity("1")).thenReturn(
                new ResponseEntity<Activity>(activity, HttpStatus.OK)
        );
        assertEquals(activity,service.getActivity("1"));
    }

    @Test
    void returnActivityFromBD() {
        when(repository.findById("1")).thenReturn(java.util.Optional.of(activity));
        assertEquals(activity,service.getActivity("1"));
    }

    @Test
    void returnActivityFromAPI() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        when(apiRepository.getActivity("1")).thenReturn(
                new ResponseEntity<Activity>(activity, HttpStatus.OK)
        );
        assertEquals(activity,service.getActivity("1"));
    }

    @Test
    void returnActivityNotExists() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        when(apiRepository.getActivity("1")).thenReturn(
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
        );
        assertNull(service.getActivity("1"));
    }

    @Test
    void updateActivityFromBD() throws IllegalAccessException {
        when(repository.findById(anyString())).thenReturn(Optional.of(activity));
        Activity updated = activity;
        updated.setTitle("new title");
        when(typeRepository.findByName(anyString())).thenReturn(Optional.of(activityType));
        when(repository.save(any(Activity.class))).thenReturn(updated);
        assertEquals(updated,service.updateActivity(updated,anyString()));
    }

    @Test
    void updateActivityFromAPI() throws IllegalAccessException {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        Activity updated = activity;
        updated.setTitle("new title");
        when(typeRepository.findByName(anyString())).thenReturn(Optional.of(activityType));
        when(apiRepository.getActivity(anyString())).thenReturn(new ResponseEntity<Activity>(activity,HttpStatus.OK));
        assertEquals(updated,service.updateActivity(updated,anyString()));
    }

    @Test
    void updateActivityMissingTypeAPI() throws IllegalAccessException {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        Activity updated = activity;
        updated.setTitle("new title");
        when(typeRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(apiRepository.getActivity(anyString())).thenReturn(new ResponseEntity<Activity>(activity,HttpStatus.OK));
        assertThrows(IllegalAccessException.class,()->service.updateActivity(updated,anyString()));
    }

    @Test
    void updateActivityNotFound() {
        Activity updated = activity;
        updated.setTitle("new title");
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(apiRepository.getActivity(anyString())).thenReturn(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
        assertThrows(IllegalAccessException.class,()->service.updateActivity(updated, anyString()));
    }

    @Test
    void removeExistingActivityBD() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.of(activity));
        assertEquals(activity,service.removeActivity(activity.getId()));
    }
    @Test
    void removeNotExistingActivity() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(apiRepository.getActivity(anyString())).thenReturn(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
        assertThrows(Exception.class,()->service.removeActivity(activity.getId()));
    }
    @Test
    void removeExistingActivityAPI() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(apiRepository.getActivity(anyString())).thenReturn(new ResponseEntity<Activity>(activity,HttpStatus.OK));
        assertEquals(activity,service.removeActivity(activity.getId()));
    }
}