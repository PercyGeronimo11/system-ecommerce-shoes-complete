package com.hb.system.ecommerce.shoes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private Category category;

    private String proName;

    private String proDescription;

    private Double proUnitPrice;

    private BigDecimal proUnitCost;

    private String proSize;

    private String proSizePlatform;

    private String proSizeTaco;

    private String proUrlImage;

    private String proColor;

    private int proStock;

    private Boolean proStatus;
}
