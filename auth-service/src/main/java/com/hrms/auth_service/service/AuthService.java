package com.hrms.auth_service.service;

import com.hrms.auth_service.dto.AuthResponse;
import com.hrms.auth_service.dto.LoginRequest;
import com.hrms.auth_service.dto.RegisterRequest;
import com.hrms.auth_service.entity.RefreshToken;
import com.hrms.auth_service.entity.Role;
import com.hrms.auth_service.entity.User;
import com.hrms.auth_service.repository.RoleRepository;
import com.hrms.auth_service.repository.UserRepository;
import com.hrms.auth_service.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = request.getRoles().stream()
                .map(r -> roleRepository.findByName(r)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        UserDetails userDetails =userDetailsService.loadUserByUsername(request.getUsername());

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        String accessToken = jwtUtil.generateAccessToken(userDetails,user.getId());
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    public AuthResponse refreshToken(String refreshTokenValue) {
        RefreshToken refreshToken=refreshTokenService.findByToken(refreshTokenValue);
        RefreshToken newRefreshToken=refreshTokenService.verifyAndRotate(refreshToken);
        User user = refreshToken.getUser();
        UserDetails userDetails= userDetailsService.loadUserByUsername(user.getUsername());
        String newAccessToken= jwtUtil.generateAccessToken(userDetails,user.getId());
        return new AuthResponse(newAccessToken,newRefreshToken.getToken());
    }

    public String logout(String refreshToken) {
        return refreshTokenService.logout(refreshToken);
    }
}
