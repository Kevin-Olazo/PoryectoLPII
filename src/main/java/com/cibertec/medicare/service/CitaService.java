package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.entity.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaService {
    // Método principal: Intentar reservar una cita
    Cita reservarCita(Cita cita);

    // Validar si un horario está libre
    boolean estaHorarioDisponible(Medico medico, LocalDate fecha, LocalTime hora);

    // Listar citas de un paciente
    List<Cita> listarCitasPorPaciente(Paciente paciente);

    // Listar citas de un médico (agenda)
    List<Cita> listarCitasPorMedico(Medico medico);
}
