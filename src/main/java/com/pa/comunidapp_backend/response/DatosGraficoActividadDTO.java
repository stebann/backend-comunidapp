package com.pa.comunidapp_backend.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosGraficoActividadDTO {
    private String prediccion;
    private Double confianzaPrediccion;
    private Long transaccionesUltimoMes;
    private Long diasSinActividad;
    private Long articulosActivos;
    private String tendencia30Dias;
    private List<PuntoSparkLineDTO> sparkLineUltimos30;
    private List<HeatmapMesDTO> heatmap12Meses;
}
