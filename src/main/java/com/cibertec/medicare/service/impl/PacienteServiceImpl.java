package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.repository.PacienteRepository;
import com.cibertec.medicare.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente obtenerPorIdUsuario(Integer idUsuario) {
        // Usa el método mágico que creamos en el repositorio
        return pacienteRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
}