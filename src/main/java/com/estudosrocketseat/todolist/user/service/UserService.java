package com.estudosrocketseat.todolist.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

        var password = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(password);
        return repository.save(user);
    }

    public void verificaUsername(String username) {
        var user = repository.findByUsername(username);

        if(user != null) {
            throw new RuntimeException("Username já cadastrado na base de dados.");
        }
    }


}
