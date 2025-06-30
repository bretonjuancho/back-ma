package com.example.tp.repository;

import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador,Integer> {
    Administrador findByDni(String dni);
}
