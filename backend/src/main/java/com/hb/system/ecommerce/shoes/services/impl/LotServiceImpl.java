package com.hb.system.ecommerce.shoes.services.impl;

import com.hb.system.ecommerce.shoes.dto.request.LotRequest;
import com.hb.system.ecommerce.shoes.dto.response.LotCompleteResp;
import com.hb.system.ecommerce.shoes.dto.response.LotDetailResp;
import com.hb.system.ecommerce.shoes.dto.response.LotListResp;
import com.hb.system.ecommerce.shoes.entity.*;
import com.hb.system.ecommerce.shoes.repositories.*;
import com.hb.system.ecommerce.shoes.services.LotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LotServiceImpl implements LotService {
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private LotDetailRepository lotDetailRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public LotListResp lotListService(String search) {
        List<Lot> lotList = lotRepository.findAll();
        return LotListResp.builder()
                .content(lotList)
                .build();
    }

    @Override
    @Transactional
    public Lot lotStoreService(LotRequest lotRequest) throws IOException {
        try {
            Lot lot = new Lot();
            Optional<Product> productFind = productRepository.findById(lotRequest.getProductId());
            if (productFind.isPresent()) {
                lot.setProduct(productFind.get());
            } else {
                throw new RuntimeException("No se encontró el producto");
            }

            lot.setLotTotalCost(lotRequest.getLotTotalCost());
            lot.setLotQuantityProducts(lotRequest.getLotQuantityProducts());
            lot.setLotStatus(true);

            Lot savedLot = lotRepository.save(lot);

            lotRequest.getLotDetail().forEach(detailRequest->{
                LotDetail detail = new LotDetail();
                Optional<Material> materialFind = materialRepository.findByName(detailRequest.getName());
                if (materialFind.isPresent()) {
                    detail.setMaterial(materialFind.get());
                    log.info("material:"+ materialFind.get());
                } else {
                    throw new RuntimeException("No se encontró el material");
                }
                detail.setDetQuantity(detailRequest.getDetQuantity());
                detail.setDetPriceUnit(detailRequest.getDetPriceUnit());
                detail.setDetSubTotal(detailRequest.getDetSubTotal());
                detail.setLot(savedLot);
                lotDetailRepository.save(detail);
            });
            productFind.get().setProStock(lot.getLotQuantityProducts()+productFind.get().getProStock());
            BigDecimal lotTotalCost = new BigDecimal(lot.getLotTotalCost());
            BigDecimal lotQuantityProducts = new BigDecimal(lot.getLotQuantityProducts());
            BigDecimal result = lotTotalCost.divide(lotQuantityProducts, 2, RoundingMode.HALF_UP);
            productFind.get().setProUnitCost(result);
            return savedLot;
        } catch (Exception e) {
            throw new RuntimeException("Error: No se pudo guarder el lote", e);
        }
    }


    @Override
    @Transactional
    public Lot lotEditService(int id,LotRequest lotRequest){
        Lot existingLot = lotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lot not found with id: " + id));
        lotDetailRepository.deleteByLot_Id(existingLot.getId());
        existingLot.getProduct().setProStock(existingLot.getProduct().getProStock()-existingLot.getLotQuantityProducts());
        existingLot.setLotTotalCost(lotRequest.getLotTotalCost());
        existingLot.setLotQuantityProducts(lotRequest.getLotQuantityProducts());
        Lot savedLot = lotRepository.save(existingLot);

        lotRequest.getLotDetail().forEach(detailRequest->{
            LotDetail detail = new LotDetail();
            Optional<Material> materialFind = materialRepository.findByName(detailRequest.getName());
            if (materialFind.isPresent()) {
                detail.setMaterial(materialFind.get());
                log.info("material:"+ materialFind.get());
            } else {
                throw new RuntimeException("No se encontró el material");
            }
            detail.setDetQuantity(detailRequest.getDetQuantity());
            detail.setDetPriceUnit(detailRequest.getDetPriceUnit());
            detail.setDetSubTotal(detailRequest.getDetSubTotal());
            detail.setLot(savedLot);
            lotDetailRepository.save(detail);
        });
        existingLot.getProduct().setProStock(existingLot.getProduct().getProStock()+lotRequest.getLotQuantityProducts());
        BigDecimal lotTotalCost = new BigDecimal(existingLot.getLotTotalCost());
        BigDecimal lotQuantityProducts = new BigDecimal(existingLot.getLotQuantityProducts());
        BigDecimal result = lotTotalCost.divide(lotQuantityProducts, 2, RoundingMode.HALF_UP);
        existingLot.getProduct().setProUnitCost(result);
        return lotRepository.save(existingLot);
    }

    @Override
    public LotCompleteResp lotGetService(int id) {
        Optional<Lot> lotFind=lotRepository.findById(id);
        List<LotDetail> lotDetails= lotDetailRepository.findAllByLot_id(id);

        List<LotDetailResp>  lotDetailRespList= new ArrayList<>();
        lotDetails.forEach(lotDetail -> {
            LotDetailResp lotDetailResp= LotDetailResp.builder()
                    .name(lotDetail.getMaterial().getName())
                    .detPriceUnit(lotDetail.getDetPriceUnit())
                    .detQuantity(lotDetail.getDetQuantity())
                    .detSubTotal(lotDetail.getDetSubTotal())
                    .build();
            lotDetailRespList.add(lotDetailResp);
        });

        LotCompleteResp lotCompleteResp= LotCompleteResp.builder()
                .product(lotFind.get().getProduct())
                .lotTotalCost(lotFind.get().getLotTotalCost())
                .lotQuantityProducts(lotFind.get().getLotQuantityProducts())
                .lotDetails(lotDetailRespList)
                .build();
        return lotCompleteResp;
    }
}
