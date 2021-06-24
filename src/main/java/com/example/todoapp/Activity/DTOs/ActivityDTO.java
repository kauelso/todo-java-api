package com.example.todoapp.Activity.DTOs;

import com.example.todoapp.Activity.Models.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityDTO {

    private String id;

    private String title;

    private String description;

    private String createdAt;

    private String updatedAt;

    private String type;

    private String localOwner;

    private String userId;

    public void toDTO(Activity activity){
        this.id = activity.getId();
        this.title = activity.getTitle();
        this.description = activity.getDescription();
        this.createdAt = activity.getCreatedAt().toString();
        this.updatedAt = activity.getUpdatedAt().toString();
        this.type = activity.getType();
        this.userId = activity.getUserId();
        this.localOwner = "kaue";
    }
    public Activity toActivity(){
        Activity activity = new Activity();
        activity.setId(this.id);
        activity.setTitle(this.title);
        activity.setDescription(this.description);
        activity.setCreatedAt(LocalDateTime.parse(this.createdAt.replace("Z","")));
        activity.setUpdatedAt(LocalDateTime.parse(this.updatedAt.replace("Z","")));
        activity.setType(this.type);
        activity.setUserId(this.userId);
        return activity;
    }
}
