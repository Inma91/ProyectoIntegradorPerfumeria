-- ============================================================
--  GOLDEN TALE — Base de datos v4
--  Cambios respecto a v3:
--    · pedido: eliminado id_empleado (el empleado no gestiona pedidos)
--    · pago: eliminado id_empleado
--    · linea_pedido: subtotal deja de ser columna GENERATED,
--                    ahora se calcula en Java y se inserta como valor
--    · eliminada la vista v_total_pedido (el total se calcula en Java)
-- ============================================================

DROP DATABASE IF EXISTS Perfume;
CREATE DATABASE Perfume CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Perfume;

-- ------------------------------------------------------------
-- 1. CLIENTE
-- ------------------------------------------------------------
CREATE TABLE cliente (
  id_cliente  VARCHAR(20)  NOT NULL,
  nombre      VARCHAR(50)  NOT NULL,
  apellido    VARCHAR(50)  NOT NULL,
  direccion   VARCHAR(120),
  telefono    VARCHAR(20)  NOT NULL,
  email       VARCHAR(80)  NOT NULL,
  PRIMARY KEY (id_cliente)
);

-- ------------------------------------------------------------
-- 2. EMPLEADO
--    Solo puede añadir y modificar perfumes.
--    No interviene en pedidos ni pagos.
-- ------------------------------------------------------------
CREATE TABLE empleado (
  id_empleado VARCHAR(20)  NOT NULL,
  passwd      VARCHAR(255) NOT NULL,
  nombre      VARCHAR(50)  NOT NULL,
  apellido    VARCHAR(50)  NOT NULL,
  direccion   VARCHAR(120),
  telefono    VARCHAR(20)  NOT NULL,
  email       VARCHAR(80)  NOT NULL,
  cargo       VARCHAR(50)  NOT NULL,
  PRIMARY KEY (id_empleado)
);

-- ------------------------------------------------------------
-- 3. PERFUME
-- ------------------------------------------------------------
CREATE TABLE perfume (
  id_perfume       VARCHAR(20)   NOT NULL,
  nombre_perfume   VARCHAR(50)   NOT NULL,
  marca            VARCHAR(50)   NOT NULL,
  categoria        VARCHAR(40)   NOT NULL,
  descripcion      TEXT,
  precio           DECIMAL(10,2) NOT NULL,
  ml               INTEGER       NOT NULL,
  publico_objetivo VARCHAR(15)   NOT NULL,
  imagen_url       VARCHAR(255),
  PRIMARY KEY (id_perfume)
);

-- ------------------------------------------------------------
-- 4. STOCK
--    localizacion asignada automáticamente por Java según ml:
--      ml <= 50  → Estante A
--      ml = 75   → Estante B
--      ml >= 100 → Estante C
-- ------------------------------------------------------------
CREATE TABLE stock (
  id_stock     VARCHAR(20) NOT NULL,
  id_perfume   VARCHAR(20) NOT NULL,
  cantidad     INTEGER     NOT NULL DEFAULT 0,
  localizacion VARCHAR(40) NOT NULL,
  PRIMARY KEY (id_stock),
  FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume)
);

-- ------------------------------------------------------------
-- 5. PEDIDO
--    El empleado ya NO interviene.
--    El cliente realiza el pedido y gestiona su estado.
-- ------------------------------------------------------------
CREATE TABLE pedido (
  id_pedido  INTEGER      NOT NULL AUTO_INCREMENT,
  id_cliente VARCHAR(20)  NOT NULL,
  fecha      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado     VARCHAR(25)  NOT NULL DEFAULT 'Pendiente',
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
);

-- ------------------------------------------------------------
-- 6. PAGO
--    El empleado ya NO interviene.
-- ------------------------------------------------------------
CREATE TABLE pago (
  id_pago    VARCHAR(20)   NOT NULL,
  id_pedido  INTEGER       NOT NULL,
  id_cliente VARCHAR(20)   NOT NULL,
  total      DECIMAL(10,2) NOT NULL,
  forma_pago VARCHAR(20)   NOT NULL,  -- 'Efectivo', 'Tarjeta', 'Bizum'
  PRIMARY KEY (id_pago),
  FOREIGN KEY (id_pedido)  REFERENCES pedido  (id_pedido),
  FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
);

-- ------------------------------------------------------------
-- 7. LINEA_PEDIDO  (N:M entre pedido y perfume)
--    subtotal se calcula en Java: cantidad × precio_unitario
--    y se inserta como valor normal, sin columna GENERATED.
-- ------------------------------------------------------------
CREATE TABLE linea_pedido (
  id_pedido       INTEGER       NOT NULL,
  id_perfume      VARCHAR(20)   NOT NULL,
  cantidad        INTEGER       NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal        DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_pedido, id_perfume),
  FOREIGN KEY (id_pedido)  REFERENCES pedido  (id_pedido),
  FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume)
);

-- ============================================================
--  DATOS DE PRUEBA
-- ============================================================

INSERT INTO cliente VALUES
('C001', 'Ana',      'García López',    'Calle Mayor 12, Madrid',          '612345678', 'ana.garcia@email.com'),
('C002', 'Carlos',   'Martínez Ruiz',   'Av. Libertad 5, Barcelona',       '623456789', 'carlos.martinez@email.com'),
('C003', 'Laura',    'Sánchez Pérez',   'C/ Rosales 8, Sevilla',           '634567890', 'laura.sanchez@email.com'),
('C004', 'Miguel',   'Torres Gómez',    'Paseo del Prado 3, Madrid',       '645678901', 'miguel.torres@email.com'),
('C005', 'Sofía',    'Ramírez Díaz',    'C/ Gran Vía 22, Valencia',        '656789012', 'sofia.ramirez@email.com'),
('C006', 'Javier',   'Fernández Alba',  'Av. Constitución 1, Málaga',      '667890123', 'javier.fernandez@email.com'),
('C007', 'Elena',    'Moreno Castro',   'C/ Sierpes 14, Sevilla',          '678901234', 'elena.moreno@email.com'),
('C008', 'Pablo',    'Jiménez Vega',    'Rambla Catalunya 9, Barcelona',   '689012345', 'pablo.jimenez@email.com'),
('C009', 'Isabel',   'Romero Ortiz',    'C/ Alcalá 45, Madrid',            '690123456', 'isabel.romero@email.com'),
('C010', 'Andrés',   'Navarro Gil',     'Av. Diagonal 100, Barcelona',     '611234567', 'andres.navarro@email.com'),
('C011', 'Carmen',   'Delgado Ramos',   'C/ Colón 7, Valencia',            '622345678', 'carmen.delgado@email.com'),
('C012', 'Luis',     'Herrera Molina',  'Paseo Marítimo 3, Málaga',        '633456789', 'luis.herrera@email.com'),
('C013', 'Marta',    'Vargas Santos',   'C/ Triana 20, Sevilla',           '644567890', 'marta.vargas@email.com'),
('C014', 'Diego',    'Reyes Fuentes',   'Gran Vía 15, Madrid',             '655678901', 'diego.reyes@email.com'),
('C015', 'Patricia', 'Cruz Blanco',     'C/ Sagrada Familia 2, Barcelona', '666789012', 'patricia.cruz@email.com'),
('C016', 'Raúl',     'Mendoza Serrano', 'Av. de la Paz 11, Zaragoza',      '677890123', 'raul.mendoza@email.com'),
('C017', 'Beatriz',  'Aguilar Ponce',   'C/ Betis 6, Sevilla',             '688901234', 'beatriz.aguilar@email.com'),
('C018', 'Tomás',    'Guerrero Lara',   'C/ Larios 4, Málaga',             '699012345', 'tomas.guerrero@email.com'),
('C019', 'Natalia',  'Ríos Medina',     'Av. Blasco Ibáñez 30, Valencia',  '610123456', 'natalia.rios@email.com'),
('C020', 'Sergio',   'Castillo Bravo',  'C/ Fuencarral 55, Madrid',        '621234567', 'sergio.castillo@email.com');

INSERT INTO empleado VALUES
('E001', 'hash_74829301', 'Roberto',  'Iglesias Mora', 'C/ Velázquez 10, Madrid', '691111111', 'roberto.iglesias@goldentale.com', 'Gerente'),
('E002', 'hash_52917463', 'Lucía',    'Peña Campos',   'C/ Serrano 5, Madrid',    '692222222', 'lucia.pena@goldentale.com',       'Vendedor'),
('E003', 'hash_38104927', 'Marcos',   'Rubio Esteve',  'C/ Goya 18, Madrid',      '693333333', 'marcos.rubio@goldentale.com',     'Vendedor'),
('E004', 'hash_91827364', 'Verónica', 'Soto Nadal',    'C/ Princesa 22, Madrid',  '694444444', 'veronica.soto@goldentale.com',    'Almacenista'),
('E005', 'hash_60392817', 'Fernando', 'Cano Espejo',   'Av. América 3, Madrid',   '695555555', 'fernando.cano@goldentale.com',    'Repartidor');

INSERT INTO perfume VALUES
('P001', 'Velvet Rose',      'Maison Luxe',  'Floral',    'Floral y amaderado con notas de rosa turca y sándalo.',         89.99,  50,  'Mujer',  NULL),
('P002', 'Black Oud',        'Orient Noir',  'Oriental',  'Intenso y especiado con corazón de oud y pachulí.',            120.00, 100,  'Hombre', NULL),
('P003', 'Citrus Dream',     'Fresca & Co',  'Cítrico',   'Fresco y cítrico con notas de bergamota, lima y cedro.',        65.50,  75,  'Unisex', NULL),
('P004', 'Midnight Jasmine', 'Maison Luxe',  'Floral',    'Oriental floral con jazmín, vainilla y almizcle blanco.',       99.95,  50,  'Mujer',  NULL),
('P005', 'Aqua Force',       'BlueSea',      'Acuático',  'Marino y fresco con notas de agua de mar y madera de deriva.',  75.00, 100,  'Hombre', NULL),
('P006', 'Golden Amber',     'Orient Noir',  'Amaderado', 'Cálido y balsámico con ámbar, resina y vainilla.',            145.00,  75,  'Unisex', NULL);

-- Localizacion según ml: <=50 → Estante A | 75 → Estante B | >=100 → Estante C
INSERT INTO stock VALUES
('S001', 'P001', 12, 'Estante A'),
('S002', 'P002',  8, 'Estante C'),
('S003', 'P003', 15, 'Estante B'),
('S004', 'P004',  6, 'Estante A'),
('S005', 'P005', 10, 'Estante C'),
('S006', 'P006',  4, 'Estante B');

-- Pedidos sin id_empleado
INSERT INTO pedido (id_pedido, id_cliente, fecha, estado) VALUES
(1,  'C001', '2025-01-10 10:30:00', 'Entregado'),
(2,  'C002', '2025-01-12 11:00:00', 'Entregado'),
(3,  'C003', '2025-01-15 09:45:00', 'Entregado'),
(4,  'C004', '2025-02-01 16:20:00', 'Entregado'),
(5,  'C005', '2025-02-03 14:10:00', 'Enviado'),
(6,  'C006', '2025-02-05 12:00:00', 'Enviado'),
(7,  'C007', '2025-02-10 10:00:00', 'Procesando'),
(8,  'C008', '2025-02-12 17:30:00', 'Pendiente'),
(9,  'C009', '2025-02-14 09:00:00', 'Pendiente'),
(10, 'C010', '2025-02-15 13:45:00', 'Cancelado');

-- Pagos sin id_empleado, subtotal calculado en Java
INSERT INTO pago VALUES
('PAG001', 1, 'C001', 179.98, 'Tarjeta'),
('PAG002', 2, 'C002', 120.00, 'Efectivo'),
('PAG003', 3, 'C003', 221.49, 'Bizum'),
('PAG004', 4, 'C004',  99.95, 'Tarjeta');
-- Pedidos 5-9 pendientes de pago | Pedido 10 cancelado (sin pago)

-- subtotal calculado en Java: cantidad × precio_unitario
INSERT INTO linea_pedido VALUES
-- Pedido 1: 2× Velvet Rose
(1, 'P001', 2,  89.99, 179.98),
-- Pedido 2: 1× Black Oud
(2, 'P002', 1, 120.00, 120.00),
-- Pedido 3: 2× Citrus Dream + 1× Velvet Rose
(3, 'P003', 2,  65.50, 131.00),
(3, 'P001', 1,  89.99,  89.99),
-- Pedido 4: 1× Midnight Jasmine
(4, 'P004', 1,  99.95,  99.95),
-- Pedido 5: 2× Aqua Force
(5, 'P005', 2,  75.00, 150.00),
-- Pedido 6: 1× Golden Amber
(6, 'P006', 1, 145.00, 145.00),
-- Pedido 7: 1× Velvet Rose + 1× Black Oud
(7, 'P001', 1,  89.99,  89.99),
(7, 'P002', 1, 120.00, 120.00),
-- Pedido 8: 2× Black Oud
(8, 'P002', 2, 120.00, 240.00),
-- Pedido 9: 1× Velvet Rose
(9, 'P001', 1,  89.99,  89.99),
-- Pedido 10 (cancelado): 1× Golden Amber
(10,'P006', 1, 145.00, 145.00);
