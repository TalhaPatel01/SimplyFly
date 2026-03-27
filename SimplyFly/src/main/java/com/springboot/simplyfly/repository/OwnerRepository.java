package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
}