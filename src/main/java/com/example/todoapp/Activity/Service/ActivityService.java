package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository repository;

    @Autowired
    public ActivityService(@Qualifier("activity") ActivityRepository repository) {
        this.repository = repository;
    }

    public List<Activity> getActivities() {
        return repository.findAll();
    }

    public Activity addNewActivity(Activity activity) {
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());
        return repository.save(activity);
    }

    public Optional<Activity> getActivity(Long id) {
        return repository.findById(id);
    }

    public Activity updateActivity(Activity activity,Long id) throws IllegalAccessException {
       Optional<Activity> result = repository.findById(id);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        Optional<Activity> updatedActivity = result.map(actv -> {
            if(activity.getTitle() != null)actv.setTitle(activity.getTitle());
            if(activity.getDescription() != null)actv.setDescription(activity.getDescription());
            return actv;
        });
        return repository.save(updatedActivity.get());
    }

    public Activity removeActivity(Long id) throws IllegalAccessException {
        Optional<Activity> result = repository.findById(id);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        try {
            repository.deleteById(id);
            return result.get();
        }
        catch (Exception e){
            System.out.println(e.toString());
            throw e;
        }

    }
}
