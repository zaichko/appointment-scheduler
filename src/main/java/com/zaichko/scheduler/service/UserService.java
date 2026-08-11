package com.zaichko.scheduler.service;

import com.zaichko.scheduler.entity.User;
import com.zaichko.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User create(User user){
        return userRepository.save(user);
    }
}
