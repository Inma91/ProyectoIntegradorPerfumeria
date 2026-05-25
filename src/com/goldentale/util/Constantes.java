package com.goldentale.util;

import com.goldentale.model.Cliente;
import com.goldentale.model.Empleado;
/*
 * import javafx.scene.paint.Color;
 * import javafx.scene.text.Font;
 * import javafx.scene.text.FontWeight;
*/
import java.util.ArrayList;

public class Constantes {

	/**
	 * Constantes globales de la aplicación Golden Tale.
	 *
	 * @author Bradon Gaviria
	 * @author Inmaculada Gil
	 * @author David Moreno
	 */

	// ── Dimensiones de la ventana ─────────────────────────────────────
	public static final double ANCHURA_APLICACION = 1100;
	public static final double ALTURA_APLICACION = 700;
	public static final double ANCHURA_SIDEBAR = 175;
	public static final double ANCHURA_PANEL_DETALLE = 300;

	// ── Título ────────────────────────────────────────────────────────
	public static final String TITULO_APLICACION = "Golden Tale";

	
	
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

	// ── Localización automática en almacén ────────────────────────────
	public static final String LOC_PEQUENIO = "Estante A";
	public static final String LOC_MEDIANO = "Estante B";
	public static final String LOC_GRANDE = "Estante C";

	// ── Umbrales de stock ─────────────────────────────────────────────
	public static final int STOCK_MINIMO_ALERTA = 5;
	public static final int STOCK_MAXIMO_REFERENCIA = 20;

	// ── Panel del dashboard por defecto ──────────────────────────────
	public static final String PANEL_DASHBOARD_POR_DEFECTO = "dashboard";

	// ── Variables de sesión globalmente accesibles ────────────────────
	/**
	 * Almacena el cliente autenticado durante toda la sesión. Es null si quien ha
	 * iniciado sesión es un empleado.
	 */
	public static Cliente clienteAutenticado;

	/**
	 * Almacena el empleado autenticado durante toda la sesión. Es null si quien ha
	 * iniciado sesión es un cliente.
	 */
	public static Empleado empleadoAutenticado;
}
