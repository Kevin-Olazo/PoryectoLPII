package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Paciente;
import com.cibertec.medicare.entity.Usuario;
import com.cibertec.medicare.service.PacienteService;
import com.cibertec.medicare.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    // 1. Mostrar el formulario
    @GetMapping
    public String mostrarFormulario() {
        return "registro"; // HTML que crearemos ahora
    }

    // 2. Procesar el registro
    @PostMapping
    public String registrar(@RequestParam String nombres,
                            @RequestParam String apellidos,
                            @RequestParam String dni,
                            @RequestParam String telefono,
                            @RequestParam String direccion,
                            @RequestParam String email,
                            @RequestParam String password,
                            Model model) {

        try {
            // 1. VALIDACIÓN PREVIA: ¿El correo ya existe?
            if (usuarioService.buscarPorEmail(email) != null) {
                model.addAttribute("error", "El correo " + email + " ya está registrado. Intenta iniciar sesión.");
                return "registro"; // Volvemos al formulario con el mensaje amigable
            }

            // 2. Crear el Usuario (Login)
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setPassword(password);
            u.setRol("PACIENTE");

            Usuario usuarioGuardado = usuarioService.registrarUsuario(u);

            // 3. Crear el Paciente
            Paciente p = new Paciente();
            p.setNombres(nombres);
            p.setApellidos(apellidos);
            p.setDni(dni);
            p.setTelefono(telefono);
            p.setDireccion(direccion);
            p.setUsuario(usuarioGuardado);

            pacienteService.registrarPaciente(p);

            return "redirect:/login?registrado=true";

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error en el sistema.");
            return "registro";
        }
    }
}