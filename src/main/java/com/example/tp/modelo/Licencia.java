package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "licencia")

public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)

    @Column(name = "idLicencia")
    private int id;

    @Column(name = "tipoLicencia")
    private Character tipoLicencia;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "titular_id", nullable = false)
    private Titular titular;

    public Licencia() {

    }

    public Licencia(Character tipoLicencia, String observaciones) {
        this.tipoLicencia = tipoLicencia;
        this.observaciones = observaciones;
    }


}
