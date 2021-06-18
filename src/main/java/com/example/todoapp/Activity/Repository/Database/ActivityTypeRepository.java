package com.example.todoapp.Activity.Repository.Database;

import com.example.todoapp.Activity.Models.ActivityType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("type")
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType,Long> {
    @Query("SELECT at FROM ActivityType at WHERE at.type = ?1")
    Optional<ActivityType> findByName(String name);
}
