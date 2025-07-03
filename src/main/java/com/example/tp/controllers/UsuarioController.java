package com.example.tp.controllers;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Usuario;
import com.example.tp.service.AuthService;
import com.example.tp.service.UsuarioService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioService_impl usuarioServiceImpl;
    @Autowired
    private AuthService service;


    /*@PostMapping("/auth/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> register(@RequestBody final RegisterRequest request){
        try{
            final TokenResponse token = service.register(request);
            return ResponseEntity.ok(token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }*/

    @PostMapping("/usuario/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> register(@RequestBody final RegisterRequest request){
        try {
            UsuarioDTO usuarioDTO = new UsuarioDTO(request.dni(), request.nombre(), request.apellido(), request.email(), request.contrasenia());
            if (!usuarioServiceImpl.usuarioExiste(usuarioDTO)) {
                if (usuarioServiceImpl.datosUsuarioValido(usuarioDTO)) {
                    final TokenResponse token = service.register(request);
                    return ResponseEntity.ok(token);
                }
            } return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario con el documento " + request.dni() + " ya existe.");
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping("/usuario/modificar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> modificarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        try{
            final Usuario usuario = usuarioServiceImpl.modificarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/obtener")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> obtenerUsuario(@RequestParam UsuarioDTO usuarioDTO){
        try{
            List<Usuario> usuarios = usuarioServiceImpl.obtenerUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }




}
