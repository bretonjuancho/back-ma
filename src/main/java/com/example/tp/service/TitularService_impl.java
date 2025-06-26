package com.example.tp.service;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.excepciones.titular.TitularDatosInvalidos;
import com.example.tp.excepciones.titular.TitularNoEncontradoException;
import com.example.tp.modelo.*;
import com.example.tp.repository.GestionTitularRepository;
import com.example.tp.repository.TitularRepository;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TitularService_impl {
    @Autowired
    private TitularRepository titularRepository;
    private GestionTitularRepository gestionTitularRepository;
    private UsuarioRepository usuarioRepository;

    public List<Titular> ListarTitulares() {
        return titularRepository.findAll();
    }

    private boolean validarTexto(String texto){
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    private boolean validarTipoDocumento(String tipoDocumento){

        return (
                        tipoDocumento.equalsIgnoreCase("DNI") ||
                        tipoDocumento.equalsIgnoreCase("CUIL") ||
                        tipoDocumento.equalsIgnoreCase("CUIT")
        );
    }

    private boolean validarGrupoSanguineo(String tipoSanguineo){

        return(
                tipoSanguineo.equalsIgnoreCase("A")
                || tipoSanguineo.equalsIgnoreCase("B")
                || tipoSanguineo.equalsIgnoreCase("AB")
                || tipoSanguineo.equalsIgnoreCase("O")
                );
    }

    private boolean validarFactorRH(String factorRH){
        return (
                factorRH.equalsIgnoreCase("+")
                || factorRH.equalsIgnoreCase("-")
                );
    }

    private boolean validarDocumento(String documento, String tipoDocumento){
        boolean repetido = (titularRepository.findByDocumento(documento)) != null;
        if (repetido) return false;
        if(tipoDocumento.equalsIgnoreCase("DNI")){
            return documento.matches("[0-9]+") && documento.length() <=8;
        }else{
            if(tipoDocumento.equalsIgnoreCase("CUIL")){
                return documento.matches("\\d\\d-[0-9]+-\\d");
            }
            else if(tipoDocumento.equalsIgnoreCase("CUIT")){
                return documento.matches("\\d\\d-[0-9]+-\\d");
            }
            else return false;
        }
    }

    public boolean validarDatosTitular(TitularDTO titularDTO)throws TitularDatosInvalidos {
        if (!validarTexto(titularDTO.getNombre())) throw new TitularDatosInvalidos("El nombre del titular no es valido: " + titularDTO.getNombre());
        if (!validarTexto((titularDTO.getApellido()))) throw new TitularDatosInvalidos("El apellido no es valido: " + titularDTO.getApellido());
        if (!validarTipoDocumento(titularDTO.getTipoDocumento())) throw new TitularDatosInvalidos(("No es un tipo de documento valido."));
        if (!validarGrupoSanguineo(titularDTO.getGrupoSanguineo())) throw new TitularDatosInvalidos("El grupo sanguineo no es valido.");
        if (!validarFactorRH(titularDTO.getFactorRH())) throw new TitularDatosInvalidos("El factorRH no es valido.");
        if (!validarDocumento(titularDTO.getDocumento(),titularDTO.getTipoDocumento())) throw new TitularDatosInvalidos("El documento no es valido.");
        return true;
    }

    private GestionTitular gestionTitular(Titular titular, Usuario usuario, String motivo){
        GestionTitular gestionTitular = new GestionTitular(titular,usuario,motivo);
        gestionTitularRepository.save(gestionTitular);
        return gestionTitular;
    }

    public Usuario getLogingUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Dni = ((User) authentication.getPrincipal()).getUsername();
        return usuarioRepository.findByDni(Dni);
    }

    public Titular crearTitular(TitularDTO titularDTO) {
        String nombre = titularDTO.getNombre();

        String apellido = titularDTO.getApellido();
        String documento = titularDTO.getDocumento();
        String tipoDocumento = titularDTO.getTipoDocumento();
        LocalDate fechaNacimiento = titularDTO.getFechaNacimiento();
        String direccion = titularDTO.getDireccion();
        String grupoSanguineo = titularDTO.getGrupoSanguineo();
       
        String factorRH = titularDTO.getFactorRH();
        boolean donante = titularDTO.isDonante();

        Titular titular = new Titular(nombre,apellido,documento,tipoDocumento, fechaNacimiento, direccion, grupoSanguineo, factorRH,donante);
        titularRepository.save(titular);

        //gestion titular:
        Usuario logUser = getLogingUser();
        titular.addGestion(gestionTitular(titular,logUser,"Creacion"));

        return titular;


    }

    public Titular modificarTitular(TitularDTO titularDTO) {
        Titular titular = titularRepository.findByDocumento(titularDTO.getDocumento());
        titular.setNombre(titularDTO.getNombre());
        titular.setApellido(titularDTO.getApellido());
        titular.setDireccion(titularDTO.getDireccion());
        titular.setDonante(titularDTO.isDonante());
        titularRepository.save(titular);

        //gestion titular:
        Usuario logUser = getLogingUser();
        titular.addGestion(gestionTitular(titular,logUser,"Modificacion"));

        return titular;


    }

    public List<Titular> buscarTitulares (String documento, String nombre, String apellido)throws TitularNoEncontradoException {

        List<Titular> titulars = titularRepository.findByDocuementoNombreApellido(documento,nombre,apellido);
        if(!titulars.isEmpty()){
            return titulars;
        }
        throw  new TitularNoEncontradoException("No se encontro un titular con los filtros aplicados");

    }


}
