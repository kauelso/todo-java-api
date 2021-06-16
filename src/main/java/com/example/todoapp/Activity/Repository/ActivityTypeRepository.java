package com.example.todoapp.Activity.Repository;

import com.example.todoapp.Activity.Models.ActivityType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("type")
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType,Long> {
    @Query("SELECT a FROM ActivityType a WHERE a.ActivityId = ?1")
    Optional<ActivityType> findByActivityId(Long aLong);
}
