package com.example.tp.excepciones.usuario;

public class UsuarioEmailInvalidoException extends RuntimeException {
    public UsuarioEmailInvalidoException(String message) {
        super(message);
    }
}
