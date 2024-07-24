package com.hb.system.ecommerce.shoes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "id")
    private Product product;

    private Double lotTotalCost;

    private Integer lotQuantityProducts;

    private Boolean lotStatus;
}
