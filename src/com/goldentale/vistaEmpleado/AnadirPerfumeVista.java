package com.goldentale.vistaEmpleado;

import javax.swing.*;
import com.goldentale.controlador.Controlador;
import com.goldentale.model.db.Perfumes;
import com.goldentale.model.data.Constantes;
import java.awt.*;

/**
 * Panel Añadir Perfume — JPanel cargado desde EmpleadoDashboardVista.
 *
 * Formulario para dar de alta un nuevo perfume en el catálogo. Al guardar, el
 * controlador llama a PerfumeDAO.save() y luego a StockDAO.save() con la
 * localización calculada por LocalizacionUtil.
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */

public class AnadirPerfumeVista extends JPanel {

	// CONSTANTES
	public static final String GUARDAR = "Guardar Perfume";
	public static final String LIMPIAR = "Limpiar Datos";
	public static final String VOLVER = "Volver";

	// ── Navbar ────────────────────────────────────────────────────────
	private JLabel lblNombreApp;
	private JButton btnVolver;

	// ── Título ────────────────────────────────────────────────────────
	private JLabel lblTitulo;

	// ── Formulario ────────────────────────────────────────────────────
	private JLabel lblNombre;
	/** Nombre comercial del perfume. Campo obligatorio. */
	private JTextField txtNombre;

	private JLabel lblMarca;
	/** Marca o casa de perfumería. Campo obligatorio. */
	private JTextField txtMarca;

	private JLabel lblCategoria;
	/**
	 * Categoría olfativa. Valores: Constantes.CATEGORIAS_PERFUME Equivalente en
	 * JavaFX: ComboBox<String>.
	 */
	private JComboBox<String> comboCategoria;

	private JLabel lblPrecio;
	/** Precio en euros. Validar que sea numérico positivo. */
	private JTextField txtPrecio;

	private JLabel lblMl;
	/** Volumen del frasco en mililitros. */
	private JTextField txtMl;

	private JLabel lblPublico;
	/**
	 * Público objetivo. Valores: Constantes.PUBLICOS_OBJETIVO Al cambiar, actualiza
	 * lblLocalizacion con LocalizacionUtil.asignar().
	 */
	private JComboBox<String> comboPublico;
	private DefaultComboBoxModel<String> dcbmPublico;

	private JLabel lblLocalizacionTitulo;
	/**
	 * Muestra la localización asignada automáticamente según los ml. Se actualiza
	 * al introducir los ml en txtMl. ml <= 50 → Estante A | ml == 75 → Estante B |
	 * ml >= 100 → Estante C
	 */

	private JLabel lblLocalizacion;

	private JLabel lblStock;
	/** Unidades iniciales a registrar en almacén. */
	private JTextField txtStock;

	private JLabel lblDescripcion;
	/**
	 * Notas olfativas y descripción del producto. Campo opcional. Equivalente en
	 * JavaFX: TextArea.
	 */
	private JTextArea txtDescripcion;
	private JScrollPane scrollDescripcion;

	// ── Feedback ──────────────────────────────────────────────────────
	/**
	 * Etiqueta de error — invisible por defecto. Se muestra si hay campos
	 * obligatorios vacíos o datos inválidos.
	 */
	private JLabel lblError;

	/**
	 * Etiqueta de éxito — invisible por defecto. Se muestra en verde cuando el
	 * perfume se guarda correctamente.
	 */
	private JLabel lblExito;

	// ── Botones de acción ─────────────────────────────────────────────
	/**
	 * Guarda el perfume. El controlador valida los campos, llama a
	 * PerfumeDAO.save() y luego a StockDAO.save() con la localización automática.
	 */
	private JButton btnGuardar;

	/**
	 * Limpia todos los campos del formulario. También resetea lblLocalizacion al
	 * texto por defecto.
	 */
	private JButton btnLimpiar;
	private DefaultComboBoxModel<String> dcbmCategoria;

	// CONSTRUCTOR
	public AnadirPerfumeVista() {
		crearComponentes();
	}

	public void crearComponentes() {
		setLayout(null);

		// "NAVBAR"
		lblNombreApp = new JLabel("Golden Tale");
		lblNombreApp.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreApp.setBounds(15, 10, 150, 20);
		add(lblNombreApp);

		btnVolver = new JButton(VOLVER);
		btnVolver.setBounds(650, 7, 100, 26);
		add(btnVolver);

		// TÍTULO PARA AÑADIR PERFUME
		lblTitulo = new JLabel("Añadir Perfume");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(15, 45, 200, 25);
		add(lblTitulo);

		// NOMBRE DEL PERFUME (ETIQUETA Y TEXTO)
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(15, 90, 80, 20);
		add(lblNombre);
		txtNombre = new JTextField();
		txtNombre.setBounds(120, 87, 250, 26);
		txtNombre.setColumns(10);
		add(txtNombre);

		// MARCA DEL PERFUME (ETIQUETA Y TEXTO)
		lblMarca = new JLabel("Marca");
		lblMarca.setBounds(15, 130, 80, 20);
		add(lblMarca);
		txtMarca = new JTextField();
		txtMarca.setBounds(120, 127, 250, 26);
		txtMarca.setColumns(10);
		add(txtMarca);

		// CATEGORÍA DEL PERFUME (ETIQUETA Y COMBO)
		lblCategoria = new JLabel("Categoría");
		lblCategoria.setBounds(15, 170, 80, 20);
		add(lblCategoria);
		comboCategoria = new JComboBox<String>();
		dcbmCategoria = new DefaultComboBoxModel<String>();
		for (String cat : Constantes.CATEGORIAS_PERFUME) {
			dcbmCategoria.addElement(cat);
		}
		comboCategoria.setModel(dcbmCategoria);
		comboCategoria.setBounds(120, 167, 250, 26);
		add(comboCategoria);

		// PRECIO DEL PERFUME (ETIQUETA Y TEXTO)
		lblPrecio = new JLabel("Precio (€)");
		lblPrecio.setBounds(15, 210, 80, 20);
		add(lblPrecio);
		txtPrecio = new JTextField();
		txtPrecio.setBounds(120, 207, 100, 26);
		txtPrecio.setColumns(10);
		add(txtPrecio);

		// ML MILILITROS (ETIQUETA Y TEXTO)
		lblMl = new JLabel("Volumen (ml)");
		lblMl.setBounds(250, 210, 100, 20);
		add(lblMl);
		txtMl = new JTextField();
		txtMl.setBounds(360, 207, 100, 26);
		txtMl.setColumns(10);
		add(txtMl);

		// LOCALIZACIÓN ETIQUETAS
		lblLocalizacionTitulo = new JLabel("Localización:");
		lblLocalizacionTitulo.setBounds(15, 330, 100, 20);
		add(lblLocalizacionTitulo);

		lblLocalizacion = new JLabel("—");
		lblLocalizacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLocalizacion.setBounds(120, 330, 200, 20);
		add(lblLocalizacion);

		// STOCK (ETIQUETA Y TEXTO)
		lblStock = new JLabel("Stock inicial");
		lblStock.setBounds(15, 250, 100, 20);
		add(lblStock);
		txtStock = new JTextField();
		txtStock.setBounds(120, 247, 100, 26);
		txtStock.setColumns(10);
		add(txtStock);

		// PÚBLICO OBJETIVO (ETIQUETA Y COMBO)
		lblPublico = new JLabel("Público");
		lblPublico.setBounds(15, 290, 80, 20);
		add(lblPublico);
		comboPublico = new JComboBox<String>();
		dcbmPublico = new DefaultComboBoxModel<String>();
		for (String pub : Constantes.PUBLICOS_OBJETIVO) {
			dcbmPublico.addElement(pub);
		}
		comboPublico.setModel(dcbmPublico);
		comboPublico.setBounds(120, 287, 250, 26);
		add(comboPublico);

		// DESCRIPCIÓN DEL PERFUME (ETIQUETA Y CUADRO DE TEXTO "GRANDE")
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setBounds(15, 370, 100, 20);
		add(lblDescripcion);
		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setBounds(120, 367, 380, 70);
		add(scrollDescripcion);

		// ETIQUETAS DE ERROR Y ÉXITO
		lblError = new JLabel("Por favor, revisa los campos obligatorios");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblError.setBounds(15, 455, 350, 20);
		lblError.setVisible(false);
		add(lblError);

		lblExito = new JLabel("Perfume guardado correctamente");
		lblExito.setForeground(new Color(0, 150, 0));
		lblExito.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblExito.setBounds(15, 455, 350, 30);
		lblExito.setVisible(false);
		add(lblExito);

		// BOTONES LIMPIAR Y GUARDAR
		btnLimpiar = new JButton(LIMPIAR);
		btnLimpiar.setBounds(270, 485, 150, 30);
		add(btnLimpiar);

		btnGuardar = new JButton(GUARDAR);
		btnGuardar.setBounds(100, 485, 150, 30);
		add(btnGuardar);

	}

	// SET CONTROLADOR
	public void setControlador(Controlador control) {
		btnGuardar.addActionListener(control);
		btnLimpiar.addActionListener(control);
		btnVolver.addActionListener(control);
	}

	// MÉTODO PARA LIMPIAR LOS DATOS
	public void limpiarDatos() {
		txtNombre.setText("");
		txtMarca.setText("");
		comboCategoria.setSelectedIndex(0);
		txtPrecio.setText("");
		txtMl.setText("");
		lblLocalizacion.setText("-");
		txtStock.setText("");
		comboPublico.setSelectedIndex(0);
		txtDescripcion.setText("");
		lblError.setVisible(false);
		lblExito.setVisible(false);
	}

	// MÉTODO PARA OBTENER LOS DATOS INTRODUCCIDOS
	public Perfumes obtenerDatos() {
		Perfumes nuevoPerfume = null;

		// Nombre
		String nombre = txtNombre.getText();
		if (nombre.isEmpty()) {
			mostrarError("El nombre no puede estar vacío");
			return null;
		}

		// Marca
		String marca = txtMarca.getText();
		if (marca.isEmpty()) {
			mostrarError("La marca no puede estar vacía");
			return null;
		}

		// Categoría
		String categoria = (String) comboCategoria.getSelectedItem();

		// Precio y comprobación
		String precioTxt = txtPrecio.getText();
		if (precioTxt.isEmpty()) {
			mostrarError("El precio no puede estar vacío");
			return null;
		}
		double precio;
		try {
			precio = Double.parseDouble(precioTxt);
		} catch (Exception e) {
			mostrarError("El precio debe ser un número");
			return null;
		}

		// Ml y comprobación
		String mlTexto = txtMl.getText();
		int ml = 0;
		if (mlTexto.isEmpty()) {
			mostrarError("El volumen no puede estar vacío");
			return null;
		}
		try {
			ml = Integer.parseInt(mlTexto);
		} catch (Exception e) {
			mostrarError("El volumen debe ser un número entero");
			return null;
		}

		// Localización calculada según ml
		String localizacion;

		if (ml <= 50) {
			localizacion = "Estante A";
		} else if (ml == 75) {
			localizacion = "Estante B";
		} else {
			localizacion = "Estante C";
		}

		// Stock
		String stockTexto = txtStock.getText();

		if (stockTexto.isEmpty()) {
			mostrarError("El stock no puede estar vacío");
			return null;
		}
		
		int stock;
		try {
			stock = Integer.parseInt(stockTexto);
		} catch (Exception e) {
			mostrarError("El stock debe ser un número entero");
			return null;
		}

		// Publico objetivo
		String publico = (String) comboPublico.getSelectedItem();

		// Descripción
		String descripcion = txtDescripcion.getText();

		// Nuevo perfume
		nuevoPerfume = new Perfumes(null, nombre, marca, categoria, descripcion, precio, ml, publico, localizacion, stock);

		return nuevoPerfume;

	}

	// MÉTODO PARA LOS ERRORES AL INTRODUCIR LOS DATOS
	private void mostrarError(String mensaje) {
		lblError.setText(mensaje);
		lblError.setVisible(true);
		lblExito.setVisible(false);
	}

	// ── Getters ───────────────────────────────────────────────────────
	public JButton getBtnVolver() {
		return btnVolver;
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

	public JLabel getlblLocalizacion() {
		return lblLocalizacion;
	}
	
	public JTextField getTxtStock() {
		return txtStock;
	}
	
	public JComboBox<String> getComboPublico() {
		return comboPublico;
	}

	public JTextArea getTxtDescripcion() {
		return txtDescripcion;
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
}
