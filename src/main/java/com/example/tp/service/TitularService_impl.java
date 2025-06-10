package com.example.tp.service;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.excepciones.TitularNoEncontradoException;
import com.example.tp.modelo.Titular;
import com.example.tp.repository.TitularRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TitularService_impl {
    private TitularRepository titularRepository;



    public List<Titular> ListarTitulares() {
        return titularRepository.findAll();
    }

   /* public List<Titular> getTitularByNombre(String nombre) throws TitularNoEncontradoException {
        List<Titular> aux = titularRepository.findAll();
        List<Titular> titulares = new ArrayList<>();
        for(Titular t : aux) {
            if(t.getNombre().startsWith(nombre)){
                titulares.add(t);
            }
        }
        return titulares;
    }*/
   private String nombre;
    private String apellido;
    private String documento;
    private String tipoDocumento;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String grupoSanquineo;
    private String factorRH;
    private boolean donante;


    private boolean textoValido(String texto){
        if(texto.isEmpty()){ return false; }
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    private boolean tipoDocumentoValido(String tipoDocumento){
        if(tipoDocumento.isEmpty()){ return false; }
        if(tipoDocumento.equals("DNI")) return true;
        if (tipoDocumento.equals("CUIT")) return true;
        if (tipoDocumento.equals("CUIL")) return true;
        return false;
    }
    public void crearTitular(TitularDTO titularDTO) {
        if(textoValido(titularDTO.getNombre())){
            if(textoValido(titularDTO.getApellido())){
                if(tipoDocumentoValido(titularDTO.getTipoDocumento())){

                }else{

                }
            }else{

            }
        }else{

        }

    }

}
