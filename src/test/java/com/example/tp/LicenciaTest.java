package com.example.tp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.service.LicenciaService_impl;

public class LicenciaTest {
    
    @Autowired
    LicenciaService_impl licenciaService;

    LocalDate date1 = LocalDate.of(2004, 10, 1);
    LocalDate date2 = LocalDate.of( 2009, 1, 1);
    TitularDTO tit1 = new TitularDTO("Mateo", "Gastaldi", "45489343", "DNi", date1, "Llerena 2260", "A", "+", true);
    TitularDTO tit2 = new TitularDTO("Juan", "Juancin", "24291870", "DNi", date2, "Llerena 2260", "AB", "-", false);

    LicenciaDTO licenciaDTO1 = new LicenciaDTO(tit1, "No tiene nada", "A");
    LicenciaDTO licenciaDTO2 = new LicenciaDTO(tit1, "No tiene nada", "A");
    LicenciaDTO licenciaDTO3 = new LicenciaDTO(tit2, "Ciego de un ojo", "B");


    @Test
    void contextLoads() {
        Assertions.assertTrue(licenciaService.edadMinima(licenciaDTO1));
        Assertions.assertTrue(licenciaService.profesional(licenciaDTO1));
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO1));

        Assertions.assertTrue(licenciaService.edadMinima(licenciaDTO2));
        Assertions.assertFalse(licenciaService.profesional(licenciaDTO2));
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO2));

        Assertions.assertFalse(licenciaService.edadMinima(licenciaDTO3));
        Assertions.assertTrue(licenciaService.profesional(licenciaDTO3));
        Assertions.assertTrue(licenciaService.repetida(licenciaDTO3));
    }
}
