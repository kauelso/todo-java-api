package com.example.todoapp.Activity.Models;

import com.example.todoapp.User.Models.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    @NotNull private String title;

    @Column(name = "description")
    @NotNull private String description;

    @Column(name = "createdAt")
    @NotNull private LocalDateTime createdAt;

    @Column(name = "updateAt")
    @NotNull private LocalDateTime updatedAt;

    @Column(name = "user_id")
    @NotNull private Long userId;

    @Column(name = "type")
    @NotNull private String type;
}
