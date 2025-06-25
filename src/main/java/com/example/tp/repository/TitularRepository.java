package com.example.tp.repository;

import com.example.tp.modelo.Titular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitularRepository extends JpaRepository<Titular, Integer>{
    Titular findByDocumento(String documento);
    @Query(value = "select * from titular " +
                    "where (:nroDocumento IS NULL OR documento = :nroDocumento) AND (:apellidoTitular IS NULL OR apellido = :apellidoTitular) AND (:nombreTitular IS NULL OR nombre = :nombreTitular)",
    nativeQuery = true)
    List<Titular> findByDocuementoNombreApellido(@Param("nroDocumento") String documento,
                                                 @Param("nombreTitular") String nombre,
                                                 @Param("apellidoTitular") String apellido);
}