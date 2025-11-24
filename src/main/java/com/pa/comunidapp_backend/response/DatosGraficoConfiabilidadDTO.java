package com.pa.comunidapp_backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosGraficoConfiabilidadDTO {
    private Integer confiabilidadScore;
    private String categoriaConfiabilidad;
    private Integer percentilComparativo;
    private String descripcionPercentil;
    private DetallesConfiabilidadDTO detalles;
    private ComponentesScoreDTO componentesScore;
}
