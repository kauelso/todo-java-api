package com.example.todoapp.Activity.Contollers;

import com.example.todoapp.Activity.Models.ActivityType;
import com.example.todoapp.Activity.Service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "activitytype")
public class ActivityTypeController {
    private final ActivityTypeService service;

    @Autowired
    public ActivityTypeController(ActivityTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ActivityType>> getActivities(){
        try{
            return new ResponseEntity<List<ActivityType>>(service.getAllActivityTypes(), HttpStatus.OK);
        }
        catch (IllegalAccessError e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping
    public ResponseEntity<ActivityType> addActivityType(@RequestBody ActivityType activity){
        try{
            return new ResponseEntity<ActivityType>(service.addNewActivityType(activity), HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityType> updateActivityType(@RequestBody ActivityType activity, @PathVariable Long id){
        try{
            return new ResponseEntity<ActivityType>(service.updateActivityType(activity,id), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityType> removeActivityType(@PathVariable Long id){
        try{
            return new ResponseEntity<ActivityType>(service.removeActivityType(id), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }
}
