package com.hb.system.ecommerce.shoes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.hb.system.ecommerce.shoes.entity.Role;
import com.hb.system.ecommerce.shoes.repositories.RolRepository;

@Configuration
public class RolInitializer {
    @Bean
    @Transactional
    public CommandLineRunner initRoles(RolRepository roleRepository) {
        return args -> {
            if (!roleRepository.findByName("Usuario").isPresent()) {
                Role userRole = new Role();
                userRole.setName("Usuario");
                userRole.setStatus(true);
                roleRepository.save(userRole);
            }

            if (!roleRepository.findByName("Administrador").isPresent()) {
                Role adminRole = new Role();
                adminRole.setName("Administrador");
                adminRole.setStatus(true);
                roleRepository.save(adminRole);
            }

            if (!roleRepository.findByName("Cliente").isPresent()) {
                Role clienteRole = new Role();
                clienteRole.setName("Cliente");
                clienteRole.setStatus(true);
                roleRepository.save(clienteRole);
            }
        };
    }
}
