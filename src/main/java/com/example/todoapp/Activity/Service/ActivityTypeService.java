package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Models.ActivityType;
import com.example.todoapp.Activity.Repository.ActivityRepository;
import com.example.todoapp.Activity.Repository.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeService {

    private final ActivityTypeRepository repository;
    private final ActivityRepository Actrepository;

    @Autowired
    public ActivityTypeService(@Qualifier("type") ActivityTypeRepository repository,@Qualifier("activity") ActivityRepository actRepository) {
        this.repository = repository;
        this.Actrepository = actRepository;
    }

    public List<ActivityType> getAllActivityTypes(){
        return repository.findAll();
    }

    public Optional<ActivityType> getActivityTypeById(Long id){
        return repository.findById(id);
    }

    public Optional<ActivityType> getActivityType(Long id){
        return repository.findByActivityId(id);
    }

    public ActivityType addNewActivityType(ActivityType activityType) throws IllegalAccessException {
        Optional<ActivityType> result = repository.findByActivityId(activityType.getActivityId());
        if(result.isPresent()){
            throw new IllegalAccessException();
        }
        Optional<Activity> resultAct = Actrepository.findById(activityType.getActivityId());
        if(!resultAct.isPresent()){
            throw new IllegalAccessException();
        }
        return repository.save(activityType);
    }

    public ActivityType removeActivityType(Long id) throws IllegalAccessException {
        Optional<ActivityType> result = repository.findById(id);
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

    public ActivityType removeActivityTypeByActivityId(Long id) throws IllegalAccessException {
        Optional<ActivityType> result = repository.findByActivityId(id);
        if(!result.isPresent()){
            throw new IllegalAccessException();
        }
        try {
            repository.deleteById(result.get().getId());
            return result.get();
        }
        catch (Exception e){
            System.out.println(e.toString());
            throw e;
        }

    }

    public ActivityType updateActivityType(ActivityType activityType, Long id) throws IllegalAccessException {
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
