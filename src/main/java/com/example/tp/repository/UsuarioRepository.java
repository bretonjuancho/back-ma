package com.example.tp.repository;

import com.example.tp.modelo.Titular;
import com.example.tp.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByDni(String dni);
}
