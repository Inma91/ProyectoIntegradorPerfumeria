package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.Perfumes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel del empleado para dar de alta un nuevo perfume. Presenta un formulario
 * con los campos del perfume y un label de localización que se asigna
 * automáticamente según los mililitros introducidos. Las validaciones de los
 * campos se realizan internamente en {@link #obtenerDatos()}.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Perfumes
 * @see Controlador
 */
public class VAniadirPerfume extends JPanel {

	private JTextField txtNombre;
	private JTextField txtMarca;
	private JComboBox<String> comboCategoria;
	private JTextField txtPrecio;
	private JTextField txtMl;
	private JComboBox<String> comboPublico;
	private JLabel lblLocalizacion;
	private JTextArea txtDescripcion;
	private JTextField txtStock;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnLimpiar;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
	public VAniadirPerfume() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(590, 580));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

		JLabel lblTitulo = new JLabel("Añadir nuevo perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		JPanel formulario = new JPanel(new GridLayout(0, 2, 14, 10));
		formulario.setOpaque(false);

		txtNombre = ComponentesUI.campoTexto("Velvet Rose");
		txtMarca = ComponentesUI.campoTexto("Maison Luxe");
		comboCategoria = new JComboBox<>(Constantes.CATEGORIAS_PERFUME);
		txtPrecio = ComponentesUI.campoTexto("89.99");
		txtMl = ComponentesUI.campoTexto("50");
		comboPublico = new JComboBox<>(Constantes.PUBLICOS_OBJETIVO);
		txtStock = ComponentesUI.campoTexto("12");

		formulario.add(ComponentesUI.etiquetaFormulario("Nombre"));
		formulario.add(txtNombre);
		formulario.add(ComponentesUI.etiquetaFormulario("Marca"));
		formulario.add(txtMarca);
		formulario.add(ComponentesUI.etiquetaFormulario("Categoría"));
		formulario.add(comboCategoria);
		formulario.add(ComponentesUI.etiquetaFormulario("Precio (€)"));
		formulario.add(txtPrecio);
		formulario.add(ComponentesUI.etiquetaFormulario("Mililitros"));
		formulario.add(txtMl);
		formulario.add(ComponentesUI.etiquetaFormulario("Público"));
		formulario.add(comboPublico);
		formulario.add(ComponentesUI.etiquetaFormulario("Stock inicial"));
		formulario.add(txtStock);

		tarjeta.add(formulario);
		tarjeta.add(Box.createVerticalStrut(14));

		JLabel lblLocTitulo = ComponentesUI.etiquetaFormulario("Localización automática");
		tarjeta.add(lblLocTitulo);
		tarjeta.add(Box.createVerticalStrut(4));

		lblLocalizacion = new JLabel(Constantes.LOC_PEQUENO + " — pendiente de calcular");
		lblLocalizacion.setFont(Tema.fuenteNegrita(13));
		lblLocalizacion.setForeground(Tema.MORADO);
		tarjeta.add(lblLocalizacion);
		tarjeta.add(Box.createVerticalStrut(12));

		tarjeta.add(ComponentesUI.etiquetaFormulario("Descripción"));
		tarjeta.add(Box.createVerticalStrut(4));
		txtDescripcion = ComponentesUI.areaTexto();
		JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
		scrollDesc.setPreferredSize(new Dimension(0, 80));
		scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		tarjeta.add(scrollDesc);
		tarjeta.add(Box.createVerticalStrut(10));

		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		tarjeta.add(lblError);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		tarjeta.add(lblExito);
		tarjeta.add(Box.createVerticalStrut(8));

		JPanel fila = new JPanel(new GridLayout(1, 2, 10, 0));
		fila.setOpaque(false);
		fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnLimpiar = ComponentesUI.botonSecundario("Limpiar");
		btnGuardar = ComponentesUI.botonPrincipal("Guardar");
		fila.add(btnLimpiar);
		fila.add(btnGuardar);
		tarjeta.add(fila);

		JScrollPane scroll = new JScrollPane(tarjeta);
		scroll.setBorder(null);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		add(scroll);
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
	 * Calcula y actualiza la etiqueta de localización según los mililitros
	 * introducidos en {@code txtMl}. Asigna {@link Constantes#LOC_PEQUENO},
	 * {@link Constantes#LOC_MEDIANO} o {@link Constantes#LOC_GRANDE} en función
	 * de los límites definidos. Si los mililitros no son un número válido,
	 * muestra un mensaje de aviso en el propio label.
	 */
	public void actualizarLocalizacion() {
		try {
			int ml = Integer.parseInt(txtMl.getText().trim());
			if (ml <= Constantes.ML_LIMITE_PEQUENO) {
				lblLocalizacion.setText(Constantes.LOC_PEQUENO);
			} else if (ml <= Constantes.ML_LIMITE_MEDIANO) {
				lblLocalizacion.setText(Constantes.LOC_MEDIANO);
			} else {
				lblLocalizacion.setText(Constantes.LOC_GRANDE);
			}
		} catch (NumberFormatException e) {
			lblLocalizacion.setText("— introduce mililitros válidos");
		}
	}

	/**
	 * Lee los campos del formulario, valida internamente nombre, marca, precio,
	 * mililitros y stock, y construye un objeto {@link Perfumes} listo para
	 * enviar al DAO. Si alguna validación falla, muestra el error en el label
	 * de feedback y devuelve {@code null}. El precio acepta coma o punto como
	 * separador decimal. El ID del perfume se asigna a {@code 0} porque lo
	 * genera la BBDD por AUTOINCREMENT.
	 *
	 * @return Objeto {@link Perfumes} con los datos del formulario, o
	 *         {@code null} si hay errores de validación.
	 */
	public Perfumes obtenerDatos() {
		mostrarError(" ");
		mostrarExito(" ");

		String nombre = txtNombre.getText().trim();
		if (nombre.isEmpty()) {
			mostrarError("El nombre del perfume no puede estar vacío.");
			return null;
		}

		String marca = txtMarca.getText().trim();
		if (marca.isEmpty()) {
			mostrarError("La marca no puede estar vacía.");
			return null;
		}

		String precioTxt = txtPrecio.getText().trim();
		String mlTxt = txtMl.getText().trim();
		String stockTxt = txtStock.getText().trim();

		if (precioTxt.isEmpty() || mlTxt.isEmpty() || stockTxt.isEmpty()) {
			mostrarError("Precio, mililitros y stock son campos obligatorios.");
			return null;
		}

		double precio = 0.0;
		int ml = 0;
		int stock = 0;

		try {
			precioTxt = precioTxt.replace(",", ".");
			precio = Double.parseDouble(precioTxt);
			if (precio <= 0) {
				mostrarError("Error en Precio: Debe ser un valor mayor que 0.");
				return null;
			}
		} catch (NumberFormatException e) {
			mostrarError("Error en Precio: No puede contener letras (usa el punto '.' para decimales).");
			return null;
		}

		try {
			ml = Integer.parseInt(mlTxt);
			if (ml <= 0) {
				mostrarError("Error en Mililitros: Deben ser mayores que 0.");
				return null;
			}
		} catch (NumberFormatException e) {
			mostrarError("Error en Mililitros: Debe ser un número entero sin letras.");
			return null;
		}

		try {
			stock = Integer.parseInt(stockTxt);
			if (stock < 0) {
				mostrarError("Error en Stock: El stock inicial no puede ser negativo.");
				return null;
			}
		} catch (NumberFormatException e) {
			mostrarError("Error en Stock: Debe ser un número entero sin letras.");
			return null;
		}

		String categoria = (String) comboCategoria.getSelectedItem();
		String descripcion = txtDescripcion.getText().trim();
		String publico = (String) comboPublico.getSelectedItem();

		int id = 0;

		return new Perfumes(id, nombre, marca, categoria, descripcion, precio, ml, publico);
	}

	/**
	 * Restablece todos los campos del formulario a su estado inicial, resetea
	 * los combos al primer elemento y limpia los labels de feedback.
	 */
	public void limpiarFormulario() {
		txtNombre.setText("");
		txtMarca.setText("");
		comboCategoria.setSelectedIndex(0);
		txtPrecio.setText("");
		txtMl.setText("");
		lblLocalizacion.setText(Constantes.LOC_PEQUENO + " — pendiente de calcular");
		txtStock.setText("");
		comboPublico.setSelectedIndex(0);
		txtDescripcion.setText("");
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public JTextField getTxtMarca() {
		return txtMarca;
	}

	public JComboBox<String> getComboCategoria() {
		return comboCategoria;
	}

	public JTextField getTxtPrecio() {
		return txtPrecio;
	}

	public JTextField getTxtMl() {
		return txtMl;
	}

	public JComboBox<String> getComboPublico() {
		return comboPublico;
	}

	public JLabel getLblLocalizacion() {
		return lblLocalizacion;
	}

	public JTextArea getTxtDescripcion() {
		return txtDescripcion;
	}

	public JTextField getTxtStock() {
		return txtStock;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public JLabel getLblExito() {
		return lblExito;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnLimpiar() {
		return btnLimpiar;
	}

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnGuardar.addActionListener(controlador);
		btnLimpiar.addActionListener(controlador);
	}
}