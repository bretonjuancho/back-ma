package com.example.tp.service;

import com.example.tp.modelo.Titular;

import java.util.List;
import java.util.Optional;
import com.example.tp.excepciones.titular.TitularNoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public interface TitularService {

    void crearTitular(Titular titular);
    List<Titular> ListarTitulares();
    public Optional<Titular> getTitularByNombre(String nombre) throws TitularNoEncontradoException;
    public List<Titular> buscarTitulares(String documento,String nombre,String apellido) throws TitularNoEncontradoException;

}