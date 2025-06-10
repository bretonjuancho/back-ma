package com.example.tp.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class GestionUsuarioId {
    @Getter
    @Setter
    private int usuarioId;
    @Getter
    @Setter
    private int administradorId;

    public GestionUsuarioId() {}

    public GestionUsuarioId(int usuarioId, int administradorId) {
        this.usuarioId = usuarioId;
        this.administradorId = administradorId;
    }

    public boolean equals(Object object){
        if(this == object) return true;
        if(!(object instanceof GestionUsuarioId)) return false;
        GestionUsuarioId that = (GestionUsuarioId) object;
        return Objects.equals(usuarioId, that.getUsuarioId()) && Objects.equals(administradorId, that.getAdministradorId());
    }

    public int hashCode(){
        return Objects.hash(usuarioId, administradorId);
    }
}
