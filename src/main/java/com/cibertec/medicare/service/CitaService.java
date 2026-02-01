package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.entity.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaService {
    Cita reservarCita(Cita cita);
    boolean estaHorarioDisponible(Medico medico, LocalDate fecha, LocalTime hora);
    List<Cita> listarCitasPorPaciente(Paciente paciente);
    List<Cita> listarCitasPorMedico(Medico medico);

    // Método nuevo para la lógica dinámica
    List<String> obtenerHorariosDisponibles(Integer idMedico, LocalDate fecha);

    void cancelarCita(Integer idCita);
}