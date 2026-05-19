package com.bladimircrisosto.auth_service.service;

import com.bladimircrisosto.auth_service.config.JwtUtil;
import com.bladimircrisosto.auth_service.dto.AuthResponse;
import com.bladimircrisosto.auth_service.dto.LoginRequest;
import com.bladimircrisosto.auth_service.dto.RegisterRequest;
import com.bladimircrisosto.auth_service.entity.User;
import com.bladimircrisosto.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //Verifica que el email no exista, encripta el password y guarda el usuario
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getEmail(), user.getRole());
    }

    //Busca el usuario, verifica el password y devuelve el token
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getEmail(), user.getRole());
    }

    //Verifica si un token es válido, lo usarán los otros microservicios
    public boolean validateToken(String token) {
        return jwtUtil.isTokenValid(token);
    }
}