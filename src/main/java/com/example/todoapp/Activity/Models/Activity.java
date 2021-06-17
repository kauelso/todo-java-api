package com.example.todoapp.Activity.Models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull private String title;

    @Column(name = "description")
    @NotNull private String description;

    @Column(name = "createdAt")
    @NotNull private LocalDateTime createdAt;

    @Column(name = "updateAt")
    @NotNull private LocalDateTime updatedAt;
}
