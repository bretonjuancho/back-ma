package com.example.tp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class LicenciaDTO {
    @Setter
    @Getter
    private String clase;
    @Setter
    @Getter
    private String observaciones;
    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaEmision;
    @Setter
    @Getter
    private TitularDTO titular;

    public LicenciaDTO() {
    }

    public LicenciaDTO(TitularDTO titular, String observaciones, String clase) {
        this.titular = titular;
        this.observaciones = observaciones;
        this.clase = clase;
        this.fechaEmision = LocalDate.now();
    }

}

