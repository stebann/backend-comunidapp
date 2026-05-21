package com.pa.comunidapp_backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa.comunidapp_backend.enums.EArticuloCondicion;
import com.pa.comunidapp_backend.enums.EEstadoArticulo;
import com.pa.comunidapp_backend.enums.EEstadoSolicitud;
import com.pa.comunidapp_backend.enums.ETipoSolicitud;
import com.pa.comunidapp_backend.enums.ETipoTransaccion;
import com.pa.comunidapp_backend.models.Articulo;
import com.pa.comunidapp_backend.repositories.ArticuloRepository;
import com.pa.comunidapp_backend.repositories.CategoriaRepository;
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

        @Autowired
        private CategoriaRepository categoriaRepository;

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

                // Desglose por condición
                contexto.append("\n--- ARTÍCULOS POR CONDICIÓN ---\n");
                long articulosNuevos = articuloRepository
                                .countByUsuarioIdAndCondicionCodigoAndEliminadoEnIsNull(
                                                usuarioId, EArticuloCondicion.Nuevo.getCodigo());
                long articulosPocoUso = articuloRepository
                                .countByUsuarioIdAndCondicionCodigoAndEliminadoEnIsNull(
                                                usuarioId, EArticuloCondicion.PocoUso.getCodigo());
                long articulosUsados = articuloRepository
                                .countByUsuarioIdAndCondicionCodigoAndEliminadoEnIsNull(
                                                usuarioId, EArticuloCondicion.Usado.getCodigo());
                long articulosDañados = articuloRepository
                                .countByUsuarioIdAndCondicionCodigoAndEliminadoEnIsNull(
                                                usuarioId, EArticuloCondicion.Dañado.getCodigo());
                long articulosDefectuosos = articuloRepository
                                .countByUsuarioIdAndCondicionCodigoAndEliminadoEnIsNull(
                                                usuarioId, EArticuloCondicion.Defectuoso.getCodigo());

                contexto.append("Nuevos: ").append(articulosNuevos).append("\n");
                contexto.append("Poco uso: ").append(articulosPocoUso).append("\n");
                contexto.append("Usados: ").append(articulosUsados).append("\n");
                contexto.append("Dañados: ").append(articulosDañados).append("\n");
                contexto.append("Defectuosos: ").append(articulosDefectuosos).append("\n");

                // Desglose por tipo de transacción
                contexto.append("\n--- ARTÍCULOS POR TIPO DE TRANSACCIÓN ---\n");
                long articulosVenta = articuloRepository
                                .countByUsuarioIdAndTipoTransaccionCodigoAndEliminadoEnIsNull(
                                                usuarioId, ETipoTransaccion.Venta.getCodigo());
                long articulosPrestamo = articuloRepository
                                .countByUsuarioIdAndTipoTransaccionCodigoAndEliminadoEnIsNull(
                                                usuarioId, ETipoTransaccion.Prestamo.getCodigo());

                contexto.append("Para venta: ").append(articulosVenta).append("\n");
                contexto.append("Para préstamo: ").append(articulosPrestamo).append("\n");

                // Desglose por categoría (top 5)
                contexto.append("\n--- ARTÍCULOS POR CATEGORÍA ---\n");
                List<Articulo> todosArticulos = articuloRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId);

                // Agrupar por categoría y contar
                var categoriaCount = todosArticulos.stream()
                                .filter(a -> a.getCategoriaCodigo() != null)
                                .collect(Collectors.groupingBy(
                                                Articulo::getCategoriaCodigo,
                                                Collectors.counting()));

                // Mostrar las categorías con sus nombres
                categoriaCount.forEach((categoriaCodigo, count) -> {
                        categoriaRepository.findByCodigo(Long.valueOf(categoriaCodigo)).ifPresent(categoria -> {
                                contexto.append(categoria.getNombre()).append(": ").append(count).append("\n");
                        });
                });

                if (categoriaCount.isEmpty()) {
                        contexto.append("No hay artículos categorizados\n");
                }

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
                                "muéstrame", "muestrame", "lista", "ver",
                                // Palabras relacionadas con condición
                                "nuevos", "nuevo", "usados", "usado", "poco uso", "dañados", "dañado",
                                "defectuosos", "defectuoso", "condición", "condicion", "estado",
                                // Palabras relacionadas con tipo de transacción
                                "venta", "ventas", "préstamo", "prestamo", "préstamos", "prestamos",
                                "vender", "prestar",
                                // Palabras relacionadas con categorías
                                "categoría", "categoria", "categorías", "categorias",
                                "electrónica", "electronica", "hogar", "deportes", "libros",
                                "herramientas", "jardín", "jardin", "música", "musica", "ropa", "juegos",
                                // Palabras relacionadas con estadísticas personales
                                "mi perfil", "mi información", "mi cuenta", "mis datos"
                };

                for (String palabra : palabrasClave) {
                        if (mensajeLower.contains(palabra)) {
                                return true;
                        }
                }

                return false;
        }
}
