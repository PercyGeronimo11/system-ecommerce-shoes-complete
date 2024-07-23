package com.hb.system.ecommerce.shoes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hb.system.ecommerce.shoes.entity.Product;
import com.hb.system.ecommerce.shoes.entity.Category;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProNameContaining(String proName);
        
    List<Product> findByCategory(Category category);
    Optional<Product> findById(int id);
}
