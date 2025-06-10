package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "administrador")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrador_seq")
    @SequenceGenerator(name = "administrador_seq", sequenceName = "administrador_seq", allocationSize = 1)
    private Long id;


    @Column(name = "password")
    private String password;

    public Administrador() {
    }

    public Administrador(String password) {
        this.password = password;
    }


}