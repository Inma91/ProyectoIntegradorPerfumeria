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

	public VCatalogoCliente() {
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
		JLabel lblTitulo = ComponentesUI.etiquetaSeccion("CATÁLOGO DE PERFUMES");
		lblTitulo.setFont(Tema.fuenteNegrita(14));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		contenido.add(lblTitulo);
		contenido.add(Box.createVerticalStrut(12));

		// ── Filtros ───────────────────────────────────────────────────
		JPanel filtros = new JPanel(new GridLayout(1, 3, 10, 0));
		filtros.setOpaque(false);
		filtros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		txtBuscar = ComponentesUI.campoTexto("Buscar perfume o marca...");
		comboCategoria = new JComboBox<>(Constantes.CATEGORIAS_PERFUME);
		comboCategoria.insertItemAt("Todas las categorías", 0);
		comboCategoria.setSelectedIndex(0);
		btnVerCarrito = ComponentesUI.botonSecundario("Ver carrito");
		filtros.add(txtBuscar);
		filtros.add(comboCategoria);
		filtros.add(btnVerCarrito);
		contenido.add(filtros);
		contenido.add(Box.createVerticalStrut(14));

		// ── Tabla ─────────────────────────────────────────────────────
		modeloTablaCatalogo = new DefaultTableModel(Constantes.COLS_CATALOGO, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// TODO: cargar catálogo real desde JDBC (PerfumesDAO.getAll())
		modeloTablaCatalogo
				.addRow(new Object[] { "Velvet Rose", "Maison Luxe", "Floral", "Mujer", "50", "89.99", "12" });
		modeloTablaCatalogo
				.addRow(new Object[] { "Black Oud", "Orient Noir", "Oriental", "Hombre", "100", "120.00", "8" });
		modeloTablaCatalogo
				.addRow(new Object[] { "Golden Amber", "Orient Noir", "Amaderado", "Unisex", "75", "145.00", "4" });

		tablaCatalogo = new JTable(modeloTablaCatalogo);
		ComponentesUI.prepararTabla(tablaCatalogo);
		scrollTablaCatalogo = ComponentesUI.scrollTabla(tablaCatalogo);
		contenido.add(scrollTablaCatalogo);
		contenido.add(Box.createVerticalStrut(14));

		// ── Botones de acción ─────────────────────────────────────────
		JPanel acciones = new JPanel(new GridLayout(1, 2, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnAnadirCarrito = ComponentesUI.botonPrincipal("Añadir al carrito");
		btnMisPedidos = ComponentesUI.botonSecundario("Mis pedidos");
		acciones.add(btnAnadirCarrito);
		acciones.add(btnMisPedidos);
		contenido.add(acciones);

		add(contenido, BorderLayout.CENTER);
	}

	// ── Getters ───────────────────────────────────────────────────────

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

	public void setControlador(Controlador controlador) {
		btnAnadirCarrito.addActionListener(controlador);
		btnVerCarrito.addActionListener(controlador);
		btnMisPedidos.addActionListener(controlador);
	}
}
