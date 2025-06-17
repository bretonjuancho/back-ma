package com.example.tp.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name="tipo_documento")
    private String tipoDocumento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name= "fecha_nacimento")
    private LocalDate fechaNacimiento;

    @Column(name="direccion")
    private String direccion;

    @Column(name="grupo_sanguineo")
    private String grupoSanguineo;

    @Column(name="fector_rh")
    private String fectorRH;

    @Column(name="donante")
    private boolean donante;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "titular-licencias")
    private List<Licencia> licencias;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<GestionTitular> gestiones;

    public void addLicencia(Licencia lic){
        this.licencias.add(lic);
    }
    public void removeLicencia(Licencia lic){
        this.licencias.remove(lic);
    }

    //comstructor personalizado
    public Titular(String nombre, String apellido, String documento,String tipoDocumento, LocalDate fechaNacimiento, String direccion, String grupoSanguineo, String fectorRH, boolean donante) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.grupoSanguineo = grupoSanguineo;
        this.fectorRH = fectorRH;
        this.donante = donante;
    }

}