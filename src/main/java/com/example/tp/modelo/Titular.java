package com.example.tp.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "titular")
public class Titular {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titular_seq")
    @SequenceGenerator(name = "titular_seq", sequenceName = "titular_seq", allocationSize = 1)

    @Column(name = "idTitular")
    private int id;

    @Column(name = "documento")
    private String documento;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;

    @Column(name = "factor_rh")
    private String factorRh;

    @Column(name = "donante")
    private boolean donante;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Licencia> licencias;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GestionTitular> gestiones;

    public Titular() {

    }

    public Titular(String documento,String tipoDocumento, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String grupoSanguineo, String factorRh,boolean donante,List<Licencia> licencias,List <GestionTitular> gestiones) {
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
        this.donante = donante;
        this.licencias = licencias;
        this.gestiones = gestiones;

    }

    public void addLicencia(Licencia lic){
        this.licencias.add(lic);
    }
    public void removeLicencia(Licencia lic){
        this.licencias.remove(lic);
    }

}