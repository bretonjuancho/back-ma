package com.example.tp.service;


import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.AdministradorRepository;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    private AdministradorRepository bdd;

    public Boolean existeAdmin(Administrador admin) {
        Administrador previo = bdd.findByDni(admin.getDni());
        if(previo != null) {return true;}
        return false;
    }

    public Administrador crearAdministrador(Administrador admin) {
        return bdd.save(admin);
    }
}
