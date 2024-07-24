package com.hb.system.ecommerce.shoes.repositories;

import com.hb.system.ecommerce.shoes.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LotRepository extends JpaRepository<Lot,Integer> {
    List<Lot> findByLotStatus(Boolean status);
    Optional<Lot> findById(Integer id);
}
