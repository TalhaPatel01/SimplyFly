package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    @Query("""
            select u from AppUser u
            where u.username = ?1
            """)
    AppUser getAppUserByUsername(String username);

    @Query("""
           select count(au.id)
           from AppUser au
           where au.role = "USER"
           """)
    long findTotalUsers();

    @Query("""
           select count(au.id)
           from AppUser au
           where au.role = "OWNER"
           """)
    long findTotalOwners();
}