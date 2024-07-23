package com.hb.system.ecommerce.shoes.controllers;
import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.dto.request.ProductReq;
import com.hb.system.ecommerce.shoes.dto.response.ProductListResp;
import com.hb.system.ecommerce.shoes.entity.Product;
import com.hb.system.ecommerce.shoes.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = { "/list" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ProductListResp>> index(String search) {
        ProductListResp productListResp = productService.productListService(search);
        ApiResponse<ProductListResp> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de productos exitosamente");
        response.setData(productListResp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(
            value = "/store",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> create(
            ProductReq productReq,
            @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        Product product= productService.productStoreService(productReq, file);
        ApiResponse<Product> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Se a guardado el producto exitosamente");
        response.setData(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(
            value = { "/listaxcate/{idcate}" },
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ApiResponse<ProductListResp>> productsByCategory(@PathVariable("idcate") int idcate) {
            ProductListResp productListResp = productService.productsByCategory(idcate);
            ApiResponse<ProductListResp> response = new ApiResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Lista de productos de una categoría exitosamente");
            response.setData(productListResp);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    @PutMapping(
            value = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> editProduct(
            @PathVariable int id,
            ProductReq productEditReq,
            @RequestParam(name="file", required = false) MultipartFile file) {
        Product product = productService.productEditService(id, productEditReq, file);
        ApiResponse<Product> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("El producto ha sido editado exitosamente");
        response.setData(product);
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
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable int id) {
        Product product = productService.productGetService(id);
        ApiResponse<Product> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle del producto recuperado exitossamente");
        response.setData(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
