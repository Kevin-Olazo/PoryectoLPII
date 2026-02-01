package com.cibertec.medicare.controller;

import com.cibertec.medicare.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController // Esto significa que retorna DATOS (JSON), no vistas HTML
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<String> obtenerHorarios(@RequestParam Integer idMedico,
                                        @RequestParam String fecha) {
        // Convertimos el String de la fecha a LocalDate
        LocalDate fechaDate = LocalDate.parse(fecha);
        return citaService.obtenerHorariosDisponibles(idMedico, fechaDate);
    }
}