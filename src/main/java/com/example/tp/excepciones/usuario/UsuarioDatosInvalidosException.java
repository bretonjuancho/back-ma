package com.example.tp.excepciones.usuario;

public class UsuarioDatosInvalidosException extends RuntimeException {
    public UsuarioDatosInvalidosException(String message) {
        super(message);
    }
}
