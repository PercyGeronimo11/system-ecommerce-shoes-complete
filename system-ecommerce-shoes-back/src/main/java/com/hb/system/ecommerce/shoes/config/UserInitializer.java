package com.hb.system.ecommerce.shoes.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.hb.system.ecommerce.shoes.entity.User;
import com.hb.system.ecommerce.shoes.entity.Role;
import com.hb.system.ecommerce.shoes.repositories.RolRepository;
import com.hb.system.ecommerce.shoes.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserInitializer {
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    @Bean
    @Transactional
    public CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
            if (!userRepository.findByUsername("admin@service.com").isPresent()) {
                User user = new User();
                Role role = rolRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));
                user.setName("Administrador");
                user.setUsername("admin@service.com");
                user.setPassword(passwordEncoder.encode("12345678"));
                user.setRegisterDate(LocalDateTime.now());
                user.setStatus(true);
                user.setRole(role);
                userRepository.save(user);
            }
        };
    }
}
