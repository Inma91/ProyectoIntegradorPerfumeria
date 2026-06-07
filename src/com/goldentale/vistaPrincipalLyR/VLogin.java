package com.goldentale.vistaPrincipalLyR;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;
import com.goldentale.model.data.Constantes;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de inicio de sesión de Golden Tale. Solicita correo y contraseña al
 * usuario; el rol (cliente o empleado) se determina automáticamente según el
 * usuario encontrado en la BBDD. Las validaciones de formato se realizan en
 * el controlador, esta vista solo expone los campos y los labels de error.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 */
public class VLogin extends JPanel {

	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnMostrarPassword;
	private JButton btnIniciarSesion;
	private JButton btnRegistrarse;
	private JLabel lblError;

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
	public VLogin() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(340, 380));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(28, 38, 24, 38));

		JLabel lblNombreApp = new JLabel(Constantes.TITULO_APLICACION);
		lblNombreApp.setFont(Tema.fuenteNegrita(24));
		lblNombreApp.setForeground(Tema.TEXTO_OSCURO);
		lblNombreApp.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblNombreApp);

		JLabel lblSubtitulo = new JLabel("Sistema de perfumería");
		lblSubtitulo.setFont(Tema.fuenteNormal(12));
		lblSubtitulo.setForeground(Tema.TEXTO_CLARO);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblSubtitulo);
		tarjeta.add(Box.createVerticalStrut(28));

		JLabel lblEmail = ComponentesUI.etiquetaFormulario("Correo electrónico");
		lblEmail.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblEmail);
		tarjeta.add(Box.createVerticalStrut(4));
		txtEmail = ComponentesUI.campoTexto("correo@goldentale.com");
		txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		tarjeta.add(txtEmail);
		tarjeta.add(Box.createVerticalStrut(14));

		JLabel lblPassword = ComponentesUI.etiquetaFormulario("Contraseña");
		lblPassword.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblPassword);
		tarjeta.add(Box.createVerticalStrut(4));
		txtPassword = ComponentesUI.campoPassword();
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		tarjeta.add(txtPassword);
		tarjeta.add(Box.createVerticalStrut(8));

		JPanel filaMostrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		filaMostrar.setOpaque(false);
		filaMostrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		btnMostrarPassword = ComponentesUI.botonSecundario("Mostrar");
		btnMostrarPassword.setPreferredSize(new Dimension(92, 28));
		filaMostrar.add(btnMostrarPassword);
		tarjeta.add(filaMostrar);
		tarjeta.add(Box.createVerticalStrut(14));

		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		lblError.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblError);
		tarjeta.add(Box.createVerticalStrut(8));

		btnIniciarSesion = ComponentesUI.botonPrincipal("Iniciar sesión");
		btnIniciarSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		tarjeta.add(btnIniciarSesion);
		tarjeta.add(Box.createVerticalStrut(10));

		btnRegistrarse = ComponentesUI.botonSecundario("¿No tienes cuenta? Regístrate");
		btnRegistrarse.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		tarjeta.add(btnRegistrarse);

		add(tarjeta);
	}

	/**
	 * Muestra un mensaje de error en el label de feedback.
	 *
	 * @param msg Texto del error a mostrar.
	 */
	public void mostrarError(String msg) {
		lblError.setText(msg);
	}

	/**
	 * Limpia el label de error dejándolo en blanco.
	 */
	public void limpiarError() {
		lblError.setText(" ");
	}

	/**
	 * Vacía los campos de correo y contraseña y limpia el label de error.
	 */
	public void limpiarFormulario() {
		txtEmail.setText("");
		txtPassword.setText("");
		lblError.setText(" ");
	}

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnIniciarSesion.addActionListener(controlador);
		btnRegistrarse.addActionListener(controlador);
		btnMostrarPassword.addActionListener(controlador);
	}

	public JTextField getTxtEmail() {
		return txtEmail;
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

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public JLabel getLblError() {
		return lblError;
	}
}