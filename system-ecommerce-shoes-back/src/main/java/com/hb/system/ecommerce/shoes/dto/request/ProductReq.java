package com.hb.system.ecommerce.shoes.dto.request;

import lombok.Data;

@Data
public class ProductReq {
    private int catId;
    private String proName;
    private String proDescription;
    private String proSizePlatform;
    private String proSizeTaco;
    private Double proUnitPrice;
    private String proSize;
    private String proColor;
}
