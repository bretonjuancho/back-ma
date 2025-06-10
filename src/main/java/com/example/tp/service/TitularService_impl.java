package com.example.tp.service;

import com.example.tp.excepciones.TitularNoEncontradoException;
import com.example.tp.modelo.Titular;
import com.example.tp.repository.TitularRepository;
import org.springframework.stereotype.Service;

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

}
