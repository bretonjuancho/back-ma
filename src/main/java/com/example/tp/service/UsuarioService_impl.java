package com.example.tp.service;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.excepciones.bddException.ErrorAlAccederABDDException;
import com.example.tp.excepciones.usuario.*;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.UsuarioRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.dao.DataAccessException;


public class UsuarioService_impl implements UsuarioService{


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
    public boolean datosUsuarioValido(UsuarioDTO usuarioDTO) throws UsuarioDatosInvalidosException {
        if (validarTexto(usuarioDTO.getNombre())) return true;
        if(validarTexto(usuarioDTO.getApellido())) return true;
        if(validarEmail(usuarioDTO.getEmail())) return true;
        if(validarPassword(usuarioDTO.getPassword())) return true;
        throw new UsuarioDatosInvalidosException("Los datos ingresados del usuario son invalidos");
    }


    private UsuarioRepository usuarioRepository;
    public boolean usuarioExiste(UsuarioDTO usuarioDTO) throws ErrorAlAccederABDDException{
        try{
            Usuario usuario = usuarioRepository.findByDni(usuarioDTO.getDni());
            return usuario != null;
        }catch (DataAccessException e){
            System.err.println(e.getMessage());
            throw new ErrorAlAccederABDDException("Se produjo un error al intentar acceder a la base de datos de Usuarios. Error: "+e.getMessage());
        }
    }

    public Usuario obtenerUsuario(UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByDni(usuarioDTO.getDni());
    }

    @Override
    public void crearUsuario(UsuarioDTO usuarioDTO){
        String dni =  usuarioDTO.getDni();
        String nombre = usuarioDTO.getNombre();
        String apellido = usuarioDTO.getApellido();
        String email = usuarioDTO.getEmail();
        String password = usuarioDTO.getPassword();
        Usuario usuario = new Usuario(dni,nombre, apellido, email, password);
        usuarioRepository.save(usuario);
    }

}