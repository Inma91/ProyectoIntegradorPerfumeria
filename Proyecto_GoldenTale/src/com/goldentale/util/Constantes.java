package com.goldentale.util;

import com.goldentale.model.Cliente;
import com.goldentale.model.Empleado;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

	// ── Colores principales (JavaFX Color) ────────────────────────────
	/*public static final Color MORADO_PRINCIPAL = Color.web("#534AB7");
	public static final Color MORADO_OSCURO = Color.web("#3C3489");
	public static final Color MORADO_CLARO = Color.web("#EEEDFE");
	public static final Color MORADO_BORDE = Color.web("#AFA9EC");

	// ── Colores de estado de stock ────────────────────────────────────
	public static final Color VERDE_STOCK = Color.web("#3B6D11");
	public static final Color AMARILLO_STOCK = Color.web("#BA7517");
	public static final Color ROJO_STOCK = Color.web("#A32D2D");

	// ── Colores de estado de pedido ───────────────────────────────────
	public static final Color COLOR_PENDIENTE = Color.web("#633806");
	public static final Color COLOR_PROCESANDO = Color.web("#0C447C");
	public static final Color COLOR_ENVIADO = Color.web("#3C3489");
	public static final Color COLOR_ENTREGADO = Color.web("#27500A");
	public static final Color COLOR_CANCELADO = Color.web("#444441");

	// ── Colores neutros ───────────────────────────────────────────────
	public static final Color BLANCO = Color.web("#FFFFFF");
	public static final Color NEGRO = Color.web("#1A1A1A");
	public static final Color GRIS_FONDO = Color.web("#F2F2F2");
	public static final Color GRIS_BORDE = Color.web("#AAAAAA");

	// ── Fuentes (JavaFX Font) ─────────────────────────────────────────
	public static final Font SEGOE_34 = Font.font("Segoe UI", 34);
	public static final Font SEGOE_20 = Font.font("Segoe UI", 20);
	public static final Font SEGOE_18 = Font.font("Segoe UI", 18);
	public static final Font SEGOE_18_NEGRITA = Font.font("Segoe UI", FontWeight.BOLD, 18);
	public static final Font SEGOE_16 = Font.font("Segoe UI", 16);
	public static final Font SEGOE_16_NEGRITA = Font.font("Segoe UI", FontWeight.BOLD, 16);
	public static final Font SEGOE_14 = Font.font("Segoe UI", 14);
	public static final Font SEGOE_13 = Font.font("Segoe UI", 13);
	public static final Font SEGOE_12 = Font.font("Segoe UI", 12);
	public static final Font SEGOE_11 = Font.font("Segoe UI", 11);

	// ── Tabla ─────────────────────────────────────────────────────────
	public static final double ALTURA_FILAS_TABLA = 40;

	// ── Rutas FXML ────────────────────────────────────────────────────
	public static final String FXML_LOGIN = "/vista/cliente/login.fxml";
	public static final String FXML_CATALOGO_CLIENTE = "/vista/cliente/catalogo.fxml";
	public static final String FXML_PEDIDO = "/vista/cliente/pedido.fxml";
	public static final String FXML_PAGO = "/vista/cliente/pago.fxml";
	public static final String FXML_MIS_PEDIDOS = "/vista/cliente/mis_pedidos.fxml";
	public static final String FXML_DASHBOARD = "/vista/empleado/dashboard.fxml";
	public static final String FXML_CATALOGO_ADMIN = "/vista/empleado/catalogo_admin.fxml";
	public static final String FXML_STOCK = "/vista/empleado/stock.fxml";
	public static final String FXML_PEDIDOS_ADMIN = "/vista/empleado/pedidos_admin.fxml";
	*/
	
	
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
	public static final String LOC_MUJER = "Estante A";
	public static final String LOC_HOMBRE = "Estante B";
	public static final String LOC_UNISEX = "Almacén Norte";

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
