package com.hb.system.ecommerce.shoes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LotDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", referencedColumnName = "id")
    @JsonIgnore
    private Lot lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mat_id", referencedColumnName = "id")
    @JsonIgnore
    private Material material;

    private Double detPriceUnit;

    private Integer detQuantity;

    private Double detSubTotal;
}