package com.example.worldline_test.repository;

import com.example.worldline_test.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}
