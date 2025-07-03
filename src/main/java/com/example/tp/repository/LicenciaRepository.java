package com.example.tp.repository;


import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {
    @Query(value = "SELECT * FROM licencia WHERE clase_licencia = :claseDeLicencia AND titular_id = :titular", nativeQuery = true)
    List<Licencia> buscarLicenciaByClaseYTitular(@Param("claseDeLicencia") String tipo, @Param("titular") int titular);

    Licencia findByNumero(int numero);

    @Query(value = "SELECT * " +
            "FROM licencia  l INNER JOIN titular t ON l.titular_id = t.idTitular " +
            "WHERE (:fechaDeEmision IS NULL OR l.fecha_emision = :fechaDeEmision) AND " +
            "t.nombre LIKE :nombreTitular AND " +
            "t.apellido LIKE :apellidoTitular AND " +
            "(:numeroLicencia IS NULL OR l.numero = :numeroLicencia) AND " +
            "(:fechaDeVencimiento IS NULL OR l.fecha_expiracion = :fechaDeVencimiento) AND " +
            "t.grupo_sanguineo LIKE :grupoSanguineo AND " +
            "t.fector_rh LIKE :factor AND " +
            "(:donante IS NULL OR t.donante = :donante)", nativeQuery = true)
    List<Licencia> buscarLicenciasVigentes(@Param("fechaDeEmision") LocalDate fechaDeEmision,
                                           @Param("fechaDeVencimiento") LocalDate fechaDeVencimiento,
                                           @Param("nombreTitular") String nombreTitular,
                                           @Param("apellidoTitular") String apellidoTitular,
                                           @Param("numeroLicencia") int numero,
                                           @Param("grupoSanguineo") String grupo,
                                           @Param("factor") String factor,
                                           @Param("donante") Boolean donante);


    @Query(value = "SELECT * " +
            "FROM licencia  l INNER JOIN titular t ON l.titular_id = t.id_titular " +
            "WHERE (:fechaEmisionDesde IS NULL OR l.fecha_emision >= :fechaEmisionDesde) AND " +
            "(:fechaEmisionHasta IS NULL OR l.fecha_emision <= :fechaEmisionHasta) AND " +
            "(:fechaVencimientoDesde IS NULL OR l.fecha_expiracion >= :fechaVencimientoDesde) AND " +
            "(:fechaVencimientoHasta IS NULL OR l.fecha_expiracion <= :fechaVencimientoHasta) AND " +
            "(:nombre IS NULL OR t.nombre = :nombre) AND " +
            "(:apellido IS NULL OR t.apellido = :apellido) AND " +
            "(:numeroLicencia IS NULL OR l.numero = :numeroLicencia) AND " +
            "(:fechaVencimientoDesde IS NULL OR l.fecha_expiracion = :fechaVencimientoDesde) AND " +
            "(:grupoSanguineo IS NULL OR t.grupo_sanguineo = :grupoSanguineo) AND " +
            "(:factorRH IS NULL OR t.fector_rh = :factorRH) AND " +
            "(:donante IS NULL OR t.donante = :donante)", nativeQuery = true)
    List<Licencia> buscarLicencias(@Param("fechaEmisionDesde") LocalDate fechaEmisionDesde,
                                   @Param("fechaEmisionHasta") LocalDate fechaEmisionHasta,
                                   @Param("fechaVencimientoDesde") LocalDate fechaVencimientoDesde,
                                   @Param("fechaVencimientoHasta") LocalDate fechaVencimientoHasta,
                                   @Param("vigente") Boolean vigente,
                                   @Param("clase") String clase,
                                   @Param("nombre") String nombre,
                                   @Param("apellido") String apellido,
                                   @Param("numeroLicencia") Integer numeroLicencia,
                                   @Param("grupoSanguineo") String grupoSanguineo,
                                   @Param("factorRH") String factorRH,
                                   @Param("donante") Boolean donante);

    @Query(
            value = "SELECT * FROM licencia WHERE clase_licencia = :claseDeLicencia AND titular_id = :titular AND fecha_expiracion >= :fechaActual", nativeQuery = true
    )
    Licencia findLicenciaByTitularAndClaseActiva(
            @Param("titular") int titular,
            @Param("claseDeLicencia") String claseDeLicencia,
            @Param("fechaActual") LocalDate fechaActual);

}