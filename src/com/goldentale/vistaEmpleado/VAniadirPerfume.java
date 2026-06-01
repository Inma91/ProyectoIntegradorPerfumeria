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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.*;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VAniadirPerfume extends JFrame {

	private JPanel panelNavbar;
	private JLabel lblNombreApp;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JPanel panelFormulario;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblMarca;
	private JTextField txtMarca;
	private JLabel lblCategoria;
	private JComboBox<String> comboCategoria;
	private JLabel lblPrecio;
	private JTextField txtPrecio;
	private JLabel lblMl;
	private JTextField txtMl;
	private JLabel lblPublico;
	private JComboBox<String> comboPublico;
	private JLabel lblLocalizacionTitulo;
	private JLabel lblLocalizacion;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JScrollPane scrollDescripcion;
	private JLabel lblStock;
	private JTextField txtStock;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnLimpiar;

	public VAniadirPerfume() {
		super(Constantes.TITULO_APLICACION + " - Anadir perfume");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(720, 690);
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
		tarjeta.setPreferredSize(new Dimension(590, 560));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

		lblTitulo = new JLabel("Anadir nuevo perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		panelFormulario = new JPanel(new GridLayout(0, 2, 14, 10));
		panelFormulario.setOpaque(false);
		lblNombre = ComponentesUI.etiquetaFormulario("Nombre");
		txtNombre = ComponentesUI.campoTexto("Velvet Rose");
		lblMarca = ComponentesUI.etiquetaFormulario("Marca");
		txtMarca = ComponentesUI.campoTexto("Maison Luxe");
		lblCategoria = ComponentesUI.etiquetaFormulario("Categoria");
		comboCategoria = new JComboBox<String>(Constantes.CATEGORIAS_PERFUME);
		lblPrecio = ComponentesUI.etiquetaFormulario("Precio");
		txtPrecio = ComponentesUI.campoTexto("89.99");
		lblMl = ComponentesUI.etiquetaFormulario("Mililitros");
		txtMl = ComponentesUI.campoTexto("50");
		lblPublico = ComponentesUI.etiquetaFormulario("Publico");
		comboPublico = new JComboBox<String>(Constantes.PUBLICOS_OBJETIVO);
		lblStock = ComponentesUI.etiquetaFormulario("Stock inicial");
		txtStock = ComponentesUI.campoTexto("12");

		panelFormulario.add(lblNombre);
		panelFormulario.add(txtNombre);
		panelFormulario.add(lblMarca);
		panelFormulario.add(txtMarca);
		panelFormulario.add(lblCategoria);
		panelFormulario.add(comboCategoria);
		panelFormulario.add(lblPrecio);
		panelFormulario.add(txtPrecio);
		panelFormulario.add(lblMl);
		panelFormulario.add(txtMl);
		panelFormulario.add(lblPublico);
		panelFormulario.add(comboPublico);
		panelFormulario.add(lblStock);
		panelFormulario.add(txtStock);
		tarjeta.add(panelFormulario);
		tarjeta.add(Box.createVerticalStrut(14));

		lblLocalizacionTitulo = ComponentesUI.etiquetaFormulario("Localizacion automatica");
		lblLocalizacion = new JLabel(Constantes.LOC_PEQUENO + " - pendiente de calcular");
		lblLocalizacion.setFont(Tema.fuenteNegrita(13));
		lblLocalizacion.setForeground(Tema.MORADO);
		tarjeta.add(lblLocalizacionTitulo);
		tarjeta.add(Box.createVerticalStrut(4));
		tarjeta.add(lblLocalizacion);
		tarjeta.add(Box.createVerticalStrut(12));

		lblDescripcion = ComponentesUI.etiquetaFormulario("Descripcion");
		txtDescripcion = ComponentesUI.areaTexto();
		scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setPreferredSize(new Dimension(0, 80));
		tarjeta.add(lblDescripcion);
		tarjeta.add(Box.createVerticalStrut(4));
		tarjeta.add(scrollDescripcion);
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
		fondo.add(tarjeta);
		add(new JScrollPane(fondo), BorderLayout.CENTER);
	}

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
}
