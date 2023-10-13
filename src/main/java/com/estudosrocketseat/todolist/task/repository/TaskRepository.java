package com.estudosrocketseat.todolist.task.repository;

import com.estudosrocketseat.todolist.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByidUser(UUID idUser);
}
