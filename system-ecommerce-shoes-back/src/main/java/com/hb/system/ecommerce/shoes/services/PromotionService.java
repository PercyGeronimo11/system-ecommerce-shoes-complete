package com.hb.system.ecommerce.shoes.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import com.hb.system.ecommerce.shoes.entity.Promotion;
import com.hb.system.ecommerce.shoes.repositories.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
@Service
@RequiredArgsConstructor
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    public List<Promotion> listAll() {
        return promotionRepository.findAllActivePromotions();
    }

    public Promotion getById(int id) {
        return promotionRepository.findById(id);
    }

    public Promotion save(Promotion promotionRequest, MultipartFile file) throws IOException {
        try {
             Promotion promotion=new Promotion();
             promotion.setPromPercentage(promotionRequest.getPromPercentage());
             promotion.setPromStartdate(promotionRequest.getPromStartdate());
             promotion.setPromEnddate(promotionRequest.getPromEnddate());
             promotion.setPromDescription(promotionRequest.getPromDescription());
             promotion.setPromStatus(true);
             promotion.setPromUrlImage(saveFile(file));
             return promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the promotion", e);
        }
    }

    public Promotion update(int id, Promotion promotionRequest, MultipartFile file) throws IOException {
        if (promotionRepository.existsById(id)) {
            promotionRequest.setId(id);
            if (file != null && !file.isEmpty()) {
                promotionRequest.setPromUrlImage(saveFile(file));
            }
            return promotionRepository.save(promotionRequest);
        } else {
            throw new IllegalArgumentException("Promotion not found with id: " + id);
        }
    }

    public void delete(int id) {
        Promotion promotion = promotionRepository.findById(id);
        if (promotion != null) {
            promotion.setPromStatus(false);
            promotionRepository.save(promotion);
        } else {
            throw new IllegalArgumentException("Promotion not found with id: " + id);
        }
    }

    @Value("${image.upload.directory}")
    private String uploadDir;
    private String saveFile(MultipartFile archivo) {
        try {
            String uploadDir = "uploads";
            Path uploadPath = Paths.get(uploadDir);
            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }
            String fileName = archivo.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "http://127.0.0.1:8080/product/images/" + fileName;

            return fileUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar el archivo: " + ex.getMessage());
        }
    }
}
