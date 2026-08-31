package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateUserRequest;
import com.zaichko.scheduler.dto.request.UpdateUserRequest;
import com.zaichko.scheduler.dto.response.UserResponse;
import com.zaichko.scheduler.entity.User;
import com.zaichko.scheduler.enums.Role;
import com.zaichko.scheduler.exception.EmailAlreadyExistsException;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.exception.UserHasAppointmentsException;
import com.zaichko.scheduler.mapper.UserMapper;
import com.zaichko.scheduler.repository.AppointmentRepository;
import com.zaichko.scheduler.repository.UserRepository;
import com.zaichko.scheduler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<UserResponse> userResponses = new ArrayList<>();
        for (User user : users){
            userResponses.add(userMapper.toResponse(user));
        }

        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("User with this email already exists.");
        }

        User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), Role.PATIENT);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (!request.getEmail().isBlank()){
            user.setEmail(request.getEmail());
        }

        if (!request.getPassword().isBlank()){
            user.setPassword(request.getPassword());
        }

        if (!request.getFirstName().isBlank()){
            user.setFirstName(request.getFirstName());
        }

        if (!request.getLastName().isBlank()){
            user.setLastName(request.getLastName());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public void deleteUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getRole() == Role.PATIENT && appointmentRepository.existsByPatientId(user.getId())){
            throw new UserHasAppointmentsException("Patient has appointments.");
        }

        userRepository.delete(user);
    }
}
