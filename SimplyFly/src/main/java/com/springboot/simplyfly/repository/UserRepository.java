package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("""
            select u from User u
            where u.appUser.username = ?1
            """)
    User findByUsername(String username);
}