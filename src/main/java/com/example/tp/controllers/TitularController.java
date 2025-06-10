package com.example.tp.controllers;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.Titular;
import com.example.tp.service.TitularService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;


@Controller

public class TitularController {
    @Autowired
    private TitularService_impl titularService;

    @PostMapping("titular/crear")
    @ResponseBody
    public void crearTitular (@RequestBody TitularDTO titularDTO) {
        titularService.crearTitular(titularDTO);
    }

    /*
    @GetMapping("titular/buscar")
    @ResponseBody
    public List<Titular> listarTitulares(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
          //  return titularService.buscarTitularesPorNombre(nombre);
        }
        return titularService.ListarTitulares();
    }*/

}
