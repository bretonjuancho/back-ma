package com.example.tp.DTO;

import java.time.LocalDate;
import java.util.Date;

public class TitularDTO {
    private String nombre;
    private String apellido;
    private String documento;
    private String tipoDocumento;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String grupoSanquineo;
    private String factorRH;
    private boolean donante;

    public TitularDTO(String nombre, String apellido, String documento,
                      String tipoDocumento, LocalDate fechaNacimiento, String direccion,
                      String grupoSanquineo, String factorRH, boolean donante) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.grupoSanquineo = grupoSanquineo;
        this.factorRH = factorRH;
        this.donante = donante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGrupoSanquineo() {
        return grupoSanquineo;
    }

    public void setGrupoSanquineo(String grupoSanquineo) {
        this.grupoSanquineo = grupoSanquineo;
    }

    public String getFactorRH() {
        return factorRH;
    }

    public void setFactorRH(String factorRH) {
        this.factorRH = factorRH;
    }

    public boolean isDonante() {
        return donante;
    }

    public void setDonante(boolean donante) {
        this.donante = donante;
    }
}
