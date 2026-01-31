package com.cibertec.medicare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_cita")
@Getter
@Setter
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaReserva;

    @Column(name = "estado", length = 20)
    private String estado; // PENDIENTE, ATENDIDO, CANCELADO

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "receta_medica", columnDefinition = "TEXT")
    private String recetaMedica;

    // Relación con Paciente (FK: id_paciente)
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    // Relación con Medico (FK: id_medico)
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;
}