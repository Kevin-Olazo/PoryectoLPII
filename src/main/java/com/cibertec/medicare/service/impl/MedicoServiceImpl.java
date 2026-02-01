package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Especialidad;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.repository.MedicoRepository;
import com.cibertec.medicare.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Medico> listarPorEspecialidad(Integer idEspecialidad) {
        // Creamos una especialidad "falsa" solo con el ID para buscar
        Especialidad esp = new Especialidad();
        esp.setIdEspecialidad(idEspecialidad);
        return medicoRepository.findByEspecialidad(esp);
    }

    @Override
    public Medico obtenerPorId(Integer id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }
}