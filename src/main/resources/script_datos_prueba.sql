-- ============================================
-- SCRIPT PARA RELLENAR LA BASE DE DATOS CON DATOS DE PRUEBA
-- ============================================
-- Ejecutar después de tener usuarios con direcciones corregidas

-- ============================================
-- COMERCIOS
-- ============================================

-- Comercio 1: Tienda de Electrónica (Usuario 1 - Medellín)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(1, 3, 'TechStore Medellín', 'Venta y reparación de equipos electrónicos', '3001234567', 'Calle 10 #45-70, El Poblado', 
 (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
 true, 4.5, true, NOW(), NULL);

-- Comercio 2: Restaurante (Usuario 3 - Cali)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(3, 1, 'Sabor Valluno', 'Comida típica del Valle del Cauca', '3109876543', 'Avenida 6N #23-50, Granada',
 (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1),
 true, 4.8, true, NOW(), NULL);

-- Comercio 3: Peluquería (Usuario 5 - Bucaramanga)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(5, 5, 'Estilo & Belleza', 'Peluquería y salón de belleza', '3156789012', 'Carrera 27 #42-20, Cabecera',
 (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1),
 false, 4.3, true, NOW(), NULL);

-- Comercio 4: Tienda de Ropa (Usuario 7 - Envigado)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(7, 2, 'Moda Urbana', 'Ropa y accesorios de moda', '3201234567', 'Carrera 43A #30 Sur-20, Zona Centro',
 (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
 true, 4.6, true, NOW(), NULL);

-- Comercio 5: Ferretería (Usuario 2 - Bogotá)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(2, 7, 'Ferretería El Tornillo', 'Herramientas y materiales de construcción', '3187654321', 'Calle 72 #10-34, Chapinero',
 (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1),
 true, 4.4, true, NOW(), NULL);

-- Comercio 6: Librería (Usuario 6 - Cartagena)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(6, 6, 'Librería Saber', 'Libros, útiles escolares y papelería', '3145678901', 'Centro Histórico, Calle de la Moneda',
 (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1),
 true, 4.7, true, NOW(), NULL);

-- Comercio 7: Gimnasio (Usuario 9 - Manizales)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(9, 4, 'FitZone Manizales', 'Gimnasio y entrenamiento personalizado', '3112345678', 'Avenida Santander #25-40',
 (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1),
 false, 4.9, true, NOW(), NULL);

-- Comercio 8: Tienda de Música (Usuario 4 - Barranquilla)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(4, 8, 'Melodías del Caribe', 'Instrumentos musicales y accesorios', '3198765432', 'Calle 84 #52-10, Riomar',
 (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1),
 true, 4.5, true, NOW(), NULL);

-- Comercio 9: Panadería (Usuario 10 - Armenia)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(10, 1, 'Pan del Quindío', 'Panadería artesanal y repostería', '3167890123', 'Carrera 14 #18-25, Centro',
 (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1),
 true, 4.8, true, NOW(), NULL);

-- Comercio 10: Veterinaria (Usuario 8 - Pereira)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(8, 4, 'Clínica Veterinaria Patitas', 'Atención veterinaria y tienda de mascotas', '3134567890', 'Avenida Circunvalar #8-45',
 (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1),
 false, 4.9, true, NOW(), NULL);

-- Comercio 11: Cafetería (Usuario 1 - Medellín)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(1, 1, 'Café Pergamino', 'Café de especialidad y postres', '3209876543', 'Carrera 37 #8A-37, El Poblado',
 (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
 true, 4.7, true, NOW(), NULL);

-- Comercio 12: Taller Mecánico (Usuario 11 - Neiva)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(11, 4, 'Taller Automotriz Express', 'Reparación y mantenimiento de vehículos', '3176543210', 'Calle 21 #5-60, Sur',
 (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1),
 false, 4.4, true, NOW(), NULL);

-- Comercio 13: Farmacia (Usuario 3 - Cali)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(3, 5, 'Farmacia Salud Total', 'Medicamentos y productos de salud', '3145678902', 'Avenida 5N #23-50',
 (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1),
 true, 4.6, true, NOW(), NULL);

-- Comercio 14: Juguetería (Usuario 7 - Envigado)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(7, 8, 'Mundo de Juguetes', 'Juguetes y artículos para niños', '3123456789', 'Carrera 43A #35 Sur-50',
 (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
 true, 4.8, true, NOW(), NULL);

-- Comercio 15: Óptica (Usuario 5 - Bucaramanga)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(5, 5, 'Óptica Visión Clara', 'Lentes, monturas y exámenes visuales', '3187654322', 'Carrera 33 #45-20',
 (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1),
 false, 4.7, true, NOW(), NULL);

-- Comercio 16: Pizzería (Usuario 2 - Bogotá)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(2, 1, 'Pizzería Napolitana', 'Pizza artesanal al horno de leña', '3198765433', 'Calle 85 #15-20, Usaquén',
 (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1),
 true, 4.9, true, NOW(), NULL);

-- Comercio 17: Lavandería (Usuario 6 - Cartagena)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(6, 4, 'Lavandería Express', 'Lavado y planchado de ropa', '3156789023', 'Calle del Arsenal, Getsemaní',
 (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1),
 true, 4.5, true, NOW(), NULL);

-- Comercio 18: Floristería (Usuario 9 - Manizales)
INSERT INTO comercios (usuario_id, categoria_id, nombre, descripcion, telefono, direccion, ciudad_id, departamento_id, tiene_envio, rating_promedio, activo, creado_en, eliminado_en) VALUES
(9, 8, 'Flores del Eje', 'Arreglos florales y plantas', '3112345679', 'Avenida Santander #30-15',
 (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1),
 (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1),
 true, 4.6, true, NOW(), NULL);


-- ============================================
-- ARTÍCULOS DE USUARIOS
-- ============================================

-- Artículos del Usuario 1 (Medellín)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(1, 'Laptop HP Pavilion', 'Laptop en excelente estado, 8GB RAM, 256GB SSD', 1, 2, 1, 1, 1200000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(1, 'Bicicleta de Montaña', 'Bicicleta Trek, poco uso, incluye casco', 3, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(1, 'Libro de Programación Java', 'Libro en buen estado, ideal para principiantes', 4, 3, 1, 1, 35000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 2 (Bogotá)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(2, 'iPhone 13 Pro', 'iPhone en perfecto estado, 128GB, con caja', 1, 1, 1, 1, 2800000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(2, 'Taladro Eléctrico', 'Taladro Black & Decker, incluye brocas', 5, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 3 (Cali)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(3, 'Smart TV Samsung 55"', 'Televisor 4K, poco uso, como nuevo', 1, 2, 1, 1, 1500000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(3, 'Juego de Sala', 'Sofá de 3 puestos + 2 poltronas, buen estado', 2, 3, 1, 1, 800000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 4 (Barranquilla)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(4, 'PlayStation 5', 'PS5 con 2 controles y 3 juegos', 9, 2, 1, 1, 2200000, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL),
(4, 'Guitarra Acústica', 'Guitarra Yamaha, ideal para principiantes', 7, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 5 (Bucaramanga)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(5, 'Cámara Canon EOS', 'Cámara réflex con lente 18-55mm', 1, 2, 1, 1, 1800000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(5, 'Patineta Eléctrica', 'Patineta con batería de larga duración', 3, 2, 1, 1, 650000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 6 (Cartagena)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(6, 'Tablet iPad Air', 'iPad Air 64GB, con Apple Pencil', 1, 2, 1, 1, 1400000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(6, 'Enciclopedia Completa', 'Colección de 20 tomos, excelente estado', 4, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 7 (Envigado)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(7, 'Zapatillas Nike Air Max', 'Talla 42, nuevas sin usar', 8, 1, 1, 1, 320000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(7, 'Mochila de Camping', 'Mochila 60L, ideal para trekking', 3, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 8 (Pereira)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(8, 'Microondas Samsung', 'Microondas 1.1 pies cúbicos, poco uso', 2, 2, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(8, 'Set de Herramientas', 'Caja con 120 piezas, marca Stanley', 5, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 9 (Manizales)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(9, 'Cafetera Espresso', 'Cafetera italiana, acero inoxidable', 2, 2, 1, 1, 180000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(9, 'Balón de Fútbol', 'Balón profesional Adidas', 3, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 10 (Armenia)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(10, 'Auriculares Sony WH-1000XM4', 'Auriculares con cancelación de ruido', 1, 2, 1, 1, 850000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL),
(10, 'Plantas Ornamentales', 'Set de 5 plantas para interior', 6, 1, 1, 1, 120000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL);

-- Artículos del Usuario 11 (Neiva)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(11, 'Drone DJI Mini 2', 'Drone con cámara 4K, poco uso', 1, 2, 1, 1, 1600000, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL),
(11, 'Raquetas de Tenis', 'Par de raquetas Wilson con estuche', 3, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL);

-- ============================================
-- ARTÍCULOS ADICIONALES PARA COMPLETAR CATEGORÍAS Y CONDICIONES
-- ============================================

-- Más artículos de ROPA (Categoría 8)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(1, 'Chaqueta North Face', 'Chaqueta impermeable para montaña', 8, 2, 1, 1, 450000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(2, 'Vestido de Fiesta', 'Vestido elegante talla M, usado una vez', 8, 2, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(3, 'Pantalones Deportivos', 'Set de 3 pantalones para gimnasio', 8, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(5, 'Camisa Formal', 'Camisa blanca para oficina, talla L', 8, 4, 1, 1, 35000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL);

-- Más artículos de MÚSICA (Categoría 7)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(6, 'Piano Eléctrico Yamaha', 'Piano de 61 teclas con soporte', 7, 2, 1, 1, 1200000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(8, 'Batería Acústica', 'Batería completa con platillos', 7, 3, 1, 1, 1800000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(9, 'Micrófono Shure SM58', 'Micrófono profesional para canto', 7, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(10, 'Amplificador Marshall', 'Amplificador para guitarra 50W', 7, 4, 1, 1, 650000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL);

-- Más artículos de JUEGOS (Categoría 9)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(1, 'Nintendo Switch', 'Consola con 5 juegos incluidos', 9, 2, 1, 1, 1400000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(7, 'Xbox Series S', 'Consola nueva en caja sellada', 9, 1, 1, 1, 1600000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(11, 'Juegos de Mesa Clásicos', 'Monopoly, Scrabble y Ajedrez', 9, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL),
(2, 'Control PS5 DualSense', 'Control con batería defectuosa', 9, 5, 1, 1, 120000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL);

-- Más artículos de JARDÍN (Categoría 6)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(3, 'Cortadora de Césped', 'Cortadora eléctrica Black & Decker', 6, 3, 1, 1, 380000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(5, 'Set de Herramientas de Jardín', 'Pala, rastrillo, tijeras de podar', 6, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(8, 'Manguera de Riego 50m', 'Manguera con pistola rociadora', 6, 3, 1, 1, 85000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(6, 'Macetas Decorativas', 'Set de 10 macetas de barro', 6, 4, 1, 1, 45000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL);

-- Artículos con condición DAÑADO (Código 4)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(4, 'Tablet Samsung Tab A', 'Pantalla con grieta pero funciona', 1, 4, 1, 1, 250000, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL),
(7, 'Bicicleta de Ruta', 'Cuadro dañado, necesita reparación', 3, 4, 1, 1, 180000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(9, 'Licuadora Oster', 'Motor hace ruido extraño', 2, 4, 1, 1, 65000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(11, 'Silla de Oficina', 'Base rota, respaldo en buen estado', 2, 4, 1, 1, 95000, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL);

-- Artículos con condición DEFECTUOSO (Código 5)
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(3, 'iPhone 11', 'No enciende, para repuestos', 1, 5, 1, 1, 350000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(6, 'Impresora HP', 'No imprime, error de sistema', 1, 5, 1, 1, 120000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(10, 'Aspiradora Electrolux', 'Motor quemado, para piezas', 2, 5, 1, 1, 80000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL),
(8, 'Consola PS4', 'No lee discos, solo digital', 9, 5, 1, 1, 450000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL);

-- Más Electrónica
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(1, 'MacBook Pro 2020', 'Laptop Apple M1, 16GB RAM, 512GB SSD', 1, 2, 1, 1, 4500000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(2, 'Monitor LG 27 pulgadas', 'Monitor 4K para diseño gráfico', 1, 1, 1, 1, 950000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(3, 'Parlantes Bluetooth JBL', 'Parlantes portátiles con buen sonido', 1, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(4, 'Smartwatch Samsung', 'Reloj inteligente con GPS', 1, 3, 1, 1, 380000, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL),
(5, 'Disco Duro Externo 2TB', 'Almacenamiento portátil Seagate', 1, 2, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(6, 'Router WiFi 6', 'Router de alta velocidad TP-Link', 1, 1, 1, 1, 320000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(7, 'Cámara GoPro Hero 9', 'Cámara de acción 4K', 1, 2, 1, 1, 1200000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL);

-- Más Hogar
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(8, 'Nevera Samsung 14 pies', 'Refrigerador con dispensador de agua', 2, 3, 1, 1, 1200000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(9, 'Lavadora LG 18kg', 'Lavadora automática con secado', 2, 2, 1, 1, 1800000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(10, 'Juego de Ollas Imusa', 'Set de 12 piezas antiadherentes', 2, 1, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL),
(11, 'Colchón Semidoble', 'Colchón ortopédico Spring', 2, 3, 1, 1, 450000, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL),
(1, 'Ventilador de Torre', 'Ventilador silencioso con control remoto', 2, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(2, 'Estufa de Gas 4 Puestos', 'Estufa Haceb en buen estado', 2, 3, 1, 1, 380000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(3, 'Comedor 6 Puestos', 'Mesa de madera con sillas tapizadas', 2, 2, 1, 1, 950000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL);

-- Más Deportes
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(4, 'Caminadora Eléctrica', 'Caminadora plegable con pantalla LCD', 3, 2, 1, 1, 1400000, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL),
(5, 'Pesas Ajustables 20kg', 'Set de mancuernas con discos', 3, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(6, 'Tabla de Surf', 'Tabla para principiantes 7 pies', 3, 3, 1, 1, 580000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(7, 'Guantes de Boxeo', 'Guantes Everlast 12oz', 3, 2, 1, 1, 180000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(8, 'Bicicleta Estática', 'Bicicleta para ejercicio en casa', 3, 3, 1, 1, 650000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(9, 'Balón de Baloncesto', 'Balón Spalding oficial NBA', 3, 1, 1, 1, 120000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(10, 'Raqueta de Tenis Wilson', 'Raqueta profesional con estuche', 3, 2, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL);

-- Más Libros
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(11, 'Colección Harry Potter', 'Los 7 libros en español, tapa dura', 4, 2, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL),
(1, 'Libro de Cocina Colombiana', 'Recetas tradicionales ilustradas', 4, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(2, 'Cien Años de Soledad', 'Edición conmemorativa García Márquez', 4, 2, 1, 1, 65000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(3, 'Libros de Medicina', 'Set de 5 libros de anatomía', 4, 3, 1, 1, 450000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(4, 'Biblia Reina Valera', 'Biblia de estudio con concordancia', 4, 2, 1, 1, 85000, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL);

-- Más Herramientas
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(5, 'Taladro Inalámbrico Dewalt', 'Taladro 20V con 2 baterías', 5, 2, 1, 1, 480000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(6, 'Sierra Circular', 'Sierra eléctrica Makita 7 1/4"', 5, 3, 1, 1, 380000, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(7, 'Compresor de Aire', 'Compresor 50L para pintura', 5, 2, 1, 1, 850000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(8, 'Escalera de Aluminio', 'Escalera extensible 6 metros', 5, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(9, 'Soldadora Eléctrica', 'Soldadora inverter 200A', 5, 2, 1, 1, 650000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL);

-- Más artículos variados
INSERT INTO articulos (usuario_id, titulo, descripcion, categoria_codigo, condicion_codigo, estado_articulo_codigo, tipo_transaccion_codigo, precio, ciudad_id, departamento_id, imagenes, creado_en, eliminado_en) VALUES
(10, 'Teclado Musical Casio', 'Teclado 61 teclas con atril', 7, 3, 1, 1, 380000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL),
(11, 'Violín 4/4', 'Violín completo con estuche y arco', 7, 2, 1, 1, 850000, (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1), NULL, NOW(), NULL),
(1, 'Chaqueta de Cuero', 'Chaqueta estilo motociclista talla L', 8, 3, 1, 1, 320000, (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(2, 'Botas de Montaña', 'Botas impermeables talla 42', 8, 2, 1, 1, 280000, (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1), NULL, NOW(), NULL),
(3, 'Traje de Baño', 'Traje completo marca Speedo', 8, 1, 1, 1, 95000, (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1), NULL, NOW(), NULL),
(4, 'Juego de Cartas Pokémon', 'Colección de 200 cartas', 9, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1), NULL, NOW(), NULL),
(5, 'Dron con Cámara', 'Dron para principiantes con cámara HD', 9, 2, 1, 1, 450000, (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1), NULL, NOW(), NULL),
(6, 'Carpa para Camping', 'Carpa 4 personas impermeable', 3, 3, 1, 2, 0, (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1), NULL, NOW(), NULL),
(7, 'Parrilla de Gas', 'Parrilla 3 quemadores con tapa', 2, 2, 1, 1, 680000, (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1), NULL, NOW(), NULL),
(8, 'Regadera Automática', 'Sistema de riego por goteo', 6, 2, 1, 1, 180000, (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1), NULL, NOW(), NULL),
(9, 'Semillas de Hortalizas', 'Pack de 20 variedades de semillas', 6, 1, 1, 1, 45000, (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1), NULL, NOW(), NULL),
(10, 'Abono Orgánico 50kg', 'Compost natural para plantas', 6, 1, 1, 1, 85000, (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1), (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1), NULL, NOW(), NULL);


-- ============================================
-- ARTÍCULOS DE COMERCIOS
-- ============================================

-- Artículos del Comercio 1 (TechStore Medellín)
INSERT INTO articulos_comercios (comercio_id, estado_id, nombre, descripcion, categoria_codigo, condicion_codigo, tipo_transaccion_codigo, precio, creado_en, eliminado_en) VALUES
((SELECT id FROM comercios WHERE nombre = 'TechStore Medellín' LIMIT 1), 1, 'Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico', 1, 1, 1, 280000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'TechStore Medellín' LIMIT 1), 1, 'Teclado Mecánico RGB', 'Teclado gaming con switches azules', 1, 1, 1, 350000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'TechStore Medellín' LIMIT 1), 1, 'Webcam Full HD', 'Cámara web 1080p con micrófono', 1, 1, 1, 180000, NOW(), NULL);

-- Artículos del Comercio 2 (Sabor Valluno)
INSERT INTO articulos_comercios (comercio_id, estado_id, nombre, descripcion, categoria_codigo, condicion_codigo, tipo_transaccion_codigo, precio, creado_en, eliminado_en) VALUES
((SELECT id FROM comercios WHERE nombre = 'Sabor Valluno' LIMIT 1), 1, 'Bandeja Paisa', 'Plato típico con frijoles, arroz, carne, chicharrón', 2, 1, 1, 25000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Sabor Valluno' LIMIT 1), 1, 'Sancocho Valluno', 'Sopa tradicional del Valle', 2, 1, 1, 18000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Sabor Valluno' LIMIT 1), 1, 'Empanadas Vallunas', 'Porción de 3 empanadas', 2, 1, 1, 8000, NOW(), NULL);

-- Artículos del Comercio 3 (Estilo & Belleza)
INSERT INTO articulos_comercios (comercio_id, estado_id, nombre, descripcion, categoria_codigo, condicion_codigo, tipo_transaccion_codigo, precio, creado_en, eliminado_en) VALUES
((SELECT id FROM comercios WHERE nombre = 'Estilo & Belleza' LIMIT 1), 1, 'Corte de Cabello Hombre', 'Corte moderno con diseño', 8, 1, 1, 25000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Estilo & Belleza' LIMIT 1), 1, 'Manicure y Pedicure', 'Servicio completo de uñas', 8, 1, 1, 40000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Estilo & Belleza' LIMIT 1), 1, 'Tinte Completo', 'Tinte profesional con tratamiento', 8, 1, 1, 80000, NOW(), NULL);

-- Artículos del Comercio 4 (Moda Urbana)
INSERT INTO articulos_comercios (comercio_id, estado_id, nombre, descripcion, categoria_codigo, condicion_codigo, tipo_transaccion_codigo, precio, creado_en, eliminado_en) VALUES
((SELECT id FROM comercios WHERE nombre = 'Moda Urbana' LIMIT 1), 1, 'Camiseta Básica', 'Camiseta 100% algodón, varios colores', 8, 1, 1, 35000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Moda Urbana' LIMIT 1), 1, 'Jeans Slim Fit', 'Pantalón jean ajustado, tallas 28-38', 8, 1, 1, 120000, NOW(), NULL),
((SELECT id FROM comercios WHERE nombre = 'Moda Urbana' LIMIT 1), 1, 'Chaqueta de Cuero', 'Chaqueta sintética, estilo motociclista', 8, 1, 1, 280000, NOW(), NULL);


-- ============================================
-- CATEGORÍAS DE ARTÍCULOS DE COMERCIOS
-- ============================================

-- Categorías para TechStore Medellín
INSERT INTO categorias_articulos_comercios (nombre, descripcion, comercio_id, creado_en, eliminado_en) VALUES
('Periféricos', 'Mouse, teclados, webcams', (SELECT id FROM comercios WHERE nombre = 'TechStore Medellín' LIMIT 1), NOW(), NULL),
('Accesorios', 'Cables, adaptadores, fundas', (SELECT id FROM comercios WHERE nombre = 'TechStore Medellín' LIMIT 1), NOW(), NULL);

-- Categorías para Sabor Valluno
INSERT INTO categorias_articulos_comercios (nombre, descripcion, comercio_id, creado_en, eliminado_en) VALUES
('Platos Fuertes', 'Comidas principales', (SELECT id FROM comercios WHERE nombre = 'Sabor Valluno' LIMIT 1), NOW(), NULL),
('Entradas', 'Aperitivos y entradas', (SELECT id FROM comercios WHERE nombre = 'Sabor Valluno' LIMIT 1), NOW(), NULL);

-- Categorías para Estilo & Belleza
INSERT INTO categorias_articulos_comercios (nombre, descripcion, comercio_id, creado_en, eliminado_en) VALUES
('Cabello', 'Servicios de peluquería', (SELECT id FROM comercios WHERE nombre = 'Estilo & Belleza' LIMIT 1), NOW(), NULL),
('Uñas', 'Servicios de manicure y pedicure', (SELECT id FROM comercios WHERE nombre = 'Estilo & Belleza' LIMIT 1), NOW(), NULL);

-- Categorías para Moda Urbana
INSERT INTO categorias_articulos_comercios (nombre, descripcion, comercio_id, creado_en, eliminado_en) VALUES
('Ropa Casual', 'Camisetas, jeans, shorts', (SELECT id FROM comercios WHERE nombre = 'Moda Urbana' LIMIT 1), NOW(), NULL),
('Ropa Formal', 'Camisas, pantalones de vestir', (SELECT id FROM comercios WHERE nombre = 'Moda Urbana' LIMIT 1), NOW(), NULL);


-- ============================================
-- TRANSACCIONES (Ventas y Préstamos)
-- ============================================

-- Transacción 1: Usuario 2 compra Laptop a Usuario 1 (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
(1, 2, 1, 1, 2, 'Me interesa la laptop, ¿está disponible?', NOW());

-- Transacción 2: Usuario 4 solicita préstamo de Bicicleta a Usuario 1 (PENDIENTE)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en) VALUES
(2, 4, 1, 2, 1, 'Necesito la bicicleta para el fin de semana', DATE_ADD(NOW(), INTERVAL 3 DAY), NOW());

-- Transacción 3: Usuario 6 compra Smart TV a Usuario 3 (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
(6, 6, 3, 1, 2, '¿Acepta 1.400.000?', NOW());

-- Transacción 4: Usuario 8 solicita préstamo de Taladro a Usuario 2 (ACEPTADA - DEVUELTO)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en, respondido_en) VALUES
(5, 8, 2, 2, 4, 'Necesito el taladro para una reparación', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY));

-- Transacción 5: Usuario 10 compra PlayStation 5 a Usuario 4 (RECHAZADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, mensaje_respuesta, creado_en, respondido_en) VALUES
(8, 10, 4, 1, 3, 'Ofrezco 2.000.000', 'Lo siento, el precio es fijo', NOW(), NOW());

-- Transacción 6: Usuario 5 solicita préstamo de Guitarra a Usuario 4 (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en, respondido_en) VALUES
(9, 5, 4, 2, 2, 'Quiero practicar para un evento', DATE_ADD(NOW(), INTERVAL 7 DAY), NOW(), NOW());

-- Transacción 7: Usuario 11 compra Cámara Canon a Usuario 5 (PENDIENTE)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
(10, 11, 5, 1, 1, 'Me interesa la cámara, ¿incluye estuche?', NOW());

-- Transacción 8: Usuario 3 compra MacBook a Usuario 1 (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'MacBook Pro 2020' LIMIT 1), 3, 1, 1, 2, 'Excelente precio, la compro', NOW());

-- Transacción 9: Usuario 7 solicita préstamo de Caminadora (PENDIENTE)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Caminadora Eléctrica' LIMIT 1), 7, 4, 2, 1, 'La necesito por 2 semanas', DATE_ADD(NOW(), INTERVAL 14 DAY), NOW());

-- Transacción 10: Usuario 9 compra Monitor LG (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Monitor LG 27 pulgadas' LIMIT 1), 9, 2, 1, 2, 'Perfecto para mi trabajo', NOW());

-- Transacción 11: Usuario 1 solicita préstamo de Taladro Dewalt (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en, respondido_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Taladro Inalámbrico Dewalt' LIMIT 1), 1, 5, 2, 2, 'Para un proyecto del fin de semana', DATE_ADD(NOW(), INTERVAL 5 DAY), NOW(), NOW());

-- Transacción 12: Usuario 6 compra Nevera Samsung (RECHAZADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, mensaje_respuesta, creado_en, respondido_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Nevera Samsung 14 pies' LIMIT 1), 6, 8, 1, 3, 'Ofrezco 1.000.000', 'Ya tengo otro comprador', NOW(), NOW());

-- Transacción 13: Usuario 10 compra Colección Harry Potter (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Colección Harry Potter' LIMIT 1), 10, 11, 1, 2, 'Me encantan estos libros', NOW());

-- Transacción 14: Usuario 2 solicita préstamo de Carpa (PENDIENTE)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Carpa para Camping' LIMIT 1), 2, 6, 2, 1, 'Para acampar este fin de semana', DATE_ADD(NOW(), INTERVAL 4 DAY), NOW());

-- Transacción 15: Usuario 8 compra GoPro (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Cámara GoPro Hero 9' LIMIT 1), 8, 7, 1, 2, 'Ideal para mis videos', NOW());

-- Transacción 16: Usuario 4 compra Lavadora LG (PENDIENTE)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Lavadora LG 18kg' LIMIT 1), 4, 9, 1, 1, '¿Está en buen estado?', NOW());

-- Transacción 17: Usuario 11 solicita préstamo de Pesas (ACEPTADA - DEVUELTO)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, fecha_estimada_devolucion, creado_en, respondido_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Pesas Ajustables 20kg' LIMIT 1), 11, 5, 2, 5, 'Para entrenar en casa', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY));

-- Transacción 18: Usuario 5 compra Smartwatch (ACEPTADA)
INSERT INTO transacciones (articulo_id, usuario_solicitante_id, usuario_propietario_id, tipo_codigo, estado_codigo, mensaje, creado_en) VALUES
((SELECT id FROM articulos WHERE titulo = 'Smartwatch Samsung' LIMIT 1), 5, 4, 1, 2, 'Buen precio', NOW());



SELECT 'Base de datos rellenada con datos de prueba correctamente' AS resultado;
