package com.example.tp.service;

import com.example.tp.modelo.Titular;

import java.util.List;
import java.util.Optional;
import com.example.tp.excepciones.TitularNoEncontradoException;

public interface TitularService {

    void crearTitular(Titular titular);
    List<Titular> ListarTitulares();
    public Optional<Titular> getTitularByNombre(String nombre) throws TitularNoEncontradoException;

}