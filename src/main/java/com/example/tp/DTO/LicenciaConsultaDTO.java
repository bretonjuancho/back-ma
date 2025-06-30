package com.example.tp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaConsultaDTO
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaEmisionDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaEmisionHasta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaVencimientoDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fechaVencimientoHasta;
    boolean vigente;
    String clase;
    String nombre;
    String apellido;
    int numeroLicencia;
    String grupoSanguineo;
    String factorRH;
    boolean donante;

}
