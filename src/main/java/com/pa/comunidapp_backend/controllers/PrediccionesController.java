package com.pa.comunidapp_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa.comunidapp_backend.response.ModelosGlobalesDTO;
import com.pa.comunidapp_backend.response.ModelosUsuarioDTO;
import com.pa.comunidapp_backend.response.PrediccionesResponseDTO;
import com.pa.comunidapp_backend.services.PrediccionesService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/predicciones")
@Tag(name = "Predicciones")
public class PrediccionesController {

    @Autowired
    private PrediccionesService prediccionesService;

    @GetMapping("/globales")
    public ResponseEntity<ModelosGlobalesDTO> obtenerModelosGlobales() {
        ModelosGlobalesDTO modelos = prediccionesService.obtenerModelosGlobales();
        return new ResponseEntity<>(modelos, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ModelosUsuarioDTO> obtenerModelosUsuario(@PathVariable Long usuarioId) {
        ModelosUsuarioDTO modelos = prediccionesService.obtenerModelosUsuario(usuarioId);
        return new ResponseEntity<>(modelos, HttpStatus.OK);
    }

    @GetMapping("/completo/{usuarioId}")
    public ResponseEntity<PrediccionesResponseDTO> obtenerPrediccionesCompletasConUsuario(
            @PathVariable Long usuarioId) {
        PrediccionesResponseDTO predicciones = prediccionesService.obtenerPrediccionesCompletas(usuarioId);
        return new ResponseEntity<>(predicciones, HttpStatus.OK);
    }
}
