package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NavegacionController {

    @Autowired
    private MedicoService medicoService;

    // PANTALLA 1: La Bifurcación (Especialidad vs Doctor)
    @GetMapping("/reservar/tipo")
    public String mostrarSeleccionTipo() {
        return "seleccion_tipo";
    }

    // PANTALLA 2: Catálogo Global de Doctores
    @GetMapping("/staff")
    public String mostrarStaffCompleto(Model model) {
        List<Medico> lista = medicoService.listarTodos();
        model.addAttribute("medicos", lista);
        return "staff_global";
    }

    // PANTALLA 4: Catálogo de Especialidades (Nueva página)
    @GetMapping("/especialidades")
    public String mostrarEspecialidades(Model model) {
        // Reutilizamos el servicio que ya tienes para listar todas
        // Nota: Asegúrate de inyectar EspecialidadService si no lo tienes aquí
        return "especialidades"; // HTML nuevo
    }
}