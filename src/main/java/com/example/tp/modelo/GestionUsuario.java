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
@Table(name = "gestion_usuario")
@Getter
@Setter
public class GestionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
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
}
