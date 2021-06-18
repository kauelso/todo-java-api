package com.example.todoapp.Activity.Repository.Database;

import com.example.todoapp.Activity.Models.Activity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("activity")
@Repository
public interface ActivityRepository extends JpaRepository<Activity,String> {
}
