package com.goldentale.model.util;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class ComponentesUI {

	public static class PanelRedondeado extends JPanel {
		private int radio;
		private Color fondo;
		private Color borde;

		public PanelRedondeado(int radio, Color fondo) {
			this(radio, fondo, null);
		}

		public PanelRedondeado(int radio, Color fondo, Color borde) {
			this.radio = radio;
			this.fondo = fondo;
			this.borde = borde;
			setOpaque(false);
		}

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

	public static JButton botonPrincipal(String texto) {
		JButton boton = new JButton(texto) {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isPressed() || getModel().isRollover()) {
					g2.setColor(Tema.MORADO_CLARO);
				} else {
					g2.setColor(Tema.MORADO);
				}
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

	public static JButton botonSecundario(String texto) {
		JButton boton = new JButton(texto) {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isRollover()) {
					g2.setColor(new Color(240, 235, 255));
				} else {
					g2.setColor(Color.WHITE);
				}
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

	public static JTextField campoTexto(final String placeholder) {
		JTextField campo = new JTextField() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
				g2.dispose();
				super.paintComponent(g);
				if (getText().length() == 0 && !isFocusOwner()) {
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

	public static JPasswordField campoPassword() {
		JPasswordField campo = new JPasswordField();
		campo.setFont(Tema.fuenteNormal(13));
		campo.setBackground(Color.WHITE);
		campo.setOpaque(false);
		campo.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Tema.BORDE, 1, true),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		return campo;
	}

	public static JTextArea areaTexto() {
		JTextArea area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(Tema.fuenteNormal(13));
		area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		return area;
	}

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

	public static JLabel etiquetaFormulario(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(Tema.fuenteNegrita(12));
		label.setForeground(Tema.TEXTO_OSCURO);
		return label;
	}

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

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		tabla.setDefaultRenderer(Object.class, renderer);
	}

	public static JScrollPane scrollTabla(JTable tabla) {
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(new LineBorder(Tema.BORDE, 1, true));
		scroll.getViewport().setBackground(Color.WHITE);
		return scroll;
	}

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
