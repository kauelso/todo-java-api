package com.example.todoapp.Activity.DTOs;

import com.example.todoapp.Activity.Models.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long userId;

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
}
