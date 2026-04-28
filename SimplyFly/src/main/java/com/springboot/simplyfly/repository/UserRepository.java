package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("""
            select u from User u
            where u.appUser.username = ?1
            """)
    User findByUsername(String username);

    @Query("""
            select u from User u
            where u.appUser.role = "USER"
            """)
    Page<User> findAllUsers(Pageable pageable);
}