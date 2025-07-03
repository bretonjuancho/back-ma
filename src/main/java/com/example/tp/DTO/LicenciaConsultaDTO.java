package com.example.tp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaConsultaDTO
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaEmisionDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaEmisionHasta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaVencimientoDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaVencimientoHasta;
    Boolean vigente;
    String clase;
    String nombre;
    String apellido;
    int numeroLicencia;
    String grupoSanguineo;
    String factorRH;
    Boolean donante;

    public LocalDate getFechaEmisionDesde() {
        return fechaEmisionDesde;
    }

    public LocalDate getFechaVencimientoDesde() {
        return fechaVencimientoDesde;
    }

    public LocalDate getFechaEmisionHasta() {
        return fechaEmisionHasta;
    }

    public LocalDate getFechaVencimientoHasta() {
        return fechaVencimientoHasta;
    }

    public Boolean isVigente() {
        return vigente;
    }

    public String getClase() {
        return clase;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getNumeroLicencia() {
        return numeroLicencia;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public String getFactorRH() {
        return factorRH;
    }

    public Boolean isDonante() {
        return donante;
    }

    public void setFechaEmisionDesde(LocalDate fechaEmisionDesde) {
        this.fechaEmisionDesde = fechaEmisionDesde;
    }

    public void setDonante(Boolean donante) {
        this.donante = donante;
    }

    public void setFactorRH(String factorRH) {
        this.factorRH = factorRH;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public void setNumeroLicencia(int numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public void setFechaVencimientoHasta(LocalDate fechaVencimientoHasta) {
        this.fechaVencimientoHasta = fechaVencimientoHasta;
    }

    public void setFechaVencimientoDesde(LocalDate fechaVencimientoDesde) {
        this.fechaVencimientoDesde = fechaVencimientoDesde;
    }

    public void setFechaEmisionHasta(LocalDate fechaEmisionHasta) {
        this.fechaEmisionHasta = fechaEmisionHasta;
    }
}

