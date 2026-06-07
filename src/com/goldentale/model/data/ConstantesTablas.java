package com.goldentale.model.data;

/**
 * Nombres de tablas y columnas de la base de datos de Golden Tale.
 * <p>
 * Centraliza todos los literales SQL para evitar cadenas duplicadas en los DAOs
 * y facilitar el mantenimiento ante cambios en el esquema.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class ConstantesTablas {

	// ── Tabla USUARIO ─────────────────────────────────────────────────
	/** Nombre de la tabla de usuarios. */
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

	// ── Tabla PERFUME ─────────────────────────────────────────────────
	/** Nombre de la tabla de perfumes. */
	public static final String TABLA_PERFUME = "perfume";
	public static final String COL_PERFUME_ID = "id_perfume";
	public static final String COL_PERFUME_NOMBRE = "nombre_perfume";
	public static final String COL_PERFUME_MARCA = "marca";
	public static final String COL_PERFUME_CATEGORIA = "categoria";
	public static final String COL_PERFUME_DESCRIPCION = "descripcion";
	public static final String COL_PERFUME_PRECIO = "precio";
	public static final String COL_PERFUME_ML = "ml";
	public static final String COL_PERFUME_PUBLICO = "publico_objetivo";

	// ── Tabla STOCK ───────────────────────────────────────────────────
	/** Nombre de la tabla de stock de almacén. */
	public static final String TABLA_STOCK = "stock";
	public static final String COL_STOCK_ID = "id_stock";
	public static final String COL_STOCK_ID_PERFUME = "id_perfume";
	public static final String COL_STOCK_CANTIDAD = "cantidad";
	public static final String COL_STOCK_LOCALIZACION = "localizacion";

	// ── Tabla PEDIDO ──────────────────────────────────────────────────
	/** Nombre de la tabla de pedidos. */
	public static final String TABLA_PEDIDO = "pedido";
	public static final String COL_PEDIDO_ID = "id_pedido";
	public static final String COL_PEDIDO_ID_USUARIO = "id_usuario";
	public static final String COL_PEDIDO_FECHA = "fecha";
	public static final String COL_PEDIDO_ESTADO = "estado";
	public static final String COL_PEDIDO_DIRECCION_ENTREGA = "direccion_entrega";

	// ── Tabla PAGO ────────────────────────────────────────────────────
	/** Nombre de la tabla de pagos. */
	public static final String TABLA_PAGO = "pago";
	public static final String COL_PAGO_ID = "id_pago";
	public static final String COL_PAGO_ID_PEDIDO = "id_pedido";
	public static final String COL_PAGO_ID_USUARIO = "id_usuario";
	public static final String COL_PAGO_TOTAL = "total";
	public static final String COL_PAGO_FORMA_PAGO = "forma_pago";

	// ── Tabla LINEA_PEDIDO ────────────────────────────────────────────
	/** Nombre de la tabla de líneas de pedido. */
	public static final String TABLA_LINEA_PEDIDO = "linea_pedido";
	public static final String COL_LINEA_ID_PEDIDO = "id_pedido";
	public static final String COL_LINEA_ID_PERFUME = "id_perfume";
	public static final String COL_LINEA_CANTIDAD = "cantidad";
	public static final String COL_LINEA_PRECIO_UNITARIO = "precio_unitario";
	public static final String COL_LINEA_SUBTOTAL = "subtotal";
}