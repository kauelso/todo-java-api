package com.example.todoapp.Activity.Service;

import com.example.todoapp.Activity.DTOs.ActivityDTO;
import com.example.todoapp.Activity.Models.Activity;
import com.example.todoapp.Activity.Repository.API.ActivityApiRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityRepository;
import com.example.todoapp.Activity.Repository.Database.ActivityTypeRepository;
import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final UserRepository userRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityApiRepository activityApiRepository;

    @Autowired
    public ActivityService(@Qualifier("activity") ActivityRepository repository,
                           @Qualifier("user") UserRepository userRepository,
                           @Qualifier("type") ActivityTypeRepository activityTypeRepository,
                           ActivityApiRepository activityApiRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.activityApiRepository = activityApiRepository;
    }

    public List<Activity> getActivities(String id) {
        List<Activity> response = repository.findAll(id);
        if(response.isEmpty()){
            return activityApiRepository.getAllActivity(id).getBody();
        }else {
            response.addAll(Objects.requireNonNull(activityApiRepository.getAllActivity(id).getBody()));
            return response;
        }
    }

    public Activity addNewActivity(Activity activity) throws IllegalAccessException {
        activity.setId(UUID.randomUUID().toString());
        activity.setType(activity.getType().toUpperCase());
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());

        if(!activityTypeRepository.findByName(activity.getType()).isPresent()){
            throw new IllegalAccessException();
        }
        Optional<User> findDB = userRepository.findById(activity.getUserId());
        if(!findDB.isPresent()){
            throw new IllegalAccessException();
        }
        if(findDB.get().isRemoteDB()){
            ActivityDTO dto = new ActivityDTO();
            dto.toDTO(activity);
            return activityApiRepository.saveActivity(dto).getBody();
        }else{
            return repository.save(activity);
        }
    }

    public Activity getActivity(String id, String userId) {
        Optional<Activity> result = repository.findById(id);
        if(result.isPresent()){
            if (result.get().getUserId().equals(userId))
                return repository.findById(id).get();
        }
        else{
            Activity apiResponse = activityApiRepository.getActivity(id).getBody();
            if(apiResponse != null && apiResponse.getUserId().equals(userId))
                return apiResponse;
        }
        return null;
    }

    public Activity updateActivity(Activity activity,String id) throws IllegalAccessException {
       Optional<Activity> result = repository.findById(id);
        activity.setType(activity.getType().toUpperCase());
        if(!activityTypeRepository.findByName(activity.getType()).isPresent()){
            throw new IllegalAccessException();
        }
        if(result.isPresent()) {
            if(!result.get().getUserId().equals(activity.getUserId()))
            {
                throw new IllegalAccessException();
            }
            Optional<Activity> updatedActivity = result.map(actv -> {
                if (activity.getTitle() != null) actv.setTitle(activity.getTitle());
                if (activity.getDescription() != null) actv.setDescription(activity.getDescription());
                if (activity.getType() != null) actv.setType(activity.getDescription());
                actv.setUpdatedAt(LocalDateTime.now());
                return actv;
            });
            return repository.save(updatedActivity.get());
        }
        else{
            Activity apiResult = activityApiRepository.getActivity(id).getBody();
            if(apiResult == null || !apiResult.getUserId().equals(apiResult.getUserId())){
                throw new IllegalAccessException();
            }
            apiResult.setId(id);
            activity.setType(activity.getType().toUpperCase());
            if (activity.getTitle() != null) apiResult.setTitle(activity.getTitle());
            if (activity.getDescription() != null) apiResult.setDescription(activity.getDescription());
            if (activity.getType() != null && activityTypeRepository.findByName(activity.getType()).isPresent()) {
                apiResult.setType(activity.getType());
            }
            activityApiRepository.putActivity(apiResult);
            return apiResult;
        }
    }

    public Activity removeActivity(String id, String userId) throws Exception {
        Optional<Activity> result = repository.findById(id);
        if(result.isPresent()) {
            if (result.get().getUserId().equals(userId)){
                repository.deleteById(id);
                return result.get();
            }else{
                throw new IllegalAccessException();
            }
        }
        else{
            ResponseEntity<Activity> activity = activityApiRepository.getActivity(id);
            if(activity.getBody() == null || !activity.getBody().getUserId().equals(userId)){
                throw new Exception("Id not found");
            }
            activityApiRepository.removeActivity(id);
            return activity.getBody();
        }
    }
}
