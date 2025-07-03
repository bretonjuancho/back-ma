package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaConsultaDTO;
import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.service.ComprobanteService;
import com.example.tp.service.LicenciaService;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService_impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ResponseAPDU;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller

public class LicenciaController {
    @Autowired
    private LicenciaService_impl licenciaService;
    @Autowired
    private ComprobanteService comprobanteService;


    @PostMapping ("/licencia/guardar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> guardarLicencia(@RequestBody LicenciaDTO licencia) {

        try{
            if(licenciaService.edadMinima(licencia)) {
                if(!licenciaService.repetida(licencia)){
                    if(licenciaService.profesional(licencia)) {
                        Licencia l = licenciaService.guardarLicencia(licencia);
                        double costo = licenciaService.calcularCosto(licencia);
                        comprobanteService.guardar(l,costo, "Creacion de Licencia");
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

    @GetMapping("/licencia/vigentes")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> mostrarLicencias(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaEmisionDesde,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaVencimientoDesde,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaVencimientoHasta,
            @RequestParam(required = false) Boolean vigente,
            @RequestParam(required = false) String clase,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) Integer numeroLicencia,
            @RequestParam(required = false) String grupoSanguineo,
            @RequestParam(required = false) String factorRH,
            @RequestParam(required = false) Boolean donante) {

        System.out.println("entro al back");

        try {
            // Crear el DTO con los parámetros recibidos
            LicenciaConsultaDTO consultaDTO = new LicenciaConsultaDTO();
            if(fechaEmisionDesde != null)consultaDTO.setFechaEmisionDesde(fechaEmisionDesde);
            if(fechaEmisionHasta!= null)consultaDTO.setFechaEmisionHasta(fechaEmisionHasta);
            if(fechaVencimientoDesde!= null)consultaDTO.setFechaVencimientoDesde(fechaVencimientoDesde);
            if(fechaVencimientoHasta!= null)consultaDTO.setFechaVencimientoHasta(fechaVencimientoHasta);
            if(vigente!= null)consultaDTO.setVigente(vigente);
            if(clase!= null)consultaDTO.setClase(clase);
            if(nombre!= null)consultaDTO.setNombre(nombre);
            if(apellido!= null)consultaDTO.setApellido(apellido);
            if(numeroLicencia!= null)consultaDTO.setNumeroLicencia(numeroLicencia);
            if(grupoSanguineo!= null)consultaDTO.setGrupoSanguineo(grupoSanguineo);
            if(factorRH!= null)consultaDTO.setFactorRH(factorRH);
            if(donante!= null)consultaDTO.setDonante(donante);
            System.out.println("va al service");
            List<Licencia> licencias = licenciaService.buscarLicencias(consultaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(licencias);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("salgo");
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
                        double costo = licenciaService.calcularCosto(licenciaDTO);
                        comprobanteService.guardar(licencia, costo,"Modificacion");
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
    public ResponseEntity<?> renovarLicencia(@RequestBody LicenciaDTO licenciaDTO) {
        try{
            Licencia lic = licenciaService.renovarLicencia(licenciaDTO);
            comprobanteService.guardar(lic, 50,"Renovacion");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(licenciaDTO);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @GetMapping("/licencia/copia")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> copiaLicencia(@RequestParam(required = false) String documento,String claseLicencia){
        Licencia licencia = licenciaService.copiaLicencia(documento,claseLicencia);
        comprobanteService.guardar(licencia, 50,"Copia");
        return ResponseEntity.ok(licencia);
    }
    
    
}
