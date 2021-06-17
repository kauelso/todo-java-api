package com.example.todoapp.Activity.Models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityType {
    public enum ActType{
        PESSOAL,PROFISSIONAL,OUTROS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @NotNull private ActType type;

    @Column(name = "activity_id")
    @NotNull private Long ActivityId;
}
