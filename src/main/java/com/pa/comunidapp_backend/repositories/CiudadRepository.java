package com.pa.comunidapp_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pa.comunidapp_backend.models.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByDepartamentoIdAndEliminadoEnIsNull(Long departamentoId);

    List<Ciudad> findByEliminadoEnIsNull();
}
