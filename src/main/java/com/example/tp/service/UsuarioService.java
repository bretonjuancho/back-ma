package com.example.tp.service;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.excepciones.usuario.UsuarioDatosInvalidosException;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UsuarioService{

    public Usuario crearUsuario(UsuarioDTO usuarioDTO);
    public boolean usuarioExiste(UsuarioDTO usuarioDTO);
    public boolean datosUsuarioValido(UsuarioDTO usuarioDTO);
    public List<Usuario> obtenerUsuario(UsuarioDTO usuarioDTO);

}