package com.hb.system.ecommerce.shoes.auth;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hb.system.ecommerce.shoes.entity.Role;
import com.hb.system.ecommerce.shoes.entity.User;
import com.hb.system.ecommerce.shoes.repositories.UserRepository;
import com.hb.system.ecommerce.shoes.repositories.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RolRepository roleRepository;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails=userRepository.findByUsername(request.getEmail()).orElseThrow();
        User user=userRepository.findByUsername(request.getEmail()).orElseThrow();
        return AuthResponse.builder()
            .token(jwtService.getToken(userDetails))
            .username(user.getName())
            .rol(user.getRole().getName())
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Role role = roleRepository.findById(request.getRole()).orElseThrow(()
                -> new IllegalArgumentException("Default role not found"));
        User user = User.builder()
            .name(request.getUsername())
            .username(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .registerDate(LocalDateTime.now())
            .status(true)
            .build();
        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .username(user.getName())
            .rol(user.getRole().getName())
            .build();
    }

}
