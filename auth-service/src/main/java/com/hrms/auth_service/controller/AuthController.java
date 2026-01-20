package com.hrms.auth_service.controller;

import com.hrms.auth_service.dto.AuthResponse;
import com.hrms.auth_service.dto.LoginRequest;
import com.hrms.auth_service.dto.RefreshTokenRequest;
import com.hrms.auth_service.dto.RegisterRequest;
import com.hrms.auth_service.entity.Role;
import com.hrms.auth_service.repository.RoleRepository;
import com.hrms.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RoleRepository roleRepository;

    public AuthController(AuthService authService, RoleRepository roleRepository) {
        this.authService = authService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestParam String name){
        Role role=new Role();
        role.setName(name);
        roleRepository.save(role);
        return ResponseEntity.ok("Role added successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.logout(refreshTokenRequest.getRefreshToken()));
    }
}
