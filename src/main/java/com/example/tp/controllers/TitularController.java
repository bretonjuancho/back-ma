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

    @PostMapping("titular/crear")
    public ResponseEntity<String> crearTitular (@RequestBody TitularDTO titularDTO) {
       try{
           titularService.validarDatosTitular(titularDTO);
           Titular titular = titularService.crearTitular(titularDTO);
           return new ResponseEntity<>(HttpStatus.CREATED);
       }catch(Exception e){
           System.out.println(e.getMessage());
           return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }


}
