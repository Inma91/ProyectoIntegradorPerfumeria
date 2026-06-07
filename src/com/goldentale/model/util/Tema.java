package com.goldentale.model.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Constantes visuales de Golden Tale. Paleta de colores, tipografías y helpers
 * de fuente.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Tema {

	/**
	 * Paleta de colores utilizada de manera uniforme en toda la interfaz gráfica de la aplicación.
	 */
	public static final Color MORADO = new Color(94, 60, 171);
	public static final Color MORADO_CLARO = new Color(115, 80, 195);
	public static final Color FONDO = new Color(246, 244, 240);
	public static final Color FONDO_LATERAL = new Color(252, 251, 249);
	public static final Color PANEL = Color.WHITE;
	public static final Color SELECCION = new Color(237, 230, 255);
	public static final Color TEXTO_OSCURO = new Color(30, 25, 20);
	public static final Color TEXTO_MEDIO = new Color(100, 90, 80);
	public static final Color TEXTO_CLARO = new Color(150, 140, 130);
	public static final Color BORDE = new Color(220, 215, 210);
	public static final Color BORDE_CLARO = new Color(235, 230, 225);
	public static final Color EXITO = new Color(34, 139, 34);
	public static final Color AVISO = new Color(200, 130, 0);
	public static final Color ERROR = new Color(180, 50, 50);

	/**
	 * Genera una tipografía estilizada en estilo plano o regular a partir de la fuente 
	 * del sistema "Segoe UI" con el tamaño de letra indicado.
	 *
	 * @param tam Tamaño en puntos de la tipografía.
	 * @return Instancia configurada de {@link Font}.
	 */
	public static Font fuenteNormal(int tam) {
		return new Font("Segoe UI", Font.PLAIN, tam);
	}

	/**
	 * Genera una tipografía estilizada en estilo negrita a partir de la fuente 
	 * del sistema "Segoe UI" con el tamaño de letra indicado.
	 *
	 * @param tam Tamaño en puntos de la tipografía.
	 * @return Instancia configurada de {@link Font}.
	 */
	public static Font fuenteNegrita(int tam) {
		return new Font("Segoe UI", Font.BOLD, tam);
	}
}