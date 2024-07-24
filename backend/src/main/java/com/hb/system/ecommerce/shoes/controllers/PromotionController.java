package com.hb.system.ecommerce.shoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.entity.Promotion;
import com.hb.system.ecommerce.shoes.services.PromotionService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Permitir solicitudes desde cualquier origen
@RequestMapping("/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Promotion>>> list() {
        List<Promotion> promotions = promotionService.listAll();
        ApiResponse<List<Promotion>> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de promociones exitosamente");
        response.setData(promotions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Promotion>> create(@RequestPart("promotion") Promotion promotionRequest,
                @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        Promotion promotion = promotionService.save(promotionRequest, file);
        ApiResponse<Promotion> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Promoción registrada exitosamente");
        response.setData(promotion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Promotion>> edit(@PathVariable int id,
            @RequestPart("promotion") Promotion promotionRequest,
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        Promotion promotion = promotionService.update(id, promotionRequest, file);
        ApiResponse<Promotion> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Promoción actualizada exitosamente");
        response.setData(promotion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Promotion>> getById(@PathVariable int id) {
        Promotion promocion = promotionService.getById(id);
        ApiResponse<Promotion> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle de la promocion recuperado exitossamente");
        response.setData(promocion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Promotion>> delete(@PathVariable int id) {
        promotionService.delete(id);
        ApiResponse<Promotion> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Promocion eliminada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Value("${image.upload.directory}")
    private String uploadDir;
    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get(uploadDir).resolve(imageName);
        Resource imageResource;
        try {
            imageResource = new UrlResource(imagePath.toUri());
            if (imageResource.exists() || imageResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // O el tipo de imagen correcto
                        .body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}