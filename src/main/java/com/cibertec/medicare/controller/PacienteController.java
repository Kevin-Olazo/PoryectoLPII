package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Cita;
import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.entity.Usuario;
import com.cibertec.medicare.service.CitaService;
import com.cibertec.medicare.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;

    @GetMapping("/mis-citas")
    public String verMisCitas(HttpSession session, Model model) {
        // 1. SEGURIDAD: Validar que haya sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogin");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // 2. Obtener los datos del Paciente real (usando el ID del Usuario)
        Paciente paciente = pacienteService.obtenerPorIdUsuario(usuarioLogueado.getIdUsuario());

        // 3. Buscar las citas de este paciente
        List<Cita> misCitas = citaService.listarCitasPorPaciente(paciente);

        // 4. Pasar los datos a la vista
        model.addAttribute("citas", misCitas);
        model.addAttribute("paciente", paciente); // Para mostrar "Hola, Kevin"

        return "mis_citas";
    }

    @PostMapping("/cita/cancelar")
    public String cancelarCita(@RequestParam Integer idCita,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        // 1. Validar seguridad (que esté logueado)
        if (session.getAttribute("usuarioLogin") == null) {
            return "redirect:/login";
        }

        try {
            citaService.cancelarCita(idCita);
            redirectAttributes.addFlashAttribute("mensaje", "Cita cancelada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo cancelar la cita.");
        }

        return "redirect:/paciente/mis-citas";
    }
}