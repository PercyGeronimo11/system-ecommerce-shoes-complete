package com.hb.system.ecommerce.shoes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.system.ecommerce.shoes.entity.Role;

public interface RolRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findById(int id);
    Optional<Role> findByName(String name);
}