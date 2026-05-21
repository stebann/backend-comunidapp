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
import com.pa.comunidapp_backend.repositories.CiudadRepository;
import com.pa.comunidapp_backend.repositories.ComercioRepository;
import com.pa.comunidapp_backend.repositories.DepartamentoRepository;
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
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

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
                .map(this::mapToComercioResumenDTO)
                .toList();
    }

    /**
     * Obtiene comercios con filtros opcionales
     */
    public List<ComercioResumenDTO> obtenerComerciosConFiltros(String nombre, Long categoriaId, Long usuarioId,
            Boolean activo, Long departamentoId, Long ciudadId) {
        return comercioRepository
                .buscarComerciosConFiltros(nombre, categoriaId, usuarioId, activo, departamentoId, ciudadId).stream()
                .map(this::mapToComercioResumenDTO)
                .toList();
    }

    private ComercioResumenDTO mapToComercioResumenDTO(Comercio comercio) {
        ComercioResumenDTO dto = new ComercioResumenDTO();
        dto.setId(comercio.getId());
        dto.setUsuarioId(comercio.getUsuario().getId());
        dto.setNombre(comercio.getNombre());
        dto.setDescripcion(comercio.getDescripcion());
        dto.setDireccion(comercio.getDireccion());
        dto.setTelefono(comercio.getTelefono());
        dto.setEmail(comercio.getEmail());
        dto.setImagenes(comercio.getImagenes());
        dto.setSitioWeb(comercio.getSitioWeb());
        dto.setTieneEnvio(comercio.getTieneEnvio());
        dto.setCategoriaNombre(comercio.getCategoria() != null ? comercio.getCategoria().getNombre() : null);

        // Mapear ubicación
        dto.setDepartamentoCodigo(comercio.getDepartamentoId());
        if (comercio.getDepartamentoId() != null) {
            departamentoRepository.findById(comercio.getDepartamentoId())
                    .ifPresent(dep -> dto.setDepartamento(dep.getNombre()));
        }

        dto.setCiudadCodigo(comercio.getCiudadId());
        if (comercio.getCiudadId() != null) {
            ciudadRepository.findById(comercio.getCiudadId())
                    .ifPresent(ciu -> dto.setCiudad(ciu.getNombre()));
        }

        return dto;
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
        comercio.setDepartamentoId(comercioDTO.getDepartamentoCodigo());
        comercio.setCiudadId(comercioDTO.getCiudadCodigo());
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
        if (comercioDTO.getDepartamentoCodigo() != null)
            comercio.setDepartamentoId(comercioDTO.getDepartamentoCodigo());
        if (comercioDTO.getCiudadCodigo() != null)
            comercio.setCiudadId(comercioDTO.getCiudadCodigo());
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
        return comercio.map(this::mapToComercioResumenDTO);
    }

    /**
     * Obtiene los comercios de un usuario específico (retorna DTO)
     */
    public List<ComercioResumenDTO> obtenerComerciosPorUsuarioDTO(Long usuarioId) {
        return comercioRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId).stream()
                .map(this::mapToComercioResumenDTO)
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
        Optional<Comercio> comercioOptional = comercioRepository.findByIdAndEliminadoEnIsNull(id);
        if (comercioOptional.isEmpty()) {
            return Optional.empty();
        }

        Comercio c = comercioOptional.get();
        ComercioDetalleDTO dto = new ComercioDetalleDTO();
        dto.setId(c.getId());
        dto.setUsuarioId(c.getUsuario().getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setDireccion(c.getDireccion());
        dto.setTelefono(c.getTelefono());
        dto.setEmail(c.getEmail());
        dto.setImagenes(c.getImagenes());
        dto.setSitioWeb(c.getSitioWeb());
        dto.setTieneEnvio(c.getTieneEnvio());
        dto.setCategoriaId(c.getCategoria().getId());
        dto.setCategoriaNombre(c.getCategoria().getNombre());

        // Mapear ubicación
        dto.setDepartamentoCodigo(c.getDepartamentoId());
        if (c.getDepartamentoId() != null) {
            departamentoRepository.findById(c.getDepartamentoId())
                    .ifPresent(dep -> dto.setDepartamento(dep.getNombre()));
        }

        dto.setCiudadCodigo(c.getCiudadId());
        if (c.getCiudadId() != null) {
            ciudadRepository.findById(c.getCiudadId())
                    .ifPresent(ciu -> dto.setCiudad(ciu.getNombre()));
        }

        dto.setCategorias(categoriaArticuloComercioService.obtenerCategoriasComercio(c.getId()));
        dto.setArticulos(articuloComercioService.obtenerArticulosComercio(c.getId()));

        return Optional.of(dto);
    }
}
