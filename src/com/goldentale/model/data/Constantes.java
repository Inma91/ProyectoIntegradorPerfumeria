package com.goldentale.model.data;

import java.awt.Font;
import com.goldentale.model.db.Usuario;

/**
 * Constantes globales de la aplicación Golden Tale.
 *
 * @author Brandon Gaviria
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

	// ── Fuentes ───────────────────────────────────────────────────────
	public static final Font SANS_34 = new Font("SansSerif", Font.PLAIN, 34);
	public static final Font SANS_20 = new Font("SansSerif", Font.PLAIN, 20);
	public static final Font SANS_18 = new Font("SansSerif", Font.PLAIN, 18);
	public static final Font SANS_18_NEGRITA = new Font("SansSerif", Font.BOLD, 18);
	public static final Font SANS_16 = new Font("SansSerif", Font.PLAIN, 16);
	public static final Font SANS_16_NEGRITA = new Font("SansSerif", Font.BOLD, 16);
	public static final Font SANS_14 = new Font("SansSerif", Font.PLAIN, 14);
	public static final Font SANS_13 = new Font("SansSerif", Font.PLAIN, 13);
	public static final Font SANS_12 = new Font("SansSerif", Font.PLAIN, 12);
	public static final Font SANS_11 = new Font("SansSerif", Font.PLAIN, 11);

	// ── Tabla ─────────────────────────────────────────────────────────
	public static final int ALTURA_FILAS_TABLA = 30;

	// ── Roles de usuario ──────────────────────────────────────────────
	public static final String ROL_CLIENTE = "cliente";
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
	public static final String LOC_PEQUENO = "Estante A"; // <= 50ml
	public static final String LOC_MEDIANO = "Estante B"; // 75ml
	public static final String LOC_GRANDE = "Estante C"; // >= 100ml
	public static final int ML_LIMITE_PEQUENO = 50;
	public static final int ML_LIMITE_MEDIANO = 75;

	// ── Umbrales de stock ─────────────────────────────────────────────
	public static final int STOCK_MINIMO_ALERTA = 5;
	public static final int STOCK_MAXIMO_REFERENCIA = 20;

	// ── Columnas de las JTable ────────────────────────────────────────
	public static final String[] COLS_CATALOGO = { "Nombre", "Marca", "Categoría", "Público", "ml", "Precio", "Stock" };
	public static final String[] COLS_MIS_PEDIDOS = { "Ref.", "Fecha", "Estado", "Total" };
	public static final String[] COLS_LINEAS_PEDIDO = { "Perfume", "Cantidad", "Precio ud.", "Subtotal" };
	public static final String[] COLS_STOCK = { "Perfume", "Ubicación", "Cantidad", "Estado" };

	// ── Variable de sesión ────────────────────────────────────────────
	/**
	 * Almacena el usuario autenticado durante toda la sesión. Puede ser una
	 * instancia de Cliente o Empleado (herencia). Se pone a null al cerrar sesión.
	 */
	public static Usuario usuarioAutenticado;
}