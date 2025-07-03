package com.example.tp.service;

import com.example.tp.DTO.LicenciaConsultaDTO;
import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.excepciones.licencia.LicenciaDatosInvalidosException;
import com.example.tp.excepciones.licencia.LicenciaNoEncontradaException;
import com.example.tp.excepciones.titular.TitularNoEncontradoException;
import com.example.tp.modelo.GestionLicencia;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.GestionLicenciaRepository;
import com.example.tp.repository.LicenciaRepository;
import com.example.tp.repository.TitularRepository;
import com.example.tp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private UsuarioRepository usuarioRepository;

    public LicenciaService_impl() {}

    public boolean repetida(LicenciaDTO licencia) throws LicenciaDatosInvalidosException{
        Titular titular = buscarTitularByDocumento(licencia.getTitular().getDocumento());
        if(titular==null){throw new LicenciaDatosInvalidosException("No existe el titular");}
        List<Licencia> lic = buscarLicenciaByClaseYTitular(licencia.getClase(), titular);
        if(lic.isEmpty())  return false;
        throw new LicenciaDatosInvalidosException("La licencia se encuentra repetida en el sistema.");
    }


    public boolean edadMinima(LicenciaDTO licencia) throws LicenciaDatosInvalidosException{
        LocalDate nacimiento = licencia.getTitular().getFechaNacimiento();
        LocalDate hoy = LocalDate.now();
        int edad= Period.between(nacimiento,hoy).getYears();
        if((licencia.getClase().equals("A") ||
                licencia.getClase().equals("B") ||
                licencia.getClase().equals("F") ||
                licencia.getClase().equals("G")) && edad<17){throw new LicenciaDatosInvalidosException("No cumple los requerimientos de edad minima para obtener la licencia.") ; }
        if((licencia.getClase().equals("C") ||
                licencia.getClase().equals("D") ||
                licencia.getClase().equals("E")) && edad<21){throw new LicenciaDatosInvalidosException("No cumple los requerimientos de edad minima para obtener la licencia.") ; }
        return true;
    };

    public boolean profesional(LicenciaDTO licencia) throws LicenciaDatosInvalidosException{
        if (licencia.getClase().equals("A") ||
                licencia.getClase().equals("B") ||
                licencia.getClase().equals("F") ||
                licencia.getClase().equals("G")) {return true;}
        Titular titular = buscarTitularByDocumento(licencia.getTitular().getDocumento());
        if(titular==null){throw new LicenciaDatosInvalidosException("No existe el titular");}
        Licencia licenciaB= licenciaMasVieja(buscarLicenciaByClaseYTitular(licencia.getClase(),titular));
        if(licenciaB==null){throw new LicenciaDatosInvalidosException("No cumple los requerimientos para obtener licencia profesional.") ;}
        LocalDate emision=licenciaB.getFechaEmision();
        LocalDate hoy=LocalDate.now();
        int tiempo= Period.between(emision,hoy).getYears();
        if(tiempo>=1){return true;}
        throw new LicenciaDatosInvalidosException("No cumple los requerimientos para obtener licencia profesional.") ;
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
        int edad = Period.between(nacimiento,LocalDate.now()).getYears();
        if(edad<21){
            LocalDate ret;
            if(buscarLicenciaByClaseYTitular(save.getClaseLicencia(),duenio).size()==1)
                ret=nacimiento.plusYears(1);
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

    public List<Licencia> buscarLicencias(LicenciaConsultaDTO licenciaConsultaDTO){
        System.out.println(licenciaConsultaDTO.getNombre());
        System.out.println(licenciaConsultaDTO.getApellido());
        System.out.println(licenciaConsultaDTO.getFactorRH());
        return bdd_licencia.buscarLicencias(
                licenciaConsultaDTO.getFechaEmisionDesde(),
                licenciaConsultaDTO.getFechaVencimientoHasta(),
                licenciaConsultaDTO.getFechaVencimientoDesde(),
                licenciaConsultaDTO.getFechaVencimientoHasta(),
                licenciaConsultaDTO.isVigente(),
                licenciaConsultaDTO.getClase(),
                licenciaConsultaDTO.getNombre(),
                licenciaConsultaDTO.getApellido(),
                licenciaConsultaDTO.getNumeroLicencia(),
                licenciaConsultaDTO.getGrupoSanguineo(),
                licenciaConsultaDTO.getFactorRH(),
                licenciaConsultaDTO.isDonante()
        );
    }


    public Licencia modificarLicencia(LicenciaDTO licenciaDTO) throws LicenciaNoEncontradaException{
        Licencia licencia = bdd_licencia.findByNumero(licenciaDTO.getNumero());
        if (licencia == null) throw new LicenciaNoEncontradaException("No se encontro la licenia en la Base de Datos");
        licencia.setFechaEmision(licenciaDTO.getFechaEmision());
        licencia.setFechaExpiracion(licenciaDTO.getFechaVencimiento());
        licencia.setClaseLicencia(licenciaDTO.getClase());
        licencia.setObservaciones(licenciaDTO.getObservaciones());
        //licencia.setActiva(); falta ponerle al DTO si esta activa o no
        bdd_licencia.save(licencia);

        //gestion titular:
        Usuario logUser = usuarioService.getLogingUser();
        licencia.addGestionLicencia(gestionLicencia(licencia,logUser,"Modificacion"));
        
        
        return licencia;
    }

    public Licencia renovarLicencia(LicenciaDTO licenciaDTO) throws TitularNoEncontradoException,LicenciaNoEncontradaException{
        Titular titular = bdd_titular.findByDocumento(licenciaDTO.getTitular().getDocumento());
        if(titular == null) throw new TitularNoEncontradoException("No se pudo obtener el titular");

        Licencia licencia = bdd_licencia.findByNumero(licenciaDTO.getNumero());
        if(licencia == null) throw new LicenciaNoEncontradaException("No se pudo obtener la licencia.");

        LocalDate validez = calcularValidez(licencia, titular);
        licencia.setFechaExpiracion(validez);

        bdd_licencia.save(licencia);

        //gestion titular:
        Usuario logUser = usuarioService.getLogingUser();
        licencia.addGestionLicencia(gestionLicencia(licencia,logUser,"Renovacion"));

        return licencia;


    }

    public Licencia copiaLicencia(String documento,String clase){
        Titular titular = bdd_titular.findByDocumento(documento);
        LocalDate fechaActual = LocalDate.now();
        Licencia licencia = bdd_licencia.findLicenciaByTitularAndClaseActiva(titular.getId(),clase,fechaActual);

        Usuario logUser = usuarioService.getLogingUser();
        licencia.addGestionLicencia(gestionLicencia(licencia,logUser,"Copia"));

        return licencia;
    }
}
