package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Models.ActivityType;
import com.example.todoapp.Activity.Repository.Database.ActivityRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeService {

    private final ActivityTypeRepository repository;

    @Autowired
    public ActivityTypeService(@Qualifier("type") ActivityTypeRepository repository) {
        this.repository = repository;
    }

    public List<ActivityType> getAllActivityTypes(){
        return repository.findAll();
    }

    public Optional<ActivityType> getActivityTypeById(Long id){
        return repository.findById(id);
    }

    public ActivityType addNewActivityType(ActivityType activityType) throws IllegalAccessException {
        activityType.setType(activityType.getType().toUpperCase());
        Optional<ActivityType> result = repository.findByName(activityType.getType());
        if(result.isPresent()){
            throw new IllegalAccessException();
        }
        return repository.save(activityType);
    }

    public ActivityType removeActivityType(Long name) throws IllegalAccessException {
        Optional<ActivityType> result = repository.findById(name);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        ActivityType type = result.get();
        repository.deleteById(type.getId());
        return type;
    }

    public ActivityType updateActivityType(ActivityType activityType, Long id) throws IllegalAccessException {
        activityType.setType(activityType.getType().toUpperCase());
        Optional<ActivityType> result = repository.findById(id);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        Optional<ActivityType> updatedActivityType = result.map(actv -> {
            if(activityType.getType() != null)actv.setType(activityType.getType());
            return actv;
        });
        return repository.save(updatedActivityType.get());
    }

}
