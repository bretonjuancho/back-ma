package com.example.tp.controllers;

import com.example.tp.modelo.Titular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;


@Controller

public class TitularController {
    @Autowired
    private final TitularService titularService;

    public TitularController(TitularService titularService) {
        this.titularService = titularService;
    }


    @PostMapping("titular/crear")
    @ResponseBody
    public void crearTitular (@RequestBody Titular titular) {
        titularService.crearTitular(titular);
    }

    @GetMapping("titular/buscar")
    @ResponseBody
    public List<Titular> listarTitulares(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return titularService.buscarTitularesPorNombre(nombre);
        }
        return titularService.ListarTitulares();
    }

}
