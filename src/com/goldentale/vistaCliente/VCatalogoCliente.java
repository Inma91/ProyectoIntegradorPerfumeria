package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel Catálogo — vista del cliente. Muestra el catálogo de perfumes con
 * búsqueda y filtro por categoría. Desde aquí se puede añadir al carrito, ir al
 * carrito o a mis pedidos.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 */
public class VCatalogoCliente extends JPanel {

	private JTextField txtBuscar;
	private JComboBox<String> comboCategoria;
	private JTable tablaCatalogo;
	private DefaultTableModel modeloTablaCatalogo;
	private JScrollPane scrollTablaCatalogo;
	private JButton btnAnadirCarrito;
	private JButton btnVerCarrito;
	private JButton btnMisPedidos;
	private JButton btnFiltrar;

	/**
	 * Construye el panel e inicializa todos los componentes visuales del catálogo.
	 */
	public VCatalogoCliente() {
		inicializarComponentes();
	}

	/**
	 * Inicializa y configura la disposición de todos los componentes gráficos
	 * de la interfaz, incluyendo la sección de filtros, la tabla con los datos
	 * de los perfumes y los paneles de botones de acción.
	 */
	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);
		setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(Tema.FONDO);

		JLabel lblTitulo = ComponentesUI.etiquetaSeccion("CATÁLOGO DE PERFUMES");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		JPanel filtros = new JPanel(new GridLayout(1, 3, 10, 0));
		filtros.setOpaque(false);
		filtros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
				
		txtBuscar = ComponentesUI.campoTexto("Buscar perfume o marca...");
		comboCategoria = new JComboBox<>(Constantes.CATEGORIAS_PERFUME);
		comboCategoria.insertItemAt("Todas las categorías", 0);
		comboCategoria.setSelectedIndex(0);
				
		btnFiltrar = ComponentesUI.botonPrincipal("Filtrar");
				
		filtros.add(txtBuscar);
		filtros.add(comboCategoria);
		filtros.add(btnFiltrar);
				
		contenido.add(filtros);
		contenido.add(Box.createVerticalStrut(14));

		modeloTablaCatalogo = new DefaultTableModel(Constantes.COLS_CATALOGO, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		

		tablaCatalogo = new JTable(modeloTablaCatalogo);
		ComponentesUI.prepararTabla(tablaCatalogo);
		scrollTablaCatalogo = ComponentesUI.scrollTabla(tablaCatalogo);
		contenido.add(scrollTablaCatalogo);
		contenido.add(Box.createVerticalStrut(14));

		JPanel accionesFila1 = new JPanel(new GridLayout(1, 2, 10, 0));
		accionesFila1.setOpaque(false);
		accionesFila1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
				
		btnAnadirCarrito = ComponentesUI.botonPrincipal("Añadir al carrito");
		btnVerCarrito = ComponentesUI.botonSecundario("Ver carrito");
				
		accionesFila1.add(btnAnadirCarrito);
		accionesFila1.add(btnVerCarrito);
		contenido.add(accionesFila1);

		contenido.add(Box.createVerticalStrut(10));

		JPanel accionesFila2 = new JPanel(new GridLayout(1, 1, 0, 0));
		accionesFila2.setOpaque(false);
		accionesFila2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
				
		btnMisPedidos = ComponentesUI.botonSecundario("Mis pedidos");
				
		accionesFila2.add(btnMisPedidos);
		contenido.add(accionesFila2);

		add(contenido, BorderLayout.CENTER);
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public JComboBox<String> getComboCategoria() {
		return comboCategoria;
	}

	public JTable getTablaCatalogo() {
		return tablaCatalogo;
	}

	public DefaultTableModel getModeloTablaCatalogo() {
		return modeloTablaCatalogo;
	}

	public JButton getBtnAnadirCarrito() {
		return btnAnadirCarrito;
	}

	public JButton getBtnVerCarrito() {
		return btnVerCarrito;
	}

	public JButton getBtnMisPedidos() {
		return btnMisPedidos;
	}
	
	public JButton getBtnFiltrar() {
	    return btnFiltrar;
	}

	/**
	 * Registra el controlador como listener encargado de capturar los eventos
	 * de los botones de interacción del catálogo.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnAnadirCarrito.addActionListener(controlador);
		btnVerCarrito.addActionListener(controlador);
		btnMisPedidos.addActionListener(controlador);
		btnFiltrar.addActionListener(controlador);
	}
	
	/**
	 * Actualiza el contenido de la tabla del catálogo cargando las nuevas filas
	 * de perfumes proporcionadas y limpiando los registros previos.
	 *
	 * @param filas Matriz bidimensional de objetos con la información de los perfumes.
	 */
	public void mostrarCatalogo(Object[][] filas) {
	    modeloTablaCatalogo.setRowCount(0);
	    for (Object[] fila : filas) {
	        modeloTablaCatalogo.addRow(fila);
	    }
	}
}