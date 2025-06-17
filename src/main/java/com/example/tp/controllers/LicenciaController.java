package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.service.LicenciaService;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;

@Controller

public class LicenciaController {
    @Autowired
    private LicenciaService_impl licenciaService;

    @PostMapping ("/licencia/guardar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> guardarLicencia(@RequestBody LicenciaDTO licencia) {
        System.out.println("se llego al back");
        System.out.println(licencia.getTitular().getDocumento());
        if(!licenciaService.edadMinima(licencia)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se alcanza la edad minima de la licencia");//aca habria q tirar un error y pedir q arranque de vuelta
        if(licenciaService.repetida(licencia)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una licencia de este tipo. Desea renovar?");//aca habria q tirar un error y pedir q arranque de vuelta
        if(!licenciaService.profesional(licencia)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El titular no es apto para obtener la licencia profesional "+licencia.getClase());//aca habria q tirar un error

        licenciaService.guardarLicencia(licencia);

        return ResponseEntity.status(HttpStatus.CREATED).body(licencia);
    }
}
