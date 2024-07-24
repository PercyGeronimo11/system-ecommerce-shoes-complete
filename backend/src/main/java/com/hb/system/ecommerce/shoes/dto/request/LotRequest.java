package com.hb.system.ecommerce.shoes.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class LotRequest {
    private int productId;
    private List<LotDetailRequest> lotDetail;
    private Integer lotQuantityProducts;
    private Double lotTotalCost;
}
