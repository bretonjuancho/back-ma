package com.example.tp.service;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.Licencia;

import java.time.LocalDate;
import java.time.Period;

public class LicenciaService_impl {
    public LicenciaService_impl() {}

    public boolean edadMinima(LicenciaDTO licencia){
        LocalDate nacimiento = licencia.getTitular().getFechaNacimiento();
        LocalDate hoy = LocalDate.now();
        int edad= Period.between(hoy,nacimiento).getYears();
        if((licencia.getTipo().equals("A") ||
                licencia.getTipo().equals("B") ||
                licencia.getTipo().equals("F") ||
                licencia.getTipo().equals("G")) && edad<17){return false; }
        if((licencia.getTipo().equals("C") ||
                licencia.getTipo().equals("D") ||
                licencia.getTipo().equals("E") && edad<21){return false; }
        return true;
    };

    public boolean profesional(LicenciaDTO licencia){
        if (licencia.getTipo().equals("A") ||
                licencia.getTipo().equals("B") ||
                licencia.getTipo().equals("F") ||
                licencia.getTipo().equals("G")) {return true;}
        Licencia licenciaB= buscarLicenciaTipoB();
        if(licenciaB==null){return false;}
        if


    }

}
