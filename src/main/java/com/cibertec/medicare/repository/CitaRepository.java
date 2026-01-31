package com.cibertec.medicare.repository;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByMedicoAndFechaReserva(Medico medico, LocalDate fechaReserva);

    List<Cita> findByMedico(Medico medico);

    List<Cita> findByPacienteIdPaciente(Integer idPaciente);

}
