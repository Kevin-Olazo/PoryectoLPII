package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.repository.CitaRepository;
import com.cibertec.medicare.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public boolean estaHorarioDisponible(Medico medico, LocalDate fecha, LocalTime hora) {
        // Obtenemos todas las citas de ese médico en esa fecha
        List<Cita> citasDelDia = citaRepository.findByMedicoAndFechaReserva(medico, fecha);

        // Revisamos si alguna coincide con la hora exacta que queremos
        for (Cita c : citasDelDia) {
            if (c.getHoraReserva().equals(hora)) {
                return false; // ¡Ya está ocupado!
            }
        }
        return true; // Está libre
    }

    @Override
    public Cita reservarCita(Cita cita) {
        // 1. Validar disponibilidad antes de guardar
        if (!estaHorarioDisponible(cita.getMedico(), cita.getFechaReserva(), cita.getHoraReserva())) {
            throw new RuntimeException("El horario seleccionado ya no está disponible.");
        }

        // 2. Establecer estado inicial
        cita.setEstado("PENDIENTE");

        // 3. Guardar
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitasPorPaciente(Paciente paciente) {
        return citaRepository.findByPacienteIdPaciente(paciente.getIdPaciente());
    }

    @Override
    public List<Cita> listarCitasPorMedico(Medico medico) {
        return citaRepository.findByMedico(medico);
    }
}
