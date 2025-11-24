# Modelos Predictivos - ComunidApp Backend

## Descripción General

El módulo de predicciones implementa **6 modelos analíticos** basados en **reglas J48** (árboles de decisión) que procesan datos históricos de transacciones, artículos y usuarios para generar insights predictivos en tiempo real.

**Endpoint:** `GET /api/predicciones/dashboard?usuarioId={id}`

---

## 🌍 MODELOS GLOBALES (Sin parámetro de usuario)

### 1️⃣ **Modelo 1: Éxito de Venta de Artículos**

**Descripción:**
Predice qué artículos tienen mayor probabilidad de venderse analizando patrones históricos de conversión. Categoriza los artículos por su potencial de venta según condición, categoría y precio.

**Features Utilizados:**

- `categoria_codigo` → Tipo de artículo (1-10)
- `condicion_codigo` → Estado físico (1=Nuevo, 2=Poco Uso, 3=Usado, 4=Dañado, 5=Defectuoso)
- `precio` → Valor del artículo
- `dias_publicado` → Tiempo desde creación
- `estado_articulo` → Disponible (1) o Prestado (2)

**Reglas J48 (Validaciones):**

```
IF condicion IN [1,2] (Nuevo/Poco Uso) AND precio < 500k
  THEN VENDIDO (tasa: 85%)

IF condicion IN [4,5] (Dañado/Defectuoso)
  THEN NO_VENDIDO (tasa: 35%)

IF condicion = 3 (Usado) AND categoria IN [1,7] (Electrónica/Música)
  THEN VENDIDO (tasa: 72%)
```

**Output:**

- Tasa de éxito general (%)
- Tasa por categoría
- Tasa por condición

**Gráfico:** Funnel Chart (embudo de conversión)

---

### 2️⃣ **Modelo 2: Cumplimiento en Préstamos**

**Descripción:**
Evalúa el riesgo de retrasos o incumplimientos en préstamos. Detecta patrones de comportamiento de usuarios que cumplen vs. que se atrasan en devoluciones.

**Features Utilizados:**

- `rating_prestador` → Calificación del usuario que presta (0-5)
- `rating_solicitante` → Calificación del usuario que solicita (0-5)
- `tipo_transaccion` → Debe ser = 2 (Préstamo)
- `fecha_estimada_devolucion` → Fecha pactada
- `respondido_en` → Fecha actual de devolución
- `condicion_articulo` → Artículos valiosos = mayor urgencia

**Reglas J48 (Validaciones):**

```
IF rating_prestador > 4.2 AND rating_solicitante > 4.0
  THEN CUMPLE (confianza: 92%)

IF rating_solicitante < 3.0
  THEN RETRASA (confianza: 68%)

IF condicion IN [4,5] (Dañado/Defectuoso)
  THEN RETRASA (confianza: 75%)
  // Mayor incentivo a devolver rápido por daño potencial

IF respondido_en > fecha_estimada_devolucion
  THEN RETRASO_CONFIRMADO
```

**Output:**

- Tasa de cumplimiento (%)
- Tasa de retraso (%)
- Retraso promedio en días
- Color indicador (Verde: >80% | Naranja: <80%)

**Gráfico:** Gauge Chart (velocímetro)

---

### 3️⃣ **Modelo 3: Tendencia por Categoría**

**Descripción:**
Identifica qué categorías están en crecimiento, estables o en declive. Compara datos mes a mes para detectar tendencias de mercado.

**Features Utilizados:**

- `categoria_codigo` → Categoría a analizar
- `tasa_venta_mensual` → (articulos_vendidos / articulos_totales) \* 100
- `precio_promedio` → Valor medio por categoría
- `volumen_transacciones` → Cantidad de movimientos

**Reglas J48 (Validaciones):**

```
IF tasa_venta_mes_actual > tasa_venta_mes_anterior + 5%
  THEN TENDENCIA_ALCISTA (crecimiento)

IF tasa_venta_mes_actual < tasa_venta_mes_anterior - 5%
  THEN TENDENCIA_BAJISTA (declive)

IF diferencia BETWEEN -5% AND 5%
  THEN TENDENCIA_LATERAL (estable)

IF volumen_transacciones < 10
  THEN DATOS_INSUFICIENTES (ignorar categoría)
```

**Output:**

- Tendencia (ALCISTA / LATERAL / BAJISTA)
- Variación porcentual
- Datos de últimos 3 meses

**Gráfico:** Line Chart (series de tiempo)

---

### 4️⃣ **Modelo 4: Demanda por Condición**

**Descripción:**
Analiza cómo la condición física del artículo impacta en su demanda y velocidad de venta. Identifica qué condición tiene mejor conversión.

**Features Utilizados:**

- `condicion_codigo` → Estado del artículo (1-5)
- `tasa_venta_por_condicion` → Porcentaje de conversión
- `precio_promedio` → Valor según condición
- `dias_venta_promedio` → Tiempo hasta venta

**Reglas J48 (Validaciones):**

```
IF condicion = 1 (Nuevo)
  THEN DEMANDA_ALTA (conversión ≥ 80%)

IF condicion IN [2,3] (Poco Uso/Usado)
  THEN DEMANDA_MEDIA (conversión 50-80%)

IF condicion IN [4,5] (Dañado/Defectuoso)
  THEN DEMANDA_BAJA (conversión < 50%)

Precio_Promedio inversamente proporcional a condición
Dias_Venta directamente proporcional a condición
```

**Output:**

- Nivel de demanda por condición
- Tasa de venta
- Precio promedio
- Días de venta promedio

**Gráfico:** Radar Chart (comparativa multidimensional)

---

## 👤 MODELOS POR USUARIO

### 5️⃣ **Modelo 5: Confiabilidad del Usuario**

**Descripción:**
Calcula una puntuación de confiabilidad (0-100) basada en el historial del usuario. Compara su confiabilidad con otros usuarios mediante percentiles.

**Features Utilizados:**

- `rating_promedio` → Calificación histórica del usuario
- `transacciones_completadas` → Transacciones exitosas
- `transacciones_totales` → Todas las transacciones
- `prestamos_a_tiempo` → Préstamos sin retraso
- `dias_antiguedad` → Tiempo desde registro
- `calificaciones_promedio` → Rating recibido de otros

**Fórmula de Score:**

```
Score = (rating_prom/5 × 0.35 × 100) +
        (transacciones_completadas/total × 0.25) +
        (prestamos_a_tiempo/total_prestamos × 0.20) +
        (min(dias_antiguedad/365, 1) × 0.15) +
        (calificaciones_promedio/5 × 0.05)

Score final = MIN(MAX(Score, 0), 100)
```

**Reglas J48 (Validaciones):**

```
IF Score > 75
  THEN CONFIABLE
  └─ Top usuarios, bajo riesgo

IF Score BETWEEN 50-75
  THEN MEDIO
  └─ Usuarios regulares, riesgo moderado

IF Score < 50
  THEN RIESGOSO
  └─ Alto riesgo, requiere supervisión

IF rating_promedio > 4.5 AND transacciones_incumplidas = 0
  THEN CONFIABLE_PREMIUM (95pts mínimo)
```

**Output:**

- Score (0-100)
- Categoría (CONFIABLE / MEDIO / RIESGOSO)
- Percentil comparativo
- Detalles componentes

**Gráfico:** Score Card (indicador circular tipo LinkedIn)

---

### 6️⃣ **Modelo 6: Predicción de Actividad Futura**

**Descripción:**
Predice si un usuario seguirá activo en los próximos 30 días. Útil para identificar usuarios inactivos o en riesgo de abandono.

**Features Utilizados:**

- `transacciones_ultimo_mes` → Actividad reciente
- `dias_sin_actividad` → Inactividad actual
- `articulos_activos` → Artículos publicados disponibles
- `rating_promedio` → Indicador de compromiso
- `ultima_actividad` → Fecha de último movimiento

**Reglas J48 (Validaciones):**

```
IF transacciones_ultimo_mes > 0 AND dias_sin_actividad < 30
  THEN ACTIVO (confianza: 94%)
  └─ Usuario activo, sin riesgo

IF dias_sin_actividad > 90 AND articulos_activos = 0
  THEN INACTIVO (confianza: 90%)
  └─ Usuario inactivo, potencial abandono

IF dias_sin_actividad BETWEEN 30-90 AND rating > 4.0
  THEN ACTIVO (confianza: 72%)
  └─ Usuarios buenos pueden estar pausados

IF rating < 3.0
  THEN INACTIVO (alta probabilidad abandono)
```

**Output:**

- Predicción (ACTIVO / INACTIVO)
- Confianza (0-1)
- Transacciones últimas 30 días
- Días sin actividad
- Artículos activos
- Spark Line (actividad diaria últimos 30 días)
- Heatmap (actividad mes a mes últimos 12 meses)

**Gráfico:** Spark Line + Heatmap
