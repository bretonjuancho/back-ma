package com.example.tp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
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


}
