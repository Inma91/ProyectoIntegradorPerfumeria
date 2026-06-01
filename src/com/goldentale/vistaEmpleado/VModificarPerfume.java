package com.goldentale.vistaEmpleado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VModificarPerfume extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JPanel panelBusqueda;
	private JLabel lblBusquedaTitulo;
	private JLabel lblBuscarNombre;
	private JTextField txtBuscarNombre;
	private JLabel lblBuscarMl;
	private JTextField txtBuscarMl;
	private JButton btnBuscar;
	private JLabel lblResultado;
	private JPanel panelModificacion;
	private JLabel lblDatosPerfume;
	private JLabel lblStockActual;
	private JLabel lblCantidadAñadir;
	private JTextField txtCantidadAñadir;
	private JLabel lblPreview;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnCancelar;

	public VModificarPerfume() {
		super(Constantes.TITULO_APLICACION + " - Modificar perfume");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 610);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		btnVolver = ComponentesUI.botonSecundario("Volver");
		btnVolver.setPreferredSize(new Dimension(100, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnVolver);
		lblNombreApp = (JLabel) panelNavbar.getComponent(0);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel fondo = new JPanel(new GridBagLayout());
		fondo.setBackground(Tema.FONDO);
		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(560, 470));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		lblTitulo = new JLabel("Modificar stock de perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		panelBusqueda = new JPanel(new GridLayout(0, 2, 12, 10));
		panelBusqueda.setOpaque(false);
		lblBusquedaTitulo = new JLabel("Busqueda superior");
		lblBusquedaTitulo.setFont(Tema.fuenteNegrita(12));
		lblBusquedaTitulo.setForeground(Tema.TEXTO_MEDIO);
		lblBuscarNombre = ComponentesUI.etiquetaFormulario("Nombre");
		txtBuscarNombre = ComponentesUI.campoTexto("Velvet Rose");
		lblBuscarMl = ComponentesUI.etiquetaFormulario("Mililitros");
		txtBuscarMl = ComponentesUI.campoTexto("50");
		btnBuscar = ComponentesUI.botonPrincipal("Buscar");
		panelBusqueda.add(lblBuscarNombre);
		panelBusqueda.add(txtBuscarNombre);
		panelBusqueda.add(lblBuscarMl);
		panelBusqueda.add(txtBuscarMl);
		panelBusqueda.add(new JLabel(""));
		panelBusqueda.add(btnBuscar);
		tarjeta.add(lblBusquedaTitulo);
		tarjeta.add(Box.createVerticalStrut(8));
		tarjeta.add(panelBusqueda);
		tarjeta.add(Box.createVerticalStrut(10));

		lblResultado = new JLabel(" ");
		lblResultado.setFont(Tema.fuenteNormal(12));
		lblResultado.setForeground(Tema.MORADO);
		tarjeta.add(lblResultado);
		tarjeta.add(Box.createVerticalStrut(14));

		panelModificacion = new PanelRedondeado(12, Tema.FONDO, Tema.BORDE_CLARO);
		panelModificacion.setLayout(new BoxLayout(panelModificacion, BoxLayout.Y_AXIS));
		panelModificacion.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		panelModificacion.setVisible(false);

		lblDatosPerfume = new JLabel("Perfume encontrado");
		lblDatosPerfume.setFont(Tema.fuenteNegrita(13));
		lblDatosPerfume.setForeground(Tema.TEXTO_OSCURO);
		lblStockActual = new JLabel("Stock actual: 0 uds");
		lblStockActual.setFont(Tema.fuenteNormal(12));
		lblStockActual.setForeground(Tema.TEXTO_MEDIO);
		lblCantidadAñadir = ComponentesUI.etiquetaFormulario("Cantidad a anadir");
		txtCantidadAñadir = ComponentesUI.campoTexto("10");
		lblPreview = new JLabel("Stock actual: 0 uds, tras anadir: 0 uds");
		lblPreview.setFont(Tema.fuenteNormal(12));
		lblPreview.setForeground(Tema.MORADO);
		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);

		panelModificacion.add(lblDatosPerfume);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(lblStockActual);
		panelModificacion.add(Box.createVerticalStrut(12));
		panelModificacion.add(lblCantidadAñadir);
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(txtCantidadAñadir);
		panelModificacion.add(Box.createVerticalStrut(8));
		panelModificacion.add(lblPreview);
		panelModificacion.add(lblError);
		panelModificacion.add(lblExito);

		JPanel fila = new JPanel(new GridLayout(1, 2, 10, 0));
		fila.setOpaque(false);
		btnCancelar = ComponentesUI.botonSecundario("Cancelar");
		btnGuardar = ComponentesUI.botonPrincipal("Guardar");
		fila.add(btnCancelar);
		fila.add(btnGuardar);
		panelModificacion.add(Box.createVerticalStrut(8));
		panelModificacion.add(fila);

		tarjeta.add(panelModificacion);
		fondo.add(tarjeta);
		add(new JScrollPane(fondo), BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JTextField getTxtBuscarNombre() {
		return txtBuscarNombre;
	}

	public JTextField getTxtBuscarMl() {
		return txtBuscarMl;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JLabel getLblResultado() {
		return lblResultado;
	}

	public JPanel getPanelModificacion() {
		return panelModificacion;
	}

	public JLabel getLblDatosPerfume() {
		return lblDatosPerfume;
	}

	public JLabel getLblStockActual() {
		return lblStockActual;
	}

	public JTextField getTxtCantidadAñadir() {
		return txtCantidadAñadir;
	}

	public JLabel getLblPreview() {
		return lblPreview;
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

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
