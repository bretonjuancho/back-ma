package com.example.tp.repository;


import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Titular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {
    @Query(value = "SELECT * FROM Licencia WHERE tipoLicencia = :tipoDeLicencia AND titular_id = :titular", nativeQuery = true)
    List<Licencia> buscarLicenciaByTipoYTitular(@Param("tipoDeLicencia") String  tipo, @Param("titular") int titular );

}
