package com.example.tp.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class GestionTitularId {

    @Getter
    @Setter
    private int titularId;
    @Getter
    @Setter
    private int usuarioId;

    public GestionTitularId() {}

    public GestionTitularId(int licenciaId, int usuarioId) {
        this.titularId = licenciaId;
        this.usuarioId = usuarioId;
    }

    public boolean equals(Object object){
        if(this == object) return true;
        if(!(object instanceof GestionTitularId)) return false;
        GestionTitularId that = (GestionTitularId) object;
        return Objects.equals(titularId, that.getTitularId()) && Objects.equals(usuarioId, that.getUsuarioId());
    }

    public int hashCode(){
        return Objects.hash(titularId, usuarioId);
    }
}
