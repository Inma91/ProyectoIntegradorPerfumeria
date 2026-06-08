package com.goldentale.vistaPrincipalLyR;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de registro de nuevo cliente. Solicita los datos personales, valida
 * internamente que cumplen los requisitos (campos no vacíos, formato de email,
 * nombre y apellido con letras válidas y teléfono numérico) y construye un
 * objeto {@link Usuario} listo para enviar al DAO. El ID del usuario lo
 * asigna la BBDD por AUTOINCREMENT.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 * @see Usuario
 */
public class VRegistroUsuario extends JPanel {

	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmarPassword;
	private JButton btnMostrarPassword;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnRegistrar;
	private JButton btnLimpiar;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
	public VRegistroUsuario() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(500, 530));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

		JLabel lblTitulo = new JLabel("Registro de cliente");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);

		JLabel lblSubtitulo = new JLabel("Crea tu cuenta para comprar perfumes");
		lblSubtitulo.setFont(Tema.fuenteNormal(12));
		lblSubtitulo.setForeground(Tema.TEXTO_CLARO);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblSubtitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 14, 10));
		panelFormulario.setOpaque(false);

		txtNombre = ComponentesUI.campoTexto("Nombre");
		txtApellido = ComponentesUI.campoTexto("Apellido");
		txtDireccion = ComponentesUI.campoTexto("Calle y número");
		txtTelefono = ComponentesUI.campoTexto("600000000");
		txtEmail = ComponentesUI.campoTexto("correo@email.com");
		txtPassword = ComponentesUI.campoPassword();
		txtConfirmarPassword = ComponentesUI.campoPassword();

		panelFormulario.add(ComponentesUI.etiquetaFormulario("Nombre"));
		panelFormulario.add(txtNombre);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Apellido"));
		panelFormulario.add(txtApellido);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Dirección"));
		panelFormulario.add(txtDireccion);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Teléfono"));
		panelFormulario.add(txtTelefono);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Email"));
		panelFormulario.add(txtEmail);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Contraseña"));
		panelFormulario.add(txtPassword);
		panelFormulario.add(ComponentesUI.etiquetaFormulario("Confirmar contraseña"));
		panelFormulario.add(txtConfirmarPassword);

		tarjeta.add(panelFormulario);
		tarjeta.add(Box.createVerticalStrut(12));

		JPanel filaMostrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		filaMostrar.setOpaque(false);
		btnMostrarPassword = ComponentesUI.botonSecundario("Mostrar");
		btnMostrarPassword.setPreferredSize(new Dimension(95, 30));
		filaMostrar.add(btnMostrarPassword);
		tarjeta.add(filaMostrar);

		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		lblError.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblError);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		lblExito.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblExito);
		tarjeta.add(Box.createVerticalStrut(10));

		JPanel filaBotones = new JPanel(new GridLayout(1, 2, 10, 0));
		filaBotones.setOpaque(false);
		filaBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnLimpiar = ComponentesUI.botonSecundario("Limpiar");
		btnRegistrar = ComponentesUI.botonPrincipal("Registrar");
		filaBotones.add(btnLimpiar);
		filaBotones.add(btnRegistrar);
		tarjeta.add(filaBotones);

		add(new JScrollPane(tarjeta) {
			{
				setBorder(null);
				setOpaque(false);
				getViewport().setOpaque(false);
			}
		});
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
	 * Restablece todos los campos del formulario a su estado inicial y limpia
	 * los labels de feedback.
	 */
	public void limpiarFormulario() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtPassword.setText("");
		txtConfirmarPassword.setText("");
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	/**
	 * Lee los campos del formulario, valida que cumplen los requisitos y
	 * construye un objeto {@link Usuario} listo para enviar al DAO. El ID se
	 * asigna a {@code 0} porque lo genera la BBDD por AUTOINCREMENT, y el rol
	 * se establece a {@link Constantes#ROL_CLIENTE}. Si alguna validación
	 * falla, muestra el motivo en el label de error y devuelve {@code null}.
	 *
	 * @return Objeto {@link Usuario} con los datos del formulario, o
	 *         {@code null} si hay errores de validación.
	 */
	public Usuario obtenerDatos() {
		int id = 0;
		String nombre = txtNombre.getText().trim();
		String apellido = txtApellido.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String email = txtEmail.getText().trim();
		String password = new String(txtPassword.getPassword()).trim();
		String rol = Constantes.ROL_CLIENTE;

		String error = validar(nombre, apellido, direccion, telefono, email, password);
		if (error != null) {
			mostrarError(error);
			return null;
		}

		return new Usuario(id, nombre, apellido, direccion, telefono, email, password, rol);
	}

	/**
	 * Comprueba que los datos del formulario son válidos: campos no vacíos,
	 * formato de email correcto, nombre y apellido con caracteres válidos y
	 * teléfono numérico con un opcional {@code +} al inicio.
	 *
	 * @param nombre    Nombre del usuario.
	 * @param apellido  Apellido del usuario.
	 * @param direccion Dirección del usuario.
	 * @param telefono  Teléfono del usuario.
	 * @param email     Correo electrónico del usuario.
	 * @param password  Contraseña del usuario.
	 * @return {@code null} si todo es válido, o un mensaje de error
	 *         describiendo el primer problema encontrado.
	 */
	public String validar(String nombre, String apellido, String direccion, String telefono, String email,
			String password) {

		if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()
				|| password.isEmpty()) {
			return "Todos los campos son obligatorios.";
		}

		if (!emailEsValido(email)) {
			return "El formato del correo electrónico no es válido.";
		}

		if (!nombreEsValido(nombre)) {
			return "El nombre solo puede contener letras, espacios o guiones.";
		}
		if (!nombreEsValido(apellido)) {
			return "El apellido solo puede contener letras, espacios o guiones.";
		}

		if (!telefonoEsValido(telefono)) {
			return "El teléfono solo puede contener números y opcionalmente un + al inicio.";
		}

		return null;
	}

	/**
	 * Comprueba que el email tiene un formato básico válido: contiene una
	 * sola arroba, hay texto antes de ella y un punto en algún lugar
	 * intermedio del dominio.
	 *
	 * @param email Cadena con el email a validar.
	 * @return {@code true} si el formato es válido, {@code false} en caso
	 *         contrario.
	 */
	private boolean emailEsValido(String email) {
		int posArroba = email.indexOf('@');
		int ultimaArroba = email.lastIndexOf('@');
		if (posArroba == -1 || posArroba != ultimaArroba) {
			return false;
		}

		String antes = email.substring(0, posArroba);
		if (antes.isEmpty()) {
			return false;
		}

		String despues = email.substring(posArroba + 1);
		int posPunto = despues.indexOf('.');
		if (posPunto == -1) {
			return false;
		}
		if (posPunto == 0 || posPunto == despues.length() - 1) {
			return false;
		}

		return true;
	}

	/**
	 * Comprueba carácter a carácter que el nombre o apellido solo contiene
	 * letras (mayúsculas y minúsculas), acentos comunes, ñ, espacios y
	 * guiones.
	 *
	 * @param nombre Cadena a validar.
	 * @return {@code true} si todos los caracteres son válidos, {@code false}
	 *         en cuanto encuentre uno no permitido.
	 */
	private boolean nombreEsValido(String nombre) {
		for (int i = 0; i < nombre.length(); i++) {
			char c = nombre.charAt(i);

			boolean esLetra = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
			boolean esEspacioOGuion = (c == ' ' || c == '-');
			boolean esLetraEspecial = "áéíóúÁÉÍÓÚñÑüÜàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛ".indexOf(c) != -1;

			if (!esLetra && !esEspacioOGuion && !esLetraEspecial) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Comprueba que el teléfono solo contiene dígitos, admitiendo un
	 * carácter {@code +} opcional al inicio (para prefijos internacionales).
	 *
	 * @param telefono Cadena a validar.
	 * @return {@code true} si el teléfono es válido, {@code false} en caso
	 *         contrario.
	 */
	private boolean telefonoEsValido(String telefono) {
		String parteNumerica = telefono;
		if (telefono.startsWith("+")) {
			parteNumerica = telefono.substring(1);
		}

		if (parteNumerica.isEmpty()) {
			return false;
		}

		for (int i = 0; i < parteNumerica.length(); i++) {
			char c = parteNumerica.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnRegistrar.addActionListener(controlador);
		btnLimpiar.addActionListener(controlador);
		btnMostrarPassword.addActionListener(controlador);
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public JTextField getTxtDireccion() {
		return txtDireccion;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	public JPasswordField getTxtConfirmarPassword() {
		return txtConfirmarPassword;
	}

	public JButton getBtnMostrarPassword() {
		return btnMostrarPassword;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public JLabel getLblExito() {
		return lblExito;
	}

	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}

	public JButton getBtnLimpiar() {
		return btnLimpiar;
	}
}