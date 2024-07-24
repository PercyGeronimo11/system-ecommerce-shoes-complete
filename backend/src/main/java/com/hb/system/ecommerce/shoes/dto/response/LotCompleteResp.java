package com.hb.system.ecommerce.shoes.dto.response;

import com.hb.system.ecommerce.shoes.entity.Product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LotCompleteResp {
    private int id;
    private Product product;
    private Double lotTotalCost;
    private Integer lotQuantityProducts;
    private List<LotDetailResp> lotDetails;
}
