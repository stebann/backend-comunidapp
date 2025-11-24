package com.pa.comunidapp_backend.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pa.comunidapp_backend.enums.EEstadoArticulo;
import com.pa.comunidapp_backend.enums.EEstadoSolicitud;
import com.pa.comunidapp_backend.enums.ETipoSolicitud;
import com.pa.comunidapp_backend.enums.ETipoTransaccion;
import com.pa.comunidapp_backend.models.Articulo;
import com.pa.comunidapp_backend.models.Calificacion;
import com.pa.comunidapp_backend.models.Categoria;
import com.pa.comunidapp_backend.models.CondicionArticulo;
import com.pa.comunidapp_backend.models.Transaccion;
import com.pa.comunidapp_backend.models.Usuario;
import com.pa.comunidapp_backend.repositories.ArticuloRepository;
import com.pa.comunidapp_backend.repositories.CalificacionRepository;
import com.pa.comunidapp_backend.repositories.CategoriaRepository;
import com.pa.comunidapp_backend.repositories.CondicionArticuloRepository;
import com.pa.comunidapp_backend.repositories.MisGestionesRepository;
import com.pa.comunidapp_backend.repositories.UsuarioRepository;
import com.pa.comunidapp_backend.response.CategoriaTendenciaDTO;
import com.pa.comunidapp_backend.response.ComponentesScoreDTO;
import com.pa.comunidapp_backend.response.CondicionDemandaDTO;
import com.pa.comunidapp_backend.response.DatoMensualDTO;
import com.pa.comunidapp_backend.response.DatosGraficoActividadDTO;
import com.pa.comunidapp_backend.response.DatosGraficoConfiabilidadDTO;
import com.pa.comunidapp_backend.response.DatosGraficoCumplimientoDTO;
import com.pa.comunidapp_backend.response.DatosGraficoDemandaDTO;
import com.pa.comunidapp_backend.response.DatosGraficoExitoVentaDTO;
import com.pa.comunidapp_backend.response.DatosGraficoTendenciaDTO;
import com.pa.comunidapp_backend.response.DetallesConfiabilidadDTO;
import com.pa.comunidapp_backend.response.ExitoVentaPorCategoriaDTO;
import com.pa.comunidapp_backend.response.ExitoVentaPorCondicionDTO;
import com.pa.comunidapp_backend.response.HeatmapMesDTO;
import com.pa.comunidapp_backend.response.ModeloConfiabilidadUsuarioDTO;
import com.pa.comunidapp_backend.response.ModeloCumplimientoPrestamosDTO;
import com.pa.comunidapp_backend.response.ModeloDemandaCondicionesDTO;
import com.pa.comunidapp_backend.response.ModeloExitoVentaDTO;
import com.pa.comunidapp_backend.response.ModeloInactividadUsuarioDTO;
import com.pa.comunidapp_backend.response.ModeloTendenciaCategoriasDTO;
import com.pa.comunidapp_backend.response.ModelosGlobalesDTO;
import com.pa.comunidapp_backend.response.ModelosUsuarioDTO;
import com.pa.comunidapp_backend.response.PrediccionesResponseDTO;
import com.pa.comunidapp_backend.response.PuntoSparkLineDTO;

@Service
@Transactional
public class PrediccionesService {

        @Autowired
        private ArticuloRepository articuloRepository;

        @Autowired
        private MisGestionesRepository misGestionesRepository;

        @Autowired
        private CategoriaRepository categoriaRepository;

        @Autowired
        private CondicionArticuloRepository condicionArticuloRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private CalificacionRepository calificacionRepository;

        public PrediccionesResponseDTO obtenerPrediccionesCompletas(Long usuarioId) {
                PrediccionesResponseDTO response = new PrediccionesResponseDTO();
                response.setTimestamp(LocalDateTime.now());
                response.setModelosGlobales(obtenerModelosGlobales());
                response.setModelosUsuario(obtenerModelosUsuario(usuarioId));
                return response;
        }

        public ModelosGlobalesDTO obtenerModelosGlobales() {
                ModelosGlobalesDTO modelos = new ModelosGlobalesDTO();
                modelos.setModelo1ExitoVenta(calcularModeloExitoVenta());
                modelos.setModelo2CumplimientoPrestamos(calcularModeloCumplimientoPrestamos());
                modelos.setModelo3TendenciaCategorias(calcularModeloTendenciaCategorias());
                modelos.setModelo4DemandaCondiciones(calcularModeloDemandaCondiciones());
                return modelos;
        }

        public ModelosUsuarioDTO obtenerModelosUsuario(Long usuarioId) {
                ModelosUsuarioDTO modelos = new ModelosUsuarioDTO();
                modelos.setModelo5ConfiabilidadUsuario(calcularModeloConfiabilidadUsuario(usuarioId));
                modelos.setModelo6InactividadUsuario(calcularModeloInactividadUsuario(usuarioId));
                return modelos;
        }

        // MODELO 1: Éxito de Venta
        private ModeloExitoVentaDTO calcularModeloExitoVenta() {
                List<Articulo> articulos = articuloRepository.findByEliminadoEnIsNull();

                // Filtrar solo artículos de venta
                List<Articulo> articulosVenta = articulos.stream()
                                .filter(a -> a.getTipoTransaccionCodigo() != null &&
                                                a.getTipoTransaccionCodigo().equals(ETipoTransaccion.Venta.getCodigo()))
                                .collect(Collectors.toList());

                // Calcular por categoría
                Map<Integer, List<Articulo>> porCategoria = articulosVenta.stream()
                                .filter(a -> a.getCategoriaCodigo() != null)
                                .collect(Collectors.groupingBy(Articulo::getCategoriaCodigo));

                List<ExitoVentaPorCategoriaDTO> exitoPorCategoria = new ArrayList<>();
                for (Map.Entry<Integer, List<Articulo>> entry : porCategoria.entrySet()) {
                        Integer categoriaCodigo = entry.getKey();
                        List<Articulo> arts = entry.getValue();

                        long vendidos = arts.stream()
                                        .filter(a -> esArticuloVendido(a.getId()))
                                        .count();

                        double tasaExito = arts.size() > 0 ? (vendidos * 100.0 / arts.size()) : 0;

                        Categoria categoria = categoriaRepository.findByCodigo(categoriaCodigo).orElse(null);
                        String nombreCategoria = categoria != null ? categoria.getNombre() : "Desconocida";

                        ExitoVentaPorCategoriaDTO dto = new ExitoVentaPorCategoriaDTO();
                        dto.setCategoriaCodigo(categoriaCodigo);
                        dto.setCategoriaNombre(nombreCategoria);
                        dto.setTasaExito(tasaExito);
                        dto.setCantidadArticulos((long) arts.size());
                        dto.setCantidadVendidos(vendidos);
                        exitoPorCategoria.add(dto);
                }

                // Calcular por condición
                Map<Integer, List<Articulo>> porCondicion = articulosVenta.stream()
                                .filter(a -> a.getCondicionCodigo() != null)
                                .collect(Collectors.groupingBy(Articulo::getCondicionCodigo));

                List<ExitoVentaPorCondicionDTO> exitoPorCondicion = new ArrayList<>();
                for (Map.Entry<Integer, List<Articulo>> entry : porCondicion.entrySet()) {
                        Integer condicionCodigo = entry.getKey();
                        List<Articulo> arts = entry.getValue();

                        long vendidos = arts.stream()
                                        .filter(a -> esArticuloVendido(a.getId()))
                                        .count();

                        double tasaExito = arts.size() > 0 ? (vendidos * 100.0 / arts.size()) : 0;

                        CondicionArticulo condicion = condicionArticuloRepository.findByCodigo(condicionCodigo)
                                        .orElse(null);
                        String nombreCondicion = condicion != null ? condicion.getNombre() : "Desconocida";

                        ExitoVentaPorCondicionDTO dto = new ExitoVentaPorCondicionDTO();
                        dto.setCondicionCodigo(condicionCodigo);
                        dto.setCondicionNombre(nombreCondicion);
                        dto.setTasaExito(tasaExito);
                        dto.setCantidadArticulos((long) arts.size());
                        dto.setCantidadVendidos(vendidos);
                        exitoPorCondicion.add(dto);
                }

                // Tasa general
                long totalVendidos = articulosVenta.stream()
                                .filter(a -> esArticuloVendido(a.getId()))
                                .count();
                double tasaExitoGeneral = articulosVenta.size() > 0 ? (totalVendidos * 100.0 / articulosVenta.size())
                                : 0;

                DatosGraficoExitoVentaDTO datosGrafico = new DatosGraficoExitoVentaDTO();
                datosGrafico.setPorCategoria(exitoPorCategoria);
                datosGrafico.setPorCondicion(exitoPorCondicion);

                ModeloExitoVentaDTO modelo = new ModeloExitoVentaDTO();
                modelo.setNombre("Éxito de Venta de Artículos");
                modelo.setDescripcion("Análisis de qué artículos se venden según condición, categoría y precio");
                modelo.setTasaExitoGeneral(tasaExitoGeneral);
                modelo.setGraficoTipo("funnel");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }

        private boolean esArticuloVendido(Long articuloId) {
                Articulo articulo = articuloRepository.findByIdAndEliminadoEnIsNull(articuloId).orElse(null);
                if (articulo == null)
                        return false;

                // Solo considerar artículos de venta
                if (articulo.getTipoTransaccionCodigo() == null ||
                                !articulo.getTipoTransaccionCodigo().equals(ETipoTransaccion.Venta.getCodigo())) {
                        return false;
                }

                // Un artículo se considera vendido si tiene una transacción aceptada
                List<Transaccion> transacciones = misGestionesRepository
                                .findByArticuloIdAndEliminadoEnIsNull(articuloId);
                return transacciones.stream()
                                .anyMatch(t -> t.getEstadoCodigo().equals(EEstadoSolicitud.Aceptada.getCodigo()) ||
                                                t.getEstadoCodigo().equals(EEstadoSolicitud.Devuelto.getCodigo()));
        }

        // MODELO 2: Cumplimiento en Préstamos
        private ModeloCumplimientoPrestamosDTO calcularModeloCumplimientoPrestamos() {
                List<Transaccion> prestamos = misGestionesRepository.findAll().stream()
                                .filter(t -> t.getEliminadoEn() == null &&
                                                t.getTipoCodigo() != null &&
                                                t.getTipoCodigo().equals(ETipoSolicitud.Prestamo.getCodigo()))
                                .collect(Collectors.toList());

                long total = prestamos.size();
                long cumplidos = prestamos.stream()
                                .filter(t -> t.getEstadoCodigo().equals(EEstadoSolicitud.Devuelto.getCodigo()))
                                .count();

                long retrasados = prestamos.stream()
                                .filter(t -> {
                                        if (t.getFechaEstimadaDevolucion() != null && t.getRespondidoEn() != null) {
                                                return t.getRespondidoEn().isAfter(t.getFechaEstimadaDevolucion()) &&
                                                                t.getEstadoCodigo().equals(
                                                                                EEstadoSolicitud.Devuelto.getCodigo());
                                        }
                                        return false;
                                })
                                .count();

                double tasaCumplimiento = total > 0 ? (cumplidos * 100.0 / total) : 0;
                double tasaRetraso = total > 0 ? (retrasados * 100.0 / total) : 0;

                // Calcular retraso promedio
                double retrasoPromedio = prestamos.stream()
                                .filter(t -> t.getFechaEstimadaDevolucion() != null && t.getRespondidoEn() != null &&
                                                t.getRespondidoEn().isAfter(t.getFechaEstimadaDevolucion()) &&
                                                t.getEstadoCodigo().equals(EEstadoSolicitud.Devuelto.getCodigo()))
                                .mapToLong(t -> ChronoUnit.DAYS.between(t.getFechaEstimadaDevolucion(),
                                                t.getRespondidoEn()))
                                .average()
                                .orElse(0.0);

                String color = tasaCumplimiento >= 80 ? "#4CAF50" : tasaCumplimiento >= 60 ? "#FF9800" : "#F44336";

                DatosGraficoCumplimientoDTO datosGrafico = new DatosGraficoCumplimientoDTO();
                datosGrafico.setTasaCumplimientoPorcentaje(tasaCumplimiento);
                datosGrafico.setTasaRetrasoPorcentaje(tasaRetraso);
                datosGrafico.setPrestamosTotales(total);
                datosGrafico.setPrestamosCumplidos(cumplidos);
                datosGrafico.setPrestamosRetrasados(retrasados);
                datosGrafico.setRetrasoPromedioDias(retrasoPromedio);
                datosGrafico.setColor(color);

                ModeloCumplimientoPrestamosDTO modelo = new ModeloCumplimientoPrestamosDTO();
                modelo.setNombre("Cumplimiento en Préstamos");
                modelo.setDescripcion("Predicción de incumplimiento y retrasos en devoluciones");
                modelo.setTasaCumplimiento(tasaCumplimiento);
                modelo.setGraficoTipo("gauge");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }

        // MODELO 3: Tendencia por Categoría
        private ModeloTendenciaCategoriasDTO calcularModeloTendenciaCategorias() {
                List<Categoria> categorias = categoriaRepository.findByEliminadoEnIsNull();
                List<CategoriaTendenciaDTO> categoriasTendencia = new ArrayList<>();

                for (Categoria categoria : categorias) {
                        List<Articulo> articulosCategoria = articuloRepository.findByEliminadoEnIsNull().stream()
                                        .filter(a -> a.getCategoriaCodigo() != null &&
                                                        a.getCategoriaCodigo().equals(categoria.getCodigo()))
                                        .collect(Collectors.toList());

                        // Calcular datos mensuales (últimos 3 meses)
                        LocalDateTime ahora = LocalDateTime.now();
                        List<DatoMensualDTO> datosMensual = new ArrayList<>();

                        for (int i = 2; i >= 0; i--) {
                                LocalDateTime inicioMes = ahora.minusMonths(i).withDayOfMonth(1).withHour(0)
                                                .withMinute(0)
                                                .withSecond(0);
                                LocalDateTime finMes = inicioMes.plusMonths(1);

                                List<Articulo> articulosMes = articulosCategoria.stream()
                                                .filter(a -> a.getCreadoEn() != null &&
                                                                a.getCreadoEn().isAfter(inicioMes) &&
                                                                a.getCreadoEn().isBefore(finMes))
                                                .collect(Collectors.toList());

                                long vendidosMes = articulosMes.stream()
                                                .filter(a -> esArticuloVendido(a.getId()))
                                                .count();

                                double tasaVenta = articulosMes.size() > 0 ? (vendidosMes * 100.0 / articulosMes.size())
                                                : 0;

                                String[] meses = { "enero", "febrero", "marzo", "abril", "mayo", "junio",
                                                "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre" };
                                String nombreMes = meses[inicioMes.getMonthValue() - 1];

                                DatoMensualDTO dato = new DatoMensualDTO();
                                dato.setMes(nombreMes);
                                dato.setTasaVenta(tasaVenta);
                                datosMensual.add(dato);
                        }

                        // Calcular tendencia
                        if (datosMensual.size() >= 2) {
                                double primera = datosMensual.get(0).getTasaVenta();
                                double ultima = datosMensual.get(datosMensual.size() - 1).getTasaVenta();
                                double variacion = primera > 0 ? ((ultima - primera) / primera) * 100 : 0;

                                String tendencia;
                                if (variacion > 5) {
                                        tendencia = "ALCISTA";
                                } else if (variacion < -5) {
                                        tendencia = "BAJISTA";
                                } else {
                                        tendencia = "ESTABLE";
                                }

                                CategoriaTendenciaDTO categoriaTendencia = new CategoriaTendenciaDTO();
                                categoriaTendencia.setCategoriaCodigo(categoria.getCodigo().intValue());
                                categoriaTendencia.setCategoriaNombre(categoria.getNombre());
                                categoriaTendencia.setTendencia(tendencia);
                                categoriaTendencia.setVariacionPorcentaje(variacion);
                                categoriaTendencia.setDatosMensual(datosMensual);
                                categoriasTendencia.add(categoriaTendencia);
                        }
                }

                DatosGraficoTendenciaDTO datosGrafico = new DatosGraficoTendenciaDTO();
                datosGrafico.setCategorias(categoriasTendencia);

                ModeloTendenciaCategoriasDTO modelo = new ModeloTendenciaCategoriasDTO();
                modelo.setNombre("Tendencia por Categoría");
                modelo.setDescripcion("Identificar categorías en crecimiento, estables o en declive");
                modelo.setGraficoTipo("line_chart");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }

        // MODELO 4: Demanda por Condición
        private ModeloDemandaCondicionesDTO calcularModeloDemandaCondiciones() {
                List<CondicionArticulo> condiciones = condicionArticuloRepository.findByEliminadoEnIsNull();
                List<CondicionDemandaDTO> condicionesDemanda = new ArrayList<>();

                for (CondicionArticulo condicion : condiciones) {
                        List<Articulo> articulosCondicion = articuloRepository.findByEliminadoEnIsNull().stream()
                                        .filter(a -> a.getCondicionCodigo() != null &&
                                                        a.getCondicionCodigo().equals(condicion.getCodigo()))
                                        .collect(Collectors.toList());

                        long vendidos = articulosCondicion.stream()
                                        .filter(a -> esArticuloVendido(a.getId()))
                                        .count();

                        double tasaVenta = articulosCondicion.size() > 0
                                        ? (vendidos * 100.0 / articulosCondicion.size())
                                        : 0;

                        double precioPromedio = articulosCondicion.stream()
                                        .filter(a -> a.getPrecio() != null)
                                        .mapToDouble(a -> a.getPrecio().doubleValue())
                                        .average()
                                        .orElse(0.0);

                        // Calcular días promedio de venta
                        double diasVentaPromedio = articulosCondicion.stream()
                                        .filter(a -> esArticuloVendido(a.getId()) && a.getCreadoEn() != null)
                                        .mapToDouble(a -> {
                                                List<Transaccion> trans = misGestionesRepository
                                                                .findByArticuloIdAndEliminadoEnIsNull(a.getId());
                                                LocalDateTime fechaVenta = trans.stream()
                                                                .filter(t -> t.getEstadoCodigo().equals(
                                                                                EEstadoSolicitud.Aceptada.getCodigo())
                                                                                ||
                                                                                t.getEstadoCodigo().equals(
                                                                                                EEstadoSolicitud.Devuelto
                                                                                                                .getCodigo()))
                                                                .map(Transaccion::getCreadoEn)
                                                                .min(LocalDateTime::compareTo)
                                                                .orElse(a.getCreadoEn());
                                                return ChronoUnit.DAYS.between(a.getCreadoEn(), fechaVenta);
                                        })
                                        .average()
                                        .orElse(0.0);

                        String nivelDemanda;
                        if (tasaVenta >= 70) {
                                nivelDemanda = "ALTA";
                        } else if (tasaVenta >= 40) {
                                nivelDemanda = "MEDIA";
                        } else {
                                nivelDemanda = "BAJA";
                        }

                        CondicionDemandaDTO condicionDemanda = new CondicionDemandaDTO();
                        condicionDemanda.setCondicionCodigo(condicion.getCodigo().intValue());
                        condicionDemanda.setCondicionNombre(condicion.getNombre());
                        condicionDemanda.setNivelDemanda(nivelDemanda);
                        condicionDemanda.setTasaVenta(tasaVenta);
                        condicionDemanda.setPrecioPromedio(precioPromedio);
                        condicionDemanda.setDiasVentaPromedio(diasVentaPromedio);
                        condicionesDemanda.add(condicionDemanda);
                }

                DatosGraficoDemandaDTO datosGrafico = new DatosGraficoDemandaDTO();
                datosGrafico.setCondiciones(condicionesDemanda);

                ModeloDemandaCondicionesDTO modelo = new ModeloDemandaCondicionesDTO();
                modelo.setNombre("Demanda por Condición del Artículo");
                modelo.setDescripcion("Impacto de la condición del artículo en la conversión de venta");
                modelo.setGraficoTipo("radar");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }

        // MODELO 5: Confiabilidad del Usuario
        private ModeloConfiabilidadUsuarioDTO calcularModeloConfiabilidadUsuario(Long usuarioId) {
                Usuario usuario = usuarioRepository.findByIdAndEliminadoEnIsNull(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Transaccion> transaccionesPropietario = misGestionesRepository
                                .findByUsuarioPropietarioIdAndEliminadoEnIsNull(usuarioId);
                List<Transaccion> transaccionesSolicitante = misGestionesRepository
                                .findByUsuarioSolicitanteIdAndEliminadoEnIsNull(usuarioId);

                long transaccionesTotales = transaccionesPropietario.size() + transaccionesSolicitante.size();
                long transaccionesCompletadas = transaccionesPropietario.stream()
                                .filter(t -> t.getEstadoCodigo().equals(EEstadoSolicitud.Devuelto.getCodigo()))
                                .count()
                                + transaccionesSolicitante.stream()
                                                .filter(t -> t.getEstadoCodigo()
                                                                .equals(EEstadoSolicitud.Devuelto.getCodigo()))
                                                .count();

                double tasaCumplimiento = transaccionesTotales > 0
                                ? (transaccionesCompletadas * 100.0 / transaccionesTotales)
                                : 0;

                // Préstamos a tiempo
                long prestamosATiempo = transaccionesPropietario.stream()
                                .filter(t -> t.getTipoCodigo() != null &&
                                                t.getTipoCodigo().equals(ETipoSolicitud.Prestamo.getCodigo()) &&
                                                t.getFechaEstimadaDevolucion() != null &&
                                                t.getRespondidoEn() != null &&
                                                !t.getRespondidoEn().isAfter(t.getFechaEstimadaDevolucion()) &&
                                                t.getEstadoCodigo().equals(EEstadoSolicitud.Devuelto.getCodigo()))
                                .count();

                long transaccionesRetrasadas = transaccionesPropietario.stream()
                                .filter(t -> t.getFechaEstimadaDevolucion() != null &&
                                                t.getRespondidoEn() != null &&
                                                t.getRespondidoEn().isAfter(t.getFechaEstimadaDevolucion()))
                                .count();

                // Calificaciones
                List<Calificacion> calificaciones = calificacionRepository
                                .findByUsuarioCalificadoIdAndEliminadoEnIsNull(usuarioId);
                double calificacionesPromedio = calificaciones.stream()
                                .mapToInt(Calificacion::getPuntuacion)
                                .average()
                                .orElse(0.0);

                // Días de antigüedad
                long diasAntiguedad = usuario.getCreadoEn() != null
                                ? ChronoUnit.DAYS.between(usuario.getCreadoEn(), LocalDateTime.now())
                                : 0;

                // Calcular score de confiabilidad (0-100)
                int score = calcularScoreConfiabilidad(
                                usuario.getRatingPromedio() != null ? usuario.getRatingPromedio().doubleValue() : 5.0,
                                transaccionesTotales,
                                tasaCumplimiento,
                                diasAntiguedad,
                                calificacionesPromedio);

                String categoriaConfiabilidad;
                if (score >= 80) {
                        categoriaConfiabilidad = "CONFIABLE";
                } else if (score >= 60) {
                        categoriaConfiabilidad = "MODERADO";
                } else {
                        categoriaConfiabilidad = "BAJO";
                }

                // Percentil comparativo (simplificado)
                int percentil = calcularPercentil(score);
                String descripcionPercentil = "Top " + (100 - percentil) + "% de usuarios más confiables";

                // Tendencia
                String tendencia = calcularTendenciaUsuario(usuarioId);

                DetallesConfiabilidadDTO detalles = new DetallesConfiabilidadDTO();
                detalles.setRatingPromedio(
                                usuario.getRatingPromedio() != null ? usuario.getRatingPromedio().doubleValue() : 5.0);
                detalles.setTransaccionesTotales(transaccionesTotales);
                detalles.setTransaccionesCompletadas(transaccionesCompletadas);
                detalles.setTasaCumplimiento(tasaCumplimiento);
                detalles.setPrestamosATiempo(prestamosATiempo);
                detalles.setTransaccionesRetrasadas(transaccionesRetrasadas);
                detalles.setDiasAntiguedad(diasAntiguedad);
                detalles.setCalificacionesPromedio(calificacionesPromedio);
                detalles.setTendencia(tendencia);

                ComponentesScoreDTO componentes = calcularComponentesScore(
                                usuario.getRatingPromedio() != null ? usuario.getRatingPromedio().doubleValue() : 5.0,
                                transaccionesTotales,
                                tasaCumplimiento,
                                diasAntiguedad,
                                calificacionesPromedio);

                DatosGraficoConfiabilidadDTO datosGrafico = new DatosGraficoConfiabilidadDTO();
                datosGrafico.setConfiabilidadScore(score);
                datosGrafico.setCategoriaConfiabilidad(categoriaConfiabilidad);
                datosGrafico.setPercentilComparativo(percentil);
                datosGrafico.setDescripcionPercentil(descripcionPercentil);
                datosGrafico.setDetalles(detalles);
                datosGrafico.setComponentesScore(componentes);

                ModeloConfiabilidadUsuarioDTO modelo = new ModeloConfiabilidadUsuarioDTO();
                modelo.setNombre("Confiabilidad del Usuario");
                modelo.setDescripcion("Puntuación de confiabilidad basada en historial y comportamiento");
                modelo.setUsuarioId(usuarioId);
                modelo.setUsuarioNombre(usuario.getNombreCompleto());
                modelo.setGraficoTipo("score_card");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }

        private int calcularScoreConfiabilidad(double rating, long transacciones,
                        double tasaCumplimiento, long diasAntiguedad,
                        double calificaciones) {
                int score = 0;
                score += (int) ((rating / 5.0) * 30); // 30 puntos por rating
                score += Math.min(transacciones * 2, 25); // 25 puntos por transacciones (máx 12-13 transacciones)
                score += (int) ((tasaCumplimiento / 100.0) * 20); // 20 puntos por cumplimiento
                score += Math.min((int) (diasAntiguedad / 30), 15); // 15 puntos por antigüedad
                score += (int) ((calificaciones / 5.0) * 10); // 10 puntos por calificaciones
                return Math.min(score, 100);
        }

        private ComponentesScoreDTO calcularComponentesScore(double rating, long transacciones,
                        double tasaCumplimiento, long diasAntiguedad,
                        double calificaciones) {
                ComponentesScoreDTO componentes = new ComponentesScoreDTO();
                componentes.setRatingContribucion((int) ((rating / 5.0) * 30));
                componentes.setTransaccionesContribucion((int) Math.min(transacciones * 2, 25));
                componentes.setCumplimientoContribucion((int) ((tasaCumplimiento / 100.0) * 20));
                componentes.setAntiguedadContribucion((int) Math.min((diasAntiguedad / 30), 15));
                componentes.setCalificacionesContribucion((int) ((calificaciones / 5.0) * 10));
                return componentes;
        }

        private int calcularPercentil(int score) {
                // Simplificado: asumimos distribución normal
                if (score >= 90)
                        return 90;
                if (score >= 80)
                        return 75;
                if (score >= 70)
                        return 50;
                if (score >= 60)
                        return 25;
                return 10;
        }

        private String calcularTendenciaUsuario(Long usuarioId) {
                // Comparar actividad últimos 30 días vs 30 días anteriores
                LocalDateTime ahora = LocalDateTime.now();
                LocalDateTime hace30 = ahora.minusDays(30);
                LocalDateTime hace60 = ahora.minusDays(60);

                long actividad30 = misGestionesRepository.findAll().stream()
                                .filter(t -> t.getEliminadoEn() == null &&
                                                (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                t.getUsuarioSolicitanteId().equals(usuarioId))
                                                &&
                                                t.getCreadoEn() != null &&
                                                t.getCreadoEn().isAfter(hace30) &&
                                                t.getCreadoEn().isBefore(ahora))
                                .count();

                long actividad60 = misGestionesRepository.findAll().stream()
                                .filter(t -> t.getEliminadoEn() == null &&
                                                (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                t.getUsuarioSolicitanteId().equals(usuarioId))
                                                &&
                                                t.getCreadoEn() != null &&
                                                t.getCreadoEn().isAfter(hace60) &&
                                                t.getCreadoEn().isBefore(hace30))
                                .count();

                if (actividad30 > actividad60 * 1.1) {
                        return "CRECIENTE";
                } else if (actividad30 < actividad60 * 0.9) {
                        return "DECRECIENTE";
                } else {
                        return "ESTABLE";
                }
        }

        // MODELO 6: Predicción de Actividad
        private ModeloInactividadUsuarioDTO calcularModeloInactividadUsuario(Long usuarioId) {
                Usuario usuario = usuarioRepository.findByIdAndEliminadoEnIsNull(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                LocalDateTime ahora = LocalDateTime.now();
                LocalDateTime hace30Dias = ahora.minusDays(30);

                // Transacciones último mes
                long transaccionesUltimoMes = misGestionesRepository.findAll().stream()
                                .filter(t -> t.getEliminadoEn() == null &&
                                                (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                t.getUsuarioSolicitanteId().equals(usuarioId))
                                                &&
                                                t.getCreadoEn() != null &&
                                                t.getCreadoEn().isAfter(hace30Dias))
                                .count();

                // Días sin actividad
                List<Transaccion> ultimasTransacciones = misGestionesRepository.findAll().stream()
                                .filter(t -> t.getEliminadoEn() == null &&
                                                (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                t.getUsuarioSolicitanteId().equals(usuarioId))
                                                &&
                                                t.getCreadoEn() != null)
                                .sorted(Comparator.comparing(Transaccion::getCreadoEn).reversed())
                                .limit(1)
                                .collect(Collectors.toList());

                long diasSinActividad = 0;
                if (!ultimasTransacciones.isEmpty()) {
                        diasSinActividad = ChronoUnit.DAYS.between(ultimasTransacciones.get(0).getCreadoEn(), ahora);
                }

                // Artículos activos
                long articulosActivos = articuloRepository.findByUsuarioIdAndEliminadoEnIsNull(usuarioId)
                                .stream()
                                .filter(a -> a.getEstadoArticuloCodigo() != null &&
                                                a.getEstadoArticuloCodigo()
                                                                .equals(EEstadoArticulo.Disponible.getCodigo()))
                                .count();

                // Spark line últimos 30 días
                List<PuntoSparkLineDTO> sparkLine = new ArrayList<>();
                for (int i = 29; i >= 0; i--) {
                        LocalDateTime dia = ahora.minusDays(i);
                        LocalDateTime inicioDia = dia.withHour(0).withMinute(0).withSecond(0);
                        LocalDateTime finDia = dia.withHour(23).withMinute(59).withSecond(59);

                        long actividad = misGestionesRepository.findAll().stream()
                                        .filter(t -> t.getEliminadoEn() == null &&
                                                        (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                        t.getUsuarioSolicitanteId().equals(usuarioId))
                                                        &&
                                                        t.getCreadoEn() != null &&
                                                        t.getCreadoEn().isAfter(inicioDia) &&
                                                        t.getCreadoEn().isBefore(finDia))
                                        .count();

                        PuntoSparkLineDTO punto = new PuntoSparkLineDTO();
                        punto.setDia(30 - i);
                        punto.setActividad((int) actividad);
                        sparkLine.add(punto);
                }

                // Heatmap 12 meses
                List<HeatmapMesDTO> heatmap = new ArrayList<>();
                String[] meses = { "enero", "febrero", "marzo", "abril", "mayo", "junio",
                                "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre" };

                for (int i = 11; i >= 0; i--) {
                        LocalDateTime inicioMes = ahora.minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0)
                                        .withSecond(0);
                        LocalDateTime finMes = inicioMes.plusMonths(1);

                        long actividadMes = misGestionesRepository.findAll().stream()
                                        .filter(t -> t.getEliminadoEn() == null &&
                                                        (t.getUsuarioPropietarioId().equals(usuarioId) ||
                                                                        t.getUsuarioSolicitanteId().equals(usuarioId))
                                                        &&
                                                        t.getCreadoEn() != null &&
                                                        t.getCreadoEn().isAfter(inicioMes) &&
                                                        t.getCreadoEn().isBefore(finMes))
                                        .count();

                        String intensidad;
                        if (actividadMes >= 10) {
                                intensidad = "alta";
                        } else if (actividadMes >= 5) {
                                intensidad = "media";
                        } else {
                                intensidad = "baja";
                        }

                        HeatmapMesDTO mes = new HeatmapMesDTO();
                        mes.setMes(meses[inicioMes.getMonthValue() - 1]);
                        mes.setActividadPromedio((int) actividadMes);
                        mes.setIntensidad(intensidad);
                        heatmap.add(mes);
                }

                // Predicción
                String prediccion;
                double confianza;
                if (transaccionesUltimoMes >= 3 && diasSinActividad < 7) {
                        prediccion = "ACTIVO";
                        confianza = 0.9;
                } else if (transaccionesUltimoMes >= 1 && diasSinActividad < 14) {
                        prediccion = "ACTIVO";
                        confianza = 0.7;
                } else if (diasSinActividad < 30) {
                        prediccion = "ACTIVO";
                        confianza = 0.5;
                } else {
                        prediccion = "INACTIVO";
                        confianza = 0.8;
                }

                // Tendencia 30 días
                String tendencia30Dias = calcularTendenciaUsuario(usuarioId);

                DatosGraficoActividadDTO datosGrafico = new DatosGraficoActividadDTO();
                datosGrafico.setPrediccion(prediccion);
                datosGrafico.setConfianzaPrediccion(confianza);
                datosGrafico.setTransaccionesUltimoMes(transaccionesUltimoMes);
                datosGrafico.setDiasSinActividad(diasSinActividad);
                datosGrafico.setArticulosActivos(articulosActivos);
                datosGrafico.setTendencia30Dias(tendencia30Dias);
                datosGrafico.setSparkLineUltimos30(sparkLine);
                datosGrafico.setHeatmap12Meses(heatmap);

                ModeloInactividadUsuarioDTO modelo = new ModeloInactividadUsuarioDTO();
                modelo.setNombre("Predicción de Actividad");
                modelo.setDescripcion("Predicción de si el usuario seguirá activo en los próximos 30 días");
                modelo.setUsuarioId(usuarioId);
                modelo.setUsuarioNombre(usuario.getNombreCompleto());
                modelo.setGraficoTipo("spark_line_heatmap");
                modelo.setDatosGrafico(datosGrafico);

                return modelo;
        }
}
