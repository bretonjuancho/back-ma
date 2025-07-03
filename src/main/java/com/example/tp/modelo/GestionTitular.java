package com.example.tp.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "gestion_titular")
@Getter
@Setter
public class GestionTitular {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titularId", nullable = false)
    @JsonBackReference(value = "titular-gestiones")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Titular titular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuario-gestionestitular")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    @Column(name = "motivo")
    @Getter
    @Setter
    private String motivo;

    @Column(name = "fecha_tiempo")
    @Getter
    @Setter
    private LocalDateTime fechaTiempo;

    public GestionTitular() {}
    public GestionTitular(Titular titular, Usuario usuario,String motivo) {
        this.titular = titular;
        this.usuario = usuario;
        this.motivo = motivo;
        this.fechaTiempo = LocalDateTime.now();
    }
}
