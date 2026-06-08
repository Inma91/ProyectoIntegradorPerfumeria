package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel principal del empleado al iniciar sesión. Muestra tres métricas
 * resumen (pedidos del día, pendientes y productos con stock bajo), una tabla
 * con los pedidos recientes y un bloque de accesos rápidos a las acciones
 * más usadas (añadir perfume, modificar, ver stock y gestionar pedidos).
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 */
public class VEmpleadoDashboard extends JPanel {

	private JLabel lblPedidosHoyValor;
	private JLabel lblPendientesValor;
	private JLabel lblStockBajoValor;

	private JTable tablaPedidos;
	private DefaultTableModel modeloTablaPedidos;

	private JButton btnAccesoAnadirPerfume;
	private JButton btnAccesoModificarPerfume;
	private JButton btnAccesoStock;
	private JButton btnAccesoGestionarPedidos;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
	public VEmpleadoDashboard() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);
		contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		contenido.add(ComponentesUI.etiquetaSeccion("RESUMEN GENERAL"));
		contenido.add(Box.createVerticalStrut(12));

		JPanel panelMetricas = new JPanel(new GridLayout(1, 3, 12, 0));
		panelMetricas.setOpaque(false);
		panelMetricas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

		JPanel tarjetaPedidosHoy = ComponentesUI.tarjetaMetrica("Pedidos hoy", "0", "Pendiente de cargar",
				Tema.TEXTO_OSCURO);
		JPanel tarjetaPendientes = ComponentesUI.tarjetaMetrica("Pendientes", "0", "Requieren revisión", Tema.AVISO);
		JPanel tarjetaStockBajo = ComponentesUI.tarjetaMetrica("Stock bajo", "0", "Menos de 5 uds", Tema.ERROR);

		lblPedidosHoyValor = (JLabel) tarjetaPedidosHoy.getComponent(2);
		lblPendientesValor = (JLabel) tarjetaPendientes.getComponent(2);
		lblStockBajoValor = (JLabel) tarjetaStockBajo.getComponent(2);

		panelMetricas.add(tarjetaPedidosHoy);
		panelMetricas.add(tarjetaPendientes);
		panelMetricas.add(tarjetaStockBajo);
		contenido.add(panelMetricas);
		contenido.add(Box.createVerticalStrut(24));

		contenido.add(ComponentesUI.etiquetaSeccion("PEDIDOS RECIENTES"));
		contenido.add(Box.createVerticalStrut(10));

		modeloTablaPedidos = new DefaultTableModel(new String[] { "Ref.", "Cliente", "Perfumes", "Total", "Estado" },
				0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		

		tablaPedidos = new JTable(modeloTablaPedidos);
		ComponentesUI.prepararTabla(tablaPedidos);
		JScrollPane scrollPedidos = ComponentesUI.scrollTabla(tablaPedidos);
		scrollPedidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
		contenido.add(scrollPedidos);
		contenido.add(Box.createVerticalStrut(22));

		PanelRedondeado panelAccesos = new PanelRedondeado(12, Color.WHITE, Tema.BORDE_CLARO);
		panelAccesos.setLayout(new BoxLayout(panelAccesos, BoxLayout.Y_AXIS));
		panelAccesos.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

		JLabel lblAccesos = new JLabel("Accesos rápidos");
		lblAccesos.setFont(Tema.fuenteNegrita(13));
		lblAccesos.setForeground(Tema.TEXTO_OSCURO);
		panelAccesos.add(lblAccesos);
		panelAccesos.add(Box.createVerticalStrut(12));

		JPanel grid = new JPanel(new GridLayout(1, 4, 10, 0));
		grid.setOpaque(false);
		btnAccesoAnadirPerfume = ComponentesUI.botonPrincipal("Añadir");
		btnAccesoModificarPerfume = ComponentesUI.botonSecundario("Modificar");
		btnAccesoStock = ComponentesUI.botonSecundario("Stock");
		btnAccesoGestionarPedidos = ComponentesUI.botonSecundario("Pedidos");
		grid.add(btnAccesoAnadirPerfume);
		grid.add(btnAccesoModificarPerfume);
		grid.add(btnAccesoStock);
		grid.add(btnAccesoGestionarPedidos);
		panelAccesos.add(grid);
		contenido.add(panelAccesos);

		JScrollPane scroll = new JScrollPane(contenido);
		scroll.setBorder(null);
		scroll.setBackground(Tema.FONDO);
		scroll.getViewport().setBackground(Tema.FONDO);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll, BorderLayout.CENTER);
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

	public JButton getBtnAccesoAnadirPerfume() {
		return btnAccesoAnadirPerfume;
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

	/**
	 * Registra el controlador como listener de los botones de accesos rápidos.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnAccesoAnadirPerfume.addActionListener(controlador);
		btnAccesoModificarPerfume.addActionListener(controlador);
		btnAccesoStock.addActionListener(controlador);
		btnAccesoGestionarPedidos.addActionListener(controlador);
	}
}