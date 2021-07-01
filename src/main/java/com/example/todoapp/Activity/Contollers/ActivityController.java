package com.example.todoapp.Activity.Contollers;

import com.example.todoapp.Activity.Service.ActivityService;
import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.User.DTO.DecodedTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try{
            return new ResponseEntity<List<Activity>>(service.getActivities(info.getId()), HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.toString() +" : " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable String id){
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try{
            return new ResponseEntity<Activity>(service.getActivity(id,info.getId()), HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.toString() +" : " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Activity> addActivity(@RequestBody Activity body){
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Activity activity = body;
        activity.setUserId(info.getId());
        System.out.println(activity);
        try{
            return new ResponseEntity<Activity>(service.addNewActivity(activity), HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.toString() +" : " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity,@PathVariable String id){
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        activity.setUserId(info.getId());
        try{
            return new ResponseEntity<Activity>(service.updateActivity(activity,id), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString() +" : " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Activity> removeActivity(@PathVariable String id){
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try{
            return new ResponseEntity<Activity>(service.removeActivity(id,info.getId()), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString() +" : " + e.getCause());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
