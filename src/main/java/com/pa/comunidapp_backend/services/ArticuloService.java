package com.pa.comunidapp_backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pa.comunidapp_backend.config.services.MapperService;
import com.pa.comunidapp_backend.dto.ArticuloActualizarDTO;
import com.pa.comunidapp_backend.dto.ArticuloCrearDTO;
import com.pa.comunidapp_backend.enums.EEstadoArticulo;
import com.pa.comunidapp_backend.models.Articulo;
import com.pa.comunidapp_backend.repositories.ArticuloRepository;
import com.pa.comunidapp_backend.repositories.CategoriaRepository;
import com.pa.comunidapp_backend.repositories.CiudadRepository;
import com.pa.comunidapp_backend.repositories.CondicionArticuloRepository;
import com.pa.comunidapp_backend.repositories.DepartamentoRepository;
import com.pa.comunidapp_backend.repositories.EstadoArticuloRepository;
import com.pa.comunidapp_backend.repositories.TipoTransaccionRepository;
import com.pa.comunidapp_backend.repositories.UsuarioRepository;
import com.pa.comunidapp_backend.response.ArticuloResponseDTO;
import com.pa.comunidapp_backend.response.ArticuloUsuarioResponseDTO;
import com.pa.comunidapp_backend.response.UsuarioBasicoDTO;

@Service
@Transactional
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CondicionArticuloRepository condicionArticuloRepository;

    @Autowired
    private EstadoArticuloRepository estadoArticuloRepository;

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void crearArticulo(ArticuloCrearDTO articuloCrearDTO, Long usuarioId) {
        Articulo articulo = new Articulo();
        articulo.setTitulo(articuloCrearDTO.getTitulo());
        articulo.setDescripcion(articuloCrearDTO.getDescripcion());
        articulo.setCategoriaCodigo(articuloCrearDTO.getCategoriaCodigo());
        articulo.setCondicionCodigo(articuloCrearDTO.getCondicionCodigo());
        articulo.setTipoTransaccionCodigo(articuloCrearDTO.getTipoTransaccionCodigo());
        articulo.setDepartamentoId(articuloCrearDTO.getDepartamentoCodigo());
        articulo.setCiudadId(articuloCrearDTO.getCiudadCodigo());
        articulo.setPrecio(articuloCrearDTO.getPrecio());
        articulo.setUsuarioId(usuarioId);
        articulo.setCreadoEn(LocalDateTime.now());
        // artículo como "Disponible"
        articulo.setEstadoArticuloCodigo(EEstadoArticulo.Disponible.getCodigo());

        // Guardar propietario en JSON
        usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
            try {
                UsuarioBasicoDTO propietarioDTO = new UsuarioBasicoDTO();
                propietarioDTO.setId(usuario.getId());
                propietarioDTO.setNombre(usuario.getNombreCompleto());
                propietarioDTO.setEmail(usuario.getEmail());
                propietarioDTO.setTelefono(usuario.getTelefono());
                propietarioDTO.setDireccion(usuario.getDireccion());
                articulo.setPropietario(objectMapper.writeValueAsString(propietarioDTO));
            } catch (Exception e) {
                // Si hay error al convertir a JSON, continuar sin propietario
            }
        });

        // Guardar las imágenes y obtener las rutas
        MultipartFile[] imagenes = articuloCrearDTO.getImagenes();
        if (imagenes != null && imagenes.length > 0) {
            List<String> rutasImagenes = fileStorageService.guardarImagenes(imagenes);
            if (!rutasImagenes.isEmpty()) {
                articulo.setImagenes(String.join(",", rutasImagenes));
            }
        }

        articuloRepository.save(articulo);
    }

    public List<ArticuloResponseDTO> obtenerTodosLosArticulos() {
        List<Articulo> articulos = articuloRepository.findByEliminadoEnIsNull();
        return articulos.stream()
                .map(this::mapToArticuloResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ArticuloResponseDTO> obtenerArticulosConFiltros(
            String nombreArticulo,
            Integer categoriaCodigo,
            Integer tipoTransaccionCodigo,
            Integer estadoArticuloCodigo,
            Integer condicionCodigo,
            Long departamentoId,
            Long ciudadId,
            String nombreUsuario) {

        List<Articulo> articulos = articuloRepository.buscarArticulosConFiltros(
                nombreArticulo,
                categoriaCodigo,
                tipoTransaccionCodigo,
                estadoArticuloCodigo,
                condicionCodigo,
                departamentoId,
                ciudadId,
                nombreUsuario);

        return articulos.stream()
                .map(this::mapToArticuloResponseDTO)
                .collect(Collectors.toList());
    }

    public ArticuloResponseDTO obtenerArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findByIdAndEliminadoEnIsNull(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        return mapToArticuloResponseDTO(articulo);
    }

    private ArticuloResponseDTO mapToArticuloResponseDTO(Articulo articulo) {
        ArticuloResponseDTO dto = new ArticuloResponseDTO();
        dto.setId(articulo.getId());
        dto.setTitulo(articulo.getTitulo());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setPrecio(articulo.getPrecio());
        dto.setCreadoEn(articulo.getCreadoEn());

        // Asignar directamente los códigos y nombres si están presentes
        dto.setCategoriaCodigo(articulo.getCategoriaCodigo());
        if (articulo.getCategoriaCodigo() != null) {
            categoriaRepository.findByCodigo(articulo.getCategoriaCodigo())
                    .ifPresent(cat -> dto.setCategoriaNombre(cat.getNombre()));
        }
        dto.setCondicionCodigo(articulo.getCondicionCodigo());
        if (articulo.getCondicionCodigo() != null) {
            condicionArticuloRepository.findByCodigo(articulo.getCondicionCodigo())
                    .ifPresent(cond -> dto.setCondicionNombre(cond.getNombre()));
        }
        dto.setEstadoArticuloCodigo(articulo.getEstadoArticuloCodigo());
        if (articulo.getEstadoArticuloCodigo() != null) {
            estadoArticuloRepository.findByCodigo(articulo.getEstadoArticuloCodigo())
                    .ifPresent(est -> dto.setEstadoArticuloNombre(est.getNombre()));
        }
        if (articulo.getTipoTransaccionCodigo() != null) {
            dto.setTipoTransaccionCodigo(articulo.getTipoTransaccionCodigo());
            tipoTransaccionRepository.findByCodigo(articulo.getTipoTransaccionCodigo())
                    .ifPresent(tipo -> dto.setTipoTransaccionNombre(tipo.getNombre()));
        }

        dto.setDepartamentoId(articulo.getDepartamentoId());
        if (articulo.getDepartamentoId() != null) {
            departamentoRepository.findById(articulo.getDepartamentoId())
                    .ifPresent(dep -> dto.setDepartamento(dep.getNombre()));
        }

        dto.setCiudadId(articulo.getCiudadId());
        if (articulo.getCiudadId() != null) {
            ciudadRepository.findById(articulo.getCiudadId())
                    .ifPresent(ciu -> dto.setCiudad(ciu.getNombre()));
        }

        if (articulo.getImagenes() != null && !articulo.getImagenes().isEmpty()) {
            String[] imagenesArray = articulo.getImagenes().split(",");
            List<String> imagenesList = java.util.Arrays.stream(imagenesArray)
                    .map(String::trim)
                    .collect(Collectors.toList());
            dto.setImagenes(imagenesList);
        }

        // Mapear propietario desde usuarioId
        if (articulo.getUsuarioId() != null) {
            usuarioRepository.findById(articulo.getUsuarioId()).ifPresent(usuario -> {
                UsuarioBasicoDTO propietario = new UsuarioBasicoDTO();
                propietario.setId(usuario.getId());
                propietario.setNombre(usuario.getNombreCompleto());
                propietario.setEmail(usuario.getEmail());
                propietario.setTelefono(usuario.getTelefono());
                propietario.setDireccion(usuario.getDireccion());
                dto.setPropietario(propietario);
            });
        }

        // Mapear solicitante desde JSON si existe y el artículo está prestado
        if (articulo.getSolicitante() != null && !articulo.getSolicitante().isEmpty()) {
            try {
                UsuarioBasicoDTO solicitante = objectMapper.readValue(articulo.getSolicitante(),
                        UsuarioBasicoDTO.class);
                dto.setSolicitante(solicitante);
            } catch (Exception e) {
                // Si hay error al parsear, dejar solicitante vacío
            }
        }

        return dto;
    }

    public List<ArticuloUsuarioResponseDTO> obtenerArticulosPorUsuario(Long usuarioId) {
        List<Articulo> articulos = articuloRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId);
        return articulos.stream()
                .map(this::mapToArticuloUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    private ArticuloUsuarioResponseDTO mapToArticuloUsuarioResponseDTO(Articulo articulo) {
        ArticuloResponseDTO fullDto = mapToArticuloResponseDTO(articulo);
        ArticuloUsuarioResponseDTO dto = new ArticuloUsuarioResponseDTO();
        dto.setId(fullDto.getId());
        dto.setTitulo(fullDto.getTitulo());
        dto.setDescripcion(fullDto.getDescripcion());
        dto.setPrecio(fullDto.getPrecio());
        dto.setCreadoEn(fullDto.getCreadoEn());
        dto.setCategoriaCodigo(fullDto.getCategoriaCodigo());
        dto.setCategoriaNombre(fullDto.getCategoriaNombre());
        dto.setCondicionCodigo(fullDto.getCondicionCodigo());
        dto.setCondicionNombre(fullDto.getCondicionNombre());
        dto.setEstadoArticuloCodigo(fullDto.getEstadoArticuloCodigo());
        dto.setEstadoArticuloNombre(fullDto.getEstadoArticuloNombre());
        dto.setTipoTransaccionCodigo(fullDto.getTipoTransaccionCodigo());
        dto.setTipoTransaccionNombre(fullDto.getTipoTransaccionNombre());
        dto.setDepartamentoId(fullDto.getDepartamentoId());
        dto.setDepartamento(fullDto.getDepartamento());
        dto.setCiudadId(fullDto.getCiudadId());
        dto.setCiudad(fullDto.getCiudad());
        dto.setImagenes(fullDto.getImagenes());
        return dto;
    }

    public List<ArticuloUsuarioResponseDTO> obtenerArticulosPorUsuarioConFiltros(
            Long usuarioId,
            String nombreArticulo,
            Integer categoriaCodigo,
            Integer tipoTransaccionCodigo,
            Integer estadoArticuloCodigo,
            Integer condicionCodigo,
            Long departamentoId,
            Long ciudadId) {

        List<Articulo> articulos = articuloRepository.buscarArticulosPorUsuarioConFiltros(
                usuarioId,
                nombreArticulo,
                categoriaCodigo,
                tipoTransaccionCodigo,
                estadoArticuloCodigo,
                condicionCodigo,
                departamentoId,
                ciudadId);

        return articulos.stream()
                .map(this::mapToArticuloUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    public void actualizarArticulo(Long id, ArticuloActualizarDTO articuloActualizarDTO,
            Long usuarioId) {
        Articulo articulo = articuloRepository.findByIdAndUsuarioIdAndEliminadoEnIsNull(id, usuarioId)
                .orElseThrow(
                        () -> new RuntimeException("Artículo no encontrado o no tienes permisos para modificarlo"));

        // Actualizar solo los campos que no son null (excepto imágenes, manejadas
        // abajo)
        if (articuloActualizarDTO.getTitulo() != null)
            articulo.setTitulo(articuloActualizarDTO.getTitulo());
        if (articuloActualizarDTO.getDescripcion() != null)
            articulo.setDescripcion(articuloActualizarDTO.getDescripcion());
        if (articuloActualizarDTO.getCategoriaCodigo() != null)
            articulo.setCategoriaCodigo(articuloActualizarDTO.getCategoriaCodigo());
        if (articuloActualizarDTO.getCondicionCodigo() != null)
            articulo.setCondicionCodigo(articuloActualizarDTO.getCondicionCodigo());
        if (articuloActualizarDTO.getEstadoArticuloCodigo() != null)
            articulo.setEstadoArticuloCodigo(articuloActualizarDTO.getEstadoArticuloCodigo());
        if (articuloActualizarDTO.getTipoTransaccionCodigo() != null)
            articulo.setTipoTransaccionCodigo(articuloActualizarDTO.getTipoTransaccionCodigo());
        if (articuloActualizarDTO.getDepartamentoCodigo() != null)
            articulo.setDepartamentoId(articuloActualizarDTO.getDepartamentoCodigo());
        if (articuloActualizarDTO.getCiudadCodigo() != null)
            articulo.setCiudadId(articuloActualizarDTO.getCiudadCodigo());
        if (articuloActualizarDTO.getPrecio() != null)
            articulo.setPrecio(articuloActualizarDTO.getPrecio());

        // Manejo de imágenes: combinar existentes + nuevas
        List<String> todasLasImagenes = new ArrayList<>();

        // 1. Agregar las imágenes existentes que se quieren mantener
        if (articuloActualizarDTO.getImagenesExistentes() != null &&
                !articuloActualizarDTO.getImagenesExistentes().trim().isEmpty()) {
            String[] existentes = articuloActualizarDTO.getImagenesExistentes().split(",");
            for (String url : existentes) {
                if (!url.trim().isEmpty()) {
                    todasLasImagenes.add(url.trim());
                }
            }
        }

        // 2. Subir y agregar las nuevas imágenes
        MultipartFile[] imagenesNuevas = articuloActualizarDTO.getImagenes();
        if (imagenesNuevas != null && imagenesNuevas.length > 0) {
            List<String> rutasNuevas = fileStorageService.guardarImagenes(imagenesNuevas);
            todasLasImagenes.addAll(rutasNuevas);
        }

        // 3. Identificar y eliminar las imágenes que ya no están
        if (articulo.getImagenes() != null && !articulo.getImagenes().isEmpty()) {
            String[] imagenesAnteriores = articulo.getImagenes().split(",");
            for (String urlAnterior : imagenesAnteriores) {
                String urlLimpia = urlAnterior.trim();
                // Si la imagen anterior NO está en las existentes, eliminarla de Cloudinary
                if (!todasLasImagenes.contains(urlLimpia)) {
                    fileStorageService.eliminarArchivo(urlLimpia);
                }
            }
        }

        // 4. Guardar todas las imágenes (existentes + nuevas)
        if (!todasLasImagenes.isEmpty()) {
            articulo.setImagenes(String.join(",", todasLasImagenes));
        } else {
            articulo.setImagenes(null);
        }

        articulo.setActualizadoEn(LocalDateTime.now());
        articuloRepository.save(articulo);
    }

    public void eliminarArticulo(Long id, Long usuarioId) {
        Articulo articulo = articuloRepository.findByIdAndUsuarioIdAndEliminadoEnIsNull(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado o no tienes permisos para eliminarlo"));

        articulo.setEliminadoEn(LocalDateTime.now());
        articuloRepository.save(articulo);
    }

    public void actualizarSolicitante(Long articuloId, Long solicitanteId) {
        Articulo articulo = articuloRepository.findByIdAndEliminadoEnIsNull(articuloId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        usuarioRepository.findById(solicitanteId).ifPresent(usuario -> {
            try {
                UsuarioBasicoDTO solicitanteDTO = new UsuarioBasicoDTO();
                solicitanteDTO.setId(usuario.getId());
                solicitanteDTO.setNombre(usuario.getNombreCompleto());
                solicitanteDTO.setEmail(usuario.getEmail());
                solicitanteDTO.setTelefono(usuario.getTelefono());
                solicitanteDTO.setDireccion(usuario.getDireccion());
                articulo.setSolicitante(objectMapper.writeValueAsString(solicitanteDTO));
                articuloRepository.save(articulo);
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar solicitante: " + e.getMessage());
            }
        });
    }

}
