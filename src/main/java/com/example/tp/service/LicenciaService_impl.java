package com.example.tp.service;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.DTO.TitularDTO;
import com.example.tp.modelo.GestionLicencia;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.GestionLicenciaRepository;
import com.example.tp.repository.LicenciaRepository;
import com.example.tp.repository.TitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
public class LicenciaService_impl implements LicenciaService{
    @Autowired
    private LicenciaRepository bdd_licencia;
    @Autowired
    private TitularRepository bdd_titular;
    @Autowired
    private UsuarioService_impl usuarioService;
    @Autowired
    private GestionLicenciaRepository gestionLicenciaRepository;

    public LicenciaService_impl() {}

    public boolean repetida(LicenciaDTO licencia){
        Titular titular = buscarTitularByDocumento(licencia.getTitular().getDocumento());
        List<Licencia> lic = buscarLicenciaByClaseYTitular(licencia.getClase(), titular);
        if(lic.isEmpty()) return false;
        return true;
    }


    public boolean edadMinima(LicenciaDTO licencia){
        LocalDate nacimiento = licencia.getTitular().getFechaNacimiento();
        LocalDate hoy = LocalDate.now();
        int edad= Period.between(nacimiento,hoy).getYears();
        if((licencia.getClase().equals("A") ||
                licencia.getClase().equals("B") ||
                licencia.getClase().equals("F") ||
                licencia.getClase().equals("G")) && edad<17){return false; }
        if((licencia.getClase().equals("C") ||
                licencia.getClase().equals("D") ||
                licencia.getClase().equals("E")) && edad<21){return false; }
        return true;
    };

    public boolean profesional(LicenciaDTO licencia){
        if (licencia.getClase().equals("A") ||
                licencia.getClase().equals("B") ||
                licencia.getClase().equals("F") ||
                licencia.getClase().equals("G")) {return true;}
        Titular titular = buscarTitularByDocumento(licencia.getTitular().getDocumento());
        Licencia licenciaB= licenciaMasVieja(buscarLicenciaByClaseYTitular(licencia.getClase(),titular));
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

    public List<Licencia> buscarLicenciaByClaseYTitular(String clase, Titular titular) {
        return bdd_licencia.buscarLicenciaByClaseYTitular(clase,titular.getId());
    }

    public Licencia licenciaMasVieja(List<Licencia> licencias){
        Licencia licencia = null;
        for(Licencia l: licencias){
            if(licencia==null || licencia.getFechaEmision().isAfter(l.getFechaEmision())){licencia=l;}
        }
        return licencia;
    }

    private GestionLicencia gestionLicencia(Licencia licencia, Usuario usuario,String motivo){
        GestionLicencia gestionLicencia = new GestionLicencia(licencia,usuario,motivo);
        gestionLicenciaRepository.save(gestionLicencia);
        return gestionLicencia;
    }

    public void guardarLicencia(LicenciaDTO licencia){
        Titular duenio=bdd_titular.findByDocumento(licencia.getTitular().getDocumento());
        Licencia save= new Licencia(licencia,duenio);
        save.setActiva(true);
        LocalDate fechaExpiracion=calcularValidez(save,duenio);
        save.setFechaExpiracion(fechaExpiracion);
        bdd_licencia.save(save);
        //gestion licencia:
        Usuario logUser = usuarioService.getLogingUser();
        save.addGestionLicencia(gestionLicencia(save,logUser,"Creacion"));


    }

    public double calcularCosto(LicenciaDTO licenciaDTO){
        Titular titular = bdd_titular.findByDocumento(licenciaDTO.getTitular().getDocumento());
        Licencia lic = new Licencia(licenciaDTO,titular);
        int aniosValidez = calcularValidezEntero(lic,titular);
        if(lic.getClaseLicencia().equals("A") || lic.getClaseLicencia().equals("B") || lic.getClaseLicencia().equals("G")){
            if ( aniosValidez == 5) return 40+8;
            if ( aniosValidez == 4) return 30+8;
            if ( aniosValidez == 3) return 25+8;
            if ( aniosValidez == 1) return 20+8;
            return 8;
        }else{
            if(lic.getClaseLicencia().equals("C")){
                if ( aniosValidez == 5) return 47+8;
                if ( aniosValidez == 4) return 35+8;
                if ( aniosValidez == 3) return 30+8;
                if ( aniosValidez == 1) return 23+8;
                return 8;
            }else{
                if(lic.getClaseLicencia().equals("E")){
                    if ( aniosValidez == 5) return 59+8;
                    if ( aniosValidez == 4) return 44+8;
                    if ( aniosValidez == 3) return 39+8;
                    if ( aniosValidez == 1) return 29+8;
                    return 8;
                }
            }
            return 8;
        }


    }


    public LocalDate calcularValidez(Licencia save,Titular duenio){
        LocalDate nacimiento=duenio.getFechaNacimiento();
        int edad= Period.between(nacimiento,LocalDate.now()).getYears();
        if(edad<21){
            LocalDate ret;
            if(buscarLicenciaByClaseYTitular(save.getClaseLicencia(),duenio).size()==1) ret=nacimiento.plusYears(1);
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

    public int calcularValidezEntero(Licencia save,Titular duenio){
        LocalDate nacimiento=duenio.getFechaNacimiento();
        int edad= Period.between(nacimiento,LocalDate.now()).getYears();
        if(edad<21){
            LocalDate ret;
            if(buscarLicenciaByClaseYTitular(save.getClaseLicencia(),duenio).size()==1) return 1;
            else  return 3;
        }
        else{
            if(edad<=46){
                return 5;
            }
            else{
                if(edad<=60){
                    return 4;
                }
                else{
                    if(edad<=70){
                        return 3;
                    }
                    else{
                        return 1;
                    }
                }
            }
        }
    }

    public List<Licencia> buscarLicenciasVigentes(LicenciaDTO licencia){
        return bdd_licencia.buscarLicenciasVigentes(licencia.getFechaEmision(),
                licencia.getFechaVencimiento(),
                licencia.getTitular().getNombre(),
                licencia.getTitular().getApellido(),
                licencia.getNumero(),
                licencia.getTitular().getGrupoSanguineo(),
                licencia.getTitular().getFactorRH(),
                licencia.getTitular().isDonante());
    }
}
