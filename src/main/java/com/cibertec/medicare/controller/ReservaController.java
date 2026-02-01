package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Medico;
import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.entity.Usuario;
import com.cibertec.medicare.service.CitaService;
import com.cibertec.medicare.service.MedicoService;
import com.cibertec.medicare.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService; // Necesitamos esto para convertir Usuario -> Paciente

    // 1. Mostrar la página de detalle (Paso 3 del flujo)
    @GetMapping("/detalle")
    public String verDetalleReserva(@RequestParam Integer idMedico,
                                    HttpSession session,
                                    Model model) {

        // 1. REGLA DE ORO: Si no hay usuario en sesión -> Redirigir a REGISTRO
        if (session.getAttribute("usuarioLogin") == null) {
            return "redirect:/registro"; // Esto buscará un RegistroController (que haremos luego)
        }

        // 2. Si hay sesión, dejamos pasar
        Medico medico = medicoService.obtenerPorId(idMedico);
        model.addAttribute("medico", medico);

        return "reserva_detalle";
    }

    // 2. Procesar la reserva (Cuando dan clic en CONFIRMAR)
    @PostMapping("/guardar")
    public String guardarCita(@RequestParam Integer idMedico,
                              @RequestParam String fecha,
                              @RequestParam String hora,
                              HttpSession session,
                              Model model) {

        // A. Validar Seguridad (Doble check)
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogin");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // B. Buscar al Paciente asociado a este Usuario
        // OJO: En nuestro sistema simple, asumimos que el usuario ES un paciente.
        // Aquí deberías tener un método en PacienteService: findByUsuarioId(id)
        Paciente paciente = pacienteService.obtenerPorIdUsuario(usuarioLogueado.getIdUsuario());

        // C. Crear el objeto Cita
        Cita nuevaCita = new Cita();
        nuevaCita.setMedico(medicoService.obtenerPorId(idMedico));
        nuevaCita.setPaciente(paciente); // Asignamos el paciente real
        nuevaCita.setFechaReserva(LocalDate.parse(fecha));
        nuevaCita.setHoraReserva(LocalTime.parse(hora + ":00")); // Formato HH:mm:ss
        nuevaCita.setEstado("PENDIENTE");

        try {
            citaService.reservarCita(nuevaCita);
            return "redirect:/reserva/exito"; // Página de éxito (¡Créala rápido!)
        } catch (Exception e) {
            model.addAttribute("error", "Error al reservar: " + e.getMessage());
            model.addAttribute("medico", nuevaCita.getMedico());
            return "reserva_detalle";
        }
    }

    @GetMapping("/exito")
    public String exito() {
        return "reserva_exito"; // Crea un HTML simple que diga "¡Listo!"
    }
}