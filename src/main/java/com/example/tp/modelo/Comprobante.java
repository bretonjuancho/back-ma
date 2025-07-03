package com.example.tp.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@Table(name = "comprobante")
@Getter
@Setter
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "comprobante_seq", sequenceName = "comprobante_seq", allocationSize = 1)
    private Long id;

    @Column(name = "costo")
    private double costo;

    @Column(name = "seccion")
    //este valor se usa para representar un numero ficticio que es constante e identifica al registro automotor
    private Integer numeroSeccion;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "fecha_emision")
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "licencia_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Licencia licenciaAsociada;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    @JsonBackReference(value = "admin-comprobante")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario creador;

    // Constructores
    public Comprobante() {
    }

    public Comprobante(double costo, Integer numeroSeccion, Usuario creador) {
        this.costo = costo;
        this.numeroSeccion = numeroSeccion;
        this.creador = creador;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Integer getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(Integer numeroSeccion) {
        this.numeroSeccion = numeroSeccion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
}