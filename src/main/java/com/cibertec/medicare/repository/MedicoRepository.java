package com.cibertec.medicare.repository;

import com.cibertec.medicare.entity.Especialidad;
import com.cibertec.medicare.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    List<Medico> findByEspecialidad(Especialidad especialidad);

    Medico findByUsuarioIdUsuario(Integer idUsuario);


}
