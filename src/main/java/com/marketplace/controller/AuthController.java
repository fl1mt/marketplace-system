package com.marketplace.controller;

import com.marketplace.dto.UserAuthRequestDTO;
import com.marketplace.dto.UserAuthResponseDTO;
import com.marketplace.dto.UserRegisterRequestDTO;
import com.marketplace.dto.UserResponseDTO;
import com.marketplace.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDTO> login(@RequestBody UserAuthRequestDTO request) {
        UserAuthResponseDTO response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

}
