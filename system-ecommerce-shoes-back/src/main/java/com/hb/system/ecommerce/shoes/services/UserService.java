package com.hb.system.ecommerce.shoes.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.system.ecommerce.shoes.dto.request.UserEditReq;
import com.hb.system.ecommerce.shoes.entity.Role;
import com.hb.system.ecommerce.shoes.entity.User;
import com.hb.system.ecommerce.shoes.repositories.RolRepository;
import com.hb.system.ecommerce.shoes.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final RolRepository roleRepository;

    public List<User> listAll() {
        return userRepository.findUsersByStatus(true);
    }

    public User getById(int id) {
        return userRepository.findById(id);
    }



    public void delete(int id) {
        User user = userRepository.findById(id);
        user.setStatus(false);
        userRepository.save(user);
    }

    public User update(int id, UserEditReq resource) {
        Role role = roleRepository.findById(resource.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id);
            user.setName(resource.getUsername());
            user.setUsername(resource.getEmail());
            user.setRole(role);
            if (resource.getPassword() != "") {
                user.setPassword(resource.getPassword());
            }
            return userRepository.save(user);
        } else
            return null;
    }
}