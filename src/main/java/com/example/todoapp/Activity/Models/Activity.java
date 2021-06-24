package com.example.todoapp.Activity.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull private LocalDateTime createdAt;

    @Column(name = "updateAt")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull private LocalDateTime updatedAt;

    @Column(name = "user_id")
    @NotNull private String userId;

    @Column(name = "type")
    @NotNull private String type;
}
