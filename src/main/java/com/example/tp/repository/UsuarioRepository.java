package com.example.tp.repository;

import com.example.tp.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByDni(String dni);
    @Query(value = "SELECT * FROM usuario " +
            "WHERE (:dni IS NULL OR dni = :dni AND :nombre IS NULL OR nombre = :nombre AND :apellido IS NULL OR apellido = :apellido)",
            nativeQuery = true
    )
    List<Usuario> obtenerUsuarios(@Param("dni") String dni,
                                  @Param("nombre") String nombre,
                                  @Param("apellido") String apellido);
    Usuario findByEmail(String email);
}
