package com.goldentale.model.data;

public class ConstantesTablas {
	// CONSTANTES DE LAS TABLAS (y sus columnas)

	// Tabla USUARIO
	public static final String TABLA_USUARIO = "usuario";
	public static final String COL_USUARIO_ID = "id_usuario";
	public static final String COL_USUARIO_PASSWD = "passwd";
	public static final String COL_USUARIO_NOMBRE = "nombre";
	public static final String COL_USUARIO_APELLIDO = "apellido";
	public static final String COL_USUARIO_DIRECCION = "direccion";
	public static final String COL_USUARIO_TELEFONO = "telefono";
	public static final String COL_USUARIO_EMAIL = "email";
	public static final String COL_USUARIO_ROL = "rol";
	public static final String COL_USUARIO_CARGO = "cargo";

	// Tabla PERFUME
	public static final String TABLA_PERFUME = "perfume";
	public static final String COL_PERFUME_ID = "id_perfume";
	public static final String COL_PERFUME_NOMBRE = "nombre_perfume";
	public static final String COL_PERFUME_MARCA = "marca";
	public static final String COL_PERFUME_CATEGORIA = "categoria";
	public static final String COL_PERFUME_DESCRIPCION = "descripcion";
	public static final String COL_PERFUME_PRECIO = "precio";
	public static final String COL_PERFUME_ML = "ml";
	public static final String COL_PERFUME_PUBLICO = "publico_objetivo";

	// Tabla STOCK
	public static final String TABLA_STOCK = "stock";
	public static final String COL_STOCK_ID = "id_stock";
	public static final String COL_STOCK_ID_PERFUME = "id_perfume";
	public static final String COL_STOCK_CANTIDAD = "cantidad";
	public static final String COL_STOCK_LOCALIZACION = "localizacion";

	// Tabla PEDIDO
	public static final String TABLA_PEDIDO = "pedido";
	public static final String COL_PEDIDO_ID = "id_pedido";
	public static final String COL_PEDIDO_ID_USUARIO = "id_usuario";
	public static final String COL_PEDIDO_FECHA = "fecha";
	public static final String COL_PEDIDO_ESTADO = "estado";
	public static final String COL_PEDIDO_DIRECCION_ENTREGA = "direccion_entrega";

	// Tabla PAGO
	public static final String TABLA_PAGO = "pago";
	public static final String COL_PAGO_ID = "id_pago";
	public static final String COL_PAGO_ID_PEDIDO = "id_pedido";
	public static final String COL_PAGO_ID_USUARIO = "id_usuario";
	public static final String COL_PAGO_TOTAL = "total";
	public static final String COL_PAGO_FORMA_PAGO = "forma_pago";

	// Tabla LINEA_PEDIDO
	public static final String TABLA_LINEA_PEDIDO = "linea_pedido";
	public static final String COL_LINEA_ID_PEDIDO = "id_pedido";
	public static final String COL_LINEA_ID_PERFUME = "id_perfume";
	public static final String COL_LINEA_CANTIDAD = "cantidad";
	public static final String COL_LINEA_PRECIO_UNITARIO = "precio_unitario";
	public static final String COL_LINEA_SUBTOTAL = "subtotal";
}
