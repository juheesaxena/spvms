package com.spvms.spvms.service;

import com.spvms.spvms.dto.AuthRequest;
import com.spvms.spvms.models.Role;
import com.spvms.spvms.models.User;
import com.spvms.spvms.repository.UserRepository;
import com.spvms.spvms.security.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(AuthRequest req) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                req.getUsername(),
                                req.getPassword()
                        )
                );

        User user = userRepository
                .findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }

    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.VENDOR);
        }

        return userRepository.save(user);
    }
}
