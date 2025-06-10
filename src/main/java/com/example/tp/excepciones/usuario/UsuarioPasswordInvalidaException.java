package com.example.tp.excepciones.usuario;

public class UsuarioPasswordInvalidaException extends RuntimeException {
    public UsuarioPasswordInvalidaException(String message) {
        super(message);
    }
}
