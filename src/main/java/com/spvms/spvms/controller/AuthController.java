package com.spvms.spvms.controller;
import org.springframework.security.core.Authentication;

import com.spvms.spvms.models.User;
import com.spvms.spvms.service.AuthService;
import com.spvms.spvms.dto.AuthRequest;
import com.spvms.spvms.dto.AuthResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User u = authService.register(user);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // JWT is stateless → client deletes token
        return ResponseEntity.ok("Logged out successfully");
    }
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> whoAmI(Authentication authentication) {

        Map<String, Object> info = new HashMap<>();
        info.put("username", authentication.getName());
        info.put("roles", authentication.getAuthorities());

        return info;
    }

}
