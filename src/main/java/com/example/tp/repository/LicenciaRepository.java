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
    @Query(value = "SELECT * FROM licencia WHERE clase_licencia = :claseDeLicencia AND titular_id = :titular", nativeQuery = true)
    List<Licencia> buscarLicenciaByClaseYTitular(@Param("claseDeLicencia") String  tipo, @Param("titular") int titular );


}
