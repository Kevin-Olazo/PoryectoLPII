package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.repository.CitaRepository;
import com.cibertec.medicare.repository.MedicoRepository;
import com.cibertec.medicare.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository; // Movido aquí arriba por orden

    @Override
    public boolean estaHorarioDisponible(Medico medico, LocalDate fecha, LocalTime hora) {
        // CAMBIO IMPORTANTE:
        // Usamos el método nuevo que ignora las citas con estado "CANCELADO"
        List<Cita> citasDelDia = citaRepository.findByMedicoAndFechaReservaAndEstadoNot(medico, fecha, "CANCELADO");

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
        // Nota: Asegúrate que tu Repository tenga este método exacto
        return citaRepository.findByPacienteIdPaciente(paciente.getIdPaciente());
    }

    @Override
    public List<Cita> listarCitasPorMedico(Medico medico) {
        return citaRepository.findByMedico(medico);
    }

    @Override
    public List<String> obtenerHorariosDisponibles(Integer idMedico, LocalDate fecha) {
        // 1. Definimos TODAS las horas posibles de atención (Horario Maestro)
        // Usamos ArrayList "new ArrayList<>(...)" para que la lista sea modificable (para poder borrar elementos luego)
        List<String> horariosTotales = new ArrayList<>(Arrays.asList(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
        ));

        // 2. Buscamos las citas que YA existen ese día para ese doctor
        Medico medico = medicoRepository.findById(idMedico).orElse(null);
        List<Cita> citasOcupadas = citaRepository.findByMedicoAndFechaReservaAndEstadoNot(medico, fecha, "CANCELADO");

        // 3. Sacamos solo las horas de esas citas ocupadas
        List<String> horasOcupadas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Cita c : citasOcupadas) {
            horasOcupadas.add(c.getHoraReserva().format(formatter));
        }

        // 4. RESTA: A los totales le quitamos los ocupados (removeAll)
        horariosTotales.removeAll(horasOcupadas);

        return horariosTotales;
    }

    @Override
    public void cancelarCita(Integer idCita) {
        // Buscamos la cita
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Cambiamos el estado
        cita.setEstado("CANCELADO");

        // Guardamos
        citaRepository.save(cita);
    }
}