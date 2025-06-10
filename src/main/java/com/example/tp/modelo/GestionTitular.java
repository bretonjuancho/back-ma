package com.example.tp.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "titularId", nullable = false)
    private Titular titular;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
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
