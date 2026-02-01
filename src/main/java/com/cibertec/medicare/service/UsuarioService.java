package com.cibertec.medicare.service;

import com.cibertec.medicare.entity.Usuario;

public interface UsuarioService {
    Usuario buscarPorEmail(String email);
    Usuario validarLogin(String email, String password);
    Usuario registrarUsuario(Usuario usuario); // Por si luego haces el registro
}