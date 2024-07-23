package com.hb.system.ecommerce.shoes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hb.system.ecommerce.shoes.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    Optional<Customer> findByCustDni(String dni);
    Optional<Customer> findByCustEmail(String email);
    Optional<Customer>findByCustEmailAndCustPassword(String custEmail, String custPassword);
    Optional<Customer> findById(int id);
    List<Customer> findByCustStatus(boolean custStatus);
}
