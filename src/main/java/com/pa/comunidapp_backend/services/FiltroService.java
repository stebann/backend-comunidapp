package com.pa.comunidapp_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa.comunidapp_backend.models.Articulo;
import com.pa.comunidapp_backend.models.Categoria;
import com.pa.comunidapp_backend.models.CategoriaFiltro;
import com.pa.comunidapp_backend.models.Ciudad;
import com.pa.comunidapp_backend.models.CiudadFiltro;
import com.pa.comunidapp_backend.models.CondicionesFiltro;
import com.pa.comunidapp_backend.models.Departamento;
import com.pa.comunidapp_backend.models.DepartamentoFiltro;
import com.pa.comunidapp_backend.models.EstadoCivilFiltro;
import com.pa.comunidapp_backend.models.GeneroFiltro;
import com.pa.comunidapp_backend.models.RolFiltro;
import com.pa.comunidapp_backend.models.TipoFiltro;
import com.pa.comunidapp_backend.models.TipoTransaccion;
import com.pa.comunidapp_backend.repositories.ArticuloRepository;
import com.pa.comunidapp_backend.repositories.CategoriaRepository;
import com.pa.comunidapp_backend.repositories.CiudadRepository;
import com.pa.comunidapp_backend.repositories.CondicionArticuloRepository;
import com.pa.comunidapp_backend.repositories.DepartamentoRepository;
import com.pa.comunidapp_backend.repositories.EstadoArticuloRepository;
import com.pa.comunidapp_backend.repositories.EstadoCivilRepository;
import com.pa.comunidapp_backend.repositories.GeneroRepository;
import com.pa.comunidapp_backend.repositories.RolRepository;
import com.pa.comunidapp_backend.repositories.TipoTransaccionRepository;
import com.pa.comunidapp_backend.response.ArticuloResponseDTO;

@Service
public class FiltroService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Autowired
    private EstadoArticuloRepository estadoArticuloRepository;

    @Autowired
    private CondicionArticuloRepository condicionArticuloRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    public List<RolFiltro> obtenerRoles() {
        return rolRepository.findAll().stream()
            .map(rol -> new RolFiltro(rol.getCodigo(), rol.getNombre()))
            .collect(java.util.stream.Collectors.toList());
    }

    public List<DepartamentoFiltro> obtenerDepartamentos() {
        return departamentoRepository.findByEliminadoEnIsNull().stream()
            .map(dep -> new DepartamentoFiltro(dep.getId().intValue(), dep.getNombre()))
            .collect(Collectors.toList());
    }

    public List<CiudadFiltro> obtenerCiudadesPorDepartamento(Long departamentoId) {
        return ciudadRepository.findByDepartamentoIdAndEliminadoEnIsNull(departamentoId).stream()
            .map(ciu -> new CiudadFiltro(ciu.getId().intValue(), ciu.getNombre()))
            .collect(Collectors.toList());
    }

    public List<CiudadFiltro> obtenerTodasLasCiudades() {
        return ciudadRepository.findByEliminadoEnIsNull().stream()
            .map(ciu -> new CiudadFiltro(ciu.getId().intValue(), ciu.getNombre()))
            .collect(Collectors.toList());
    }

    public List<GeneroFiltro> obtenerGeneros() {
        return generoRepository.findAll().stream()
            .filter(g -> g.getEliminadoEn() == null)
            .map(g -> new GeneroFiltro(g.getId().intValue(), g.getNombre()))
            .collect(Collectors.toList());
    }

    public List<EstadoCivilFiltro> obtenerEstadosCiviles() {
        return estadoCivilRepository.findAll().stream()
            .filter(ec -> ec.getEliminadoEn() == null)
            .map(ec -> new EstadoCivilFiltro(ec.getId().intValue(), ec.getNombre()))
            .collect(Collectors.toList());
    }

    public List<CategoriaFiltro> obtenerCategorias() {
        List<Categoria> categorias = categoriaRepository.findByEliminadoEnIsNull();
        List<CategoriaFiltro> categoriasFiltro = new ArrayList<>();

        for (Categoria categoria : categorias) {
            categoriasFiltro.add(new CategoriaFiltro(categoria.getCodigo(), categoria.getNombre()));
        }

        return categoriasFiltro;
    }

    public List<TipoFiltro> obtenerTipos() {
        List<TipoTransaccion> tipos = tipoTransaccionRepository.findByEliminadoEnIsNull();
        List<TipoFiltro> tiposFiltro = new ArrayList<>();

        for (TipoTransaccion tipo : tipos) {
            tiposFiltro.add(new TipoFiltro(tipo.getCodigo(), tipo.getNombre()));
        }

        return tiposFiltro;
    }

    public List<CondicionesFiltro> obtenerCondiciones() {
        List<com.pa.comunidapp_backend.models.CondicionArticulo> condiciones = condicionArticuloRepository.findByEliminadoEnIsNull();
        List<CondicionesFiltro> condicionesFiltro = new ArrayList<>();
        for (com.pa.comunidapp_backend.models.CondicionArticulo condicion : condiciones) {
            condicionesFiltro.add(new CondicionesFiltro(condicion.getCodigo(), condicion.getNombre()));
        }
        return condicionesFiltro;
    }

    public List<CategoriaFiltro> buscarCategoriasPorNombre(String nombre) {
        List<Categoria> categorias = categoriaRepository.findByNombreContainingIgnoreCaseAndEliminadoEnIsNull(nombre);
        List<CategoriaFiltro> categoriasFiltro = new ArrayList<>();

        for (Categoria categoria : categorias) {
            categoriasFiltro.add(new CategoriaFiltro(categoria.getCodigo(), categoria.getNombre()));
        }

        return categoriasFiltro;
    }

    public List<ArticuloResponseDTO> buscarArticulos(String nombreArticulo, Integer categoriaCodigo, Integer tipoTransaccionCodigo,
            Integer estadoArticuloCodigo, Integer condicionCodigo, Long departamentoId, Long ciudadId, String nombreUsuario) {
        List<Articulo> articulos = articuloRepository.buscarArticulosConFiltrosPorCodigo(nombreArticulo, categoriaCodigo, tipoTransaccionCodigo, estadoArticuloCodigo,
                condicionCodigo, departamentoId, ciudadId, nombreUsuario);
        return articulos.stream()
                .map(this::mapToArticuloResponseDTO)
                .collect(Collectors.toList());
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
        dto.setTipoTransaccionCodigo(articulo.getTipoTransaccionCodigo());
        if (articulo.getTipoTransaccionCodigo() != null) {
            tipoTransaccionRepository.findByCodigo(articulo.getTipoTransaccionCodigo())
                .ifPresent(tipo -> dto.setTipoTransaccionNombre(tipo.getNombre()));
        }

        // Convertir string de imágenes a lista
        if (articulo.getImagenes() != null && !articulo.getImagenes().isEmpty()) {
            dto.setImagenes(List.of(articulo.getImagenes().split(",")));
        }

        return dto;
    }
}
