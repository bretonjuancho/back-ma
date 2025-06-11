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

    @PostMapping("")//hay que agregar la url
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO usuarioDto){
        try {
            if (!usuarioServiceImpl.usuarioExiste(usuarioDto)) {
                if (usuarioServiceImpl.datosUsuarioValido(usuarioDto)) {
                    usuarioServiceImpl.crearUsuario(usuarioDto);
                    return new ResponseEntity(HttpStatus.CREATED);
                }
            } return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
