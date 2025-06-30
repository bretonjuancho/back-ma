package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "administrador")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrador_seq")
    @SequenceGenerator(name = "administrador_seq", sequenceName = "administrador_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "DNI")
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Token> tokens;

    public Administrador(String nombre, String apellido, String dni, String email, String encode) {
        this.nombre=nombre;
        this.apellido=apellido;
        this.dni=dni;
        this.email=email;
        this.password=encode;
    }



}