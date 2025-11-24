package com.pa.comunidapp_backend.services;

import com.pa.comunidapp_backend.models.*;
import com.pa.comunidapp_backend.repositories.*;
import com.pa.comunidapp_backend.dto.SolicitudAccesoResumenDTO;
import com.pa.comunidapp_backend.enums.EEstadoSolicitudAcceso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class PremiumService {

        private final SolicitudAccesoRepository solicitudAccesoRepository;
        private final UsuarioRepository usuarioRepository;
        private final EstadoSolicitudComercioRepository estadoSolicitudComercioRepository;
        private final UsuarioPermisoRepository usuarioPermisoRepository;
        private final PermisoRepository permisoRepository;

        /**
         * Crear una nueva solicitud de acceso premium
         */
        @Transactional
        public SolicitudAcceso crearSolicitud(Long usuarioId) {
                Usuario usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Validar que no exista una solicitud pendiente
                List<SolicitudAcceso> solicitudesPendientes = solicitudAccesoRepository
                                .findByUsuarioIdAndEstadoCodigo(usuarioId,
                                                EEstadoSolicitudAcceso.Pendiente.getCodigo());
                if (!solicitudesPendientes.isEmpty()) {
                        throw new RuntimeException("El usuario ya tiene una solicitud pendiente");
                }

                EstadoSolicitudComercio pendiente = estadoSolicitudComercioRepository
                                .findByCodigo(EEstadoSolicitudAcceso.Pendiente.getCodigo())
                                .orElseThrow(() -> new RuntimeException("Estado de solicitud no encontrado"));

                SolicitudAcceso solicitud = new SolicitudAcceso();
                solicitud.setUsuario(usuario);
                solicitud.setEstado(pendiente);

                return solicitudAccesoRepository.save(solicitud);
        }

        /**
         * Obtener todas las solicitudes (pendientes, aprobadas, rechazadas)
         * Ordena por estado (pendientes primero) y luego por fecha descendente
         */
        @Transactional(readOnly = true)
        public List<SolicitudAccesoResumenDTO> obtenerSolicitudesPendientes() {
                List<SolicitudAcceso> solicitudes = solicitudAccesoRepository.findAll();

                return solicitudes.stream()
                                .sorted(Comparator
                                                .comparingInt((SolicitudAcceso s) -> s.getEstado().getCodigo()) // Pendientes
                                                                                                                // (1)
                                                                                                                // primero
                                                .thenComparing(Comparator.comparing(SolicitudAcceso::getCreadoEn)
                                                                .reversed())) // Luego por fecha descendente
                                .map(s -> new SolicitudAccesoResumenDTO(
                                                s.getId(),
                                                s.getUsuario().getId(),
                                                s.getUsuario().getNombreCompleto(),
                                                s.getUsuario().getEmail(),
                                                s.getUsuario().getTelefono(),
                                                s.getEstado().getCodigo(),
                                                s.getEstado().getNombre(),
                                                s.getCreadoEn()))
                                .toList();
        }

        /**
         * Obtener solicitudes de un usuario específico
         */
        public List<SolicitudAcceso> obtenerSolicitudesPorUsuario(Long usuarioId) {
                return solicitudAccesoRepository.findByUsuarioIdOrderByCreadoEnDesc(usuarioId);
        }

        /**
         * Cambiar el estado de una solicitud de acceso
         */
        @Transactional
        public SolicitudAcceso cambiarEstadoSolicitud(Long solicitudId, Long adminId, Integer estadoCodigo) {
                SolicitudAcceso solicitud = solicitudAccesoRepository.findById(solicitudId)
                                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

                Usuario admin = usuarioRepository.findById(adminId)
                                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

                EstadoSolicitudComercio nuevoEstado = estadoSolicitudComercioRepository.findByCodigo(estadoCodigo)
                                .orElseThrow(() -> new RuntimeException("Estado no válido"));

                solicitud.setEstado(nuevoEstado);
                solicitud.setAdminRevisor(admin);
                solicitud.setRevisadoEn(LocalDateTime.now());

                // Si es aprobada (Aceptada), asignamos el permiso PREMIUM
                if (estadoCodigo.equals(EEstadoSolicitudAcceso.Aceptada.getCodigo())) {
                        asignarPermisoPremium(solicitud.getUsuario().getId());
                }

                return solicitudAccesoRepository.save(solicitud);
        }

        /**
         * Asignar permiso PREMIUM a un usuario
         */
        private void asignarPermisoPremium(Long usuarioId) {
                Usuario usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                asignarPermiso(usuario, "PREMIUM");
                asignarPermiso(usuario, "GESTIONAR_COMERCIOS");
        }

        private void asignarPermiso(Usuario usuario, String nombrePermiso) {
                // Verificar si ya tiene el permiso
                List<UsuarioPermiso> permisos = usuarioPermisoRepository
                                .findByUsuarioIdAndEliminadoEnIsNull(usuario.getId());
                boolean yaTienePermiso = permisos.stream()
                                .anyMatch(p -> nombrePermiso.equals(p.getPermiso().getNombre()));

                if (!yaTienePermiso) {
                        Permiso permiso = permisoRepository.findByNombre(nombrePermiso)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Permiso " + nombrePermiso + " no existe"));

                        UsuarioPermiso usuarioPermiso = new UsuarioPermiso();
                        usuarioPermiso.setUsuario(usuario);
                        usuarioPermiso.setPermiso(permiso);
                        usuarioPermiso.setCreadoEn(LocalDateTime.now());
                        usuarioPermisoRepository.save(usuarioPermiso);
                }
        }
}
