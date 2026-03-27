package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}