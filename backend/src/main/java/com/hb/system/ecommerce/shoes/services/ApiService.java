package com.hb.system.ecommerce.shoes.services;

import java.util.List;

public interface ApiService<Entity> {
    List<Entity> listAll();
    Entity getById(int id);
    Entity save(Entity resource);
    Entity update(int id, Entity resource);
    void delete(int id);
}
