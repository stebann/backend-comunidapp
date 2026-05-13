package com.pa.comunidapp_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioDetalleDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String email;
    private List<String> imagenes;
    private String sitioWeb;
    private Boolean tieneEnvio;
    private Long categoriaId;
    private String categoriaNombre;
    private Long departamentoCodigo;
    private String departamento;
    private Long ciudadCodigo;
    private String ciudad;
    private List<CategoriaArticuloComercioResponseDTO> categorias;
    private List<ArticuloComercioResponseDTO> articulos;
}
