package com.cibertec.medicare.repository;

import com.cibertec.medicare.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Paciente findByDni(String dni);

    Paciente findByUsuarioIdUsuario(Integer idUsuario);

}
