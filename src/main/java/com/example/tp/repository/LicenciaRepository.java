package com.example.tp.repository;


import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
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
    List<Licencia> buscarLicenciaByClaseYTitular(@Param("claseDeLicencia") String  tipo, @Param("titular") int titular );

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
}
