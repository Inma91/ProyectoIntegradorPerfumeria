package com.goldentale.vistaEmpleado;

import javax.swing.*;

import com.goldentale.controlador.Controlador;

import java.awt.*;

/**
 * Ventana de Login — JPanel que aparece en la vista principal tras haber dado al botón de ingresar en la aplicación.
 *
 * El usuario introduce su usuario y contraseña para acceder a la aplicación. Dependiendo de sus credenciales ingresará como 
 * cliente o como empleado y las vistas que se mostrarán serán diferenes.
 *
 * Equivalente futuro en JavaFX: login.fxml + LoginController.java
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class LoginVista extends JPanel {

	// CONSTANTES
	public static final String INICIO = "Iniciar sesión";

	// FIELD COMPONENTE

	// ── Cabecera ──────────────────────────────────────────────────────
	/** Etiqueta con el nombre de la aplicación "Golden Tale" */
	private JLabel lblNombreApp;

	/** Etiqueta con el subtítulo "Sistema de inventario" */
	private JLabel lblSubtitulo;
	
	
	// ── Campos del formulario ─────────────────────────────────────────
	/** Etiqueta del campo de identificador */
	private JLabel lblId;

	/**
	 * Campo de texto para el ID de usuario. El texto de ayuda cambia según el rol
	 * seleccionado. Cliente → "Ej: C001" | Empleado → "Ej: E001"
	 */
	private JTextField txtId;

	/** Etiqueta del campo de contraseña */
	private JLabel lblPassword;

	/**
	 * Campo de contraseña — oculta los caracteres con puntos. Equivalente en
	 * JavaFX: PasswordField.
	 */
	private JPasswordField txtPassword;

	/**
	 * Botón para mostrar u ocultar la contraseña. Alterna entre mostrar y ocultar
	 * los caracteres.
	 */
	private JButton btnMostrarPassword;

	// ── Acciones ──────────────────────────────────────────────────────
	/**
	 * Botón principal de inicio de sesión. El controlador valida contra ClienteDAO
	 * o EmpleadoDAO según rolSeleccionado, verifica el hash con
	 * HashUtil.verificar(), cierra este JFrame y abre la ventana correspondiente.
	 */
	private JButton btnIniciarSesion;

	// ── Feedback ──────────────────────────────────────────────────────
	/**
	 * Etiqueta de error — invisible por defecto (setVisible(false)). Se muestra en
	 * rojo cuando las credenciales son incorrectas o algún campo está vacío.
	 */
	private JLabel lblError;


	// CONSTRUCTOR
	public LoginVista() {
		crearComponentes();
	}

	// CONFIGURACIÓN DE LOS COMPONENTES
	
	public void crearComponentes() {
		setLayout(null);

		// ETIQUETA PARA EL TÍTULO
		lblNombreApp = new JLabel("Golden Tale");
		lblNombreApp.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombreApp.setBounds(140, 20, 200, 30);
		add(lblNombreApp);

		// ETIQUETA PARA EL SUBTÍTULO
		lblSubtitulo = new JLabel("Sistema de inventario");
		lblSubtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSubtitulo.setBounds(130, 50, 200, 20);
		add(lblSubtitulo);

		// ETIQUETA DEL USUARIO
		lblId = new JLabel("Usuario");
		lblId.setBounds(40, 162, 100, 20);
		add(lblId);

		// TEXTO PARA INTRODUCIR EL USUARIO
		txtId = new JTextField();
		txtId.setBounds(40, 182, 300, 26);
		txtId.setColumns(10);
		add(txtId);

		// ETIQUETA DE LA CONTRASEÑA
		lblPassword = new JLabel("Contraseña");
		lblPassword.setBounds(40, 220, 100, 20);
		add(lblPassword);

		// TEXTO "ESPECIAL" PARA INTRODUCIR LA CONTRASEÑA
		txtPassword = new JPasswordField();
		txtPassword.setBounds(40, 240, 260, 26);
		add(txtPassword);

		// BOTÓN PARA MOSTRAR LA CONTRASEÑA
		btnMostrarPassword = new JButton("ver");
		btnMostrarPassword.setBounds(308, 240, 32, 26);
		add(btnMostrarPassword);

		// ETIQUETA ERROR - invisible por defecto
		lblError = new JLabel("Usuario o contraseña incorrectos");
		lblError.setForeground(java.awt.Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblError.setBounds(40, 274, 280, 20);
		lblError.setVisible(false);
		add(lblError);

		// BOTÓN INICIAR SESIÓN
		btnIniciarSesion = new JButton("Iniciar sesión");
		btnIniciarSesion.setBounds(40, 298, 300, 30);
		add(btnIniciarSesion);

	}

	public void setControlador(Controlador control) {
		btnIniciarSesion.addActionListener(control);
	}


	// ── Getters ───────────────────────────────────────────────────────
	
	public JTextField getTxtId() {
		return txtId;
	}

	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	public JButton getBtnMostrarPassword() {
		return btnMostrarPassword;
	}

	public JButton getBtnIniciarSesion() {
		return btnIniciarSesion;
	}

	public JLabel getLblError() {
		return lblError;
	}

}
