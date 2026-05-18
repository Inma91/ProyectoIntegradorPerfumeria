-- ============================================================
--  GOLDEN TALE — Base de datos v2
--  Cambios respecto a v1:
--    · perfume: añadidos marca, descripcion, imagen_url
--    · stock: desvinculado de pedido; añadida columna cantidad
--             localizacion se asigna automáticamente desde Java
--    · linea_pedido: nueva tabla intermedia pedido ↔ perfume
--    · pedido: eliminados cantidad_unidades y total_precio
--              (se calculan desde linea_pedido)
--    · passwd ampliado a 255 para almacenar hash bcrypt/SHA
-- ============================================================

DROP DATABASE IF EXISTS Perfume;
CREATE DATABASE Perfume CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Perfume;

-- ------------------------------------------------------------
-- 1. CLIENTE
-- ------------------------------------------------------------
CREATE TABLE cliente (
  id_cliente       VARCHAR(20)  NOT NULL,
  nombre_completo  VARCHAR(80)  NOT NULL,
  telefono         VARCHAR(20)  NOT NULL,
  email            VARCHAR(80)  NOT NULL,
  PRIMARY KEY (id_cliente)
);

-- ------------------------------------------------------------
-- 2. EMPLEADO
-- ------------------------------------------------------------
CREATE TABLE empleado (
  id_empleado      VARCHAR(20)  NOT NULL,
  passwd           VARCHAR(255) NOT NULL,   -- guardar hash, nunca texto plano
  nombre_completo  VARCHAR(80)  NOT NULL,
  telefono         VARCHAR(20)  NOT NULL,
  email            VARCHAR(80)  NOT NULL,
  cargo            VARCHAR(50)  NOT NULL,   -- 'Gerente', 'Vendedor', 'Almacenista'…
  PRIMARY KEY (id_empleado)
);

-- ------------------------------------------------------------
-- 3. PERFUME
-- ------------------------------------------------------------
CREATE TABLE perfume (
  id_perfume       VARCHAR(20)   NOT NULL,
  nombre_perfume   VARCHAR(50)   NOT NULL,
  marca            VARCHAR(50)   NOT NULL,
  descripcion      TEXT,
  precio           DECIMAL(10,2) NOT NULL,
  publico_objetivo VARCHAR(15)   NOT NULL,  -- 'Mujer', 'Hombre', 'Unisex'
  imagen_url       VARCHAR(255),            -- ruta o URL de la imagen
  PRIMARY KEY (id_perfume)
);

-- ------------------------------------------------------------
-- 4. STOCK
--    localizacion la asigna Java según publico_objetivo:
--      Mujer  → Estante A  |  Hombre → Estante B  |  Unisex → Almacén Norte
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
--    total_precio se obtiene con SUM(linea_pedido.subtotal)
-- ------------------------------------------------------------
CREATE TABLE pedido (
  id_pedido    INTEGER      NOT NULL AUTO_INCREMENT,
  id_cliente   VARCHAR(20)  NOT NULL,
  id_empleado  VARCHAR(20)  NOT NULL,
  fecha        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado       VARCHAR(25)  NOT NULL DEFAULT 'Pendiente',
                                     -- 'Pendiente','Procesando','Enviado','Entregado','Cancelado'
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_cliente)  REFERENCES cliente  (id_cliente),
  FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)
);

-- ------------------------------------------------------------
-- 6. LINEA_PEDIDO  (relación N:M entre pedido y perfume)
--    subtotal = cantidad × precio_unitario
-- ------------------------------------------------------------
CREATE TABLE linea_pedido (
  id_pedido       INTEGER       NOT NULL,
  id_perfume      VARCHAR(20)   NOT NULL,
  cantidad        INTEGER       NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,   -- precio en el momento de la compra
  subtotal        DECIMAL(10,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
  PRIMARY KEY (id_pedido, id_perfume),
  FOREIGN KEY (id_pedido)  REFERENCES pedido  (id_pedido),
  FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume)
);

-- ============================================================
--  DATOS DE PRUEBA
-- ============================================================

INSERT INTO cliente VALUES
('C001', 'Ana García López',       '612345678', 'ana.garcia@email.com'),
('C002', 'Carlos Martínez Ruiz',   '623456789', 'carlos.martinez@email.com'),
('C003', 'Laura Sánchez Pérez',    '634567890', 'laura.sanchez@email.com'),
('C004', 'Miguel Torres Gómez',    '645678901', 'miguel.torres@email.com'),
('C005', 'Sofía Ramírez Díaz',     '656789012', 'sofia.ramirez@email.com'),
('C006', 'Javier Fernández Alba',  '667890123', 'javier.fernandez@email.com'),
('C007', 'Elena Moreno Castro',    '678901234', 'elena.moreno@email.com'),
('C008', 'Pablo Jiménez Vega',     '689012345', 'pablo.jimenez@email.com'),
('C009', 'Isabel Romero Ortiz',    '690123456', 'isabel.romero@email.com'),
('C010', 'Andrés Navarro Gil',     '611234567', 'andres.navarro@email.com'),
('C011', 'Carmen Delgado Ramos',   '622345678', 'carmen.delgado@email.com'),
('C012', 'Luis Herrera Molina',    '633456789', 'luis.herrera@email.com'),
('C013', 'Marta Vargas Santos',    '644567890', 'marta.vargas@email.com'),
('C014', 'Diego Reyes Fuentes',    '655678901', 'diego.reyes@email.com'),
('C015', 'Patricia Cruz Blanco',   '666789012', 'patricia.cruz@email.com'),
('C016', 'Raúl Mendoza Serrano',   '677890123', 'raul.mendoza@email.com'),
('C017', 'Beatriz Aguilar Ponce',  '688901234', 'beatriz.aguilar@email.com'),
('C018', 'Tomás Guerrero Lara',    '699012345', 'tomas.guerrero@email.com'),
('C019', 'Natalia Ríos Medina',    '610123456', 'natalia.rios@email.com'),
('C020', 'Sergio Castillo Bravo',  '621234567', 'sergio.castillo@email.com');

-- passwd en producción debe ser hash; aquí se deja indicado
INSERT INTO empleado VALUES
('E001', 'hash_de_74829301', 'Roberto Iglesias Mora',  '691111111', 'roberto.iglesias@perfume.com', 'Gerente'),
('E002', 'hash_de_52917463', 'Lucía Peña Campos',      '692222222', 'lucia.pena@perfume.com',       'Vendedor'),
('E003', 'hash_de_38104927', 'Marcos Rubio Esteve',    '693333333', 'marcos.rubio@perfume.com',     'Vendedor'),
('E004', 'hash_de_91827364', 'Verónica Soto Nadal',    '694444444', 'veronica.soto@perfume.com',    'Almacenista'),
('E005', 'hash_de_60392817', 'Fernando Cano Espejo',   '695555555', 'fernando.cano@perfume.com',    'Repartidor');

INSERT INTO perfume VALUES
('P001', 'Velvet Rose',      'Maison Luxe',   'Floral y amaderado con notas de rosa turca y sándalo.',          89.99,  'Mujer',  NULL),
('P002', 'Black Oud',        'Orient Noir',   'Intenso y especiado con corazón de oud y pachulí.',             120.00,  'Hombre', NULL),
('P003', 'Citrus Dream',     'Fresca & Co',   'Fresco y cítrico con notas de bergamota, lima y cedro.',         65.50,  'Unisex', NULL),
('P004', 'Midnight Jasmine', 'Maison Luxe',   'Oriental floral con jazmín, vainilla y almizcle blanco.',        99.95,  'Mujer',  NULL),
('P005', 'Aqua Force',       'BlueSea',       'Marino y fresco con notas de agua de mar y madera de deriva.',   75.00,  'Hombre', NULL),
('P006', 'Golden Amber',     'Orient Noir',   'Cálido y balsámico con ámbar, resina y vainilla.',              145.00,  'Unisex', NULL);

-- localizacion asignada por Java según publico_objetivo
-- Mujer → Estante A | Hombre → Estante B | Unisex → Almacén Norte
INSERT INTO stock VALUES
('S001', 'P001', 12, 'Estante A'),
('S002', 'P002',  8, 'Estante B'),
('S003', 'P003', 15, 'Almacén Norte'),
('S004', 'P004',  6, 'Estante A'),
('S005', 'P005', 10, 'Estante B'),
('S006', 'P006',  4, 'Almacén Norte');

INSERT INTO pedido (id_pedido, id_cliente, id_empleado, fecha, estado) VALUES
(1,  'C001', 'E002', '2025-01-10 10:30:00', 'Entregado'),
(2,  'C002', 'E003', '2025-01-12 11:00:00', 'Entregado'),
(3,  'C003', 'E002', '2025-01-15 09:45:00', 'Entregado'),
(4,  'C004', 'E001', '2025-02-01 16:20:00', 'Entregado'),
(5,  'C005', 'E003', '2025-02-03 14:10:00', 'Enviado'),
(6,  'C006', 'E002', '2025-02-05 12:00:00', 'Enviado'),
(7,  'C007', 'E003', '2025-02-10 10:00:00', 'Procesando'),
(8,  'C008', 'E001', '2025-02-12 17:30:00', 'Pendiente'),
(9,  'C009', 'E002', '2025-02-14 09:00:00', 'Pendiente'),
(10, 'C010', 'E003', '2025-02-15 13:45:00', 'Cancelado');

INSERT INTO linea_pedido (id_pedido, id_perfume, cantidad, precio_unitario) VALUES
-- Pedido 1: 2× Velvet Rose
(1, 'P001', 2, 89.99),
-- Pedido 2: 1× Black Oud
(2, 'P002', 1, 120.00),
-- Pedido 3: 2× Citrus Dream + 1× Velvet Rose
(3, 'P003', 2, 65.50),
(3, 'P001', 1, 89.99),
-- Pedido 4: 1× Midnight Jasmine
(4, 'P004', 1, 99.95),
-- Pedido 5: 2× Aqua Force
(5, 'P005', 2, 75.00),
-- Pedido 6: 1× Golden Amber
(6, 'P006', 1, 145.00),
-- Pedido 7: 1× Velvet Rose + 1× Black Oud
(7, 'P001', 1, 89.99),
(7, 'P002', 1, 120.00),
-- Pedido 8: 2× Black Oud
(8, 'P002', 2, 120.00),
-- Pedido 9: 1× Velvet Rose
(9, 'P001', 1, 89.99),
-- Pedido 10 (cancelado): 1× Golden Amber
(10, 'P006', 1, 145.00);

-- ============================================================
--  VISTA ÚTIL: total por pedido (evita calcular en Java)
-- ============================================================
CREATE VIEW v_total_pedido AS
SELECT
  p.id_pedido,
  p.id_cliente,
  p.id_empleado,
  p.fecha,
  p.estado,
  SUM(lp.subtotal) AS total_precio,
  SUM(lp.cantidad) AS total_unidades
FROM pedido p
JOIN linea_pedido lp ON p.id_pedido = lp.id_pedido
GROUP BY p.id_pedido, p.id_cliente, p.id_empleado, p.fecha, p.estado;
