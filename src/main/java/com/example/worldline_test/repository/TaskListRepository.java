package com.example.worldline_test.repository;

import com.example.worldline_test.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {}
