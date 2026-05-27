package com.goldentale.util;

import com.goldentale.model.Usuario;
import java.awt.Color;
import java.awt.Font;

/**
 * Constantes globales de la aplicación Golden Tale. Usa Swing (java.awt) en
 * lugar de JavaFX.
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Constantes {

	// ── Dimensiones de la ventana ─────────────────────────────────────
	public static final int ANCHURA_APLICACION = 1100;
	public static final int ALTURA_APLICACION = 700;
	public static final int ANCHURA_SIDEBAR = 175;
	public static final int ANCHURA_PANEL_DETALLE = 300;

	// ── Título ────────────────────────────────────────────────────────
	public static final String TITULO_APLICACION = "Golden Tale";

	// ── Roles de usuario ──────────────────────────────────────────────
	/**
	 * Valor del campo 'rol' en la tabla usuario para clientes. Uso:
	 * usuario.getRol().equals(Constantes.ROL_CLIENTE)
	 */
	public static final String ROL_CLIENTE = "cliente";

	/**
	 * Valor del campo 'rol' en la tabla usuario para empleados. Uso:
	 * usuario.getRol().equals(Constantes.ROL_EMPLEADO) o: usuario instanceof
	 * Empleado
	 */
	public static final String ROL_EMPLEADO = "empleado";

	// ── Estados de pedido ─────────────────────────────────────────────
	public static final String ESTADO_PENDIENTE = "Pendiente";
	public static final String ESTADO_PROCESANDO = "Procesando";
	public static final String ESTADO_ENVIADO = "Enviado";
	public static final String ESTADO_ENTREGADO = "Entregado";
	public static final String ESTADO_CANCELADO = "Cancelado";
	public static final String[] ESTADOS_PEDIDO = { ESTADO_PENDIENTE, ESTADO_PROCESANDO, ESTADO_ENVIADO,
			ESTADO_ENTREGADO, ESTADO_CANCELADO };

	// ── Formas de pago ────────────────────────────────────────────────
	public static final String PAGO_EFECTIVO = "Efectivo";
	public static final String PAGO_TARJETA = "Tarjeta";
	public static final String PAGO_BIZUM = "Bizum";
	public static final String[] FORMAS_PAGO = { PAGO_EFECTIVO, PAGO_TARJETA, PAGO_BIZUM };

	// ── Público objetivo ──────────────────────────────────────────────
	public static final String PUBLICO_MUJER = "Mujer";
	public static final String PUBLICO_HOMBRE = "Hombre";
	public static final String PUBLICO_UNISEX = "Unisex";
	public static final String[] PUBLICOS_OBJETIVO = { PUBLICO_MUJER, PUBLICO_HOMBRE, PUBLICO_UNISEX };

	// ── Categorías de perfume ─────────────────────────────────────────
	public static final String[] CATEGORIAS_PERFUME = { "Floral", "Oriental", "Cítrico", "Acuático", "Amaderado" };

	// ── Localización automática en almacén (según ml del frasco) ──────
	/**
	 * Frascos de 50ml o menos van al Estante A.
	 */
	public static final String LOC_PEQUENO = "Estante A";

	/**
	 * Frascos de 75ml van al Estante B.
	 */
	public static final String LOC_MEDIANO = "Estante B";

	/**
	 * Frascos de 100ml o más van al Estante C.
	 */
	public static final String LOC_GRANDE = "Estante C";

	/**
	 * Umbral máximo (inclusive) para Estante A.
	 */
	public static final int ML_LIMITE_PEQUENO = 50;

	/**
	 * Valor exacto para Estante B.
	 */
	public static final int ML_LIMITE_MEDIANO = 75;

	// ── Umbrales de stock ─────────────────────────────────────────────
	/**
	 * Cantidad por debajo de la cual se considera stock bajo. Se usa para colorear
	 * la celda en rojo/amarillo en la tabla.
	 */
	public static final int STOCK_MINIMO_ALERTA = 5;

	/**
	 * Referencia de stock máximo para calcular el porcentaje de la barra de stock
	 * en la pantalla de almacén.
	 */
	public static final int STOCK_MAXIMO_REFERENCIA = 20;

	// ── Columnas de las JTable ────────────────────────────────────────
	public static final String[] COLS_CATALOGO = { "Nombre", "Marca", "Categoría", "Público", "ml", "Precio", "Stock" };
	public static final String[] COLS_MIS_PEDIDOS = { "Ref.", "Fecha", "Estado", "Total" };
	public static final String[] COLS_LINEAS_PEDIDO = { "Perfume", "Cantidad", "Precio ud.", "Subtotal" };
	public static final String[] COLS_STOCK = { "Perfume", "Ubicación", "Cantidad", "Estado" };

	// ── Variable de sesión ────────────────────────────────────────────
	/**
	 * Almacena el usuario autenticado durante toda la sesión. Puede ser una
	 * instancia de Cliente o Empleado (herencia).
	 *
	 * Para saber el tipo: if (Constantes.usuarioAutenticado instanceof Empleado
	 * emp) { ... } if (Constantes.usuarioAutenticado instanceof Cliente cli) { ...
	 * }
	 *
	 * Se pone a null al cerrar sesión.
	 */
	public static Usuario usuarioAutenticado;
}