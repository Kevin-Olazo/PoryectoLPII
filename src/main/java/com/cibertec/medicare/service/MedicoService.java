package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Medico;
import java.util.List;

public interface MedicoService {
    List<Medico> listarPorEspecialidad(Integer idEspecialidad);
    Medico obtenerPorId(Integer id);
    List<Medico> listarTodos();
}