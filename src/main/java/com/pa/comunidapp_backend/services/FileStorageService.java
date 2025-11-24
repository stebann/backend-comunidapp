package com.pa.comunidapp_backend.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/articulos}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo crear el directorio para almacenar archivos.", ex);
        }
    }

    public List<String> guardarImagenes(MultipartFile[] files) {
        List<String> rutasImagenes = new ArrayList<>();

        if (files == null || files.length == 0) {
            return rutasImagenes;
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String nombreArchivo = guardarArchivo(file);
            rutasImagenes.add(nombreArchivo);
        }

        return rutasImagenes;
    }

    public String guardarArchivo(MultipartFile file) {
        // Obtener el nombre original del archivo
        String nombreOriginal = file.getOriginalFilename();

        // Validar que el archivo tenga nombre
        if (nombreOriginal == null || nombreOriginal.isEmpty()) {
            throw new RuntimeException("El archivo no tiene un nombre válido");
        }

        // Normalizar el nombre del archivo
        nombreOriginal = StringUtils.cleanPath(nombreOriginal);

        try {
            // Validar el nombre del archivo
            if (nombreOriginal.contains("..")) {
                throw new RuntimeException("El nombre del archivo contiene una secuencia de ruta no válida: " + nombreOriginal);
            }

            // Generar un nombre único para el archivo
            String extension = "";
            int i = nombreOriginal.lastIndexOf('.');
            if (i > 0) {
                extension = nombreOriginal.substring(i);
            }
            String nombreUnico = UUID.randomUUID().toString() + extension;

            // Copiar el archivo a la ubicación de destino
            Path targetLocation = this.fileStorageLocation.resolve(nombreUnico);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nombreUnico;
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + nombreOriginal + ". Por favor, inténtelo de nuevo.", ex);
        }
    }

    public void eliminarArchivo(String nombreArchivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nombreArchivo).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo eliminar el archivo: " + nombreArchivo, ex);
        }
    }

    public void eliminarImagenes(String imagenesString) {
        if (imagenesString == null || imagenesString.isEmpty()) {
            return;
        }

        String[] imagenes = imagenesString.split(",");
        for (String imagen : imagenes) {
            if (!imagen.trim().isEmpty()) {
                eliminarArchivo(imagen.trim());
            }
        }
    }

    public Resource cargarArchivo(String nombreArchivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nombreArchivo).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Archivo no encontrado: " + nombreArchivo);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Archivo no encontrado: " + nombreArchivo, ex);
        }
    }
}
