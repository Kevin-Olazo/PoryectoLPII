package com.cibertec.medicare.service.impl;

import com.cibertec.medicare.entity.Usuario;
import com.cibertec.medicare.repository.UsuarioRepository;
import com.cibertec.medicare.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario validarLogin(String email, String password) {
        // 1. Buscar por email
        Usuario usuario = usuarioRepository.findByEmail(email);

        // 2. Si existe y la contraseña coincide, retornamos el usuario
        // NOTA: En un sistema real, aquí usaríamos BCrypt para comparar hashes
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null; // Login fallido
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}