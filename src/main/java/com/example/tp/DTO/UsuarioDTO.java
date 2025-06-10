package com.example.tp.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO {
    @Getter @Setter
    private String dni;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String apellido;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;

    public UsuarioDTO(String dni, String nombre, String apellido, String email, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }
}
