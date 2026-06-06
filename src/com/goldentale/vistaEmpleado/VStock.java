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

	// ── Botón ─────────────────────────────────────────────────────────
	private JButton btnFiltrar;

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
		contenido.add(Box.createVerticalStrut(8));

		// ── Botón Filtrar ─────────────────────────────────────────────
		JPanel filaBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		filaBoton.setOpaque(false);
		filaBoton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnFiltrar = ComponentesUI.botonPrincipal("Filtrar");
		btnFiltrar.setPreferredSize(new Dimension(120, 36));
		filaBoton.add(btnFiltrar);
		contenido.add(filaBoton);
		contenido.add(Box.createVerticalStrut(14));

		// ── Tabla ─────────────────────────────────────────────────────
		modeloTablaStock = new DefaultTableModel(Constantes.COLS_STOCK, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		tablaStock = new JTable(modeloTablaStock);
		ComponentesUI.prepararTabla(tablaStock);
		JScrollPane scroll = ComponentesUI.scrollTabla(tablaStock);
		contenido.add(scroll);

		add(contenido, BorderLayout.CENTER);
	}

	// ── Métodos para cargar datos desde el controlador ────────────────

	/**
	 * Pinta la tabla con los datos recibidos del controlador.
	 * El controlador es responsable de pasar el estado ya calculado.
	 *
	 * @param filas array donde cada fila contiene 4 columnas:
	 *              {nombre, localizacion, cantidad, estado}.
	 */
	public void mostrarPerfumesConStock(Object[][] filas) {
		modeloTablaStock.setRowCount(0); // vaciar la tabla antes de pintar

		for (Object[] fila : filas) {
			modeloTablaStock.addRow(fila);
		}
	}

	/**
	 * Actualiza las tres tarjetas de métricas del resumen del almacén.
	 *
	 * @param total    número total de perfumes en catálogo.
	 * @param bajo     número de perfumes con stock bajo.
	 * @param sinStock número de perfumes sin stock.
	 */
	public void actualizarMetricas(int total, int bajo, int sinStock) {
		lblTotalProductosValor.setText(String.valueOf(total));
		lblStockBajoValor.setText(String.valueOf(bajo));
		lblSinStockValor.setText(String.valueOf(sinStock));
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

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public JTable getTablaStock() {
		return tablaStock;
	}

	public DefaultTableModel getModeloTablaStock() {
		return modeloTablaStock;
	}

	public void setControlador(Controlador controlador) {
		btnFiltrar.addActionListener(controlador);
	}
}