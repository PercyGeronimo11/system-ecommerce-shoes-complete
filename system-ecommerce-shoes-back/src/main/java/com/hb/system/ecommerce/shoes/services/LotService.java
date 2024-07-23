package com.hb.system.ecommerce.shoes.services;

import com.hb.system.ecommerce.shoes.dto.request.LotRequest;
import com.hb.system.ecommerce.shoes.dto.response.LotCompleteResp;
import com.hb.system.ecommerce.shoes.dto.response.LotListResp;
import com.hb.system.ecommerce.shoes.entity.Lot;

import java.io.IOException;


public interface LotService {
    LotListResp lotListService(String search);
    Lot lotStoreService(LotRequest lotRequest)throws IOException;
    Lot lotEditService(int id,LotRequest lotReq);
    LotCompleteResp lotGetService(int id);
}
