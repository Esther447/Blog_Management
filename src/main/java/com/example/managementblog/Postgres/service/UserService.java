package com.example.managementblog.Postgres.service;

import com.example.managementblog.Postgres.model.User;
import com.example.managementblog.Postgres.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())) {
            return false;
        }

        userRepository.save(user);
        return true;
    }
}
