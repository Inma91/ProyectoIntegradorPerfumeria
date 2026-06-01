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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VLogin extends JFrame {

	private JPanel panelCabecera;
	private JLabel lblNombreApp;
	private JLabel lblSubtitulo;
	private JLabel lblRol;
	private JPanel panelSelectorRol;
	private JButton btnRolCliente;
	private JButton btnRolEmpleado;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JButton btnMostrarPassword;
	private JButton btnIniciarSesion;
	private JButton btnRegistrarse;
	private JLabel lblError;
	private String rolSeleccionado;

	public VLogin() {
		super(Constantes.TITULO_APLICACION + " - Login");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		rolSeleccionado = Constantes.ROL_CLIENTE;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(430, 560);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Tema.FONDO);
		setLayout(new BorderLayout());

		panelCabecera = ComponentesUI.navbar(Constantes.TITULO_APLICACION, null);
		add(panelCabecera, BorderLayout.NORTH);

		JPanel fondo = new JPanel(new GridBagLayout());
		fondo.setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(340, 440));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(28, 38, 24, 38));

		lblNombreApp = new JLabel(Constantes.TITULO_APLICACION);
		lblNombreApp.setFont(Tema.fuenteNegrita(24));
		lblNombreApp.setForeground(Tema.TEXTO_OSCURO);
		lblNombreApp.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblNombreApp);

		lblSubtitulo = new JLabel("Sistema de perfumeria");
		lblSubtitulo.setFont(Tema.fuenteNormal(12));
		lblSubtitulo.setForeground(Tema.TEXTO_CLARO);
		lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblSubtitulo);
		tarjeta.add(Box.createVerticalStrut(22));

		lblRol = ComponentesUI.etiquetaFormulario("Tipo de acceso");
		lblRol.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblRol);
		tarjeta.add(Box.createVerticalStrut(6));

		panelSelectorRol = new JPanel(new GridLayout(1, 2, 8, 0));
		panelSelectorRol.setOpaque(false);
		panelSelectorRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
		btnRolCliente = ComponentesUI.botonSecundario("Cliente");
		btnRolEmpleado = ComponentesUI.botonSecundario("Empleado");
		panelSelectorRol.add(btnRolCliente);
		panelSelectorRol.add(btnRolEmpleado);
		tarjeta.add(panelSelectorRol);
		tarjeta.add(Box.createVerticalStrut(18));

		lblId = ComponentesUI.etiquetaFormulario("ID de usuario");
		lblId.setAlignmentX(LEFT_ALIGNMENT);
		tarjeta.add(lblId);
		tarjeta.add(Box.createVerticalStrut(4));

		txtId = ComponentesUI.campoTexto("Ej: C001 o E001");
		txtId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		tarjeta.add(txtId);
		tarjeta.add(Box.createVerticalStrut(12));

		lblPassword = ComponentesUI.etiquetaFormulario("Contrasena");
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

		btnIniciarSesion = ComponentesUI.botonPrincipal("Iniciar sesion");
		btnIniciarSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		tarjeta.add(btnIniciarSesion);
		tarjeta.add(Box.createVerticalStrut(10));

		btnRegistrarse = ComponentesUI.botonSecundario("Nuevo cliente");
		btnRegistrarse.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		tarjeta.add(btnRegistrarse);

		fondo.add(tarjeta);
		add(fondo, BorderLayout.CENTER);
	}

	public JPanel getPanelSelectorRol() {
		return panelSelectorRol;
	}

	public JButton getBtnRolCliente() {
		return btnRolCliente;
	}

	public JButton getBtnRolEmpleado() {
		return btnRolEmpleado;
	}

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

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public String getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(String rol) {
		this.rolSeleccionado = rol;
	}
}
