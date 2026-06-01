package com.goldentale.vistaPrincipalLyR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

@SuppressWarnings("serial")
public class VPGoldenTale extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNavNombre;
	private JLabel lblNavEstado;

	private JPanel panelLateral;
	private JLabel lblMenuTitulo;
	private JButton btnLateralInicio;
	private JButton btnLateralLogin;
	private JButton btnLateralRegistro;

	private JPanel panelCentral;
	private JLabel lblLogo;
	private JLabel lblNombreApp;
	private JLabel lblEslogan;
	private JSeparator separador;
	private JButton btnInicio;
	private JButton btnIrRegistro;

	private JPanel panelPie;
	private JLabel lblPie;

	public VPGoldenTale() {
		super(Constantes.TITULO_APLICACION);
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(Constantes.ANCHURA_APLICACION, Constantes.ALTURA_APLICACION);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		String rootFolder = System.getProperty("user.dir");
		Path imagePath = Paths.get(rootFolder, "img", "logo.png");
		ImageIcon originalIcon = new ImageIcon(imagePath.toString());
		setIconImage(originalIcon.getImage());

		panelNavbar = new JPanel(new BorderLayout());
		panelNavbar.setBackground(Tema.MORADO);
		panelNavbar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		panelNavbar.setPreferredSize(new Dimension(0, 56));

		lblNavNombre = new JLabel(Constantes.TITULO_APLICACION);
		lblNavNombre.setFont(Tema.fuenteNegrita(16));
		lblNavNombre.setForeground(Color.WHITE);
		panelNavbar.add(lblNavNombre, BorderLayout.WEST);

		lblNavEstado = new JLabel("Bienvenido a " + Constantes.TITULO_APLICACION);
		lblNavEstado.setFont(Tema.fuenteNormal(12));
		lblNavEstado.setForeground(new Color(220, 210, 245));
		lblNavEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNavbar.add(lblNavEstado, BorderLayout.EAST);
		add(panelNavbar, BorderLayout.NORTH);

		panelLateral = new JPanel();
		panelLateral.setBackground(Tema.FONDO_LATERAL);
		panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
		panelLateral.setPreferredSize(new Dimension(Constantes.ANCHURA_SIDEBAR, 0));
		panelLateral
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Tema.BORDE),
						BorderFactory.createEmptyBorder(18, 12, 18, 12)));

		lblMenuTitulo = new JLabel("MENU");
		lblMenuTitulo.setFont(Tema.fuenteNegrita(10));
		lblMenuTitulo.setForeground(Tema.TEXTO_CLARO);
		lblMenuTitulo.setAlignmentX(LEFT_ALIGNMENT);
		panelLateral.add(lblMenuTitulo);
		panelLateral.add(Box.createVerticalStrut(8));

		btnLateralInicio = ComponentesUI.botonSecundario("Inicio");
		btnLateralLogin = ComponentesUI.botonSecundario("Iniciar sesion");
		btnLateralRegistro = ComponentesUI.botonSecundario("Registrarse");
		prepararBotonLateral(btnLateralInicio);
		prepararBotonLateral(btnLateralLogin);
		prepararBotonLateral(btnLateralRegistro);
		panelLateral.add(btnLateralInicio);
		panelLateral.add(Box.createVerticalStrut(8));
		panelLateral.add(btnLateralLogin);
		panelLateral.add(Box.createVerticalStrut(8));
		panelLateral.add(btnLateralRegistro);
		panelLateral.add(Box.createVerticalGlue());
		add(panelLateral, BorderLayout.WEST);

		panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setBackground(Tema.FONDO);
		panelCentral.setBorder(BorderFactory.createEmptyBorder(35, 45, 35, 45));

		PanelRedondeado tarjeta = new PanelRedondeado(18, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(520, 430));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(34, 46, 34, 46));

		Path logoPath = Paths.get(rootFolder, "img", "logo.png");
		ImageIcon logoIcon = new ImageIcon(logoPath.toString());
		lblLogo = new JLabel(logoIcon);
		lblLogo.setPreferredSize(new Dimension(90, 90));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblLogo);
		tarjeta.add(Box.createVerticalStrut(16));

		lblNombreApp = new JLabel(Constantes.TITULO_APLICACION);
		lblNombreApp.setFont(Tema.fuenteNegrita(34));
		lblNombreApp.setForeground(Tema.TEXTO_OSCURO);
		lblNombreApp.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreApp.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblNombreApp);

		lblEslogan = new JLabel("Tu perfumeria de confianza");
		lblEslogan.setFont(Tema.fuenteNormal(16));
		lblEslogan.setForeground(Tema.TEXTO_MEDIO);
		lblEslogan.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblEslogan);
		tarjeta.add(Box.createVerticalStrut(18));

		separador = new JSeparator();
		separador.setMaximumSize(new Dimension(300, 2));
		separador.setForeground(Tema.BORDE);
		tarjeta.add(separador);
		tarjeta.add(Box.createVerticalStrut(22));

		JPanel filaBotones = new JPanel(new GridLayout(1, 2, 12, 0));
		filaBotones.setOpaque(false);
		filaBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
		btnInicio = ComponentesUI.botonPrincipal("Iniciar sesion");
		btnIrRegistro = ComponentesUI.botonSecundario("Registrarse");
		btnIrRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
		filaBotones.add(btnInicio);
		filaBotones.add(btnIrRegistro);
		tarjeta.add(filaBotones);

		JLabel nota = new JLabel("Catalogo, pedidos y stock en una sola aplicacion");
		nota.setFont(Tema.fuenteNormal(11));
		nota.setForeground(Tema.TEXTO_CLARO);
		nota.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(Box.createVerticalStrut(18));
		tarjeta.add(nota);

		panelCentral.add(tarjeta);
		add(panelCentral, BorderLayout.CENTER);

		panelPie = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
		panelPie.setBackground(Tema.FONDO);
		panelPie.setPreferredSize(new Dimension(0, 35));
		lblPie = new JLabel(Constantes.TITULO_APLICACION + " v1.0 - Proyecto Integrador DAM 2024-2025");
		lblPie.setFont(Tema.fuenteNormal(11));
		lblPie.setForeground(Tema.TEXTO_CLARO);
		panelPie.add(lblPie);
		add(panelPie, BorderLayout.SOUTH);

		Controlador c = new Controlador(this);
		btnInicio.addActionListener(c);
		btnIrRegistro.addActionListener(c);
		btnLateralInicio.addActionListener(c);
		btnLateralLogin.addActionListener(c);
		btnLateralRegistro.addActionListener(c);

		UIManager.put("OptionPane.yesButtonText", "Si");
		UIManager.put("OptionPane.noButtonText", "No");
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
	}

	private void prepararBotonLateral(JButton boton) {
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		boton.setPreferredSize(new Dimension(150, 36));
		boton.setAlignmentX(LEFT_ALIGNMENT);
	}

	public void hacerVisible() {
		setVisible(true);
	}

	public JLabel getLblNavEstado() {
		return lblNavEstado;
	}

	public JButton getBtnLateralInicio() {
		return btnLateralInicio;
	}

	public JButton getBtnLateralLogin() {
		return btnLateralLogin;
	}

	public JButton getBtnLateralRegistro() {
		return btnLateralRegistro;
	}

	public JLabel getLblLogo() {
		return lblLogo;
	}

	public JLabel getLblNombreApp() {
		return lblNombreApp;
	}

	public JButton getBtnInicio() {
		return btnInicio;
	}

	public JButton getBtnIrRegistro() {
		return btnIrRegistro;
	}
}
