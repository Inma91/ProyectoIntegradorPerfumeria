package com.goldentale.vistaEmpleado;

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
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

public class VStock extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JPanel panelMetricas;
	private JPanel tarjetaTotalProductos;
	private JLabel lblTotalProductosTitulo;
	private JLabel lblTotalProductosValor;
	private JPanel tarjetaStockBajo;
	private JLabel lblStockBajoTitulo;
	private JLabel lblStockBajoValor;
	private JPanel tarjetaSinStock;
	private JLabel lblSinStockTitulo;
	private JLabel lblSinStockValor;
	private JPanel panelFiltros;
	private JTextField txtBuscar;
	private JComboBox<String> comboFiltroUbicacion;
	private JComboBox<String> comboFiltroEstado;
	private JTable tablaStock;
	private DefaultTableModel modeloTablaStock;
	private JScrollPane scrollTablaStock;

	public VStock() {
		super(Constantes.TITULO_APLICACION + " - Stock");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(850, 610);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		btnVolver = ComponentesUI.botonSecundario("Volver");
		btnVolver.setPreferredSize(new Dimension(100, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnVolver);
		lblNombreApp = (JLabel) panelNavbar.getComponent(0);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);
		contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		lblTitulo = new JLabel("RESUMEN DE ALMACEN");
		lblTitulo.setFont(Tema.fuenteNegrita(12));
		lblTitulo.setForeground(Tema.TEXTO_MEDIO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		panelMetricas = new JPanel(new GridLayout(1, 3, 12, 0));
		panelMetricas.setOpaque(false);
		panelMetricas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
		tarjetaTotalProductos = ComponentesUI.tarjetaMetrica("Total productos", "0", "En catalogo", Tema.TEXTO_OSCURO);
		tarjetaStockBajo = ComponentesUI.tarjetaMetrica("Stock bajo", "0", "Menos de 5 uds", Tema.AVISO);
		tarjetaSinStock = ComponentesUI.tarjetaMetrica("Sin stock", "0", "Reponer pronto", Tema.ERROR);
		lblTotalProductosTitulo = (JLabel) tarjetaTotalProductos.getComponent(0);
		lblTotalProductosValor = (JLabel) tarjetaTotalProductos.getComponent(2);
		lblStockBajoTitulo = (JLabel) tarjetaStockBajo.getComponent(0);
		lblStockBajoValor = (JLabel) tarjetaStockBajo.getComponent(2);
		lblSinStockTitulo = (JLabel) tarjetaSinStock.getComponent(0);
		lblSinStockValor = (JLabel) tarjetaSinStock.getComponent(2);
		panelMetricas.add(tarjetaTotalProductos);
		panelMetricas.add(tarjetaStockBajo);
		panelMetricas.add(tarjetaSinStock);
		contenido.add(panelMetricas);
		contenido.add(Box.createVerticalStrut(20));

		panelFiltros = new JPanel(new GridLayout(1, 3, 10, 0));
		panelFiltros.setOpaque(false);
		panelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		txtBuscar = ComponentesUI.campoTexto("Buscar perfume...");
		comboFiltroUbicacion = new JComboBox<String>(
				new String[] { "Todas", Constantes.LOC_PEQUENO, Constantes.LOC_MEDIANO, Constantes.LOC_GRANDE });
		comboFiltroEstado = new JComboBox<String>(new String[] { "Todos", "Con stock", "Stock bajo", "Sin stock" });
		panelFiltros.add(txtBuscar);
		panelFiltros.add(comboFiltroUbicacion);
		panelFiltros.add(comboFiltroEstado);
		contenido.add(panelFiltros);
		contenido.add(Box.createVerticalStrut(14));

		modeloTablaStock = new DefaultTableModel(Constantes.COLS_STOCK, 0);
		modeloTablaStock.addRow(new Object[] { "Velvet Rose", Constantes.LOC_PEQUENO, "12", "OK" });
		modeloTablaStock.addRow(new Object[] { "Golden Amber", Constantes.LOC_MEDIANO, "4", "Stock bajo" });
		// TODO cargar datos reales desde base de datos
		tablaStock = new JTable(modeloTablaStock) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		ComponentesUI.prepararTabla(tablaStock);
		scrollTablaStock = ComponentesUI.scrollTabla(tablaStock);
		contenido.add(scrollTablaStock);
		add(contenido, BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JLabel getLblTotalProductosValor() {
		return lblTotalProductosValor;
	}

	public JLabel getLblStockBajoValor() {
		return lblStockBajoValor;
	}

	public JLabel getLblSinStockValor() {
		return lblSinStockValor;
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public JComboBox<String> getComboFiltroUbicacion() {
		return comboFiltroUbicacion;
	}

	public JComboBox<String> getComboFiltroEstado() {
		return comboFiltroEstado;
	}

	public JTable getTablaStock() {
		return tablaStock;
	}

	public DefaultTableModel getModeloTablaStock() {
		return modeloTablaStock;
	}

	public JScrollPane getScrollTablaStock() {
		return scrollTablaStock;
	}
}
