package com.example.tp.service;


import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.AdministradorRepository;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    @Autowired
    private AdministradorRepository bdd;

    public Boolean existeAdmin(String dni) {
        Administrador previo = bdd.findByDni(dni);
        if(previo != null) {return true;}
        return false;
    }

    public Administrador getLogingUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String dni = ((User) authentication.getPrincipal()).getUsername();
        return bdd.findByDni(dni);
    }

}
