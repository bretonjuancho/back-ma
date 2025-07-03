package com.example.tp.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "gestion_usuario")
@Getter
@Setter
public class GestionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value="usuario-gestionusuario")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id", nullable = false)
    @JsonBackReference(value="usuario-admin")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Administrador administrador;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaTiempo;

    public GestionUsuario() {}
    public GestionUsuario(Usuario usuario, Administrador administrador) {
        this.usuario = usuario;
        this.administrador = administrador;
        this.fechaTiempo = LocalDateTime.now();
    }
    public GestionUsuario(Usuario usuario, Administrador administrador, String motivo) {
        this.usuario = usuario;
        this.administrador = administrador;
        this.fechaTiempo = LocalDateTime.now();
        this.motivo = motivo;
    }
}
