package com.example.tp.controllers;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Titular;
import com.example.tp.service.TitularService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class TitularController {
    @Autowired
    private TitularService_impl titularService;

    @PostMapping("/titular/crear")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> crearTitular (@RequestBody TitularDTO titularDTO) {
        
        try{
           if(titularService.validarDatosTitular(titularDTO)){
                Titular titular = titularService.crearTitular(titularDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(titular);
           }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el titular, los datos son invalidos.");
           }
           
       }catch(Exception e){
           System.out.println(e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }

    }

    @GetMapping("/titular")
    @CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/titular/modificar")
    public ResponseEntity<?> modificarTitular(@RequestBody TitularDTO titularDTO) {
        try{
            if(titularService.validarDatosTitular(titularDTO)) {
                Titular titular = titularService.modificarTitular(titularDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(titularDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar los datos del titular.");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    


}
