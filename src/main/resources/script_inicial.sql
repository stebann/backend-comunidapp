-- Categorías (codigo fijo: 1-10)
INSERT INTO categorias (codigo, nombre, creado_en, eliminado_en) VALUES
(1, 'Electrónica', NOW(), NULL),
(2, 'Hogar', NOW(), NULL),
(3, 'Deportes', NOW(), NULL),
(4, 'Libros', NOW(), NULL),
(5, 'Herramientas', NOW(), NULL),
(6, 'Jardín', NOW(), NULL),
(7, 'Música', NOW(), NULL),
(8, 'Ropa', NOW(), NULL),
(9, 'Juegos', NOW(), NULL),
(10, 'Otro', NOW(), NULL);


-- Departamentos
INSERT INTO departamentos (nombre, creado_en, eliminado_en) VALUES
('Antioquia', NOW(), NULL),
('Bogotá D.C.', NOW(), NULL),
('Valle del Cauca', NOW(), NULL),
('Atlántico', NOW(), NULL),
('Santander', NOW(), NULL),
('Bolívar', NOW(), NULL),
('Caldas', NOW(), NULL),
('Risaralda', NOW(), NULL),
('Quindío', NOW(), NULL),
('Huila', NOW(), NULL),
('Magdalena', NOW(), NULL),
('Cundinamarca', NOW(), NULL),
('Norte de Santander', NOW(), NULL),
('Tolima', NOW(), NULL),
('Meta', NOW(), NULL),
('Nariño', NOW(), NULL),
('Córdoba', NOW(), NULL),
('Cesar', NOW(), NULL);


-- Ciudades
INSERT INTO ciudades (nombre, departamento_id, creado_en, eliminado_en) VALUES
('Medellín', 1, NOW(), NULL),
('Envigado', 1, NOW(), NULL),
('Itagüí', 1, NOW(), NULL),
('Bello', 1, NOW(), NULL),
('Bogotá', 2, NOW(), NULL),
('Cali', 3, NOW(), NULL),
('Palmira', 3, NOW(), NULL),
('Buenaventura', 3, NOW(), NULL),
('Barranquilla', 4, NOW(), NULL),
('Soledad', 4, NOW(), NULL),
('Bucaramanga', 5, NOW(), NULL),
('Floridablanca', 5, NOW(), NULL),
('Cartagena', 6, NOW(), NULL),
('Manizales', 7, NOW(), NULL),
('Pereira', 8, NOW(), NULL),
('Armenia', 9, NOW(), NULL),
('Neiva', 10, NOW(), NULL),
('Santa Marta', 11, NOW(), NULL),
('Soacha', 12, NOW(), NULL),
('Chía', 12, NOW(), NULL),
('Cúcuta', 13, NOW(), NULL),
('Ibagué', 14, NOW(), NULL),
('Villavicencio', 15, NOW(), NULL),
('Pasto', 16, NOW(), NULL),
('Montería', 17, NOW(), NULL),
('Valledupar', 18, NOW(), NULL);


-- Categorías de Comercios (codigo fijo: 1-8)
INSERT INTO categorias_comercio (codigo, nombre, descripcion, creado_en, eliminado_en) VALUES
(1, 'Alimentos y Bebidas', 'Comercios de comida y bebidas', NOW(), NULL),
(2, 'Ropa y Accesorios', 'Tiendas de ropa, zapatos y accesorios', NOW(), NULL),
(3, 'Tecnología', 'Tiendas de electrónica y tecnología', NOW(), NULL),
(4, 'Servicios', 'Servicios profesionales', NOW(), NULL),
(5, 'Salud y Belleza', 'Farmacias, peluquerías y salones de belleza', NOW(), NULL),
(6, 'Educación', 'Academias, tutorías y centros educativos', NOW(), NULL),
(7, 'Hogar y Decoración', 'Tiendas de muebles y decoración', NOW(), NULL),
(8, 'Otro', 'Otras categorías de comercios', NOW(), NULL);


-- Condiciones de artículos (codigo fijo: 1=Nuevo, 2=Poco Uso, 3=Usado, 4=Dañado, 5=Defectuoso)
INSERT INTO condiciones_articulo (codigo, nombre, creado_en, eliminado_en) VALUES
(1, 'Nuevo', NOW(), NULL),
(2, 'Poco Uso', NOW(), NULL),
(3, 'Usado', NOW(), NULL),
(4, 'Dañado', NOW(), NULL),
(5, 'Defectuoso', NOW(), NULL);


-- Estados del artículo (codigo fijo: 1=Disponible, 2=Prestado)
INSERT INTO estados_articulo (codigo, nombre, creado_en, eliminado_en) VALUES
(1, 'Disponible', NOW(), NULL),
(2, 'Prestado', NOW(), NULL);


-- Tipos de transacción (codigo fijo: 1=Venta, 2=Préstamo)
INSERT INTO tipos_transaccion (codigo, nombre, creado_en, eliminado_en) VALUES
(1, 'Venta', NOW(), NULL),
(2, 'Préstamo', NOW(), NULL);


-- Estados de transacción (codigo fijo: 1=Pendiente, 2=Aceptada, 3=Rechazada, 4=DevolucionPendiente, 5=Devuelto, 6=Cancelado)
INSERT INTO estado_transacciones (codigo, nombre, descripcion) VALUES
(1, 'Pendiente', 'La transacción está pendiente de respuesta'),
(2, 'Aceptada', 'La transacción ha sido aceptada'),
(3, 'Rechazada', 'La transacción ha sido rechazada'),
(4, 'DevolucionPendiente', 'El artículo fue devuelto pero la devolución está pendiente de confirmación'),
(5, 'Devuelto', 'El artículo ha sido devuelto y confirmado'),
(6, 'Cancelado', 'La transacción ha sido cancelada');


-- Estados de solicitud de comercio (codigo fijo: 1=Pendiente, 2=Aprobada, 3=Rechazada, 4=Suspendida)
INSERT INTO estados_solicitud_comercio (codigo, nombre, creado_en, eliminado_en) VALUES
(1, 'PENDIENTE', NOW(), NULL),
(2, 'APROBADA', NOW(), NULL),
(3, 'RECHAZADA', NOW(), NULL),
(4, 'SUSPENDIDA', NOW(), NULL);


-- Permisos (con tipo: USUARIO o ADMIN)
INSERT INTO permisos (codigo, nombre, descripcion, tipo, creado_en, eliminado_en) VALUES
(1, 'GESTIONAR_COMERCIOS', 'Permite gestionar sus propios comercios', 'USUARIO', NOW(), NULL),
(2, 'GESTIONAR_USUARIOS', 'Permite editar/banear usuarios', 'ADMIN', NOW(), NULL),
(3, 'PREMIUM', 'Acceso a plan premium', 'USUARIO', NOW(), NULL);


-- Roles (codigo fijo: 1=usuario, 2=admin, 3=admin-lider)
INSERT INTO roles (codigo, nombre, descripcion, created_at) VALUES
(1, 'usuario', 'Usuario estándar', NOW()),
(2, 'admin', 'Administrador', NOW()),
(3, 'admin-lider', 'Líder administrador', NOW());


-- Menús Usuario
INSERT INTO menus (nombre, ruta, icono, orden, created_at, por_defecto) VALUES
('Inicio', 'inicio', 'lucideHome', 1, NOW(), TRUE),
('Explorar', 'explorar', 'lucideSearch', 2, NOW(), FALSE),
('Mis Artículos', 'mis-articulos', 'lucidePackage', 3, NOW(), FALSE),
('Estadísticas', 'estadisticas', 'lucideBarChart3', 4, NOW(), FALSE),
('Comercios', 'comercios', 'lucideStore', 5, NOW(), FALSE),
('ComuniBot', 'comunibot', 'lucideSparkles', 6, NOW(), FALSE);

-- Menús Admin
INSERT INTO menus (nombre, ruta, icono, orden, created_at, por_defecto) VALUES
('Inicio', 'admin-inicio', 'lucideHome', 1, NOW(), TRUE),
('Artículos', 'articulos', 'lucidePackage', 2, NOW(), FALSE),
('Usuarios', 'usuarios', 'lucideUsers', 3, NOW(), FALSE),
('Gestión Premium', 'gestion-premium', 'lucideCrown', 4, NOW(), FALSE);

-- Asignación de menús por rol
-- USUARIO (rol_id=1) obtiene menús principales 1-8
INSERT INTO rol_menus (rol_id, menu_id)
SELECT 1, id FROM menus WHERE ruta IN ('inicio', 'explorar', 'mis-articulos', 'estadisticas', 'comercios', 'comunibot');

-- ADMIN (rol_id=2) obtiene menús admin
INSERT INTO rol_menus (rol_id, menu_id)
SELECT 2, id FROM menus WHERE ruta IN ('admin-inicio', 'articulos', 'usuarios', 'gestion-premium');


-- -- Asignar rol ADMIN (rol_id=2) a un usuario por su ID
-- UPDATE usuarios SET rol_id = 2 WHERE id = 2;
