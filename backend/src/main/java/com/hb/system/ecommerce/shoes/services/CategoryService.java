package com.hb.system.ecommerce.shoes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hb.system.ecommerce.shoes.entity.Category;
import com.hb.system.ecommerce.shoes.repositories.CategoryRepository;

@Service
public class CategoryService {
   
    @Autowired
    private  CategoryRepository categoryRepository;

    public List<Category> listAll() {
     
        return categoryRepository.findAllActiveCategories();
    }

    public Category getById(int id) {
        Optional<Category> categoryFind=categoryRepository.findById(id);
      
        return categoryFind.get();

    }

 
  public Category save(Category resource) {
      resource.setCatStatus(true);
      return categoryRepository.save(resource);
}

   public Category update(int id, Category resource){
    if (categoryRepository.existsById(id)) {
        resource.setId(id);
        return categoryRepository.save(resource);
    } else
        return null;
}
// Método para eliminar (desactivar) una catg
public void delete(int id) {
    Optional<Category> categoria = categoryRepository.findById(id);
    categoria.get().setCatStatus(false);
    categoryRepository.save(categoria.get());

}
}
