package com.hb.system.ecommerce.shoes.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hb.system.ecommerce.shoes.dto.request.ProductReq;
import com.hb.system.ecommerce.shoes.dto.response.ProductListResp;
import com.hb.system.ecommerce.shoes.entity.Category;
import com.hb.system.ecommerce.shoes.entity.Product;
import com.hb.system.ecommerce.shoes.repositories.CategoryRepository;
import com.hb.system.ecommerce.shoes.repositories.ProductRepository;
import com.hb.system.ecommerce.shoes.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductListResp productListService(String search) {
        List<Product> productList = productRepository.findByProNameContaining(search);
        List<Product> filteredProductList = productList.stream()
        .filter(product -> product.getProStock() > 0)
        .collect(Collectors.toList());
        return ProductListResp.builder()
                .content(productList)
                .build();
    }

@Override
public ProductListResp productsByCategory(int idcategory) {
    Optional<Category> categoryOptional = categoryRepository.findById(idcategory);
    if (!categoryOptional.isPresent()) {
        throw new RuntimeException("Categoría no encontrada");
    }
    List<Product> productList = productRepository.findByCategory(categoryOptional.get());
    List<Product> filteredProductList = productList.stream()
        .filter(product -> product.getProStock() > 0)
        .collect(Collectors.toList());
    return ProductListResp.builder()
        .content(productList)
        .build();
}

    @Override
    public Product productStoreService(ProductReq productReq, MultipartFile file) throws IOException {
        try {
            Product product = new Product();
            product.setProName(productReq.getProName());
            product.setProDescription(productReq.getProDescription());
            Optional<Category> categoryOptional = categoryRepository.findById(productReq.getCatId());
            if (categoryOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
            } else {
                throw new RuntimeException("Categoría no encontrada");
            }
            product.setProUnitPrice(productReq.getProUnitPrice());
            product.setProSizePlatform(productReq.getProSizePlatform());
            product.setProSizeTaco(productReq.getProSizeTaco());
            product.setProSize(productReq.getProSize());
            product.setProColor(productReq.getProColor());
            product.setProStock(0);
            product.setProStatus(true);
            product.setProUrlImage(saveFile(file));
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error in store product ", e);
        }
    }

    @Override
    public Product productEditService(int id,ProductReq productEditReq, MultipartFile file){
            Optional<Product> productFind=productRepository.findById(id);
            productFind.get().setProName(productEditReq.getProName());
            productFind.get().setProDescription(productEditReq.getProDescription());
            Optional<Category> categoryOptional = categoryRepository.findById(productEditReq.getCatId());
            if (categoryOptional.isPresent()) {
                productFind.get().setCategory(categoryOptional.get());
            } else {
                throw new RuntimeException("Categoría no encontrada");
            }
            productFind.get().setProUnitPrice(productEditReq.getProUnitPrice());
            productFind.get().setProSizePlatform(productEditReq.getProSizePlatform());
            productFind.get().setProSizeTaco(productEditReq.getProSizeTaco());
            if (file != null && !file.isEmpty()) {
                productFind.get().setProUrlImage(saveFile(file));
            }
            return productRepository.save(productFind.get());
    }

    @Override
    public Product productGetService(int id) {
        Optional<Product> productFind=productRepository.findById(id);
        if(productFind.isPresent()){
            return productFind.get();
        }else{
            return null;
        }
    }

    private void deleteFile(String fileName) {
        try {
            String uploadDir = "uploads";
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Error al eliminar el archivo: " + ex.getMessage());
        }
    }

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    private String saveFile(MultipartFile archivo) {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }
            String fileName = archivo.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "http://157.230.191.218:8080/product/images/" + fileName;
            return fileUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar el archivo: " + ex.getMessage());
        }
    }
}
