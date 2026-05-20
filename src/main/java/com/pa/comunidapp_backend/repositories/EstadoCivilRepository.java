package com.pa.comunidapp_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pa.comunidapp_backend.models.EstadoCivil;

@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {
}
