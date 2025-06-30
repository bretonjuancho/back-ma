package com.example.tp.modelo;


import com.example.tp.DTO.LicenciaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@Entity
@Table(name = "licencia")

public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @Column(name = "fecha_emision")
    @Getter
    @Setter
    private LocalDate fechaEmision;

    @Column(name = "fecha_expiracion")
    @Getter
    @Setter
    private LocalDate fechaExpiracion;

    @Column(name = "clase_licencia")
    @Getter
    @Setter
    private String claseLicencia;

    @Column(name = "observaciones")
    @Getter
    @Setter
    private String observaciones;

    @Column(name = "numero",unique = true)
    @Getter
    @Setter
    private int numero;

    @Column(name = "activa")
    @Getter
    @Setter
    private Boolean activa;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_id", nullable = false)
    @JsonBackReference(value = "titular-licencias")
    private Titular titular;

    @Setter
    @Getter
    @OneToMany(mappedBy = "licencia", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<GestionLicencia> gestiones;

    public Licencia() {
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        this.numero = randomInt;
        this.gestiones = new ArrayList<>();
    }

    public Licencia(String claseLicencia, String observaciones, Titular titular) {
        this.claseLicencia = claseLicencia;
        this.observaciones = observaciones;
        this.titular = titular;
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        this.numero = randomInt;
        this.gestiones = new ArrayList<>();
    }

    public Licencia(LicenciaDTO licencia,Titular titular) {
        this.fechaEmision = licencia.getFechaEmision();
        this.claseLicencia=licencia.getClase();
        this.observaciones=licencia.getObservaciones();
        this.titular=titular;
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        this.numero = randomInt;
        this.gestiones=new ArrayList<>();
    }

    public void addGestionLicencia(GestionLicencia gestionLicencia) {
        this.gestiones.add(gestionLicencia);
    }


}
