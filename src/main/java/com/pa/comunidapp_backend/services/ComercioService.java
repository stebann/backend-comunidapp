package com.pa.comunidapp_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa.comunidapp_backend.dto.ComercioCrearDTO;
import com.pa.comunidapp_backend.dto.ComercioDetalleDTO;
import com.pa.comunidapp_backend.dto.ComercioResumenDTO;
import com.pa.comunidapp_backend.models.CategoriaComercio;
import com.pa.comunidapp_backend.models.Comercio;
import com.pa.comunidapp_backend.models.Usuario;
import com.pa.comunidapp_backend.models.UsuarioPermiso;
import com.pa.comunidapp_backend.repositories.CategoriaComercioRepository;
import com.pa.comunidapp_backend.repositories.ComercioRepository;
import com.pa.comunidapp_backend.repositories.UsuarioPermisoRepository;
import com.pa.comunidapp_backend.repositories.UsuarioRepository;

@Service
public class ComercioService {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioPermisoRepository usuarioPermisoRepository;

    @Autowired
    private CategoriaComercioRepository categoriaComercioRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ArticuloComercioService articuloComercioService;

    @Autowired
    private CategoriaArticuloComercioService categoriaArticuloComercioService;

    // ==================== COMERCIOS ====================

    /**
     * Obtiene todos los comercios activos
     */
    public List<ComercioResumenDTO> obtenerTodosComercios() {
        return comercioRepository.findByEliminadoEnIsNullAndActivoTrue().stream()
                .map(comercio -> new ComercioResumenDTO(
                        comercio.getId(),
                        comercio.getNombre(),
                        comercio.getDescripcion(),
                        comercio.getDireccion(),
                        comercio.getTelefono(),
                        comercio.getEmail(),
                        comercio.getImagenes(),
                        comercio.getSitioWeb(),
                        comercio.getTieneEnvio(),
                        comercio.getCategoria().getNombre()))
                .toList();
    }

    /**
     * Obtiene un comercio por ID
     */
    public Optional<Comercio> obtenerComercioById(Long id) {
        return comercioRepository.findByIdAndEliminadoEnIsNull(id);
    }

    /**
     * Obtiene los comercios de un usuario específico
     */
    public List<Comercio> obtenerComerciosPorUsuario(Long usuarioId) {
        return comercioRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId);
    }

    /**
     * Crea un nuevo comercio (solo si el usuario tiene permiso)
     */
    public Comercio crearComercio(Long usuarioId, ComercioCrearDTO comercioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CategoriaComercio categoria = categoriaComercioRepository.findById(comercioDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Verificar si el usuario tiene permiso para gestionar comercios
        List<UsuarioPermiso> permisos = usuarioPermisoRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId);
        boolean tienePermiso = permisos.stream()
                .anyMatch(p -> "GESTIONAR_COMERCIOS".equals(p.getPermiso().getNombre()));

        if (!tienePermiso) {
            throw new RuntimeException("El usuario no tiene permiso para crear comercios");
        }

        Comercio comercio = new Comercio();
        comercio.setUsuario(usuario);
        comercio.setCategoria(categoria);
        comercio.setNombre(comercioDTO.getNombre());
        comercio.setDescripcion(comercioDTO.getDescripcion());
        comercio.setDireccion(comercioDTO.getDireccion());
        comercio.setTelefono(comercioDTO.getTelefono());
        comercio.setEmail(comercioDTO.getEmail());
        comercio.setSitioWeb(comercioDTO.getSitioWeb());
        comercio.setTieneEnvio(comercioDTO.getTieneEnvio());
        comercio.setActivo(true);
        comercio.setCreadoEn(LocalDateTime.now());
        comercio.setActualizadoEn(LocalDateTime.now());

        // Guardar las imágenes y obtener las rutas
        org.springframework.web.multipart.MultipartFile[] imagenes = comercioDTO.getImagenes();
        if (imagenes != null && imagenes.length > 0) {
            List<String> rutasImagenes = fileStorageService.guardarImagenes(imagenes);
            if (!rutasImagenes.isEmpty()) {
                comercio.setImagenes(rutasImagenes);
            }
        }

        return comercioRepository.save(comercio);
    }

    /**
     * Actualiza un comercio existente
     */
    public Comercio actualizarComercio(Long comercioId, Long usuarioId, ComercioCrearDTO comercioDTO) {
        Comercio comercio = comercioRepository.findByIdAndEliminadoEnIsNull(comercioId)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        // Verificar que el usuario sea el propietario
        if (!comercio.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permiso para actualizar este comercio");
        }

        CategoriaComercio categoria = categoriaComercioRepository.findById(comercioDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        comercio.setCategoria(categoria);
        comercio.setNombre(comercioDTO.getNombre());
        comercio.setDescripcion(comercioDTO.getDescripcion());
        comercio.setDireccion(comercioDTO.getDireccion());
        comercio.setTelefono(comercioDTO.getTelefono());
        comercio.setEmail(comercioDTO.getEmail());
        comercio.setSitioWeb(comercioDTO.getSitioWeb());
        comercio.setTieneEnvio(comercioDTO.getTieneEnvio());
        comercio.setActualizadoEn(LocalDateTime.now());

        // Guardar las imágenes y obtener las rutas (si hay nuevas imágenes)
        org.springframework.web.multipart.MultipartFile[] imagenes = comercioDTO.getImagenes();
        if (imagenes != null && imagenes.length > 0) {
            List<String> rutasImagenes = fileStorageService.guardarImagenes(imagenes);
            if (!rutasImagenes.isEmpty()) {
                comercio.setImagenes(rutasImagenes);
            }
        }

        return comercioRepository.save(comercio);
    }

    /**
     * Obtiene un comercio por ID (retorna DTO)
     */
    public Optional<ComercioResumenDTO> obtenerComercioByIdDTO(Long id) {
        Optional<Comercio> comercio = comercioRepository.findByIdAndEliminadoEnIsNull(id);
        return comercio.map(c -> new ComercioResumenDTO(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEmail(),
                c.getImagenes(),
                c.getSitioWeb(),
                c.getTieneEnvio(),
                c.getCategoria().getNombre()));
    }

    /**
     * Obtiene los comercios de un usuario específico (retorna DTO)
     */
    public List<ComercioResumenDTO> obtenerComerciosPorUsuarioDTO(Long usuarioId) {
        return comercioRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId).stream()
                .map(c -> new ComercioResumenDTO(
                        c.getId(),
                        c.getNombre(),
                        c.getDescripcion(),
                        c.getDireccion(),
                        c.getTelefono(),
                        c.getEmail(),
                        c.getImagenes(),
                        c.getSitioWeb(),
                        c.getTieneEnvio(),
                        c.getCategoria().getNombre()))
                .toList();
    }

    /**
     * Desactiva un comercio (eliminación lógica)
     */
    public void desactivarComercio(Long comercioId, Long usuarioId) {
        Comercio comercio = comercioRepository.findByIdAndEliminadoEnIsNull(comercioId)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        if (!comercio.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permiso para desactivar este comercio");
        }

        comercio.setEliminadoEn(LocalDateTime.now());
        comercio.setActualizadoEn(LocalDateTime.now());
        comercioRepository.save(comercio);
    }

    /**
     * Obtiene un comercio por ID con todos sus artículos
     */
    public Optional<ComercioDetalleDTO> obtenerComercioByIdConArticulos(Long id) {
        Optional<Comercio> comercio = comercioRepository.findByIdAndEliminadoEnIsNull(id);
        return comercio.map(c -> new ComercioDetalleDTO(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEmail(),
                c.getImagenes(),
                c.getSitioWeb(),
                c.getTieneEnvio(),
                c.getCategoria().getNombre(),
                categoriaArticuloComercioService.obtenerCategoriasComercio(c.getId()),
                articuloComercioService.obtenerArticulosComercio(c.getId())));
    }
}
