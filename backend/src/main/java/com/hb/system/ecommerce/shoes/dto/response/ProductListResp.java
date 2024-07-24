package com.hb.system.ecommerce.shoes.dto.response;

import com.hb.system.ecommerce.shoes.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductListResp {
    private List<Product> content;
}
