package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel del empleado para modificar un perfume existente. Permite buscar por
 * nombre y mililitros y, una vez encontrado, modificar su precio y/o ajustar
 * el stock. El panel de modificación permanece oculto hasta que la búsqueda
 * tiene éxito. Las validaciones de los campos se realizan internamente.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 */
public class VModificarPerfume extends JPanel {

	private JTextField txtBuscarNombre;
	private JTextField txtBuscarMl;
	private JButton btnBuscar;
	private JLabel lblResultado;

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

	/** Indica si la última validación numérica detectó un error de formato. */
	private boolean tieneError;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
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

		JLabel lblTitulo = new JLabel("Modificar perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		JLabel lblBusquedaTitulo = new JLabel("Búsqueda del perfume");
		lblBusquedaTitulo.setFont(Tema.fuenteNegrita(12));
		lblBusquedaTitulo.setForeground(Tema.TEXTO_MEDIO);
		lblBusquedaTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblBusquedaTitulo);
		tarjeta.add(Box.createVerticalStrut(8));

		JLabel lblNombre = ComponentesUI.etiquetaFormulario("Nombre");
		lblNombre.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblNombre);
		tarjeta.add(Box.createVerticalStrut(4));
		txtBuscarNombre = ComponentesUI.campoTexto("Velvet Rose");
		txtBuscarNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		tarjeta.add(txtBuscarNombre);
		tarjeta.add(Box.createVerticalStrut(10));

		JLabel lblMl = ComponentesUI.etiquetaFormulario("Mililitros");
		lblMl.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblMl);
		tarjeta.add(Box.createVerticalStrut(4));
		txtBuscarMl = ComponentesUI.campoTexto("50");
		txtBuscarMl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		tarjeta.add(txtBuscarMl);
		tarjeta.add(Box.createVerticalStrut(10));

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

		panelModificacion = new PanelRedondeado(12, Tema.FONDO, Tema.BORDE_CLARO);
		panelModificacion.setLayout(new BoxLayout(panelModificacion, BoxLayout.Y_AXIS));
		panelModificacion.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		panelModificacion.setVisible(false);

		lblDatosPerfume = new JLabel("Perfume encontrado");
		lblDatosPerfume.setFont(Tema.fuenteNegrita(13));
		lblDatosPerfume.setForeground(Tema.TEXTO_OSCURO);
		lblDatosPerfume.setAlignmentX(LEFT_ALIGNMENT);

		lblPrecioActual = new JLabel("Precio actual: 0.00€");
		lblPrecioActual.setFont(Tema.fuenteNormal(12));
		lblPrecioActual.setForeground(Tema.TEXTO_MEDIO);
		lblPrecioActual.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblNuevoPrecioTitulo = ComponentesUI.etiquetaFormulario("Nuevo precio (€)");
		lblNuevoPrecioTitulo.setAlignmentX(LEFT_ALIGNMENT);

		txtNuevoPrecio = ComponentesUI.campoTexto("Dejar en blanco para no modificar");
		txtNuevoPrecio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		lblStockActual = new JLabel("Stock actual: 0 uds");
		lblStockActual.setFont(Tema.fuenteNormal(12));
		lblStockActual.setForeground(Tema.TEXTO_MEDIO);
		lblStockActual.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblCantidadTitulo = ComponentesUI.etiquetaFormulario("Cantidad a añadir");
		lblCantidadTitulo.setAlignmentX(LEFT_ALIGNMENT);

		txtCantidadAnadir = ComponentesUI.campoTexto("Dejar en blanco para no modificar");
		txtCantidadAnadir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		lblError.setAlignmentX(LEFT_ALIGNMENT);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		lblExito.setAlignmentX(LEFT_ALIGNMENT);

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

		JScrollPane scroll = new JScrollPane(tarjeta);
		scroll.setBorder(null);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Muestra un mensaje de error en el label de feedback y limpia el de éxito.
	 *
	 * @param msg Texto del error a mostrar.
	 */
	public void mostrarError(String msg) {
		lblError.setText(msg);
		lblExito.setText(" ");
	}

	/**
	 * Muestra un mensaje de éxito en el label de feedback y limpia el de error.
	 *
	 * @param msg Texto del éxito a mostrar.
	 */
	public void mostrarExito(String msg) {
		lblExito.setText(msg);
		lblError.setText(" ");
	}

	/**
	 * Limpia los labels de feedback de error y éxito dejándolos en blanco.
	 */
	public void limpiarFeedback() {
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	/**
	 * Restablece todos los campos del formulario a su estado inicial, vacía los
	 * labels de feedback y oculta el panel de modificación.
	 */
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
	 * Valida los campos de búsqueda (nombre y mililitros) y devuelve los datos
	 * listos para consultar a la BBDD. Si alguna validación falla, muestra el
	 * mensaje en {@code lblResultado} y devuelve {@code null}.
	 *
	 * @return Array donde el índice 0 contiene el nombre ({@link String}) y el
	 *         índice 1 los mililitros ({@link Integer}), o {@code null} si la
	 *         validación falla.
	 */
	public Object[] obtenerDatosBusqueda() {
		String nombre = txtBuscarNombre.getText().trim();
		String mlTexto = txtBuscarMl.getText().trim();

		if (nombre.isEmpty() || mlTexto.isEmpty()) {
			lblResultado.setText("Por favor, introduce nombre y mililitros.");
			lblResultado.setForeground(Tema.ERROR);
			return null;
		}

		int ml = 0;
		try {
			ml = Integer.parseInt(mlTexto);
			if (ml <= 0) {
				lblResultado.setText("Los mililitros deben ser mayores que 0.");
				lblResultado.setForeground(Tema.ERROR);
				return null;
			}
		} catch (NumberFormatException e) {
			lblResultado.setText("Los mililitros deben ser un número entero (sin letras).");
			lblResultado.setForeground(Tema.ERROR);
			return null;
		}

		return new Object[] { nombre, ml };
	}

	/**
	 * Indica si la última llamada a {@link #obtenerNuevoPrecio()} o a
	 * {@link #obtenerCantidadASumar(int)} encontró un error de validación.
	 * Permite al controlador distinguir un campo vacío (no modificar) de un
	 * error de formato (parar el proceso).
	 *
	 * @return {@code true} si la última validación falló, {@code false} en
	 *         caso contrario.
	 */
	public boolean tieneError() {
		return tieneError;
	}

	/**
	 * Valida el campo de nuevo precio y lo devuelve como {@link Double}.
	 * Devuelve {@code null} tanto si el campo está vacío (no se quiere
	 * modificar) como si hay un error de formato; el controlador debe usar
	 * {@link #tieneError()} para distinguir entre ambos casos.
	 *
	 * @return Nuevo precio si es válido, o {@code null} si el campo está
	 *         vacío o la validación falla.
	 */
	public Double obtenerNuevoPrecio() {
		tieneError = false;
		String precioTexto = txtNuevoPrecio.getText().trim();

		if (precioTexto.isEmpty()) {
			return null;
		}

		try {
			double nuevoPrecio = Double.parseDouble(precioTexto);
			if (nuevoPrecio <= 0) {
				lblResultado.setText("El nuevo precio debe ser mayor que 0.");
				lblResultado.setForeground(Tema.ERROR);
				tieneError = true;
				return null;
			}
			return nuevoPrecio;
		} catch (NumberFormatException e) {
			lblResultado.setText("El precio debe ser un número válido.");
			lblResultado.setForeground(Tema.ERROR);
			tieneError = true;
			return null;
		}
	}

	/**
	 * Valida el campo de cantidad a sumar (puede ser negativa para restar
	 * stock) y lo devuelve como {@link Integer}. Verifica además que el stock
	 * resultante (actual + cantidad) no sea negativo. Devuelve {@code null}
	 * tanto si el campo está vacío como si hay un error de formato; el
	 * controlador debe usar {@link #tieneError()} para distinguir entre ambos.
	 *
	 * @param stockActual Stock actual del perfume, usado para impedir que el
	 *                    stock resultante quede en negativo.
	 * @return Cantidad a sumar al stock si es válida, o {@code null} si el
	 *         campo está vacío o la validación falla.
	 */
	public Integer obtenerCantidadASumar(int stockActual) {
		tieneError = false;
		String cantidadTexto = txtCantidadAnadir.getText().trim();

		if (cantidadTexto.isEmpty()) {
			return null;
		}

		try {
			int aSumar = Integer.parseInt(cantidadTexto);
			if (stockActual + aSumar < 0) {
				lblResultado.setText("El stock final no puede ser negativo (Actual: " + stockActual + ").");
				lblResultado.setForeground(Tema.ERROR);
				tieneError = true;
				return null;
			}
			return aSumar;
		} catch (NumberFormatException e) {
			lblResultado.setText("La cantidad debe ser un número entero.");
			lblResultado.setForeground(Tema.ERROR);
			tieneError = true;
			return null;
		}
	}

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

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnBuscar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
	}
}