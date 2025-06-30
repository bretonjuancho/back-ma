package com.example.tp.controllers;

import com.example.tp.service.AdministradorService;
import com.example.tp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService service;
    @Autowired
    private AdministradorService administradorService;

    /*@PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request){
        final TokenResponse token = service.register(request);
        System.out.println(token);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }*/

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenResponse> autenticate(@RequestBody final LoginRequest request){
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/administrador")
    public ResponseEntity<?> crearAdministrador(@RequestBody final RegisterRequest request) {
        try {
            if (!administradorService.existeAdmin(request.dni())) {
                final TokenResponse token =service.registerAdmin(request);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El administrador  ya existe.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


  /*  @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader){
        return service.refreshToken(authHeader);
    }*/


}
