-- Script para corregir las direcciones de los usuarios según ciudades y departamentos existentes
-- Ejecutar después de tener usuarios creados

-- Usuario 1: Medellín, Antioquia
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Medellín' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
    direccion = 'Calle 10 #45-67, El Poblado'
WHERE id = 1;

-- Usuario 2: Bogotá, Bogotá D.C.
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Bogotá' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Bogotá D.C.' LIMIT 1),
    direccion = 'Carrera 7 #32-16, Chapinero'
WHERE id = 2;

-- Usuario 3: Cali, Valle del Cauca
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Cali' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca' LIMIT 1),
    direccion = 'Avenida 6N #23-45, Granada'
WHERE id = 3;

-- Usuario 4: Barranquilla, Atlántico
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Barranquilla' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Atlántico' LIMIT 1),
    direccion = 'Calle 72 #54-32, El Prado'
WHERE id = 4;

-- Usuario 5: Bucaramanga, Santander
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Bucaramanga' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Santander' LIMIT 1),
    direccion = 'Carrera 27 #42-18, Cabecera'
WHERE id = 5;

-- Usuario 6: Cartagena, Bolívar
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Cartagena' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Bolívar' LIMIT 1),
    direccion = 'Calle del Arsenal #8B-56, Centro Histórico'
WHERE id = 6;

-- Usuario 7: Envigado, Antioquia
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Envigado' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Antioquia' LIMIT 1),
    direccion = 'Carrera 43A #30 Sur-18, Zona Centro'
WHERE id = 7;

-- Usuario 8: Pereira, Risaralda
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Pereira' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Risaralda' LIMIT 1),
    direccion = 'Calle 14 #9-35, Centro'
WHERE id = 8;

-- Usuario 9: Manizales, Caldas
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Manizales' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Caldas' LIMIT 1),
    direccion = 'Carrera 23 #62-03, Versalles'
WHERE id = 9;

-- Usuario 10: Armenia, Quindío
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Armenia' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Quindío' LIMIT 1),
    direccion = 'Avenida Bolívar #15-25, Centro'
WHERE id = 10;

-- Usuario 11: Neiva, Huila
UPDATE usuarios SET 
    ciudad_id = (SELECT id FROM ciudades WHERE nombre = 'Neiva' LIMIT 1),
    departamento_id = (SELECT id FROM departamentos WHERE nombre = 'Huila' LIMIT 1),
    direccion = 'Calle 7 #5-67, Centro'
WHERE id = 11;

SELECT 'Direcciones de 11 usuarios actualizadas correctamente' AS resultado;
