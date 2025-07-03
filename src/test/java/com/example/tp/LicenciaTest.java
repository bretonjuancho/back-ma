package com.example.tp;

import com.example.tp.controllers.TitularController;
import com.example.tp.excepciones.licencia.LicenciaDatosInvalidosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.service.LicenciaService_impl;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LicenciaTest {
    
    @Autowired
    LicenciaService_impl licenciaService;

    LocalDate date1 = LocalDate.of(2000, 10, 10);
    LocalDate date2 = LocalDate.of( 2009, 1, 1);
    TitularDTO tit1 = new TitularDTO("Mateo", "Gastaldi", "45489", "DNi", date1, "Llerena 2260", "AB", "+", true);
    TitularDTO tit2 = new TitularDTO("Juan", "Juancin", "24291870", "DNi", date2, "Llerena 2260", "AB", "-", false);


    LicenciaDTO licenciaDTO1 = new LicenciaDTO(tit1, "No tiene nada", "A");
    LicenciaDTO licenciaDTO2 = new LicenciaDTO(tit1, "No tiene nada", "A");
    LicenciaDTO licenciaDTO3 = new LicenciaDTO(tit2, "Ciego de un ojo", "B");


    @Test
    void pruebaEdadMinimaValida() {
        Assertions.assertTrue(licenciaService.edadMinima(licenciaDTO1));
    }
    void pruebaEdadMinimaInvalida() {
        Assertions.assertTrue(licenciaService.edadMinima(licenciaDTO1));
    }
    @Test
    void pruebaLicenciaProfesional() {
        Assertions.assertTrue(licenciaService.profesional(licenciaDTO1));
    }
    @Test
    void pruebaLicenciaRepetida() {
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO1));
    }
    @Test
    void pruebaTitularInexistente() {
        Assertions.assertThrows(LicenciaDatosInvalidosException.class,()->{licenciaService.edadMinima(licenciaDTO2);});
    }
    @Test
    void pruebaTitularExistente() {
        Assertions.assertFalse(licenciaService.profesional(licenciaDTO2));
    }
    @Test
    void ncontextLoads() {
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO2));
    }
    @Test
    void nombre() {
        Assertions.assertFalse(licenciaService.edadMinima(licenciaDTO3));
    }
    @Test
    void contextLreroads() {
        Assertions.assertTrue(licenciaService.profesional(licenciaDTO3));
    }
    @Test
    void contextLoadsere() {
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO3));
    }
}
