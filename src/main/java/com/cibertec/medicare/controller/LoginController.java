package com.cibertec.medicare.controller;

import com.cibertec.medicare.entity.Usuario;
import com.cibertec.medicare.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService; // Necesitas crear este servicio

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Usuario usuario = usuarioService.validarLogin(email, password);

        if (usuario != null) {
            // ¡LOGIN EXITOSO! Guardamos al usuario en la "caja fuerte" (Sesión)
            session.setAttribute("usuarioLogin", usuario);
            return "redirect:/"; // O redirigir a donde estaba antes
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruye la sesión
        return "redirect:/";
    }
}