package com.example.tp;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.controllers.UsuarioController;
import com.example.tp.service.UsuarioService;
import com.example.tp.service.UsuarioService_impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UsuarioController.class)
public class UsuarioTest {
    @Autowired
    private UsuarioService usuarioService;

    UsuarioDTO usuarioDTO1 = new UsuarioDTO("45489343","Mateo","Gastaldi","mateogastaldi@gmail.com","21n32jn23");
    UsuarioDTO usuarioDTO2 = new UsuarioDTO("32a89343", "Juan1", "Juanceto","12212121","juannn___");
    @Test
    public void prueba(){

    }

}
