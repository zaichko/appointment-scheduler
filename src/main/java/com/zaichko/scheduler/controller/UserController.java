package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateUserRequest;
import com.zaichko.scheduler.dto.request.UpdateUserRequest;
import com.zaichko.scheduler.dto.response.UserResponse;
import com.zaichko.scheduler.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable @Positive Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @PutMapping
    public UserResponse updateUser(@Valid @RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable @Positive Long id){
        userService.deleteUserById(id);
    }
}
