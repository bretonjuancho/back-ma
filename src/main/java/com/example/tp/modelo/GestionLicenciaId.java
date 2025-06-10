package com.example.tp.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


public class GestionLicenciaId implements Serializable {
    @Getter
    @Setter
    private int licenciaId;
    @Getter
    @Setter
    private int usuarioId;

    public GestionLicenciaId() {}

    public GestionLicenciaId(int licenciaId, int usuarioId) {
        this.licenciaId = licenciaId;
        this.usuarioId = usuarioId;
    }

    public boolean equals(Object object){
        if(this == object) return true;
        if(!(object instanceof GestionLicenciaId)) return false;
        GestionLicenciaId that = (GestionLicenciaId) object;
        return Objects.equals(licenciaId, that.getLicenciaId()) && Objects.equals(usuarioId, that.getUsuarioId());
    }

    public int hashCode(){
        return Objects.hash(licenciaId, usuarioId);
    }

}
