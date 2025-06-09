package com.example.tp.service;

import com.example.tp.DTO.LicenciaDTO;
import com.example.tp.modelo.Licencia;
import org.springframework.stereotype.Service;

@Service
public interface LicenciaService {
    public boolean edadMinima(LicenciaDTO licencia);
    public boolean profesional(LicenciaDTO licencia);
}
