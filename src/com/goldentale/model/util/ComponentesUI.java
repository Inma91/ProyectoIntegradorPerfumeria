package com.goldentale.model.util;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Fábrica de componentes visuales reutilizables para Golden Tale. Todos los
 * componentes siguen la paleta definida en {@link Tema}.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Tema
 */
public class ComponentesUI {

	/**
	 * Panel personalizado con esquinas redondeadas, soporte para color de fondo
	 * y contorno de borde opcional.
	 */
	public static class PanelRedondeado extends JPanel {
		private final int radio;
		private final Color fondo;
		private final Color borde;

		/**
		 * Inicializa un panel redondeado sin contorno de borde.
		 *
		 * @param radio Radio de redondeo de las esquinas en píxeles.
		 * @param fondo Color de fondo del panel.
		 */
		public PanelRedondeado(int radio, Color fondo) {
			this(radio, fondo, null);
		}

		/**
		 * Inicializa un panel redondeado con color de fondo y contorno de borde específico.
		 *
		 * @param radio Radio de redondeo de las esquinas en píxeles.
		 * @param fondo Color de fondo del panel.
		 * @param borde Color del contorno del borde, o {@code null} si se omite.
		 */
		public PanelRedondeado(int radio, Color fondo, Color borde) {
			this.radio = radio;
			this.fondo = fondo;
			this.borde = borde;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(fondo);
			g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radio, radio));
			if (borde != null) {
				g2.setColor(borde);
				g2.setStroke(new BasicStroke(1f));
				g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, radio, radio));
			}
			g2.dispose();
			super.paintComponent(g);
		}
	}

	/**
	 * Crea un botón principal estilizado con fondo morado, esquinas redondeadas
	 * y texto en color blanco. Cambia de tonalidad al pasar el cursor o presionarlo.
	 *
	 * @param texto Texto que se mostrará en el botón.
	 * @return Instancia configurada de {@link JButton}.
	 */
	public static JButton botonPrincipal(String texto) {
		JButton boton = new JButton(texto) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getModel().isPressed() || getModel().isRollover() ? Tema.MORADO_CLARO : Tema.MORADO);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				g2.setColor(Color.WHITE);
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
						(getHeight() + fm.getAscent() - fm.getDescent()) / 2);
				g2.dispose();
			}
		};
		boton.setFont(Tema.fuenteNegrita(13));
		boton.setForeground(Color.WHITE);
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return boton;
	}

	/**
	 * Crea un botón secundario estilizado con fondo blanco, contorno definido,
	 * esquinas redondeadas y texto oscuro. Cuenta con efecto de realce al pasar el cursor.
	 *
	 * @param texto Texto que se mostrará en el botón.
	 * @return Instancia configurada de {@link JButton}.
	 */
	public static JButton botonSecundario(String texto) {
		JButton boton = new JButton(texto) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getModel().isRollover() ? new Color(240, 235, 255) : Color.WHITE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				g2.setColor(Tema.BORDE);
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
				g2.setColor(Tema.TEXTO_OSCURO);
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
						(getHeight() + fm.getAscent() - fm.getDescent()) / 2);
				g2.dispose();
			}
		};
		boton.setFont(Tema.fuenteNegrita(13));
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return boton;
	}

	/**
	 * Botón lateral del sidebar: sin borde, fondo transparente, highlight en hover
	 * y estado activo en morado.
	 *
	 * @param texto Texto que se mostrará en el botón.
	 * @return Instancia configurada de {@link JButton}.
	 */
	public static JButton botonSidebar(String texto) {
		JButton boton = new JButton(texto) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				boolean activo = Boolean.TRUE.equals(getClientProperty("activo"));
				if (activo) {
					g2.setColor(Tema.SELECCION);
					g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
				} else if (getModel().isRollover()) {
					g2.setColor(new Color(230, 225, 220));
					g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
				}
				g2.setColor(activo ? Tema.MORADO : Tema.TEXTO_OSCURO);
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(getText(), 12, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
				g2.dispose();
			}
		};
		boton.setFont(Tema.fuenteNormal(13));
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		boton.setPreferredSize(new Dimension(150, 36));
		boton.setAlignmentX(Component.LEFT_ALIGNMENT);
		return boton;
	}

	/**
	 * Crea un campo de texto redondeado con un marcador de posición (placeholder)
	 * que se muestra cuando el campo está vacío y no tiene el foco de edición.
	 *
	 * @param placeholder Texto de sugerencia o guía que aparecerá de fondo.
	 * @return Instancia configurada de {@link JTextField}.
	 */
	public static JTextField campoTexto(final String placeholder) {
		JTextField campo = new JTextField() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
				g2.dispose();
				super.paintComponent(g);
				if (getText().isEmpty() && !isFocusOwner()) {
					Graphics2D g3 = (Graphics2D) g.create();
					Insets ins = getInsets();
					g3.setColor(Tema.TEXTO_CLARO);
					g3.setFont(getFont());
					g3.drawString(placeholder, ins.left + 2, ins.top + g3.getFontMetrics().getAscent());
					g3.dispose();
				}
			}
		};
		campo.setFont(Tema.fuenteNormal(13));
		campo.setBackground(Color.WHITE);
		campo.setOpaque(false);
		campo.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Tema.BORDE, 1, true),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		return campo;
	}

	/**
	 * Crea un campo de contraseña redondeado con márgenes internos estilizados
	 * y enmascaramiento automático de caracteres.
	 *
	 * @return Instancia configurada de {@link JPasswordField}.
	 */
	public static JPasswordField campoPassword() {
		JPasswordField campo = new JPasswordField();
		campo.setFont(Tema.fuenteNormal(13));
		campo.setBackground(Color.WHITE);
		campo.setOpaque(false);
		campo.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Tema.BORDE, 1, true),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		return campo;
	}

	/**
	 * Crea un área de edición de texto multilínea configurada con ajuste automático
	 * de líneas por palabra y márgenes interiores.
	 *
	 * @return Instancia configurada de {@link JTextArea}.
	 */
	public static JTextArea areaTexto() {
		JTextArea area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(Tema.fuenteNormal(13));
		area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		return area;
	}

	/**
	 * Genera una etiqueta estandarizada para campos de formularios con tipografía
	 * resaltada y color oscuro.
	 *
	 * @param texto Texto descriptivo del campo de formulario.
	 * @return Instancia configurada de {@link JLabel}.
	 */
	public static JLabel etiquetaFormulario(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(Tema.fuenteNegrita(12));
		label.setForeground(Tema.TEXTO_OSCURO);
		return label;
	}

	/**
	 * Genera una etiqueta alineada a la izquierda para identificar secciones
	 * o encabezados secundarios de la interfaz.
	 *
	 * @param texto Título o nombre de la sección.
	 * @return Instancia configurada de {@link JLabel}.
	 */
	public static JLabel etiquetaSeccion(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(Tema.fuenteNegrita(12));
		label.setForeground(Tema.TEXTO_MEDIO);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		return label;
	}

	/**
	 * Construye un contenedor visual en forma de tarjeta analítica que muestra de manera
	 * estructurada un título descriptor, un valor numérico destacado y un subtexto de detalle.
	 *
	 * @param titulo     Nombre del indicador o métrica.
	 * @param valor      Dato numérico o estadístico principal.
	 * @param detalle    Información aclaratoria complementaria de menor tamaño.
	 * @param colorValor Color temático con el que se pintará el texto del valor principal.
	 * @return Componente contenedor {@link JPanel} estructurado en forma de tarjeta.
	 */
	public static JPanel tarjetaMetrica(String titulo, String valor, String detalle, Color colorValor) {
		PanelRedondeado tarjeta = new PanelRedondeado(12, Color.WHITE, Tema.BORDE_CLARO);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(Tema.fuenteNormal(11));
		lblTitulo.setForeground(Tema.TEXTO_MEDIO);
		lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblValor = new JLabel(valor);
		lblValor.setFont(Tema.fuenteNegrita(26));
		lblValor.setForeground(colorValor);
		lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblDetalle = new JLabel(detalle);
		lblDetalle.setFont(Tema.fuenteNormal(10));
		lblDetalle.setForeground(Tema.TEXTO_CLARO);
		lblDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);

		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(4));
		tarjeta.add(lblValor);
		tarjeta.add(Box.createVerticalStrut(2));
		tarjeta.add(lblDetalle);
		return tarjeta;
	}

	/**
	 * Configura el diseño estético general de una tabla de datos, incluyendo fuentes,
	 * alturas de fila, colores de selección y renderizadores personalizados con soporte
	 * para alternancia de color en filas pares e impares.
	 *
	 * @param tabla Instancia de {@link JTable} sobre la que se aplicarán las directrices del tema.
	 */
	public static void prepararTabla(JTable tabla) {
		tabla.setFont(Tema.fuenteNormal(12));
		tabla.setRowHeight(36);
		tabla.setShowHorizontalLines(true);
		tabla.setShowVerticalLines(false);
		tabla.setGridColor(Tema.BORDE_CLARO);
		tabla.setBackground(Color.WHITE);
		tabla.setSelectionBackground(Tema.SELECCION);
		tabla.setSelectionForeground(Tema.TEXTO_OSCURO);
		tabla.setFocusable(false);
		tabla.getTableHeader().setFont(Tema.fuenteNegrita(11));
		tabla.getTableHeader().setBackground(new Color(248, 246, 242));
		tabla.getTableHeader().setForeground(Tema.TEXTO_MEDIO);
		tabla.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDE));

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int row,
					int col) {
				super.getTableCellRendererComponent(t, v, sel, foc, row, col);
				setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				setBackground(sel ? Tema.SELECCION : (row % 2 == 0 ? Color.WHITE : new Color(251, 249, 246)));
				setForeground(Tema.TEXTO_OSCURO);
				return this;
			}
		};
		tabla.setDefaultRenderer(Object.class, renderer);
	}

	/**
	 * Envuelve una tabla de datos en un panel con barras de desplazamiento, asociándole
	 * un borde curvo unificado acorde con la interfaz de la aplicación.
	 *
	 * @param tabla Instancia de {@link JTable} que requiere scroll.
	 * @return El contenedor {@link JScrollPane} configurado.
	 */
	public static JScrollPane scrollTabla(JTable tabla) {
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(new LineBorder(Tema.BORDE, 1, true));
		scroll.getViewport().setBackground(Color.WHITE);
		return scroll;
	}

	/**
	 * Barra superior morada con título a la izquierda y un botón opcional a la
	 * derecha.
	 *
	 * @param titulo       Texto o título principal que lucirá en la zona izquierda.
	 * @param botonDerecha Componente botón que se ubicará a la derecha, o {@code null} si no se requiere.
	 * @return El contenedor {@link JPanel} de la barra superior.
	 */
	public static JPanel navbar(String titulo, JButton botonDerecha) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Tema.MORADO);
		panel.setPreferredSize(new Dimension(0, 56));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JLabel nombre = new JLabel(titulo);
		nombre.setFont(Tema.fuenteNegrita(16));
		nombre.setForeground(Color.WHITE);
		panel.add(nombre, BorderLayout.WEST);

		if (botonDerecha != null) {
			panel.add(botonDerecha, BorderLayout.EAST);
		}
		return panel;
	}
}