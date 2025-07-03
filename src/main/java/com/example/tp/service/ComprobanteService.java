package com.example.tp.service;


import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.excepciones.licencia.ComprobanteNoCreado;
import com.example.tp.modelo.Comprobante;
import com.example.tp.modelo.Licencia;
import com.example.tp.modelo.Usuario;
import com.example.tp.repository.ComprobanteRepository;
import com.example.tp.repository.LicenciaRepository;
import com.example.tp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ComprobanteService {
    @Autowired
    private ComprobanteRepository comprobanteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LicenciaRepository licenciaRepository;
    public Usuario getLogingUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Dni = ((User) authentication.getPrincipal()).getUsername();
        return usuarioRepository.findByDni(Dni);
    }
    public Comprobante guardar(Licencia licencia, double costo, String concepto) throws ComprobanteNoCreado{
            Usuario usuario = getLogingUser();
            Comprobante comprobante = new Comprobante();
            comprobante.setCosto(costo);
            comprobante.setCreador(usuario);
            comprobante.setFecha(LocalDate.now());
            comprobante.setNumeroSeccion(1);
            comprobante.setConcepto(concepto);
            comprobante.setLicenciaAsociada(licencia);
            Comprobante c = comprobanteRepository.save(comprobante);
            if(c==null)throw new ComprobanteNoCreado("No se pudo crear el comprobante");
            return c;
    }
}
