package com.example.todoapp.Activity.Contollers;

import com.example.todoapp.Activity.Service.ActivityService;
import com.example.todoapp.Activity.Models.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "activity")
public class ActivityController {
    private final ActivityService service;

    @Autowired
    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getActivities(){
        try{
            return new ResponseEntity<List<Activity>>(service.getActivities(), HttpStatus.OK);
        }
        catch (IllegalAccessError e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable Long id){
        try{
            return new ResponseEntity<Activity>(service.getActivity(id).get(), HttpStatus.OK);
        }
        catch (IllegalAccessError e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity){
        try{
            return new ResponseEntity<Activity>(service.addNewActivity(activity), HttpStatus.OK);
        }
        catch (IllegalAccessError e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity,@PathVariable Long id){
        try{
            return new ResponseEntity<Activity>(service.updateActivity(activity,id), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Activity> removeActivity(@PathVariable Long id){
        try{
            return new ResponseEntity<Activity>(service.removeActivity(id), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        }
    }
}
