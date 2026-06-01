package com.goldentale.vistaPrincipalLyR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VRegistroUsuario extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JLabel lblSubtitulo;
	private JPanel panelFormulario;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblApellido;
	private JTextField txtApellido;
	private JLabel lblDireccion;
	private JTextField txtDireccion;
	private JLabel lblTelefono;
	private JTextField txtTelefono;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JLabel lblConfirmarPassword;
	private JPasswordField txtConfirmarPassword;
	private JButton btnMostrarPassword;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnRegistrar;
	private JButton btnLimpiar;

	public VRegistroUsuario() {
		super(Constantes.TITULO_APLICACION + " - Registro");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(620, 700);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Tema.FONDO);
		setLayout(new BorderLayout());

		btnVolver = ComponentesUI.botonSecundario("Volver");
		btnVolver.setPreferredSize(new Dimension(100, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnVolver);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel fondo = new JPanel(new GridBagLayout());
		fondo.setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(500, 570));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

		lblTitulo = new JLabel("Registro de cliente");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);

		lblSubtitulo = new JLabel("Crea tu cuenta para comprar perfumes");
		lblSubtitulo.setFont(Tema.fuenteNormal(12));
		lblSubtitulo.setForeground(Tema.TEXTO_CLARO);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblSubtitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		panelFormulario = new JPanel(new GridLayout(0, 2, 14, 10));
		panelFormulario.setOpaque(false);

		lblId = ComponentesUI.etiquetaFormulario("ID");
		txtId = ComponentesUI.campoTexto("C003");
		lblNombre = ComponentesUI.etiquetaFormulario("Nombre");
		txtNombre = ComponentesUI.campoTexto("Nombre");
		lblApellido = ComponentesUI.etiquetaFormulario("Apellido");
		txtApellido = ComponentesUI.campoTexto("Apellido");
		lblDireccion = ComponentesUI.etiquetaFormulario("Direccion");
		txtDireccion = ComponentesUI.campoTexto("Calle y numero");
		lblTelefono = ComponentesUI.etiquetaFormulario("Telefono");
		txtTelefono = ComponentesUI.campoTexto("600000000");
		lblEmail = ComponentesUI.etiquetaFormulario("Email");
		txtEmail = ComponentesUI.campoTexto("correo@email.com");
		lblPassword = ComponentesUI.etiquetaFormulario("Contrasena");
		txtPassword = ComponentesUI.campoPassword();
		lblConfirmarPassword = ComponentesUI.etiquetaFormulario("Confirmar contrasena");
		txtConfirmarPassword = ComponentesUI.campoPassword();

		panelFormulario.add(lblId);
		panelFormulario.add(txtId);
		panelFormulario.add(lblNombre);
		panelFormulario.add(txtNombre);
		panelFormulario.add(lblApellido);
		panelFormulario.add(txtApellido);
		panelFormulario.add(lblDireccion);
		panelFormulario.add(txtDireccion);
		panelFormulario.add(lblTelefono);
		panelFormulario.add(txtTelefono);
		panelFormulario.add(lblEmail);
		panelFormulario.add(txtEmail);
		panelFormulario.add(lblPassword);
		panelFormulario.add(txtPassword);
		panelFormulario.add(lblConfirmarPassword);
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

		fondo.add(tarjeta);
		add(new JScrollPane(fondo), BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JTextField getTxtId() {
		return txtId;
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
