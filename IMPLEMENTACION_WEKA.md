# Implementación de Modelos Predictivos con Weka

## 📋 Resumen

Se han implementado dos modelos predictivos usando Weka que reemplazan los cálculos manuales anteriores:

1. **Modelo de Actividad Futura** - Predice si un usuario estará ACTIVO o INACTIVO en los próximos 30 días
2. **Modelo de Potencial de Ventas** - Predice si un usuario tiene ALTO_POTENCIAL o BAJO_POTENCIAL para vender

## 🔧 Cambios Realizados

### 1. Servicio WekaService (`WekaService.java`)

Se creó un nuevo servicio que:

- Carga los modelos de Weka desde `src/main/resources/` al iniciar la aplicación
- Proporciona métodos para hacer predicciones con ambos modelos
- Calcula distribuciones de probabilidad para obtener niveles de confianza

**Modelos cargados:**

- `Actividad_futura.model` - Modelo de actividad futura
- `modelo_confiabilidad.model` - Modelo de potencial de ventas

### 2. Modificaciones en PrediccionesService

#### Modelo 5: Potencial de Ventas (antes Confiabilidad)

- **Antes:** Cálculo manual basado en reglas simples
- **Ahora:** Usa modelo de Weka entrenado
- **Atributos utilizados:**
  - `cantidad_articulos_activos` - Cantidad de artículos activos del usuario
  - `velocidad_venta_promedio` - Días promedio para vender un artículo
  - `calificacion_promedio_ventas` - Rating promedio del usuario
  - `precio_promedio_articulos` - Precio promedio de los artículos del usuario

#### Modelo 6: Actividad Futura

- **Antes:** Cálculo manual basado en reglas simples
- **Ahora:** Usa modelo de Weka entrenado
- **Atributos utilizados:**
  - `dias_desde_ultima_actividad` - Días desde la última transacción
  - `articulos_publicados_ultimo_mes` - Artículos publicados en los últimos 30 días
  - `frecuencia_conexion_semanal` - Transacciones en los últimos 7 días
  - `tasa_transacciones_completadas` - Porcentaje de transacciones completadas (0-1)

## 📊 Ejemplo de Respuesta JSON

### Endpoint: `GET /api/predicciones/completo/{usuarioId}`

```json
{
  "timestamp": "2024-12-19T10:30:00",
  "modelosGlobales": {
    // ... modelos globales sin cambios
  },
  "modelosUsuario": {
    "modelo5ConfiabilidadUsuario": {
      "nombre": "Potencial de Ventas del Usuario",
      "descripcion": "Predicción de potencial de ventas basada en modelo de Weka: ALTO_POTENCIAL (confianza: 87.50%)",
      "usuarioId": 1,
      "usuarioNombre": "Juan Pérez",
      "graficoTipo": "score_card",
      "datosGrafico": {
        "confiabilidadScore": 87,
        "categoriaConfiabilidad": "ALTO_POTENCIAL",
        "percentilComparativo": 90,
        "descripcionPercentil": "Top 10% de usuarios más confiables",
        "detalles": {
          "ratingPromedio": 4.8,
          "transaccionesTotales": 45,
          "transaccionesCompletadas": 42,
          "tasaCumplimiento": 93.3,
          "prestamosATiempo": 15,
          "transaccionesRetrasadas": 2,
          "diasAntiguedad": 180,
          "calificacionesPromedio": 4.7,
          "tendencia": "CRECIENTE"
        }
      }
    },
    "modelo6InactividadUsuario": {
      "nombre": "Predicción de Actividad",
      "descripcion": "Predicción de si el usuario seguirá activo en los próximos 30 días",
      "usuarioId": 1,
      "usuarioNombre": "Juan Pérez",
      "graficoTipo": "spark_line_heatmap",
      "datosGrafico": {
        "prediccion": "ACTIVO",
        "confianzaPrediccion": 0.92,
        "transaccionesUltimoMes": 8,
        "diasSinActividad": 2,
        "articulosActivos": 5,
        "tendencia30Dias": "CRECIENTE",
        "sparkLineUltimos30": [
          // ... datos de actividad diaria
        ],
        "heatmap12Meses": [
          // ... datos de actividad mensual
        ]
      }
    }
  }
}
```

## 🎨 Visualización en el Frontend (Angular)

### Modelo 5: Potencial de Ventas

**Componente visual sugerido:**

```
┌─────────────────────────────────────────┐
│  Potencial de Ventas del Usuario        │
│  Juan Pérez                             │
├─────────────────────────────────────────┤
│                                         │
│         ┌──────────────┐                │
│         │              │                │
│         │     87%      │                │
│         │              │                │
│         └──────────────┘                │
│                                         │
│  Categoría: ALTO_POTENCIAL              │
│  Confianza: 87.50%                      │
│                                         │
│  Top 10% de usuarios más confiables    │
│                                         │
│  Detalles:                              │
│  • Rating promedio: 4.8/5                │
│  • Transacciones: 45 (42 completadas)  │
│  • Tasa cumplimiento: 93.3%            │
│  • Tendencia: CRECIENTE                 │
└─────────────────────────────────────────┘
```

### Modelo 6: Actividad Futura

**Componente visual sugerido:**

```
┌─────────────────────────────────────────┐
│  Predicción de Actividad                │
│  Juan Pérez                             │
├─────────────────────────────────────────┤
│                                         │
│  Estado: ACTIVO                         │
│  Confianza: 92%                         │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │ Actividad últimos 30 días       │   │
│  │ ▁▃▁▅▃▁▃▁▃▁▅▁▃▁▃▁▅▁▃▁▃▁▅▁▃▁▃▁▅  │   │
│  └─────────────────────────────────┘   │
│                                         │
│  Estadísticas:                          │
│  • Transacciones último mes: 8          │
│  • Días sin actividad: 2               │
│  • Artículos activos: 5                │
│  • Tendencia: CRECIENTE                 │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │ Heatmap 12 meses                │   │
│  │ Ene Feb Mar Abr May Jun         │   │
│  │ ▓▓ ▓▓ ▓▓▓ ▓▓▓ ▓▓▓ ▓▓▓          │   │
│  │ Jul Ago Sep Oct Nov Dic         │   │
│  │ ▓▓ ▓▓ ▓▓▓ ▓▓▓ ▓▓▓ ▓▓           │   │
│  └─────────────────────────────────┘   │
└─────────────────────────────────────────┘
```

## 🔍 Diferencias Clave

### Antes (Cálculo Manual)

- Reglas if-else simples
- Confianza fija basada en rangos
- No usa aprendizaje automático

### Ahora (Modelo Weka)

- Modelo entrenado con datos históricos
- Confianza basada en distribución de probabilidades
- Predicciones más precisas y adaptativas

## ⚠️ Notas Importantes

1. **Modelos deben existir:** Los archivos `.model` deben estar en `src/main/resources/`
2. **Inicialización:** Los modelos se cargan al iniciar la aplicación (método `@PostConstruct`)
3. **Compatibilidad:** Los modelos globales (1-4) NO fueron modificados, solo los modelos de usuario (5-6)
4. **Formato de respuesta:** La estructura JSON se mantiene compatible con el frontend existente

## 🚀 Próximos Pasos

1. Probar el endpoint con diferentes usuarios
2. Verificar que los modelos se carguen correctamente
3. Ajustar la visualización en Angular si es necesario
4. Monitorear la precisión de las predicciones
