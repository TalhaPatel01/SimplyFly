package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    @Query("""
            select u from AppUser u
            where u.username = ?1
            """)
    AppUser getAppUserByUsername(String username);
}