package com.example.todoapp.Activity.Repository;

import com.example.todoapp.Activity.Models.Activity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("activity")
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
