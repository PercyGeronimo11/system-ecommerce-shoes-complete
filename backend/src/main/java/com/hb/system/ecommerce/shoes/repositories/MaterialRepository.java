package com.hb.system.ecommerce.shoes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.system.ecommerce.shoes.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer>{
    Optional<Material> findById(int id);
    Optional<Material> findByName(String name);
    List<Material> findMaterialsByStatus(boolean status);
}
