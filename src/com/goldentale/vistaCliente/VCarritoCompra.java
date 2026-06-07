package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel Carrito de Compra — vista del cliente. Muestra las líneas del carrito,
 * el total y permite finalizar la compra.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VCarritoCompra extends JPanel {

	private JTable tablaCarrito;
	private DefaultTableModel modeloTablaCarrito;
	private JScrollPane scrollTablaCarrito;
	private JLabel lblTotal;
	private JButton btnEliminarLinea;
	private JButton btnVaciarCarrito;
	private JButton btnFinalizarCompra;

	public VCarritoCompra() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);
		setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);

		// ── Título ────────────────────────────────────────────────────
		JLabel lblTitulo = new JLabel("MI CARRITO");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		// ── Tabla ─────────────────────────────────────────────────────
		modeloTablaCarrito = new DefaultTableModel(new String[] { "Perfume", "Cantidad", "Precio ud.", "Subtotal" },
				0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// TODO: cargar líneas reales del carrito desde sesión/BD
		modeloTablaCarrito.addRow(new Object[] { "Velvet Rose", "1", "89.99 EUR", "89.99 EUR" });

		tablaCarrito = new JTable(modeloTablaCarrito);
		ComponentesUI.prepararTabla(tablaCarrito);
		scrollTablaCarrito = ComponentesUI.scrollTabla(tablaCarrito);
		contenido.add(scrollTablaCarrito);
		contenido.add(Box.createVerticalStrut(14));

		// ── Total ─────────────────────────────────────────────────────
		lblTotal = new JLabel("Total: 89.99 EUR");
		lblTotal.setFont(Tema.fuenteNegrita(22));
		lblTotal.setForeground(Tema.MORADO);
		contenido.add(lblTotal);
		contenido.add(Box.createVerticalStrut(12));

		// ── Botones de acción ─────────────────────────────────────────
		JPanel acciones = new JPanel(new GridLayout(1, 3, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnEliminarLinea = ComponentesUI.botonSecundario("Eliminar línea");
		btnVaciarCarrito = ComponentesUI.botonSecundario("Vaciar");
		btnFinalizarCompra = ComponentesUI.botonPrincipal("Finalizar compra");
		acciones.add(btnEliminarLinea);
		acciones.add(btnVaciarCarrito);
		acciones.add(btnFinalizarCompra);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	// ── Getters ───────────────────────────────────────────────────────

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

	public void setControlador(Controlador controlador) {
		btnEliminarLinea.addActionListener(controlador);
		btnVaciarCarrito.addActionListener(controlador);
		btnFinalizarCompra.addActionListener(controlador);
	}

	/**
	 * Vuelca las filas y el total recibidos del controlador en la vista del
	 * carrito.
	 *
	 * @param filas Matriz Object[][] con las líneas del carrito.
	 * @param total Total acumulado a mostrar en la etiqueta.
	 */
	public void mostrarCarrito(Object[][] filas, double total) {
		DefaultTableModel modelo = (DefaultTableModel) tablaCarrito.getModel();
		modelo.setRowCount(0);
		for (Object[] fila : filas) {
			modelo.addRow(fila);
		}
		lblTotal.setText(String.format("Total: %.2f EUR", total));
	}
}
