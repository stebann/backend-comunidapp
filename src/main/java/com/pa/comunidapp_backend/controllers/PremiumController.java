package com.pa.comunidapp_backend.controllers;

import com.pa.comunidapp_backend.models.SolicitudAcceso;
import com.pa.comunidapp_backend.dto.SolicitudAccesoResumenDTO;
import com.pa.comunidapp_backend.enums.EEstadoSolicitudAcceso;
import com.pa.comunidapp_backend.services.PremiumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/premium")
@Tag(name = "Premium")
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitarAccesoPremium(@RequestParam Long usuarioId) {
        premiumService.crearSolicitud(usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/solicitudes")
    public ResponseEntity<List<SolicitudAccesoResumenDTO>> obtenerSolicitudesPendientes() {
        List<SolicitudAccesoResumenDTO> solicitudes = premiumService.obtenerSolicitudesPendientes();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/solicitudes/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitudAcceso>> obtenerSolicitudesPorUsuario(@PathVariable Long usuarioId) {
        List<SolicitudAcceso> solicitudes = premiumService.obtenerSolicitudesPorUsuario(usuarioId);
        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping("/solicitudes/cambiar-estado/{id}")
    public ResponseEntity<Void> cambiarEstadoSolicitud(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam Integer estado) {
        premiumService.cambiarEstadoSolicitud(id, adminId, estado);
        return ResponseEntity.noContent().build();
    }
}
