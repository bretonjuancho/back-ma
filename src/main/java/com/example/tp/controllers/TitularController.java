package com.example.tp.controllers;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Titular;
import com.example.tp.service.TitularService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;


@RestController

public class TitularController {
    @Autowired
    private TitularService_impl titularService;

    @PostMapping("/titular/crear")
    public ResponseEntity<?> crearTitular (@RequestBody TitularDTO titularDTO) {
       try{
           titularService.validarDatosTitular(titularDTO);
           Titular titular = titularService.crearTitular(titularDTO);
           return ResponseEntity.status(HttpStatus.CREATED).body(titular);
       }catch(Exception e){
           System.out.println(e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }

    }

    @GetMapping("/titular")
    public ResponseEntity<?> buscarTitular (@RequestParam(required = false) String documento,
                                            @RequestParam(required = false) String nombre,
                                            @RequestParam(required = false) String apellido){
        try{
            List<Titular> titulares = titularService.buscarTitulares(documento,nombre,apellido);
            return ResponseEntity.status(HttpStatus.OK).body(titulares);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
