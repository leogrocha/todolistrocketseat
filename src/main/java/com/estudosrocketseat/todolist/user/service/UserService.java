package com.estudosrocketseat.todolist.user.service;

import com.estudosrocketseat.todolist.user.domain.User;
import com.estudosrocketseat.todolist.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    @Transactional
    public User save(User user) {
        verificaUsername(user.getUsername());
        return repository.save(user);
    }

    public void verificaUsername(String username) {
        if(repository.existsByUsername(username)) {
            throw new RuntimeException("Username já cadastrado na base de dados.");
        }
    }


}
