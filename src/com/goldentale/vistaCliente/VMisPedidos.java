package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel Mis Pedidos — vista del cliente. Lista los pedidos del cliente con
 * filtro por estado y muestra el detalle de las líneas del pedido seleccionado.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VMisPedidos extends JPanel {

	private JComboBox<String> comboFiltroEstado;
	private JTable tablaPedidos;
	private DefaultTableModel modeloTablaPedidos;
	private JScrollPane scrollTablaPedidos;
	private JTable tablaDetallePedido;
	private DefaultTableModel modeloTablaDetallePedido;
	private JScrollPane scrollTablaDetallePedido;
	private JLabel lblPedidoSeleccionado;
	private JButton btnVerDetalle;

	public VMisPedidos() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);
		setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);

		// ── Cabecera con título y filtro ──────────────────────────────
		JPanel cabecera = new JPanel(new BorderLayout(10, 0));
		cabecera.setOpaque(false);
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

		JLabel lblTitulo = new JLabel("MIS PEDIDOS");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);

		comboFiltroEstado = new JComboBox<>(Constantes.ESTADOS_PEDIDO);
		comboFiltroEstado.insertItemAt("Todos", 0);
		comboFiltroEstado.setSelectedIndex(0);

		cabecera.add(lblTitulo, BorderLayout.WEST);
		cabecera.add(comboFiltroEstado, BorderLayout.EAST);
		contenido.add(cabecera);
		contenido.add(Box.createVerticalStrut(12));

		// ── Tabla de pedidos ──────────────────────────────────────────
		modeloTablaPedidos = new DefaultTableModel(Constantes.COLS_MIS_PEDIDOS, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// TODO: cargar pedidos reales del usuario autenticado desde JDBC
		modeloTablaPedidos.addRow(new Object[] { "#1001", "01/06/2026", "Pendiente", "89.99 EUR" });
		modeloTablaPedidos.addRow(new Object[] { "#1002", "25/05/2026", "Entregado", "120.00 EUR" });

		tablaPedidos = new JTable(modeloTablaPedidos);
		ComponentesUI.prepararTabla(tablaPedidos);
		scrollTablaPedidos = ComponentesUI.scrollTabla(tablaPedidos);
		contenido.add(scrollTablaPedidos);
		contenido.add(Box.createVerticalStrut(14));

		// ── Detalle del pedido seleccionado ───────────────────────────
		lblPedidoSeleccionado = new JLabel("Detalle del pedido seleccionado");
		lblPedidoSeleccionado.setFont(Tema.fuenteNegrita(12));
		lblPedidoSeleccionado.setForeground(Tema.TEXTO_MEDIO);
		contenido.add(lblPedidoSeleccionado);
		contenido.add(Box.createVerticalStrut(8));

		modeloTablaDetallePedido = new DefaultTableModel(Constantes.COLS_LINEAS_PEDIDO, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// TODO: cargar líneas del pedido seleccionado desde JDBC
		modeloTablaDetallePedido.addRow(new Object[] { "Velvet Rose", "1", "89.99 EUR", "89.99 EUR" });

		tablaDetallePedido = new JTable(modeloTablaDetallePedido);
		ComponentesUI.prepararTabla(tablaDetallePedido);
		scrollTablaDetallePedido = ComponentesUI.scrollTabla(tablaDetallePedido);
		scrollTablaDetallePedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));
		contenido.add(scrollTablaDetallePedido);
		contenido.add(Box.createVerticalStrut(12));

		// ── Botón ver detalle ─────────────────────────────────────────
		JPanel acciones = new JPanel(new GridLayout(1, 1, 0, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		btnVerDetalle = ComponentesUI.botonPrincipal("Ver detalle");
		acciones.add(btnVerDetalle);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	// ── Getters ───────────────────────────────────────────────────────

	public JComboBox<String> getComboFiltroEstado() {
		return comboFiltroEstado;
	}

	public JTable getTablaPedidos() {
		return tablaPedidos;
	}

	public DefaultTableModel getModeloTablaPedidos() {
		return modeloTablaPedidos;
	}

	public JTable getTablaDetallePedido() {
		return tablaDetallePedido;
	}

	public DefaultTableModel getModeloTablaDetallePedido() {
		return modeloTablaDetallePedido;
	}

	public JLabel getLblPedidoSeleccionado() {
		return lblPedidoSeleccionado;
	}

	public JButton getBtnVerDetalle() {
		return btnVerDetalle;
	}

	public void setControlador(Controlador controlador) {
		btnVerDetalle.addActionListener(controlador);
	}
}
