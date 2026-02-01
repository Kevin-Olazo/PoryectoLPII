package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Paciente;

public interface PacienteService {
    Paciente obtenerPorIdUsuario(Integer idUsuario);
    Paciente registrarPaciente(Paciente paciente);
}