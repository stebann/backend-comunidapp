package com.pa.comunidapp_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa.comunidapp_backend.services.FileStorageService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/archivos")
@Tag(name = "Archivos")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/imagen/{nombreArchivo:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreArchivo) {
        Resource resource = fileStorageService.cargarArchivo(nombreArchivo);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
