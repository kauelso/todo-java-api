package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Repository.Database.ActivityRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final ActivityTypeRepository activityTypeRepository;

    @Autowired
    public ActivityService(@Qualifier("activity") ActivityRepository repository,@Qualifier("type") ActivityTypeRepository activityTypeRepository) {
        this.repository = repository;
        this.activityTypeRepository = activityTypeRepository;
    }

    public List<Activity> getActivities() {
        return repository.findAll();
    }

    public Activity addNewActivity(Activity activity) throws IllegalAccessException {
        activity.setId(UUID.randomUUID().toString());
        activity.setType(activity.getType().toUpperCase());
        if(!activityTypeRepository.findByName(activity.getType()).isPresent()){
            throw new IllegalAccessException();
        }
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());
        return repository.save(activity);
    }

    public Optional<Activity> getActivity(String id) {
        return repository.findById(id);
    }

    public Activity updateActivity(Activity activity,String id) throws IllegalAccessException {
       Optional<Activity> result = repository.findById(id);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        activity.setType(activity.getType().toUpperCase());
        Optional<Activity> updatedActivity = result.map(actv -> {
            if(activity.getTitle() != null)actv.setTitle(activity.getTitle());
            if(activity.getDescription() != null)actv.setDescription(activity.getDescription());
            if(activity.getType() != null && activityTypeRepository.findByName(activity.getType()).isPresent())
                actv.setType(activity.getDescription());
            return actv;
        });
        return repository.save(updatedActivity.get());
    }

    public Activity removeActivity(String id) throws IllegalAccessException {
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
