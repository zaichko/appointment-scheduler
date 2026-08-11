package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
