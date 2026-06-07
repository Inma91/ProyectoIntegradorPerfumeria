package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel del cliente para consultar el historial de pedidos. Lista los pedidos
 * del cliente autenticado con posibilidad de filtrarlos por estado y, al
 * seleccionar uno, muestra el detalle de sus líneas (perfumes, cantidades y
 * subtotales). Tanto la carga inicial como el filtrado los realiza el
 * controlador, que pasa los datos a esta vista mediante
 * {@link #mostrarPedidos(Object[][])} y
 * {@link #mostrarDetalle(int, Object[][])}.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
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
	private JButton btnFiltrar;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
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

		JPanel cabecera = new JPanel(new BorderLayout(10, 0));
		cabecera.setOpaque(false);
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

		JLabel lblTitulo = new JLabel("MIS PEDIDOS");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);

		comboFiltroEstado = new JComboBox<>(Constantes.ESTADOS_PEDIDO);
		comboFiltroEstado.insertItemAt("Todos", 0);
		comboFiltroEstado.setSelectedIndex(0);

		btnFiltrar = ComponentesUI.botonPrincipal("Filtrar");
		btnFiltrar.setPreferredSize(new Dimension(90, 30));

		JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		filtroPanel.setOpaque(false);
		filtroPanel.add(comboFiltroEstado);
		filtroPanel.add(btnFiltrar);

		cabecera.add(lblTitulo, BorderLayout.WEST);
		cabecera.add(filtroPanel, BorderLayout.EAST);
		contenido.add(cabecera);

		modeloTablaPedidos = new DefaultTableModel(Constantes.COLS_MIS_PEDIDOS, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		tablaPedidos = new JTable(modeloTablaPedidos);
		ComponentesUI.prepararTabla(tablaPedidos);
		scrollTablaPedidos = ComponentesUI.scrollTabla(tablaPedidos);
		contenido.add(scrollTablaPedidos);
		contenido.add(Box.createVerticalStrut(14));

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

		tablaDetallePedido = new JTable(modeloTablaDetallePedido);
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

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnVerDetalle.addActionListener(controlador);
		btnFiltrar.addActionListener(controlador);
	}

	/**
	 * Vuelca la lista de pedidos en la tabla principal. Vacía la tabla antes
	 * de pintar para no acumular filas entre llamadas.
	 *
	 * @param filas Matriz con los pedidos a mostrar.
	 */
	public void mostrarPedidos(Object[][] filas) {
		modeloTablaPedidos.setRowCount(0);
		for (Object[] fila : filas) {
			modeloTablaPedidos.addRow(fila);
		}
	}

	/**
	 * Vuelca el detalle de las líneas de un pedido en la tabla de detalle y
	 * actualiza el label con el número de pedido seleccionado.
	 *
	 * @param idPedido ID del pedido que se está mostrando.
	 * @param filas    Matriz con las líneas del pedido.
	 */
	public void mostrarDetalle(int idPedido, Object[][] filas) {
		lblPedidoSeleccionado.setText("Detalle del pedido #" + idPedido);
		modeloTablaDetallePedido.setRowCount(0);
		for (Object[] fila : filas) {
			modeloTablaDetallePedido.addRow(fila);
		}
	}

	/**
	 * Limpia las dos tablas, restablece el label de detalle al texto por
	 * defecto y resetea el filtro de estado al primer elemento. Útil al
	 * cerrar sesión o al navegar fuera de la vista.
	 */
	public void limpiarVista() {
		modeloTablaPedidos.setRowCount(0);
		modeloTablaDetallePedido.setRowCount(0);
		lblPedidoSeleccionado.setText("Detalle del pedido seleccionado");
		comboFiltroEstado.setSelectedIndex(0);
	}
}