package com.example.tp.DTO;

public class LicenciaDTO {
    private String tipo;
    private String observaciones;
    private TitularDTO titular;

    public LicenciaDTO() {
    }

    public LicenciaDTO(TitularDTO titular, String observaciones, String tipo) {
        this.titular = titular;
        this.observaciones = observaciones;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TitularDTO getTitular() {
        return titular;
    }

    public void setTitular(TitularDTO titular) {
        this.titular = titular;
    }
}

