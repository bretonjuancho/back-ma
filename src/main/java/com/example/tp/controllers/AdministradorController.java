package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import com.example.tp.service.AdministradorService;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;



    @PostMapping("/administrador/crear")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> crearAdministrador(@RequestBody Administrador administrador) {
        try {
            if (!administradorService.existeAdmin(administrador)) {
                administradorService.crearAdministrador(administrador);
                return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El administrador  ya existe.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
