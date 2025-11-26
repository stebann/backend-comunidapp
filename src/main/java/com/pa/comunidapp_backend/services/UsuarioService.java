package com.pa.comunidapp_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pa.comunidapp_backend.config.services.MapperService;
import com.pa.comunidapp_backend.dto.CambiarPasswordDTO;
import com.pa.comunidapp_backend.dto.UsuarioActualizarDTO;
import com.pa.comunidapp_backend.models.Usuario;
import com.pa.comunidapp_backend.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findByEliminadoEnIsNull();
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findByIdAndEliminadoEnIsNull(id);
    }

    public void actualizarDatosPersonales(Long id, UsuarioActualizarDTO usuarioActualizarDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioActualizarDTO.getNombreCompleto() != null) {
            usuario.setNombreCompleto(usuarioActualizarDTO.getNombreCompleto());
        }
        if (usuarioActualizarDTO.getEmail() != null) {
            usuario.setEmail(usuarioActualizarDTO.getEmail());
        }
        if (usuarioActualizarDTO.getDireccion() != null) {
            usuario.setDireccion(usuarioActualizarDTO.getDireccion());
        }
        if (usuarioActualizarDTO.getTelefono() != null) {
            usuario.setTelefono(usuarioActualizarDTO.getTelefono());
        }

        usuario.setActualizadoEn(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    public void cambiarPassword(Long id, CambiarPasswordDTO cambiarPasswordDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(cambiarPasswordDTO.getPasswordActual(), usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        // Actualizar la contraseña
        usuario.setContrasena(passwordEncoder.encode(cambiarPasswordDTO.getPasswordNuevo()));
        usuario.setActualizadoEn(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    public void suspenderUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminación lógica: marcar como eliminado
        usuario.setEliminadoEn(LocalDateTime.now());
        usuario.setActualizadoEn(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
