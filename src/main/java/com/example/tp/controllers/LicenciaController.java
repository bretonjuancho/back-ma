package com.example.tp.controllers;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.service.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tp.service.TitularService;
import java.util.List;

@Controller

public class LicenciaController {
    @Autowired
    private LicenciaService licenciaService;

    @GetMapping("/licencia/nueva")
    public String nuevaLicencia() {
        return "licencia/nueva";//esta seria la parte de mostrar el formulario de la licencia
        //habria q profundizar en esto mas adelante
    }

    @PostMapping ("/licencia/guardar")
    public String guardarLicencia(LicenciaDTO licencia) {
        if(!licenciaService.edadMinima(licencia)) return "redirect:/licencia/nueva";//aca habria q tirar un error y pedir q arranque de vuelta
        if(!licenciaService.profesional(licencia)) return "redirect:/licencia/nueva";//aca habria q tirar un error
        licenciaService.guardar(licencia);
        return "redirect:/licencia";
    }
}
