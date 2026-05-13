package com.pa.comunidapp_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pa.comunidapp_backend.models.Comercio;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Long> {

    List<Comercio> findByEliminadoEnIsNull();

    List<Comercio> findByEliminadoEnIsNullAndActivoTrue();

    List<Comercio> findByUsuarioIdAndEliminadoEnIsNull(Long usuarioId);

    Optional<Comercio> findByIdAndEliminadoEnIsNull(Long id);

    List<Comercio> findByNombreContainingIgnoreCaseAndEliminadoEnIsNull(String nombre);

    // Filtrados por categoría
    List<Comercio> findByEliminadoEnIsNullAndActivoTrueAndCategoriaId(Long categoriaId);

    // Búsqueda por nombre
    List<Comercio> findByEliminadoEnIsNullAndActivoTrueAndNombreContainingIgnoreCase(String nombre);

    // Combinado: categoría y nombre
    List<Comercio> findByEliminadoEnIsNullAndActivoTrueAndCategoriaIdAndNombreContainingIgnoreCase(Long categoriaId,
            String nombre);

    // Búsqueda dinámica con filtros
    @org.springframework.data.jpa.repository.Query("SELECT c FROM Comercio c WHERE c.eliminadoEn IS NULL " +
            "AND (:activo IS NULL OR c.activo = :activo) " +
            "AND (:usuarioId IS NULL OR c.usuario.id = :usuarioId) " +
            "AND (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:departamentoId IS NULL OR c.departamentoId = :departamentoId) " +
            "AND (:ciudadId IS NULL OR c.ciudadId = :ciudadId) " +
            "AND (:categoriaId IS NULL OR c.categoria.id = :categoriaId)")
    List<Comercio> buscarComerciosConFiltros(
            @org.springframework.data.repository.query.Param("nombre") String nombre,
            @org.springframework.data.repository.query.Param("categoriaId") Long categoriaId,
            @org.springframework.data.repository.query.Param("usuarioId") Long usuarioId,
            @org.springframework.data.repository.query.Param("activo") Boolean activo,
            @org.springframework.data.repository.query.Param("departamentoId") Long departamentoId,
            @org.springframework.data.repository.query.Param("ciudadId") Long ciudadId);
}
