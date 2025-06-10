package com.example.tp.modelo;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Entity
@Table(name = "gestion_licencia")
public class GestionLicencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "licencia_id", nullable = false)
    @Setter
    @Getter
    private Licencia licencia;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Setter
    @Getter
    private Usuario usuario;

    @Column(name = "motivo")
    @Getter
    @Setter
    private String motivo;

    @Column(name = "fecha_tiempo")
    @Getter
    @Setter
    private LocalDateTime fechaTiempo;

    public GestionLicencia() {}
    public GestionLicencia(Licencia licencia, Usuario usuario) {
        this.licencia = licencia;
        this.usuario = usuario;
        this.fechaTiempo = LocalDateTime.now();
    }


}
