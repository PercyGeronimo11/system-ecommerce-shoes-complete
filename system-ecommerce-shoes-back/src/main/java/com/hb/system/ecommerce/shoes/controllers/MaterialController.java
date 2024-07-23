package com.hb.system.ecommerce.shoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.entity.Material;
import com.hb.system.ecommerce.shoes.services.MaterialService;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Material>>> list() {
        List<Material> materials = materialService.listAll();
        ApiResponse<List<Material>> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de productos exitosamente");
        response.setData(materials);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Material>> create(@RequestBody Material materialRequest){
        Material material= materialService.save(materialRequest);
        ApiResponse<Material> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Se registró el material exitosamente");
        response.setData(material);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Material>> edit(@PathVariable int id, @RequestBody Material materialRequest){
        Material material= materialService.update(id,materialRequest);
        material.setStatus(true);
        ApiResponse<Material> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("El material se actualizó exitosamente");
        response.setData(material);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Material>> getById(@PathVariable int id){
        Material material= materialService.getById(id);
        ApiResponse<Material> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle del producto recuperado exitossamente");
        response.setData(material);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Material>> delete(@PathVariable int id){
        materialService.delete(id);
        ApiResponse<Material> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Producto eliminado exitossamente");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
