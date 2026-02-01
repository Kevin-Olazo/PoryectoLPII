package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Especialidad;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.service.EspecialidadService;
import com.cibertec.medicare.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/{id}")
    public String verDetalleEspecialidad(@PathVariable Integer id, Model model) {
        // 1. Buscamos la info de la especialidad (Nombre, descripción, foto)
        Especialidad especialidad = especialidadService.obtenerPorId(id);

        // 2. Buscamos SOLO los médicos de esta especialidad
        List<Medico> medicos = medicoService.listarPorEspecialidad(id);

        // 3. Pasamos los datos a la vista
        model.addAttribute("especialidad", especialidad);
        model.addAttribute("medicos", medicos);

        return "especialidad"; // Renderiza especialidad.html
    }
}