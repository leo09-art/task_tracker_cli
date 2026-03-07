package com.example.task_tracker_cli.repository;

import com.example.task_tracker_cli.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
