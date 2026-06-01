package com.goldentale.vistaEmpleado;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VEmpleadoDashboard extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JLabel lblNombreEmpleado;
	private JButton btnCerrarSesion;
	private JPanel panelMetricas;
	private JPanel tarjetaPedidosHoy;
	private JLabel lblPedidosHoyTitulo;
	private JLabel lblPedidosHoyValor;
	private JPanel tarjetaPendientes;
	private JLabel lblPendientesTitulo;
	private JLabel lblPendientesValor;
	private JPanel tarjetaStockBajo;
	private JLabel lblStockBajoTitulo;
	private JLabel lblStockBajoValor;
	private JLabel lblTituloPedidos;
	private JTable tablaPedidos;
	private DefaultTableModel modeloTablaPedidos;
	private JScrollPane scrollTablaPedidos;
	private JPanel panelAccesosRapidos;
	private JLabel lblPedidoSeleccionado;
	private JButton btnAccesoAñadirPerfume;
	private JButton btnAccesoModificarPerfume;
	private JButton btnAccesoStock;
	private JButton btnAccesoGestionarPedidos;

	public VEmpleadoDashboard() {
		super(Constantes.TITULO_APLICACION + " - Empleado");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(930, 650);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Tema.FONDO);
		setLayout(new BorderLayout());

		btnCerrarSesion = ComponentesUI.botonSecundario("Salir");
		btnCerrarSesion.setPreferredSize(new Dimension(90, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnCerrarSesion);
		lblNombreApp = (JLabel) panelNavbar.getComponent(0);
		lblNombreEmpleado = new JLabel("Empleado");
		lblNombreEmpleado.setFont(Tema.fuenteNormal(12));
		lblNombreEmpleado.setForeground(Color.WHITE);
		panelNavbar.add(lblNombreEmpleado, BorderLayout.CENTER);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);
		contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		JLabel lblResumen = tituloSeccion("RESUMEN GENERAL");
		contenido.add(lblResumen);
		contenido.add(Box.createVerticalStrut(12));

		panelMetricas = new JPanel(new GridLayout(1, 3, 12, 0));
		panelMetricas.setOpaque(false);
		panelMetricas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
		tarjetaPedidosHoy = ComponentesUI.tarjetaMetrica("Pedidos hoy", "0", "Pendiente de cargar", Tema.TEXTO_OSCURO);
		tarjetaPendientes = ComponentesUI.tarjetaMetrica("Pendientes", "0", "Requieren revision", Tema.AVISO);
		tarjetaStockBajo = ComponentesUI.tarjetaMetrica("Stock bajo", "0", "Menos de 5 uds", Tema.ERROR);
		lblPedidosHoyTitulo = (JLabel) tarjetaPedidosHoy.getComponent(0);
		lblPedidosHoyValor = (JLabel) tarjetaPedidosHoy.getComponent(2);
		lblPendientesTitulo = (JLabel) tarjetaPendientes.getComponent(0);
		lblPendientesValor = (JLabel) tarjetaPendientes.getComponent(2);
		lblStockBajoTitulo = (JLabel) tarjetaStockBajo.getComponent(0);
		lblStockBajoValor = (JLabel) tarjetaStockBajo.getComponent(2);
		panelMetricas.add(tarjetaPedidosHoy);
		panelMetricas.add(tarjetaPendientes);
		panelMetricas.add(tarjetaStockBajo);
		contenido.add(panelMetricas);
		contenido.add(Box.createVerticalStrut(24));

		lblTituloPedidos = tituloSeccion("PEDIDOS RECIENTES");
		contenido.add(lblTituloPedidos);
		contenido.add(Box.createVerticalStrut(10));

		modeloTablaPedidos = new DefaultTableModel(new String[] { "Ref.", "Cliente", "Perfumes", "Total", "Estado" },
				0);
		modeloTablaPedidos.addRow(new Object[] { "#1001", "Cliente prueba", "Velvet Rose", "89.99 EUR", "Pendiente" });
		modeloTablaPedidos.addRow(new Object[] { "#1002", "Cliente prueba", "Black Oud", "120.00 EUR", "Procesando" });
		// TODO cargar datos reales desde JDBC
		tablaPedidos = new JTable(modeloTablaPedidos) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaPedidos);
		scrollTablaPedidos = ComponentesUI.scrollTabla(tablaPedidos);
		scrollTablaPedidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
		contenido.add(scrollTablaPedidos);
		contenido.add(Box.createVerticalStrut(22));

		panelAccesosRapidos = new PanelRedondeado(12, Color.WHITE, Tema.BORDE_CLARO);
		panelAccesosRapidos.setLayout(new BoxLayout(panelAccesosRapidos, BoxLayout.Y_AXIS));
		panelAccesosRapidos.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		lblPedidoSeleccionado = new JLabel("Accesos rapidos");
		lblPedidoSeleccionado.setFont(Tema.fuenteNegrita(13));
		lblPedidoSeleccionado.setForeground(Tema.TEXTO_OSCURO);
		panelAccesosRapidos.add(lblPedidoSeleccionado);
		panelAccesosRapidos.add(Box.createVerticalStrut(12));

		JPanel grid = new JPanel(new GridLayout(1, 4, 10, 0));
		grid.setOpaque(false);
		btnAccesoAñadirPerfume = ComponentesUI.botonPrincipal("Anadir");
		btnAccesoModificarPerfume = ComponentesUI.botonSecundario("Modificar");
		btnAccesoStock = ComponentesUI.botonSecundario("Stock");
		btnAccesoGestionarPedidos = ComponentesUI.botonSecundario("Pedidos");
		grid.add(btnAccesoAñadirPerfume);
		grid.add(btnAccesoModificarPerfume);
		grid.add(btnAccesoStock);
		grid.add(btnAccesoGestionarPedidos);
		panelAccesosRapidos.add(grid);
		contenido.add(panelAccesosRapidos);

		add(new JScrollPane(contenido), BorderLayout.CENTER);
	}

	private JLabel tituloSeccion(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(Tema.fuenteNegrita(12));
		label.setForeground(Tema.TEXTO_MEDIO);
		label.setAlignmentX(LEFT_ALIGNMENT);
		return label;
	}

	public JLabel getLblNombreEmpleado() {
		return lblNombreEmpleado;
	}

	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	public JLabel getLblPedidosHoyValor() {
		return lblPedidosHoyValor;
	}

	public JLabel getLblPendientesValor() {
		return lblPendientesValor;
	}

	public JLabel getLblStockBajoValor() {
		return lblStockBajoValor;
	}

	public JTable getTablaPedidos() {
		return tablaPedidos;
	}

	public DefaultTableModel getModeloTablaPedidos() {
		return modeloTablaPedidos;
	}

	public JScrollPane getScrollTablaPedidos() {
		return scrollTablaPedidos;
	}

	public JPanel getPanelAccesosRapidos() {
		return panelAccesosRapidos;
	}

	public JLabel getLblPedidoSeleccionado() {
		return lblPedidoSeleccionado;
	}

	public JButton getBtnAccesoAñadirPerfume() {
		return btnAccesoAñadirPerfume;
	}

	public JButton getBtnAccesoModificarPerfume() {
		return btnAccesoModificarPerfume;
	}

	public JButton getBtnAccesoStock() {
		return btnAccesoStock;
	}

	public JButton getBtnAccesoGestionarPedidos() {
		return btnAccesoGestionarPedidos;
	}
}
