package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel Control de Stock — vista del empleado. Muestra resumen del almacén,
 * filtros y tabla de stock.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VStock extends JPanel {

	// ── Métricas ──────────────────────────────────────────────────────
	private JLabel lblTotalProductosValor;
	private JLabel lblStockBajoValor;
	private JLabel lblSinStockValor;

	// ── Filtros ───────────────────────────────────────────────────────
	private JTextField txtBuscar;
	private JComboBox<String> comboFiltroUbicacion;
	private JComboBox<String> comboFiltroEstado;

	// ── Tabla ─────────────────────────────────────────────────────────
	private JTable tablaStock;
	private DefaultTableModel modeloTablaStock;

	public VStock() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);
		setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);

		// ── Resumen de almacén ────────────────────────────────────────
		contenido.add(ComponentesUI.etiquetaSeccion("RESUMEN DE ALMACÉN"));
		contenido.add(Box.createVerticalStrut(12));

		JPanel panelMetricas = new JPanel(new GridLayout(1, 3, 12, 0));
		panelMetricas.setOpaque(false);
		panelMetricas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

		JPanel tarjetaTotal = ComponentesUI.tarjetaMetrica("Total productos", "0", "En catálogo", Tema.TEXTO_OSCURO);
		JPanel tarjetaBajo = ComponentesUI.tarjetaMetrica("Stock bajo", "0", "Menos de 5 uds", Tema.AVISO);
		JPanel tarjetaSinStock = ComponentesUI.tarjetaMetrica("Sin stock", "0", "Reponer pronto", Tema.ERROR);

		lblTotalProductosValor = (JLabel) tarjetaTotal.getComponent(2);
		lblStockBajoValor = (JLabel) tarjetaBajo.getComponent(2);
		lblSinStockValor = (JLabel) tarjetaSinStock.getComponent(2);

		panelMetricas.add(tarjetaTotal);
		panelMetricas.add(tarjetaBajo);
		panelMetricas.add(tarjetaSinStock);
		contenido.add(panelMetricas);
		contenido.add(Box.createVerticalStrut(20));

		// ── Sección estado almacén ────────────────────────────────────
		contenido.add(ComponentesUI.etiquetaSeccion("ESTADO DEL ALMACÉN"));
		contenido.add(Box.createVerticalStrut(10));

		// ── Filtros ───────────────────────────────────────────────────
		JPanel panelFiltros = new JPanel(new GridLayout(1, 3, 10, 0));
		panelFiltros.setOpaque(false);
		panelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

		txtBuscar = ComponentesUI.campoTexto("Buscar perfume...");
		comboFiltroUbicacion = new JComboBox<>(new String[] { "Todas las ubicaciones", Constantes.LOC_PEQUENO,
				Constantes.LOC_MEDIANO, Constantes.LOC_GRANDE });
		comboFiltroEstado = new JComboBox<>(
				new String[] { "Todos los estados", "Con stock", "Stock bajo", "Sin stock" });
		panelFiltros.add(txtBuscar);
		panelFiltros.add(comboFiltroUbicacion);
		panelFiltros.add(comboFiltroEstado);
		contenido.add(panelFiltros);
		contenido.add(Box.createVerticalStrut(14));

		// ── Tabla ─────────────────────────────────────────────────────
		modeloTablaStock = new DefaultTableModel(Constantes.COLS_STOCK, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// TODO: cargar stock real desde JDBC (PerfumesDAO.getAll())
		modeloTablaStock.addRow(new Object[] { "Velvet Rose", Constantes.LOC_PEQUENO, "12", "OK" });
		modeloTablaStock.addRow(new Object[] { "Golden Amber", Constantes.LOC_MEDIANO, "4", "Stock bajo" });

		tablaStock = new JTable(modeloTablaStock);
		ComponentesUI.prepararTabla(tablaStock);
		JScrollPane scroll = ComponentesUI.scrollTabla(tablaStock);
		contenido.add(scroll);

		add(contenido, BorderLayout.CENTER);
	}

	// ── Getters ───────────────────────────────────────────────────────

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

	public void setControlador(Controlador controlador) {
		// TODO: añadir listeners cuando se implementen botones de acción en stock
	}
}
