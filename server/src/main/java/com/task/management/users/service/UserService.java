package com.task.management.users.service;

import com.task.management.users.model.User;
import com.task.management.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean checkUserNameExists(String username){
        Optional<User> user = userRepository.findByUserName(username);
        return user.isEmpty();
    }

    public boolean checkEmailExists(String userEmail){
        return userRepository.existsByUserEmail(userEmail);
    }
}
