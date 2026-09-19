package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateUserRequest;
import com.zaichko.scheduler.dto.request.UpdateUserRequest;
import com.zaichko.scheduler.dto.response.UserResponse;
import com.zaichko.scheduler.entity.User;
import com.zaichko.scheduler.enums.Role;
import com.zaichko.scheduler.exception.ConflictException;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.mapper.UserMapper;
import com.zaichko.scheduler.repository.AppointmentRepository;
import com.zaichko.scheduler.repository.UserRepository;
import com.zaichko.scheduler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper :: toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new ConflictException("User with this email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.email(),
                encodedPassword,
                request.firstName(),
                request.lastName(),
                Role.PATIENT);
        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (request.email() != null && !request.email().isBlank()
        && !Objects.equals(request.email(), user.getEmail())){
            if (userRepository.existsByEmail(request.email())){
                throw new ConflictException("User with this email already exists.");
            }
            user.setEmail(request.email());
        }
        if (request.password() != null && !request.password().isBlank()
        && !passwordEncoder.matches(request.password(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.firstName() != null && !request.firstName().isBlank()
        && !Objects.equals(request.firstName(), user.getFirstName())){
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null && !request.lastName().isBlank()
        && !Objects.equals(request.lastName(), user.getLastName())){
            user.setLastName(request.lastName());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public void deleteUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getRole() == Role.PATIENT && appointmentRepository.existsByPatientId(id)){
            throw new ConflictException("Patient has appointments.");
        }

        userRepository.delete(user);
    }
}
