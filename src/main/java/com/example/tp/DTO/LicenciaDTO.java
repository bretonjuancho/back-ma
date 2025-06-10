package com.example.tp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class LicenciaDTO {
    @Setter
    @Getter
    private String tipo;
    @Setter
    @Getter
    private String observaciones;
    @Getter
    @Setter
    private LocalDate fechaEmision;
    @Setter
    @Getter
    private TitularDTO titular;

    public LicenciaDTO() {
    }

    public LicenciaDTO(TitularDTO titular, String observaciones, String tipo) {
        this.titular = titular;
        this.observaciones = observaciones;
        this.tipo = tipo;
        this.fechaEmision = LocalDate.now();
    }

}

