package com.hb.system.ecommerce.shoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.entity.Category;
import com.hb.system.ecommerce.shoes.services.CategoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> list() {
        List<Category> categories = categoryService.listAll();
        ApiResponse<List<Category>> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de categorias exitosamente");
        response.setData(categories);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> create(@RequestBody Category categoriaRequest){
        Category categoria= categoryService.save(categoriaRequest);
        ApiResponse<Category> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Se registró una categoria exitosamente");
        response.setData(categoria);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> edit(@PathVariable int id, @RequestBody Category categoriaRequest){
        Category categoria= categoryService.update(id,categoriaRequest);
        ApiResponse<Category> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("La categoria se actualizó exitosamente");
        response.setData(categoria);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getById(@PathVariable int id){
        Category categoria= categoryService.getById(id);
        ApiResponse<Category> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle de la categoria recuperado exitossamente");
        response.setData(categoria);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> delete(@PathVariable int id){
        categoryService.delete(id);
        ApiResponse<Category> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Categoria eliminada exitosamente");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
