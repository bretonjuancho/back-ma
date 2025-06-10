package com.example.tp.service;


import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    private UsuarioRepository bdd;

    public void guardarUsuario(UsuarioDTO usuario) {
        Usuario nuevo=new Usuario(usuario);
        //hacer la gestion

        bdd.save(nuevo);
    }
}
