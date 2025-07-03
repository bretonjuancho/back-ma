package com.example.tp;

import com.example.tp.DTO.UsuarioDTO;
import com.example.tp.controllers.UsuarioController;
import com.example.tp.excepciones.usuario.UsuarioDatosInvalidosException;
import com.example.tp.service.UsuarioService;
import com.example.tp.service.UsuarioService_impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioTest {
    @Autowired
    private UsuarioService_impl usuarioService;

    UsuarioDTO usuarioDTO1 = new UsuarioDTO("45489343","Mateo","Gastaldi","mateogastaldi@gmail.com","21n3J$2jn23");
    UsuarioDTO usuarioDTO2 = new UsuarioDTO("32789343", "Juan", "Perez","juancho31@gmail.com","Juannn1__");
    UsuarioDTO usuarioDTO3 = new UsuarioDTO("32893435", "Juan35", "Perez","juancho31@gmail.com","Juannn1__");
    UsuarioDTO usuarioDTO4 = new UsuarioDTO("32893435", "Juan", "Pere54z","juancho31@gmail.com","Juannn1__");
    UsuarioDTO usuarioDTO5 = new UsuarioDTO("32893435", "Juan", "Perez","juancho31gmail.com","Juannn1__");
    UsuarioDTO usuarioDTO6 = new UsuarioDTO("32893435", "Juan", "Pere54z","juancho31@gmail.com","uannn");
    UsuarioDTO usuarioDTO7 = new UsuarioDTO("4548kj9343","Mateo","Gastaldi","mateogastaldi@gmail.com","21n3J$2jn23");
    @Test
    public void pruebaUsuarioNoExistente(){
        Assertions.assertFalse(usuarioService.usuarioExiste(usuarioDTO1));

    }
    @Test
    public void pruebaUsuarioExistente(){
        Assertions.assertFalse(usuarioService.usuarioExiste(usuarioDTO2));
    }
    @Test
    public void pruebaDatosValidos(){
        Assertions.assertTrue(usuarioService.datosUsuarioValido(usuarioDTO1));
    }
    @Test
    public void pruebaDNIInvalido(){
        Assertions.assertThrows(UsuarioDatosInvalidosException.class,()->{usuarioService.datosUsuarioValido(usuarioDTO7);});
    }
    @Test
    public void pruebaNombreInvalido(){
        Assertions.assertThrows(UsuarioDatosInvalidosException.class,()->{usuarioService.datosUsuarioValido(usuarioDTO3);});
    }
    @Test
    public void pruebaApellidoInvalido(){
        Assertions.assertThrows(UsuarioDatosInvalidosException.class,()->{usuarioService.datosUsuarioValido(usuarioDTO4);});
    }
    @Test
    public void pruebaEmailInvalido(){
        Assertions.assertThrows(UsuarioDatosInvalidosException.class,()->{usuarioService.datosUsuarioValido(usuarioDTO5);});
    }
    @Test
    public void pruebaContraInvalido(){
        Assertions.assertThrows(UsuarioDatosInvalidosException.class,()->{usuarioService.datosUsuarioValido(usuarioDTO6);});
    }

}
