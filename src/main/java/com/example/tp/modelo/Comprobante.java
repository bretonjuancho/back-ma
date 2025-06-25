package com.example.tp.modelo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;

public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal costo;

    private Integer numeroSeccion;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Administrador creador;

    // Constructores
    public Comprobante() {
    }

    public Comprobante(BigDecimal costo, Integer numeroSeccion, Administrador creador) {
        this.costo = costo;
        this.numeroSeccion = numeroSeccion;
        this.creador = creador;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Integer getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(Integer numeroSeccion) {
        this.numeroSeccion = numeroSeccion;
    }

    public Administrador getCreador() {
        return creador;
    }

    public void setCreador(Administrador creador) {
        this.creador = creador;
    }
}