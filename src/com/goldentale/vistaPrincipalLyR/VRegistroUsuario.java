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
 * Panel de registro de nuevo cliente. El ID es autoincrement en BD, no se pide
 * al usuario.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
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

		// ── Títulos ───────────────────────────────────────────────────
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

		// ── Formulario ────────────────────────────────────────────────
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

		// ── Mostrar contraseña ────────────────────────────────────────
		JPanel filaMostrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		filaMostrar.setOpaque(false);
		btnMostrarPassword = ComponentesUI.botonSecundario("Mostrar");
		btnMostrarPassword.setPreferredSize(new Dimension(95, 30));
		filaMostrar.add(btnMostrarPassword);
		tarjeta.add(filaMostrar);

		// ── Feedback ──────────────────────────────────────────────────
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

		// ── Botones ───────────────────────────────────────────────────
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

	// ── Métodos de ayuda ──────────────────────────────────────────────

	public void mostrarError(String msg) {
		lblError.setText(msg);
		lblExito.setText(" ");
	}

	public void mostrarExito(String msg) {
		lblExito.setText(msg);
		lblError.setText(" ");
	}

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
	 * Lee los campos del formulario, valida que cumplen los requisitos y construye
	 * un objeto Usuario listo para enviar al DAO.
	 *
	 * @return Usuario si todo es válido, null si hay algún error. En caso de null,
	 *         el motivo se muestra en lblError.
	 */
	public Usuario obtenerDatos() {
		int id = 0; // lo asigna la BBDD (AUTOINCREMENT)
		String nombre = txtNombre.getText().trim();
		String apellido = txtApellido.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String email = txtEmail.getText().trim();
		String password = new String(txtPassword.getPassword()).trim();
		String rol = Constantes.ROL_CLIENTE;

		// Validar antes de construir el Usuario
		String error = validar(nombre, apellido, direccion, telefono, email, password);
		if (error != null) {
			mostrarError(error);
			return null;
		}

		return new Usuario(id, nombre, apellido, direccion, telefono, email, password, rol);
	}

	/**
	 * Comprueba que los datos del formulario son válidos.
	 *
	 * @return null si todo está bien, o un mensaje de error si algo falla.
	 */
	private String validar(String nombre, String apellido, String direccion, String telefono, String email,
			String password) {

		// Campos no vacíos
		if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()
				|| password.isEmpty()) {
			return "Todos los campos son obligatorios.";
		}

		// Formato de email
		if (!emailEsValido(email)) {
			return "El formato del correo electrónico no es válido.";
		}

		// Nombre y apellido: solo letras, espacios y guiones
		if (!nombreEsValido(nombre)) {
			return "El nombre solo puede contener letras, espacios o guiones.";
		}
		if (!nombreEsValido(apellido)) {
			return "El apellido solo puede contener letras, espacios o guiones.";
		}

		// Teléfono: solo dígitos y opcionalmente un + al principio
		if (!telefonoEsValido(telefono)) {
			return "El teléfono solo puede contener números y opcionalmente un + al inicio.";
		}

		return null;
	}

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

	private boolean nombreEsValido(String nombre) {
		// Permite letras (mayúsculas y minúsculas), acentos, ñ, espacios y guiones
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

	private boolean telefonoEsValido(String telefono) {
		// Si empieza con +, validamos el resto del string
		String parteNumerica = telefono;
		if (telefono.startsWith("+")) {
			parteNumerica = telefono.substring(1);
		}

		// Tiene que haber al menos un dígito después del +
		if (parteNumerica.isEmpty()) {
			return false;
		}

		// Todo el resto deben ser dígitos
		for (int i = 0; i < parteNumerica.length(); i++) {
			char c = parteNumerica.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}

		return true;
	}

	public void setControlador(Controlador controlador) {
		btnRegistrar.addActionListener(controlador);
		btnLimpiar.addActionListener(controlador);
		btnMostrarPassword.addActionListener(controlador);
	}

	// ── Getters ───────────────────────────────────────────────────────
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