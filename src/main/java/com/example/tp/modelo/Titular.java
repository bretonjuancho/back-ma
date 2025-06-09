package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "titular")

public class Titular {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titular_seq")
    @SequenceGenerator(name = "titular_seq", sequenceName = "titular_seq", allocationSize = 1)

    @Column(name = "idTitular")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Licencia> licencias = new ArrayList<>();

    public Titular() {

    }
}