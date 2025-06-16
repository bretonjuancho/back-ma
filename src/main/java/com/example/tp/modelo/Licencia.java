package com.example.tp.modelo;


import com.example.tp.DTO.LicenciaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "licencia")

public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licencia_seq")
    @SequenceGenerator(name = "licencia_seq", sequenceName = "licencia_seq", allocationSize = 1)
    private int id;

    @Column(name = "fechaEmision")
    @Getter
    @Setter
    private LocalDate fechaEmision;

    @Getter
    @Setter
    private LocalDate fechaExpiracion;

    @Column(name = "tipo_licencia")
    @Getter
    @Setter
    private String tipoLicencia;

    @Column(name = "observaciones")
    @Getter
    @Setter
    private String observaciones;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "titular_id", nullable = false)
    private Titular titular;

    @Setter
    @Getter
    @OneToMany(mappedBy = "licencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GestionLicencia> gestiones;

    public Licencia() {

    }

    public Licencia(String tipoLicencia, String observaciones, Titular titular) {
        this.tipoLicencia = tipoLicencia;
        this.observaciones = observaciones;
        this.titular = titular;
    }

    public Licencia(LicenciaDTO licencia,Titular titular) {
        this.fechaEmision = licencia.getFechaEmision();
        this.tipoLicencia=licencia.getTipo();
        this.observaciones=licencia.getObservaciones();
        this.titular=titular;
    }


}
