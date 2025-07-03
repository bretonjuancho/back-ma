package com.example.tp.modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "gestion_licencia")
public class GestionLicencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "licencia_id", nullable = false)
    @JsonBackReference(value = "licencia-gestiones")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter
    @Getter
    private Licencia licencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuario-gestioneslicencias")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
    public GestionLicencia(Licencia licencia, Usuario usuario, String motivo) {
        this.licencia = licencia;
        this.usuario = usuario;
        this.fechaTiempo = LocalDateTime.now();
    }


}
