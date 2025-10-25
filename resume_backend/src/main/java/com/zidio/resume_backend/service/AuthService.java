package com.zidio.resume_backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.zidio.resume_backend.dto.AuthRequest;
import com.zidio.resume_backend.dto.AuthResponse;
import com.zidio.resume_backend.entity.User;
import com.zidio.resume_backend.repository.UserRepository;
import com.zidio.resume_backend.util.JwtUtil;
import com.zidio.resume_backend.exception.InvalidCredentialsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // ✅ User registration
    public AuthResponse register(User user) {
        user.setPassword(pwEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole());
        return new AuthResponse(token, saved.getRole());
    }

    public AuthResponse login(AuthRequest req) {
        User u = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        if (!pwEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());
        return new AuthResponse(token, u.getRole());
    }
}