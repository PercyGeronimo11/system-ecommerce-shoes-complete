package com.hb.system.ecommerce.shoes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hb.system.ecommerce.shoes.entity.Material;
import com.hb.system.ecommerce.shoes.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService implements ApiService<Material>{
    @Autowired
    private MaterialRepository resourceRepository;
    
    public List<Material> listAll() {
        return resourceRepository.findMaterialsByStatus(true);
    }

    public Material getById(int id) {
        Optional<Material> material=resourceRepository.findById(id);
        if(material.isPresent()){
            return material.get();
        }else{
            throw new RuntimeException("No se encontro material");
        }
    }

    public Material save(Material resource) {
        resource.setStatus(true);
        return resourceRepository.save(resource);
    }

    public Material update(int id, Material resource){
        if (resourceRepository.existsById(id)) {
            resource.setId(id);
            resource.setStatus(true);
            return resourceRepository.save(resource);
        } else
            return null;
    }

    public void delete(int id) {
        Optional<Material> material=resourceRepository.findById(id);
        if(material.isPresent()){
            material.get().setStatus(false);
            resourceRepository.save(material.get());
        }else{
            throw new RuntimeException("No se encontro material");
        }
    }
}
