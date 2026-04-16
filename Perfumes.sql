DROP DATABASE IF EXISTS Perfume;
CREATE DATABASE Perfume CHARACTER SET utf8mb4;
USE Perfume;

CREATE TABLE cliente (
  id_cliente INTEGER NOT NULL,
  nombre_completo VARCHAR(80) NOT NULL,
  telefono INTEGER NOT NULL, 
  email VARCHAR(80) NOT NULL,
  PRIMARY KEY (id_cliente)
);

CREATE TABLE empleado (
  id_empleado INTEGER NOT NULL,
  passwd VARCHAR(50) NOT NULL, 
  nombre_completo VARCHAR(80) NOT NULL,
  telefono INTEGER NOT NULL, 
  email VARCHAR(80) NOT NULL,
  cargo VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_empleado)
);

CREATE TABLE perfume (
  id_perfume INTEGER NOT NULL,
  nombre_perfume VARCHAR(50) NOT NULL,
  precio DECIMAL (10, 2) NOT NULL, 
  publico_objetivo VARCHAR(15) NOT NULL,
  PRIMARY KEY (id_perfume)
);

CREATE TABLE pedido (
  id_pedido INTEGER NOT NULL,
  id_cliente INTEGER NOT NULL,
  id_empleado INTEGER NOT NULL,
  cantidad_unidades INTEGER NOT NULL,
  total_precio DECIMAL (10, 2) NOT NULL, 
  estado VARCHAR (25) NOT NULL, 
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente),
  FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)
  
);

CREATE TABLE stock (
id_stock INTEGER NOT NULL, 
id_perfume INTEGER NOT NULL,
localizacion_almacen VARCHAR (20) NOT NULL,
id_pedido INTEGER NOT NULL, 
PRIMARY KEY (id_stock),
FOREIGN KEY (id_perfume) REFERENCES perfume (id_perfume),
FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido)
); 


/*
-- Datos
INSERT INTO oficina VALUES ('BCN-ES','Barcelona','España','Barcelona','08019','+34 93 3561182','Avenida Diagonal, 38','3A escalera Derecha');
INSERT INTO oficina VALUES ('BOS-USA','Boston','EEUU','MA','02108','+1 215 837 0825','1550 Court Place','Suite 102');

*/