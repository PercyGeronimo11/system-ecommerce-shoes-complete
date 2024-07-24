package com.hb.system.ecommerce.shoes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hb.system.ecommerce.shoes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

    Optional<Category> findById(int id);
    List<Category> findByCatStatus(boolean catStatus);
    @Query("SELECT c FROM Category c WHERE c.catStatus = true")
    List<Category> findAllActiveCategories();
}
