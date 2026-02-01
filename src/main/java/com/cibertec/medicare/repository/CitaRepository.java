package com.cibertec.medicare.repository;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Buscar citas de un médico en una fecha específica
    List<Cita> findByMedicoAndFechaReservaAndEstadoNot(Medico medico, LocalDate fechaReserva, String estado);

    // Historial médico del doctor
    List<Cita> findByMedico(Medico medico);

    // Historial del paciente (Usando el ID del paciente)
    List<Cita> findByPacienteIdPaciente(Integer idPaciente);
}