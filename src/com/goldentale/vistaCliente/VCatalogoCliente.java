package com.goldentale.vistaCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.*;
import com.goldentale.model.util.*;

public class VCatalogoCliente extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JButton btnCerrarSesion;
	private JLabel lblTitulo;
	private JTextField txtBuscar;
	private JComboBox<String> comboCategoria;
	private JTable tablaCatalogo;
	private DefaultTableModel modeloTablaCatalogo;
	private JScrollPane scrollTablaCatalogo;
	private JButton btnAnadirCarrito;
	private JButton btnVerCarrito;
	private JButton btnMisPedidos;

	public VCatalogoCliente() {
		super(Constantes.TITULO_APLICACION + " - Catalogo");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(900, 620);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		btnCerrarSesion = ComponentesUI.botonSecundario("Salir");
		btnCerrarSesion.setPreferredSize(new Dimension(90, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnCerrarSesion);
		lblNombreApp = (JLabel) panelNavbar.getComponent(0);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);
		contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		lblTitulo = new JLabel("CATALOGO DE PERFUMES");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		JPanel filtros = new JPanel(new GridLayout(1, 3, 10, 0));
		filtros.setOpaque(false);
		filtros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		txtBuscar = ComponentesUI.campoTexto("Buscar perfume o marca...");
		comboCategoria = new JComboBox<String>(Constantes.CATEGORIAS_PERFUME);
		btnVerCarrito = ComponentesUI.botonSecundario("Ver carrito");
		filtros.add(txtBuscar);
		filtros.add(comboCategoria);
		filtros.add(btnVerCarrito);
		contenido.add(filtros);
		contenido.add(Box.createVerticalStrut(14));

		modeloTablaCatalogo = new DefaultTableModel(Constantes.COLS_CATALOGO, 0);
		modeloTablaCatalogo
				.addRow(new Object[] { "Velvet Rose", "Maison Luxe", "Floral", "Mujer", "50", "89.99", "12" });
		modeloTablaCatalogo
				.addRow(new Object[] { "Black Oud", "Orient Noir", "Oriental", "Hombre", "100", "120.00", "8" });
		modeloTablaCatalogo
				.addRow(new Object[] { "Golden Amber", "Orient Noir", "Amaderado", "Unisex", "75", "145.00", "4" });
		// TODO cargar catalogo real desde JDBC
		tablaCatalogo = new JTable(modeloTablaCatalogo) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaCatalogo);
		scrollTablaCatalogo = ComponentesUI.scrollTabla(tablaCatalogo);
		contenido.add(scrollTablaCatalogo);
		contenido.add(Box.createVerticalStrut(14));

		JPanel acciones = new JPanel(new GridLayout(1, 2, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnAnadirCarrito = ComponentesUI.botonPrincipal("Anadir al carrito");
		btnMisPedidos = ComponentesUI.botonSecundario("Mis pedidos");
		acciones.add(btnAnadirCarrito);
		acciones.add(btnMisPedidos);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public JComboBox<String> getComboCategoria() {
		return comboCategoria;
	}

	public JTable getTablaCatalogo() {
		return tablaCatalogo;
	}

	public DefaultTableModel getModeloTablaCatalogo() {
		return modeloTablaCatalogo;
	}

	public JButton getBtnAnadirCarrito() {
		return btnAnadirCarrito;
	}

	public JButton getBtnVerCarrito() {
		return btnVerCarrito;
	}

	public JButton getBtnMisPedidos() {
		return btnMisPedidos;
	}
}
