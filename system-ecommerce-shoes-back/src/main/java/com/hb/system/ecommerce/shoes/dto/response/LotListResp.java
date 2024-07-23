package com.hb.system.ecommerce.shoes.dto.response;

import com.hb.system.ecommerce.shoes.entity.Lot;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LotListResp {
    private List<Lot> content;
}
