package com.example.tp.controllers;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Usuario;
import com.example.tp.service.UsuarioService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioService_impl usuarioServiceImpl;

    @PostMapping("/usuario/crear")//hay que agregar la url
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDto){
        try {
            if (!usuarioServiceImpl.usuarioExiste(usuarioDto)) {
                if (usuarioServiceImpl.datosUsuarioValido(usuarioDto)) {
                    Usuario u = usuarioServiceImpl.crearUsuario(usuarioDto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(u);
                }
            } return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario con el documento " + usuarioDto.getDni() + " ya existe.");
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
