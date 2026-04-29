DROP DATABASE IF EXISTS Perfume;
CREATE DATABASE Perfume CHARACTER SET utf8mb4;
USE Perfume;

CREATE TABLE cliente (
  id_cliente VARCHAR(20) NOT NULL,
  nombre_completo VARCHAR(80) NOT NULL,
  telefono VARCHAR(20) NOT NULL, 
  email VARCHAR(80) NOT NULL,
  PRIMARY KEY (id_cliente)
);

CREATE TABLE empleado (
  id_empleado VARCHAR(20) NOT NULL,
  passwd VARCHAR(50) NOT NULL, 
  nombre_completo VARCHAR(80) NOT NULL,
  telefono VARCHAR(20) NOT NULL, 
  email VARCHAR(80) NOT NULL,
  cargo VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_empleado)
);

CREATE TABLE perfume (
  id_perfume VARCHAR(20) NOT NULL,
  nombre_perfume VARCHAR(50) NOT NULL,
  precio DECIMAL (10, 2) NOT NULL, 
  publico_objetivo VARCHAR(15) NOT NULL,
  PRIMARY KEY (id_perfume)
);

CREATE TABLE pedido (
  id_pedido INTEGER NOT NULL,
  id_cliente VARCHAR(20) NOT NULL,
  id_empleado VARCHAR(20) NOT NULL,
  cantidad_unidades INTEGER NOT NULL,
  total_precio DECIMAL (10, 2) NOT NULL, 
  estado VARCHAR (25) NOT NULL, 
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente),
  FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)
  
);

CREATE TABLE stock (
id_stock VARCHAR(20) NOT NULL, 
id_perfume VARCHAR(20) NOT NULL,
localizacion_almacen VARCHAR (20) NOT NULL,
id_pedido INTEGER NOT NULL, 
PRIMARY KEY (id_stock),
FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume),
FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido)
); 


INSERT INTO cliente VALUES
('C001', 'Ana García López',        612345678, 'ana.garcia@email.com'),
('C002', 'Carlos Martínez Ruiz',    623456789, 'carlos.martinez@email.com'),
('C003', 'Laura Sánchez Pérez',     634567890, 'laura.sanchez@email.com'),
('C004', 'Miguel Torres Gómez',     645678901, 'miguel.torres@email.com'),
('C005', 'Sofía Ramírez Díaz',      656789012, 'sofia.ramirez@email.com'),
('C006', 'Javier Fernández Alba',   667890123, 'javier.fernandez@email.com'),
('C007', 'Elena Moreno Castro',     678901234, 'elena.moreno@email.com'),
('C008', 'Pablo Jiménez Vega',      689012345, 'pablo.jimenez@email.com'),
('C009', 'Isabel Romero Ortiz',     690123456, 'isabel.romero@email.com'),
('C010', 'Andrés Navarro Gil',      611234567, 'andres.navarro@email.com'),
('C011', 'Carmen Delgado Ramos',    622345678, 'carmen.delgado@email.com'),
('C012', 'Luis Herrera Molina',     633456789, 'luis.herrera@email.com'),
('C013', 'Marta Vargas Santos',     644567890, 'marta.vargas@email.com'),
('C014', 'Diego Reyes Fuentes',     655678901, 'diego.reyes@email.com'),
('C015', 'Patricia Cruz Blanco',    666789012, 'patricia.cruz@email.com'),
('C016', 'Raúl Mendoza Serrano',    677890123, 'raul.mendoza@email.com'),
('C017', 'Beatriz Aguilar Ponce',   688901234, 'beatriz.aguilar@email.com'),
('C018', 'Tomás Guerrero Lara',     699012345, 'tomas.guerrero@email.com'),
('C019', 'Natalia Ríos Medina',     610123456, 'natalia.rios@email.com'),
('C020', 'Sergio Castillo Bravo',   621234567, 'sergio.castillo@email.com');

INSERT INTO empleado VALUES
('E001', '74829301', 'Roberto Iglesias Mora',  691111111, 'roberto.iglesias@perfume.com', 'Gerente'),
('E002', '52917463', 'Lucía Peña Campos',       692222222, 'lucia.pena@perfume.com',       'Vendedor'),
('E003', '38104927', 'Marcos Rubio Esteve',     693333333, 'marcos.rubio@perfume.com',     'Vendedor'),
('E004', '91827364', 'Verónica Soto Nadal',     694444444, 'veronica.soto@perfume.com',    'Almacenista'),
('E005', '60392817', 'Fernando Cano Espejo',    695555555, 'fernando.cano@perfume.com',    'Repartidor');


INSERT INTO perfume VALUES
('P001', 'Velvet Rose',       89.99, 'Mujer'),
('P002', 'Black Oud',        120.00, 'Hombre'),
('P003', 'Citrus Dream',      65.50, 'Unisex'),
('P004', 'Midnight Jasmine',  99.95, 'Mujer'),
('P005', 'Aqua Force',        75.00, 'Hombre'),
('P006', 'Golden Amber',     145.00, 'Unisex');


INSERT INTO pedido VALUES
(1,  'C001', 'E002', 2,  179.98, 'Entregado'),
(2,  'C002', 'E003', 1,  120.00, 'Entregado'),
(3,  'C003', 'E002', 3,  196.50, 'Entregado'),
(4,  'C004', 'E001', 1,   99.95, 'Entregado'),
(5,  'C005', 'E003', 2,  150.00, 'Entregado'),
(6,  'C006', 'E002', 1,  145.00, 'Entregado'),
(7,  'C007', 'E003', 4,  359.96, 'Entregado'),
(8,  'C008', 'E001', 2,  240.00, 'Enviado'),
(9,  'C009', 'E002', 1,   89.99, 'Enviado'),
(10, 'C010', 'E003', 3,  435.00, 'Enviado'),
(11, 'C011', 'E002', 2,  199.90, 'Enviado'),
(12, 'C012', 'E001', 1,   65.50, 'Procesando'),
(13, 'C013', 'E003', 2,  290.00, 'Procesando'),
(14, 'C014', 'E002', 1,  145.00, 'Procesando'),
(15, 'C015', 'E001', 3,  269.97, 'Procesando'),
(16, 'C016', 'E003', 2,  179.98, 'Pendiente'),
(17, 'C017', 'E002', 1,  120.00, 'Pendiente'),
(18, 'C018', 'E001', 4,  262.00, 'Pendiente'),
(19, 'C019', 'E003', 2,  298.50, 'Pendiente'),
(20, 'C020', 'E002', 1,   75.00, 'Pendiente'),
(21, 'C001', 'E001', 3,  435.00, 'Cancelado'),
(22, 'C005', 'E003', 2,  131.00, 'Cancelado'),
(23, 'C010', 'E002', 1,   89.99, 'Cancelado');

INSERT INTO stock VALUES
('S001',  'P001', 'Estante A1',    1),
('S002',  'P002', 'Estante A2',    2),
('S003',  'P003', 'Estante B1',    3),
('S004',  'P004', 'Estante A3',    4),
('S005',  'P005', 'Almacen Norte', 5),
('S006',  'P006', 'Almacen Sur',   6),
('S007',  'P001', 'Estante A1',    7),
('S008',  'P002', 'Estante A2',    8),
('S009',  'P001', 'Estante B2',    9),
('S010',  'P005', 'Almacen Norte', 10),
('S011',  'P004', 'Estante A3',    11),
('S012',  'P003', 'Estante B1',    12),
('S013',  'P002', 'Almacen Este',  13),
('S014',  'P006', 'Almacen Sur',   14),
('S015',  'P001', 'Estante A1',    15),
('S016',  'P001', 'Estante B3',    16),
('S017',  'P002', 'Estante A2',    17),
('S018',  'P005', 'Almacen Norte', 18),
('S019',  'P003', 'Estante B1',    19),
('S020',  'P005', 'Almacen Este',  20),
('S021',  'P005', 'Almacen Norte', 21),
('S022',  'P003', 'Estante B2',    22),
('S023',  'P001', 'Estante A1',    23);
