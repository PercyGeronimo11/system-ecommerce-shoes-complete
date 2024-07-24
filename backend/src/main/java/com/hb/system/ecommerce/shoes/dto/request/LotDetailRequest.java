package com.hb.system.ecommerce.shoes.dto.request;

import lombok.Data;

@Data
public class LotDetailRequest {
    private String name;
    private Double detPriceUnit;
    private Integer detQuantity;
    private Double detSubTotal;
}
