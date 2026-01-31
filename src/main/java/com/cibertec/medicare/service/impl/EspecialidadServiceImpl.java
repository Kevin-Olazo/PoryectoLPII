package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Especialidad;
import com.cibertec.medicare.repository.EspecialidadRepository;
import com.cibertec.medicare.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    @Override
    public Especialidad obtenerPorId(Integer id) {
        return especialidadRepository.findById(id).orElse(null);
    }
}