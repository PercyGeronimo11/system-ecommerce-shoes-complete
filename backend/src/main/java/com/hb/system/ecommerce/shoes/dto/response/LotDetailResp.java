package com.hb.system.ecommerce.shoes.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LotDetailResp {
    private int id;
    private String name;
    private Double detPriceUnit;
    private Integer detQuantity;
    private Double detSubTotal;
}
