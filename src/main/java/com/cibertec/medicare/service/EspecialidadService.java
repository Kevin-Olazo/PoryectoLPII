package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Especialidad;
import java.util.List;

public interface EspecialidadService {
    List<Especialidad> listarTodas();
    Especialidad obtenerPorId(Integer id);
}