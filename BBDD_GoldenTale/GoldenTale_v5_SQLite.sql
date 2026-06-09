-- ============================================================
--  GOLDEN TALE — Base de datos v5
--  Adaptado a SQLite
-- ============================================================

PRAGMA foreign_keys = ON;

-- ------------------------------------------------------------
-- 1. USUARIO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
  id_usuario  INTEGER      PRIMARY KEY AUTOINCREMENT,
  passwd      TEXT         NOT NULL,
  nombre      TEXT         NOT NULL,
  apellido    TEXT         NOT NULL,
  direccion   TEXT,
  telefono    TEXT         NOT NULL,
  email       TEXT         NOT NULL,
  rol         TEXT         NOT NULL,
  cargo       TEXT
);

-- ------------------------------------------------------------
-- 2. PERFUME
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS perfume (
  id_perfume       INTEGER  PRIMARY KEY AUTOINCREMENT,
  nombre_perfume   TEXT     NOT NULL,
  marca            TEXT     NOT NULL,
  categoria        TEXT     NOT NULL,
  descripcion      TEXT,
  precio           REAL     NOT NULL,
  ml               INTEGER  NOT NULL,
  publico_objetivo TEXT     NOT NULL
);

-- ------------------------------------------------------------
-- 3. STOCK
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS stock (
  id_stock     INTEGER  PRIMARY KEY AUTOINCREMENT,
  id_perfume   INTEGER  NOT NULL,
  cantidad     INTEGER  NOT NULL DEFAULT 0,
  localizacion TEXT     NOT NULL,
  FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume)
);

-- ------------------------------------------------------------
-- 4. PEDIDO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pedido (
  id_pedido  INTEGER  PRIMARY KEY AUTOINCREMENT,
  id_usuario INTEGER  NOT NULL,
  fecha      TEXT     NOT NULL DEFAULT (datetime('now')),
  estado     TEXT     NOT NULL DEFAULT 'Pendiente',
  FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

-- ------------------------------------------------------------
-- 5. PAGO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pago (
  id_pago    INTEGER  PRIMARY KEY AUTOINCREMENT,
  id_pedido  INTEGER  NOT NULL,
  id_usuario INTEGER  NOT NULL,
  total      REAL     NOT NULL,
  forma_pago TEXT     NOT NULL,
  FOREIGN KEY (id_pedido)  REFERENCES pedido  (id_pedido),
  FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

-- ------------------------------------------------------------
-- 6. LINEA_PEDIDO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS linea_pedido (
  id_pedido       INTEGER  NOT NULL,
  id_perfume      INTEGER  NOT NULL,
  cantidad        INTEGER  NOT NULL,
  precio_unitario REAL     NOT NULL,
  subtotal        REAL     NOT NULL,
  PRIMARY KEY (id_pedido, id_perfume),
  FOREIGN KEY (id_pedido)  REFERENCES pedido  (id_pedido),
  FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume)
);

-- ============================================================
--  DATOS DE PRUEBA
-- ============================================================

INSERT INTO usuario (passwd, nombre, apellido, direccion, telefono, email, rol, cargo) VALUES
('hash_cli001', 'Ana',      'García López',    'Calle Mayor 12, Madrid',          '612345678', 'ana.garcia@email.com',      'cliente',  NULL),
('hash_cli002', 'Carlos',   'Martínez Ruiz',   'Av. Libertad 5, Barcelona',       '623456789', 'carlos.martinez@email.com', 'cliente',  NULL),
('hash_cli003', 'Laura',    'Sánchez Pérez',   'C/ Rosales 8, Sevilla',           '634567890', 'laura.sanchez@email.com',   'cliente',  NULL),
('hash_cli004', 'Miguel',   'Torres Gómez',    'Paseo del Prado 3, Madrid',       '645678901', 'miguel.torres@email.com',   'cliente',  NULL),
('hash_cli005', 'Sofía',    'Ramírez Díaz',    'C/ Gran Vía 22, Valencia',        '656789012', 'sofia.ramirez@email.com',   'cliente',  NULL),
('hash_cli006', 'Javier',   'Fernández Alba',  'Av. Constitución 1, Málaga',      '667890123', 'javier.fernandez@email.com','cliente',  NULL),
('hash_cli007', 'Elena',    'Moreno Castro',   'C/ Sierpes 14, Sevilla',          '678901234', 'elena.moreno@email.com',    'cliente',  NULL),
('hash_cli008', 'Pablo',    'Jiménez Vega',    'Rambla Catalunya 9, Barcelona',   '689012345', 'pablo.jimenez@email.com',   'cliente',  NULL),
('hash_cli009', 'Isabel',   'Romero Ortiz',    'C/ Alcalá 45, Madrid',            '690123456', 'isabel.romero@email.com',   'cliente',  NULL),
('hash_cli010', 'Andrés',   'Navarro Gil',     'Av. Diagonal 100, Barcelona',     '611234567', 'andres.navarro@email.com',  'cliente',  NULL),
('hash_cli011', 'Carmen',   'Delgado Ramos',   'C/ Colón 7, Valencia',            '622345678', 'carmen.delgado@email.com',  'cliente',  NULL),
('hash_cli012', 'Luis',     'Herrera Molina',  'Paseo Marítimo 3, Málaga',        '633456789', 'luis.herrera@email.com',    'cliente',  NULL),
('hash_cli013', 'Marta',    'Vargas Santos',   'C/ Triana 20, Sevilla',           '644567890', 'marta.vargas@email.com',    'cliente',  NULL),
('hash_cli014', 'Diego',    'Reyes Fuentes',   'Gran Vía 15, Madrid',             '655678901', 'diego.reyes@email.com',     'cliente',  NULL),
('hash_cli015', 'Patricia', 'Cruz Blanco',     'C/ Sagrada Familia 2, Barcelona', '666789012', 'patricia.cruz@email.com',   'cliente',  NULL),
('hash_74829301', 'Roberto',  'Iglesias Mora', 'C/ Velázquez 10, Madrid', '691111111', 'roberto.iglesias@goldentale.com', 'empleado', 'Gerente'),
('hash_52917463', 'Lucía',    'Peña Campos',   'C/ Serrano 5, Madrid',    '692222222', 'lucia.pena@goldentale.com',       'empleado', 'Vendedor'),
('hash_38104927', 'Marcos',   'Rubio Esteve',  'C/ Goya 18, Madrid',      '693333333', 'marcos.rubio@goldentale.com',     'empleado', 'Vendedor'),
('hash_91827364', 'Verónica', 'Soto Nadal',    'C/ Princesa 22, Madrid',  '694444444', 'veronica.soto@goldentale.com',    'empleado', 'Almacenista'),
('hash_60392817', 'Fernando', 'Cano Espejo',   'Av. América 3, Madrid',   '695555555', 'fernando.cano@goldentale.com',    'empleado', 'Repartidor');

INSERT INTO perfume (nombre_perfume, marca, categoria, descripcion, precio, ml, publico_objetivo) VALUES
('Velvet Rose',      'Maison Luxe',  'Floral',    'Floral y amaderado con notas de rosa turca y sándalo.',          89.99,  50,  'Mujer'),
('Black Oud',        'Orient Noir',  'Oriental',  'Intenso y especiado con corazón de oud y pachulí.',             120.00, 100, 'Hombre'),
('Citrus Dream',     'Fresca & Co',  'Cítrico',   'Fresco y cítrico con notas de bergamota, lima y cedro.',         65.50,  75, 'Unisex'),
('Midnight Jasmine', 'Maison Luxe',  'Floral',    'Oriental floral con jazmín, vainilla y almizcle blanco.',        99.95,  50, 'Mujer'),
('Aqua Force',       'BlueSea',      'Acuático',  'Marino y fresco con notas de agua de mar y madera de deriva.',   75.00, 100, 'Hombre'),
('Golden Amber',     'Orient Noir',  'Amaderado', 'Cálido y balsámico con ámbar, resina y vainilla.',             145.00,  75, 'Unisex');

INSERT INTO stock (id_perfume, cantidad, localizacion) VALUES
(1, 12, 'Estante A'),
(2,  8, 'Estante C'),
(3, 15, 'Estante B'),
(4,  6, 'Estante A'),
(5, 10, 'Estante C'),
(6,  4, 'Estante B');

INSERT INTO pedido (id_usuario, fecha, estado) VALUES
(1,  '2025-01-10 10:30:00', 'Entregado'),
(2,  '2025-01-12 11:00:00', 'Entregado'),
(3,  '2025-01-15 09:45:00', 'Entregado'),
(4,  '2025-02-01 16:20:00', 'Entregado'),
(5,  '2025-02-03 14:10:00', 'Enviado'),
(6,  '2025-02-05 12:00:00', 'Enviado'),
(7,  '2025-02-10 10:00:00', 'Procesando'),
(8,  '2025-02-12 17:30:00', 'Pendiente'),
(9,  '2025-02-14 09:00:00', 'Pendiente'),
(10, '2025-02-15 13:45:00', 'Cancelado');

INSERT INTO pago (id_pedido, id_usuario, total, forma_pago) VALUES
(1, 1, 179.98, 'Tarjeta'),
(2, 2, 120.00, 'Efectivo'),
(3, 3, 221.49, 'Bizum'),
(4, 4,  99.95, 'Tarjeta');

INSERT INTO linea_pedido (id_pedido, id_perfume, cantidad, precio_unitario, subtotal) VALUES
(1,  1, 2,  89.99, 179.98),
(2,  2, 1, 120.00, 120.00),
(3,  3, 2,  65.50, 131.00),
(3,  1, 1,  89.99,  89.99),
(4,  4, 1,  99.95,  99.95),
(5,  5, 2,  75.00, 150.00),
(6,  6, 1, 145.00, 145.00),
(7,  1, 1,  89.99,  89.99),
(7,  2, 1, 120.00, 120.00),
(8,  2, 2, 120.00, 240.00),
(9,  1, 1,  89.99,  89.99),
(10, 6, 1, 145.00, 145.00);
