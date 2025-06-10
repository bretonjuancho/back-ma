package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "titular")

public class Titular {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titular_seq")
    @SequenceGenerator(name = "titular_seq", sequenceName = "titular_seq", allocationSize = 1)

    @Column(name = "idTitular")
    private int id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Licencia> licencias;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GestionTitular> gestiones;

    public Titular() {

    }

    public Titular(String dni, String nombre, String apellido,List<Licencia> licencias) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void addLicencia(Licencia lic){
        this.licencias.add(lic);
    }
    public void removeLicencia(Licencia lic){
        this.licencias.remove(lic);
    }

}