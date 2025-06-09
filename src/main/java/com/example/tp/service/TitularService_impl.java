package com.example.tp.service;

import com.example.tp.excepciones.TitularNoEncontradoException;
import com.example.tp.modelo.Titular;

import java.util.ArrayList;
import java.util.List;

public class TitularService_impl {

    public List<Titular> ListarTitulares() {
        return titularRepository.findAll();
    }

    public List<Titular> getTitularByNombre(String nombre) throws TitularNoEncontradoException {
        List<Titular> aux = titularRepository.findAll();
        List<Titular> titulares = new ArrayList<>();
        for(Titular t : aux) {
            if(t.getNombre().startsWith(nombre)){
                titulares.add(t);
            }
        }
        return titulares;
    }

}
