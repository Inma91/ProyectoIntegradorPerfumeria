package com.goldentale.vistaCliente;

import java.awt.BorderLayout;
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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.*;
import com.goldentale.model.util.*;

public class VMisPedidos extends JFrame {

	private JPanel panelNavbar;
	private JButton btnVolver;
	private JLabel lblTitulo;
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
		super(Constantes.TITULO_APLICACION + " - Mis pedidos");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(850, 620);
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

		JPanel cabecera = new JPanel(new BorderLayout(10, 0));
		cabecera.setOpaque(false);
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		lblTitulo = new JLabel("MIS PEDIDOS");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		comboFiltroEstado = new JComboBox<String>(Constantes.ESTADOS_PEDIDO);
		cabecera.add(lblTitulo, BorderLayout.WEST);
		cabecera.add(comboFiltroEstado, BorderLayout.EAST);
		contenido.add(cabecera);
		contenido.add(Box.createVerticalStrut(12));

		modeloTablaPedidos = new DefaultTableModel(Constantes.COLS_MIS_PEDIDOS, 0);
		modeloTablaPedidos.addRow(new Object[] { "#1001", "01/06/2026", "Pendiente", "89.99 EUR" });
		modeloTablaPedidos.addRow(new Object[] { "#1002", "25/05/2026", "Entregado", "120.00 EUR" });
		// TODO implementar consulta JDBC
		tablaPedidos = new JTable(modeloTablaPedidos) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaPedidos);
		scrollTablaPedidos = ComponentesUI.scrollTabla(tablaPedidos);
		contenido.add(scrollTablaPedidos);
		contenido.add(Box.createVerticalStrut(14));

		lblPedidoSeleccionado = new JLabel("Detalle del pedido seleccionado");
		lblPedidoSeleccionado.setFont(Tema.fuenteNegrita(12));
		lblPedidoSeleccionado.setForeground(Tema.TEXTO_MEDIO);
		contenido.add(lblPedidoSeleccionado);
		contenido.add(Box.createVerticalStrut(8));

		modeloTablaDetallePedido = new DefaultTableModel(Constantes.COLS_LINEAS_PEDIDO, 0);
		modeloTablaDetallePedido.addRow(new Object[] { "Velvet Rose", "1", "89.99 EUR", "89.99 EUR" });
		tablaDetallePedido = new JTable(modeloTablaDetallePedido) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaDetallePedido);
		scrollTablaDetallePedido = ComponentesUI.scrollTabla(tablaDetallePedido);
		scrollTablaDetallePedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));
		contenido.add(scrollTablaDetallePedido);
		contenido.add(Box.createVerticalStrut(12));

		JPanel acciones = new JPanel(new GridLayout(1, 1, 0, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		btnVerDetalle = ComponentesUI.botonPrincipal("Ver detalle");
		acciones.add(btnVerDetalle);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

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
}
