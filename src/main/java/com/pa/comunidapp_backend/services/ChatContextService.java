package com.pa.comunidapp_backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa.comunidapp_backend.enums.EEstadoArticulo;
import com.pa.comunidapp_backend.enums.EEstadoSolicitud;
import com.pa.comunidapp_backend.enums.ETipoSolicitud;
import com.pa.comunidapp_backend.models.Articulo;
import com.pa.comunidapp_backend.repositories.ArticuloRepository;
import com.pa.comunidapp_backend.repositories.MisGestionesRepository;
import com.pa.comunidapp_backend.repositories.UsuarioRepository;

@Service
public class ChatContextService {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private ArticuloRepository articuloRepository;

        @Autowired
        private MisGestionesRepository misGestionesRepository;

        /**
         * Obtiene el contexto del usuario para el chat
         */
        public String obtenerContextoUsuario(Long usuarioId) {
                StringBuilder contexto = new StringBuilder();
                contexto.append("\n--- INFORMACIÓN DEL USUARIO ---\n");

                // Información del usuario
                usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
                        contexto.append("Nombre: ").append(usuario.getNombreCompleto()).append("\n");
                        contexto.append("Email: ").append(usuario.getEmail()).append("\n");
                        contexto.append("Rating: ")
                                        .append(usuario.getRatingPromedio() != null ? usuario.getRatingPromedio()
                                                        : "Sin calificaciones")
                                        .append("\n");
                });

                // Estadísticas de artículos
                long articulosPublicados = articuloRepository.countByUsuarioIdAndEliminadoEnIsNull(usuarioId);
                long articulosDisponibles = articuloRepository
                                .countByUsuarioIdAndEstadoArticuloCodigoAndEliminadoEnIsNull(
                                                usuarioId, EEstadoArticulo.Disponible.getCodigo());
                long articulosPrestados = articuloRepository
                                .countByUsuarioIdAndEstadoArticuloCodigoAndEliminadoEnIsNull(
                                                usuarioId, EEstadoArticulo.Prestado.getCodigo());

                contexto.append("\n--- MIS ARTÍCULOS ---\n");
                contexto.append("Total publicados: ").append(articulosPublicados).append("\n");
                contexto.append("Disponibles: ").append(articulosDisponibles).append("\n");
                contexto.append("Prestados: ").append(articulosPrestados).append("\n");

                // Estadísticas de solicitudes
                long solicitudesPendientes = misGestionesRepository
                                .findByUsuarioSolicitanteIdAndTipoCodigoAndEliminadoEnIsNull(usuarioId,
                                                ETipoSolicitud.Solicitud.getCodigo())
                                .stream()
                                .filter(s -> s.getEstadoCodigo().equals(EEstadoSolicitud.Pendiente.getCodigo()))
                                .count();

                long prestamosActivos = misGestionesRepository
                                .findByUsuarioSolicitanteIdAndTipoCodigoAndEliminadoEnIsNull(usuarioId,
                                                ETipoSolicitud.Prestamo.getCodigo())
                                .stream()
                                .filter(s -> s.getEstadoCodigo().equals(EEstadoSolicitud.Aceptada.getCodigo()) ||
                                                s.getEstadoCodigo().equals(
                                                                EEstadoSolicitud.DevolucionPendiente.getCodigo()))
                                .count();

                long solicitudesRecibidas = misGestionesRepository
                                .findByUsuarioPropietarioIdAndTipoCodigoAndEliminadoEnIsNull(usuarioId,
                                                ETipoSolicitud.Solicitud.getCodigo())
                                .stream()
                                .filter(s -> s.getEstadoCodigo().equals(EEstadoSolicitud.Pendiente.getCodigo()))
                                .count();

                contexto.append("\n--- MIS GESTIONES ---\n");
                contexto.append("Solicitudes enviadas (pendientes): ").append(solicitudesPendientes).append("\n");
                contexto.append("Préstamos activos: ").append(prestamosActivos).append("\n");
                contexto.append("Solicitudes recibidas (pendientes): ").append(solicitudesRecibidas).append("\n");

                return contexto.toString();
        }

        /**
         * Obtiene información pública de artículos disponibles
         */
        public String obtenerArticulosDisponibles(String categoria) {
                List<Articulo> articulos;

                if (categoria != null && !categoria.isEmpty()) {
                        // Buscar por categoría (esto requeriría mapear el nombre a código)
                        articulos = articuloRepository.findByEstadoArticuloCodigoAndEliminadoEnIsNull(
                                        EEstadoArticulo.Disponible.getCodigo());
                } else {
                        articulos = articuloRepository.findByEstadoArticuloCodigoAndEliminadoEnIsNull(
                                        EEstadoArticulo.Disponible.getCodigo());
                }

                if (articulos.isEmpty()) {
                        return "No hay artículos disponibles en este momento.";
                }

                // Limitar a 10 artículos para no saturar el contexto
                String listaArticulos = articulos.stream()
                                .limit(10)
                                .map(a -> String.format("- %s (ID: %d, Precio: $%.2f)",
                                                a.getTitulo(), a.getId(), a.getPrecio()))
                                .collect(Collectors.joining("\n"));

                return String.format("Artículos disponibles (%d total, mostrando primeros 10):\n%s",
                                articulos.size(), listaArticulos);
        }

        /**
         * Detecta si el mensaje requiere contexto de BD
         */
        public boolean requiereContextoBD(String mensaje) {
                String mensajeLower = mensaje.toLowerCase();

                // Palabras clave que indican que necesita consultar la BD
                String[] palabrasClave = {
                                "cuántos", "cuantos", "mis artículos", "mi rating", "mis solicitudes",
                                "mis préstamos", "mis gestiones", "tengo", "he publicado",
                                "artículos disponibles", "qué artículos", "que articulos",
                                "muéstrame", "muestrame", "lista", "ver"
                };

                for (String palabra : palabrasClave) {
                        if (mensajeLower.contains(palabra)) {
                                return true;
                        }
                }

                return false;
        }
}
