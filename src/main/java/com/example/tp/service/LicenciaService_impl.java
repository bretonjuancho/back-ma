package com.example.tp.service;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.GestionLicencia;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.repository.LicenciaRepository;
import com.example.tp.repository.TitularRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
public class LicenciaService_impl implements LicenciaService{
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

    public Titular buscarTitularByDocumento(String documento){
        Titular titular = null;
        titular=bdd_titular.findByDocumento(documento);
        return titular;
    }

    public List<Licencia> buscarLicenciaByTipoYTitular(String tipo, Titular titular) {
        return bdd_licencia.buscarLicenciaByTipoYTitular(tipo,titular.getId());
    }

    public Licencia licenciaMasVieja(List<Licencia> licencias){
        Licencia licencia = null;
        for(Licencia l: licencias){
            if(licencia==null || licencia.getFechaEmision().isAfter(l.getFechaEmision())){licencia=l;}
        }
        return licencia;
    }

    public void guardarLicencia(LicenciaDTO licencia){
        Titular duenio=bdd_titular.findByDocumento(licencia.getTitular().getDocumento());
        Licencia save= new Licencia(licencia,duenio);
        LocalDate fechaExpiracion=calcularValidez(save,duenio);
        save.setFechaExpiracion(fechaExpiracion);
       // GestionLicencia
        bdd_licencia.save(save);
    }

    public int calcularCosto(Licencia save, Titular duenio){
        //if()
    }


    public LocalDate calcularValidez(Licencia save,Titular duenio){
        LocalDate nacimiento=duenio.getFechaNacimiento();
        int edad= Period.between(nacimiento,LocalDate.now()).getYears();
        if(edad<21){
            LocalDate ret;
            if(bdd_licencia.buscarLicenciaByTipoYTitular(save.getTipoLicencia(),duenio.getId()).size()==1) ret=nacimiento.plusYears(1);
            else  ret=nacimiento.plusYears(3);
            return ret;
        }
        else{
            if(edad<=46){
                LocalDate ret=nacimiento.plusYears(5);
                return ret;
            }
            else{
                if(edad<=60){
                    LocalDate ret=nacimiento.plusYears(4);
                    return ret;
                }
                else{
                    if(edad<=70){
                        LocalDate ret=nacimiento.plusYears(3);
                        return ret;
                    }
                    else{
                        LocalDate ret=nacimiento.plusYears(1);
                        return ret;
                    }
                }
            }
        }
    }
}
