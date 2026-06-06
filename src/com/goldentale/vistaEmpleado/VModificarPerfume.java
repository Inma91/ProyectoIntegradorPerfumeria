package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel Modificar Perfume — vista del empleado. Permite buscar un perfume
 * por nombre y ml, y modificar su precio y/o añadir stock.
 */
public class VModificarPerfume extends JPanel {

	// ── Búsqueda ──────────────────────────────────────────────────────
	private JTextField txtBuscarNombre;
	private JTextField txtBuscarMl;
	private JButton btnBuscar;
	private JLabel lblResultado;

	// ── Panel de modificación (oculto hasta encontrar el perfume) ─────
	private JPanel panelModificacion;
	private JLabel lblDatosPerfume;
	private JLabel lblPrecioActual;
	private JTextField txtNuevoPrecio;
	private JLabel lblStockActual;
	private JTextField txtCantidadAnadir;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnCancelar;

	public VModificarPerfume() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(560, 700));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		// ── Título ────────────────────────────────────────────────────
		JLabel lblTitulo = new JLabel("Modificar perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		// ── Búsqueda ──────────────────────────────────────────────────
		JLabel lblBusquedaTitulo = new JLabel("Búsqueda del perfume");
		lblBusquedaTitulo.setFont(Tema.fuenteNegrita(12));
		lblBusquedaTitulo.setForeground(Tema.TEXTO_MEDIO);
		lblBusquedaTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblBusquedaTitulo);
		tarjeta.add(Box.createVerticalStrut(8));

		// Fila Nombre
		JLabel lblNombre = ComponentesUI.etiquetaFormulario("Nombre");
		lblNombre.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblNombre);
		tarjeta.add(Box.createVerticalStrut(4));
		txtBuscarNombre = ComponentesUI.campoTexto("Velvet Rose");
		txtBuscarNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		tarjeta.add(txtBuscarNombre);
		tarjeta.add(Box.createVerticalStrut(10));

		// Fila Mililitros
		JLabel lblMl = ComponentesUI.etiquetaFormulario("Mililitros");
		lblMl.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblMl);
		tarjeta.add(Box.createVerticalStrut(4));
		txtBuscarMl = ComponentesUI.campoTexto("50");
		txtBuscarMl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		tarjeta.add(txtBuscarMl);
		tarjeta.add(Box.createVerticalStrut(10));

		// Botón Buscar alineado a la derecha
		JPanel filaBuscar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		filaBuscar.setOpaque(false);
		filaBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		btnBuscar = ComponentesUI.botonPrincipal("Buscar");
		btnBuscar.setPreferredSize(new Dimension(110, 32));
		filaBuscar.add(btnBuscar);
		tarjeta.add(filaBuscar);
		tarjeta.add(Box.createVerticalStrut(8));

		lblResultado = new JLabel(" ");
		lblResultado.setFont(Tema.fuenteNormal(12));
		lblResultado.setForeground(Tema.MORADO);
		lblResultado.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblResultado);
		tarjeta.add(Box.createVerticalStrut(14));

		// ── Panel de modificación (oculto inicialmente) ───────────────
		panelModificacion = new PanelRedondeado(12, Tema.FONDO, Tema.BORDE_CLARO);
		panelModificacion.setLayout(new BoxLayout(panelModificacion, BoxLayout.Y_AXIS));
		panelModificacion.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		panelModificacion.setVisible(false);

		lblDatosPerfume = new JLabel("Perfume encontrado");
		lblDatosPerfume.setFont(Tema.fuenteNegrita(13));
		lblDatosPerfume.setForeground(Tema.TEXTO_OSCURO);
		lblDatosPerfume.setAlignmentX(LEFT_ALIGNMENT);

		// Precio actual + campo nuevo precio
		lblPrecioActual = new JLabel("Precio actual: 0.00€");
		lblPrecioActual.setFont(Tema.fuenteNormal(12));
		lblPrecioActual.setForeground(Tema.TEXTO_MEDIO);
		lblPrecioActual.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblNuevoPrecioTitulo = ComponentesUI.etiquetaFormulario("Nuevo precio (€)");
		lblNuevoPrecioTitulo.setAlignmentX(LEFT_ALIGNMENT);

		txtNuevoPrecio = ComponentesUI.campoTexto("Dejar en blanco para no modificar");
		txtNuevoPrecio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		// Stock actual + campo cantidad a añadir
		lblStockActual = new JLabel("Stock actual: 0 uds");
		lblStockActual.setFont(Tema.fuenteNormal(12));
		lblStockActual.setForeground(Tema.TEXTO_MEDIO);
		lblStockActual.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblCantidadTitulo = ComponentesUI.etiquetaFormulario("Cantidad a añadir");
		lblCantidadTitulo.setAlignmentX(LEFT_ALIGNMENT);

		txtCantidadAnadir = ComponentesUI.campoTexto("Dejar en blanco para no modificar");
		txtCantidadAnadir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		// Feedback
		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		lblError.setAlignmentX(LEFT_ALIGNMENT);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		lblExito.setAlignmentX(LEFT_ALIGNMENT);

		// Montaje del panel de modificación
		panelModificacion.add(lblDatosPerfume);
		panelModificacion.add(Box.createVerticalStrut(12));

		panelModificacion.add(lblPrecioActual);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(lblNuevoPrecioTitulo);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(txtNuevoPrecio);
		panelModificacion.add(Box.createVerticalStrut(12));

		panelModificacion.add(lblStockActual);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(lblCantidadTitulo);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(txtCantidadAnadir);
		panelModificacion.add(Box.createVerticalStrut(8));

		panelModificacion.add(lblError);
		panelModificacion.add(lblExito);
		panelModificacion.add(Box.createVerticalStrut(8));

		// Botones Cancelar y Guardar alineados a la derecha
		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		filaBotones.setOpaque(false);
		filaBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnCancelar = ComponentesUI.botonSecundario("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(110, 32));
		btnGuardar = ComponentesUI.botonPrincipal("Guardar");
		btnGuardar.setPreferredSize(new Dimension(110, 32));
		filaBotones.add(btnCancelar);
		filaBotones.add(btnGuardar);
		panelModificacion.add(filaBotones);

		tarjeta.add(panelModificacion);

		// ── Scroll wrapper ────────────────────────────────────────────
		JScrollPane scroll = new JScrollPane(tarjeta);
		scroll.setBorder(null);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll, BorderLayout.CENTER);
	}

	// ── Métodos de ayuda ──────────────────────────────────────────────

	public void mostrarError(String msg) {
		lblError.setText(msg);
		lblExito.setText(" ");
	}

	public void mostrarExito(String msg) {
		lblExito.setText(msg);
		lblError.setText(" ");
	}

	public void limpiarFeedback() {
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	public void limpiarFormulario() {
		txtBuscarNombre.setText("");
		txtBuscarMl.setText("");
		txtNuevoPrecio.setText("");
		txtCantidadAnadir.setText("");
		lblResultado.setText(" ");
		lblError.setText(" ");
		lblExito.setText(" ");
		panelModificacion.setVisible(false);
	}

	/**
	 * Valida los campos de búsqueda y devuelve los datos listos.
	 * @return Un array Object donde [0] es el nombre (String) y [1] los mililitros (Integer). Devuelve null si hay error.
	 */
	public Object[] obtenerDatosBusqueda() {
		String nombre = txtBuscarNombre.getText().trim();
		String mlTexto = txtBuscarMl.getText().trim();

		if (nombre.isEmpty() || mlTexto.isEmpty()) {
			lblResultado.setText("Por favor, introduce nombre y mililitros.");
			lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
			return null;
		}

		int ml = 0;
		try {
			ml = Integer.parseInt(mlTexto);
			if (ml <= 0) {
				lblResultado.setText("Los mililitros deben ser mayores que 0.");
				lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
				return null;
			}
		} catch (NumberFormatException e) {
			lblResultado.setText("Los mililitros deben ser un número entero (sin letras).");
			lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
			return null;
		}

		return new Object[] { nombre, ml };
	}

	/**
	 * Valida los campos de modificación de precio y stock.
	 * @param stockActual El stock que tiene el perfume para calcular que el cambio no lo deje en negativo.
	 * @return Un array Double donde [0] es el nuevo precio y [1] es la cantidad a sumar/restar. null si hay error.
	 */
	public Double[] obtenerDatosModificados(int stockActual) {
		String precioTexto = txtNuevoPrecio.getText().trim();
		String cantidadTexto = txtCantidadAnadir.getText().trim();

		if (precioTexto.isEmpty() && cantidadTexto.isEmpty()) {
			lblResultado.setText("Rellena al menos un campo para modificar.");
			lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
			return null;
		}

		Double nuevoPrecio = null;
		if (!precioTexto.isEmpty()) {
			try {
				nuevoPrecio = Double.parseDouble(precioTexto);
				if (nuevoPrecio <= 0) {
					lblResultado.setText("El nuevo precio debe ser mayor que 0.");
					lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
					return null;
				}
			} catch (NumberFormatException e) {
				lblResultado.setText("El precio debe ser un número válido.");
				lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
				return null;
			}
		}

		Double cantidadAnadir = null;
		if (!cantidadTexto.isEmpty()) {
			try {
				int aSumar = Integer.parseInt(cantidadTexto);
				if (stockActual + aSumar < 0) {
					lblResultado.setText("El stock final no puede ser negativo (Actual: " + stockActual + ").");
					lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
					return null;
				}
				cantidadAnadir = (double) aSumar;
			} catch (NumberFormatException e) {
				lblResultado.setText("La cantidad a añadir/quitar debe ser un número entero.");
				lblResultado.setForeground(com.goldentale.model.util.Tema.ERROR);
				return null;
			}
		}

		return new Double[] { nuevoPrecio, cantidadAnadir };
	}
	
	// ── Getters ───────────────────────────────────────────────────────

	public JTextField getTxtBuscarNombre() { return txtBuscarNombre; }
	public JTextField getTxtBuscarMl() { return txtBuscarMl; }
	public JButton getBtnBuscar() { return btnBuscar; }
	public JLabel getLblResultado() { return lblResultado; }
	public JPanel getPanelModificacion() { return panelModificacion; }
	public JLabel getLblDatosPerfume() { return lblDatosPerfume; }
	public JLabel getLblPrecioActual() { return lblPrecioActual; }
	public JTextField getTxtNuevoPrecio() { return txtNuevoPrecio; }
	public JLabel getLblStockActual() { return lblStockActual; }
	public JTextField getTxtCantidadAnadir() { return txtCantidadAnadir; }
	public JLabel getLblError() { return lblError; }
	public JLabel getLblExito() { return lblExito; }
	public JButton getBtnGuardar() { return btnGuardar; }
	public JButton getBtnCancelar() { return btnCancelar; }

	public void setControlador(Controlador controlador) {
		btnBuscar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
	}
}