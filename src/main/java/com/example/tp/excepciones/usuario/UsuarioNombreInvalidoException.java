package com.example.tp.excepciones.usuario;

public class UsuarioNombreInvalidoException extends RuntimeException {
    public UsuarioNombreInvalidoException(String message) {
        super(message);
    }
}
