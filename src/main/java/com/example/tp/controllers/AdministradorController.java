package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.service.AdministradorService;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/administrador/nuevoUsuario")
    public String nuevoUsuario() {
        return "administrador/nueva";//esta seria la parte de mostrar el formulario de la licencia
        //habria q profundizar en esto mas adelante
    }

    @PostMapping("/administrador/guardarUsuario")
    public String guardarUsuario(UsuarioDTO usuario) {
        administradorService.guardarUsuario(usuario);
        return "redirect:/licencia";
    }
}
