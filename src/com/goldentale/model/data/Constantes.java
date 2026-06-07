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
	/** Anchura total de la ventana principal en píxeles. */
	public static final int ANCHURA_APLICACION = 1100;

	/** Altura total de la ventana principal en píxeles. */
	public static final int ALTURA_APLICACION = 700;

	/** Anchura del panel lateral (sidebar) en píxeles. */
	public static final int ANCHURA_SIDEBAR = 175;

	/** Anchura del panel de detalle lateral en píxeles. */
	public static final int ANCHURA_PANEL_DETALLE = 300;

	// ── Título ────────────────────────────────────────────────────────
	/** Título de la aplicación, usado en la barra de navegación y mensajes. */
	public static final String TITULO_APLICACION = "Golden Tale";

	// ── Fuentes ───────────────────────────────────────────────────────
	/**
	 * Fuentes SansSerif predefinidas para uso en la interfaz.
	 * <p>
	 * Se recomienda usar {@code Tema.fuenteNormal} o {@code Tema.fuenteNegrita} en
	 * su lugar siempre que sea posible.
	 * </p>
	 */
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
	/** Altura en píxeles de cada fila de las tablas de la aplicación. */
	public static final int ALTURA_FILAS_TABLA = 30;

	// ── Roles de usuario ──────────────────────────────────────────────
	/** Rol asignado a los clientes de la tienda. */
	public static final String ROL_CLIENTE = "cliente";

	/** Rol asignado a los empleados de la tienda. */
	public static final String ROL_EMPLEADO = "empleado";

	// ── Estados de pedido ─────────────────────────────────────────────
	public static final String ESTADO_PENDIENTE = "Pendiente";
	public static final String ESTADO_PROCESANDO = "Procesando";
	public static final String ESTADO_ENVIADO = "Enviado";
	public static final String ESTADO_ENTREGADO = "Entregado";
	public static final String ESTADO_CANCELADO = "Cancelado";

	/**
	 * Todos los estados posibles de un pedido, en el orden en que se ofrecen al
	 * usuario.
	 */
	public static final String[] ESTADOS_PEDIDO = { ESTADO_PENDIENTE, ESTADO_PROCESANDO, ESTADO_ENVIADO,
			ESTADO_ENTREGADO, ESTADO_CANCELADO };

	// ── Formas de pago ────────────────────────────────────────────────
	public static final String PAGO_EFECTIVO = "Efectivo";
	public static final String PAGO_TARJETA = "Tarjeta";
	public static final String PAGO_BIZUM = "Bizum";

	/**
	 * Todas las formas de pago aceptadas, en el orden en que se ofrecen al usuario.
	 */
	public static final String[] FORMAS_PAGO = { PAGO_EFECTIVO, PAGO_TARJETA, PAGO_BIZUM };

	// ── Público objetivo ──────────────────────────────────────────────
	public static final String PUBLICO_MUJER = "Mujer";
	public static final String PUBLICO_HOMBRE = "Hombre";
	public static final String PUBLICO_UNISEX = "Unisex";

	/**
	 * Todos los públicos objetivo disponibles, en el orden en que se ofrecen al
	 * usuario.
	 */
	public static final String[] PUBLICOS_OBJETIVO = { PUBLICO_MUJER, PUBLICO_HOMBRE, PUBLICO_UNISEX };

	// ── Categorías de perfume ─────────────────────────────────────────
	/** Categorías olfativas disponibles para clasificar los perfumes. */
	public static final String[] CATEGORIAS_PERFUME = { "Floral", "Oriental", "Cítrico", "Acuático", "Amaderado" };

	// ── Localización automática en almacén (según ml del frasco) ──────
	/**
	 * Ubicación en el almacén para frascos de hasta {@value #ML_LIMITE_PEQUENO} ml.
	 */
	public static final String LOC_PEQUENO = "Estante A";

	/** Ubicación en el almacén para frascos de {@value #ML_LIMITE_MEDIANO} ml. */
	public static final String LOC_MEDIANO = "Estante B";

	/**
	 * Ubicación en el almacén para frascos de {@value #ML_LIMITE_MEDIANO} ml en
	 * adelante.
	 */
	public static final String LOC_GRANDE = "Estante C";

	/** Límite superior (inclusive) en ml para considerar un frasco como pequeño. */
	public static final int ML_LIMITE_PEQUENO = 50;

	/** Tamaño en ml correspondiente al frasco mediano. */
	public static final int ML_LIMITE_MEDIANO = 75;

	// ── Umbrales de stock ─────────────────────────────────────────────
	/**
	 * Cantidad mínima de unidades a partir de la cual se considera que el stock es
	 * bajo y se muestra una alerta en el panel de gestión.
	 */
	public static final int STOCK_MINIMO_ALERTA = 5;

	/**
	 * Cantidad máxima de referencia usada para calcular el porcentaje de ocupación
	 * del stock en las métricas del panel de empleado.
	 */
	public static final int STOCK_MAXIMO_REFERENCIA = 20;

	// ── Columnas de las JTable ────────────────────────────────────────
	/** Cabeceras de la tabla del catálogo de cliente. */
	public static final String[] COLS_CATALOGO = { "Nombre", "Marca", "Categoría", "Público", "ml", "Precio", "Stock" };

	/** Cabeceras de la tabla de pedidos del cliente. */
	public static final String[] COLS_MIS_PEDIDOS = { "Ref.", "Fecha", "Estado", "Total" };

	/** Cabeceras de la tabla de líneas de detalle de un pedido. */
	public static final String[] COLS_LINEAS_PEDIDO = { "Perfume", "Cantidad", "Precio ud.", "Subtotal" };

	/** Cabeceras de la tabla de stock del empleado. */
	public static final String[] COLS_STOCK = { "Perfume", "Ubicación", "Cantidad", "Estado" };

	// ── Claves del CardLayout (vistas) ────────────────────────────────
	/** Clave de la vista de inicio (pantalla de bienvenida). */
	public static final String VISTA_INICIO = "inicio";

	/** Clave de la vista de inicio de sesión. */
	public static final String VISTA_LOGIN = "login";

	/** Clave de la vista de registro de nuevo usuario. */
	public static final String VISTA_REGISTRO = "registro";

	/** Clave de la vista del catálogo de perfumes (cliente). */
	public static final String VISTA_CATALOGO = "catalogo";

	/** Clave de la vista del carrito de compra (cliente). */
	public static final String VISTA_CARRITO = "carrito";

	/** Clave de la vista de pedidos del cliente. */
	public static final String VISTA_MIS_PEDIDOS = "misPedidos";

	/** Clave de la vista de pago (cliente). */
	public static final String VISTA_PAGO = "pago";

	/** Clave de la vista del dashboard del empleado. */
	public static final String VISTA_DASHBOARD = "dashboard";

	/** Clave de la vista de alta de perfume (empleado). */
	public static final String VISTA_ANADIR = "anadirPerfume";

	/** Clave de la vista de modificación de perfume (empleado). */
	public static final String VISTA_MODIFICAR = "modificarPerfume";

	/** Clave de la vista de gestión de stock (empleado). */
	public static final String VISTA_STOCK = "stock";

	// ── Variable de sesión ────────────────────────────────────────────
	/**
	 * Usuario autenticado durante la sesión activa. Puede ser un cliente o un
	 * empleado (subclases de {@link Usuario}). Se establece a {@code null} al
	 * cerrar sesión.
	 */
	public static Usuario usuarioAutenticado;
}