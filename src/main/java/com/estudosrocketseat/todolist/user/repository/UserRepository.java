package com.estudosrocketseat.todolist.user.repository;

import com.estudosrocketseat.todolist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
}
