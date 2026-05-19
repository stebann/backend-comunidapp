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



SELECT 'Base de datos rellenada con datos de prueba correctamente' AS resultado;
