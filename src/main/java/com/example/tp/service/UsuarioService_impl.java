package com.example.tp.service;

import com.example.tp.DTO.TitularDTO;
import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.excepciones.bddException.ErrorAlAccederABDDException;
import com.example.tp.excepciones.usuario.*;
import com.example.tp.modelo.Titular;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.UsuarioRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService_impl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioService_impl(){}

    private boolean validarTexto(String texto){
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    private boolean validarEmail(String email){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
    private boolean validarPassword(String password){
        if(password.length() < 8) return false;
        if(!password.matches("[^(?=.*[!@#$%^&*()_+\\\\-\\\\=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>/?])(?!.*\\\\s).+$]")) return false;
        if(!password.matches("[0-9]+")) return false;
        return true;
    }
    @Override
    public boolean datosUsuarioValido(UsuarioDTO usuarioDTO) throws UsuarioDatosInvalidosException{
        if (!validarTexto(usuarioDTO.getNombre())) throw new UsuarioDatosInvalidosException("Nombre del usuario invalido: "+usuarioDTO.getNombre());
        if(!validarTexto(usuarioDTO.getApellido())) throw new UsuarioDatosInvalidosException("Apellido del usuario invalido: "+ usuarioDTO.getApellido());
        if(!validarEmail(usuarioDTO.getEmail())) throw new UsuarioDatosInvalidosException("Email del usuario incorrecto: "+ usuarioDTO.getEmail());
        if(!validarPassword(usuarioDTO.getPassword())) throw new UsuarioDatosInvalidosException("Password invalida, no cumple con las caracteristicas.");
        return true;
    }


    public boolean usuarioExiste(UsuarioDTO usuarioDTO) throws ErrorAlAccederABDDException{
        try{
            Usuario usuario = usuarioRepository.findByDni(usuarioDTO.getDni());
            return usuario != null;
        }catch (DataAccessException e){
            System.err.println(e.getMessage());
            throw new ErrorAlAccederABDDException("Se produjo un error al intentar acceder a la base de datos de Usuarios. Error: "+e.getMessage());
        }
    }

    public Usuario getLogingUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String Dni = ((User) authentication.getPrincipal()).getUsername();
         return usuarioRepository.findByDni(Dni);
    }

    public Usuario obtenerUsuario(UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByDni(usuarioDTO.getDni());
    }

    @Override
    public Usuario crearUsuario(UsuarioDTO usuarioDTO){
        String dni =  usuarioDTO.getDni();
        String nombre = usuarioDTO.getNombre();
        String apellido = usuarioDTO.getApellido();
        String email = usuarioDTO.getEmail();
        String password = usuarioDTO.getPassword();
        Usuario usuario = new Usuario(dni,nombre, apellido, email, password);
        usuarioRepository.save(usuario);

        //registrar quien lo hizo
        return usuario;
    }

    public Usuario modificarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findByDni(usuarioDTO.getDni());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuarioRepository.save(usuario);

        //gestion del admin
        return usuario;


    }


}