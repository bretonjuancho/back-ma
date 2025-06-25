package com.example.tp.controllers;

import com.example.tp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request){
        final TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenResponse> autenticate(@RequestBody final LoginRequest request){
        System.out.println(request.dni());
        System.out.println(request.password());
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

  /*  @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader){
        return service.refreshToken(authHeader);
    }*/


}
