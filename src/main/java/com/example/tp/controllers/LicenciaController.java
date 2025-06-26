package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.service.LicenciaService;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService_impl;
import java.util.List;

import javax.smartcardio.ResponseAPDU;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller

public class LicenciaController {

    private final LicenciaService licenciaService_1;
    @Autowired
    private LicenciaService_impl licenciaService;

    LicenciaController(LicenciaService licenciaService_1) {
        this.licenciaService_1 = licenciaService_1;
    }

    @PostMapping ("/licencia/guardar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> guardarLicencia(@RequestBody LicenciaDTO licencia) {

        try{
            if(licenciaService.edadMinima(licencia)) {
                if(!licenciaService.repetida(licencia)){
                    if(licenciaService.profesional(licencia)) {
                        licenciaService.guardarLicencia(licencia);
                        return ResponseEntity.status(HttpStatus.CREATED).body(licencia);
                    } 
                } 
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la licencia.");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }   
    }

    @GetMapping ("/licencia/vigentes")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> mostrarLicenciasVigentes(@RequestBody LicenciaDTO licencia){
        try{
            List<Licencia> licencias = licenciaService.buscarLicenciasVigentes(licencia);
            return ResponseEntity.status(HttpStatus.OK).body(licencias);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/licencia/modificar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> modificarLicencia(@RequestBody LicenciaDTO licenciaDTO) {
        TitularService_impl titularService_impl = new TitularService_impl();
        try{
            TitularDTO titularDTO = licenciaDTO.getTitular();
            if(titularService_impl.validarDatosTitular(titularDTO)){
                Titular titular = titularService_impl.modificarTitular(titularDTO);
                if(licenciaService.edadMinima(licenciaDTO)){
                    if(licenciaService.profesional(licenciaDTO)){
                        Licencia licencia = licenciaService.modificarLicencia(licenciaDTO);
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(licencia);
                    }
                }

                
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar la licencia");
            

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @PostMapping("/licencia/renovar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> postMethodName(@RequestBody LicenciaDTO licenciaDTO) {
        try{
            licenciaService.renovarLicencia(licenciaDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(licenciaDTO);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
    
    
}
