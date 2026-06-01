package com.goldentale.vistaCliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.*;
import com.goldentale.model.util.*;

public class VCarritoCompra extends JFrame {

	private JPanel panelNavbar;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JTable tablaCarrito;
	private DefaultTableModel modeloTablaCarrito;
	private JScrollPane scrollTablaCarrito;
	private JLabel lblTotal;
	private JButton btnEliminarLinea;
	private JButton btnVaciarCarrito;
	private JButton btnFinalizarCompra;

	public VCarritoCompra() {
		super(Constantes.TITULO_APLICACION + " - Carrito");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(760, 560);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		btnVolver = ComponentesUI.botonSecundario("Volver");
		btnVolver.setPreferredSize(new Dimension(95, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnVolver);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);
		contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		lblTitulo = new JLabel("MI CARRITO");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		modeloTablaCarrito = new DefaultTableModel(new String[] { "Perfume", "Cantidad", "Precio ud.", "Subtotal" }, 0);
		modeloTablaCarrito.addRow(new Object[] { "Velvet Rose", "1", "89.99 EUR", "89.99 EUR" });
		// TODO cargar lineas reales del carrito
		tablaCarrito = new JTable(modeloTablaCarrito) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaCarrito);
		scrollTablaCarrito = ComponentesUI.scrollTabla(tablaCarrito);
		contenido.add(scrollTablaCarrito);
		contenido.add(Box.createVerticalStrut(14));

		lblTotal = new JLabel("Total: 89.99 EUR");
		lblTotal.setFont(Tema.fuenteNegrita(22));
		lblTotal.setForeground(Tema.MORADO);
		contenido.add(lblTotal);
		contenido.add(Box.createVerticalStrut(12));

		JPanel acciones = new JPanel(new GridLayout(1, 3, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnEliminarLinea = ComponentesUI.botonSecundario("Eliminar linea");
		btnVaciarCarrito = ComponentesUI.botonSecundario("Vaciar");
		btnFinalizarCompra = ComponentesUI.botonPrincipal("Finalizar compra");
		acciones.add(btnEliminarLinea);
		acciones.add(btnVaciarCarrito);
		acciones.add(btnFinalizarCompra);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JTable getTablaCarrito() {
		return tablaCarrito;
	}

	public DefaultTableModel getModeloTablaCarrito() {
		return modeloTablaCarrito;
	}

	public JLabel getLblTotal() {
		return lblTotal;
	}

	public JButton getBtnEliminarLinea() {
		return btnEliminarLinea;
	}

	public JButton getBtnVaciarCarrito() {
		return btnVaciarCarrito;
	}

	public JButton getBtnFinalizarCompra() {
		return btnFinalizarCompra;
	}
}
