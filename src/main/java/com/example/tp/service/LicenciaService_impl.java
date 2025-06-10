package com.example.tp.service;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.repository.LicenciaRepository;
import com.example.tp.repository.TitularRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
public class LicenciaService_impl {
    private LicenciaRepository bdd_licencia;
    private TitularRepository bdd_titular;

    public LicenciaService_impl() {}

    public boolean edadMinima(LicenciaDTO licencia){
        LocalDate nacimiento = licencia.getTitular().getFechaNacimiento();
        LocalDate hoy = LocalDate.now();
        int edad= Period.between(nacimiento,hoy).getYears();
        if((licencia.getTipo().equals("A") ||
                licencia.getTipo().equals("B") ||
                licencia.getTipo().equals("F") ||
                licencia.getTipo().equals("G")) && edad<17){return false; }
        if((licencia.getTipo().equals("C") ||
                licencia.getTipo().equals("D") ||
                licencia.getTipo().equals("E")) && edad<21){return false; }
        return true;
    };

    public boolean profesional(LicenciaDTO licencia){
        if (licencia.getTipo().equals("A") ||
                licencia.getTipo().equals("B") ||
                licencia.getTipo().equals("F") ||
                licencia.getTipo().equals("G")) {return true;}
        Titular titular = buscarTitularByDocumento(licencia.getTitular().getDocumento());
        Licencia licenciaB= licenciaMasVieja(buscarLicenciaByTipoYTitular(licencia.getTipo(),titular));
        if(licenciaB==null){return false;}
        LocalDate emision=licenciaB.getFechaEmision();
        LocalDate hoy=LocalDate.now();
        int tiempo= Period.between(emision,hoy).getYears();
        if(tiempo>=1){return true;}
        return false;
    }

    private Titular buscarTitularByDocumento(String documento){
        Titular titular = null;
        titular=bdd_titular.findByDni(documento);
        return titular;
    }

    private List<Licencia> buscarLicenciaByTipoYTitular(String tipo, Titular titular) {
        return bdd_licencia.buscarLicenciaByTipoYTitular(tipo,titular.getId());
    }

    private Licencia licenciaMasVieja(List<Licencia> licencias){
        Licencia licencia = null;
        for(Licencia l: licencias){
            if(licencia==null || licencia.getFechaEmision().isAfter(l.getFechaEmision())){licencia=l;}
        }
        return licencia;
    }
}
