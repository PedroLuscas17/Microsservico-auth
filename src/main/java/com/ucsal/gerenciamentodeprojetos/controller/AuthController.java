package com.ucsal.gerenciamentodeprojetos.controller;

import com.ucsal.gerenciamentodeprojetos.dto.AuthRequest;
import com.ucsal.gerenciamentodeprojetos.entity.User;
import com.ucsal.gerenciamentodeprojetos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request.getUsername(), request.getPassword()));
    }
}
