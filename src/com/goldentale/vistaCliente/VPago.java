package com.goldentale.vistaCliente;

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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.*;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

public class VPago extends JFrame {

	private JPanel panelNavbar;
	private JButton btnVolver;
	private JLabel lblTitulo;
	private JLabel lblResumen;
	private JLabel lblTotal;
	private JComboBox<String> comboFormaPago;
	private JTextField txtDireccionEntrega;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnConfirmarPago;
	private JButton btnCancelar;

	public VPago() {
		super(Constantes.TITULO_APLICACION + " - Pago");
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(560, 520);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		btnVolver = ComponentesUI.botonSecundario("Volver");
		btnVolver.setPreferredSize(new Dimension(95, 32));
		panelNavbar = ComponentesUI.navbar(Constantes.TITULO_APLICACION, btnVolver);
		add(panelNavbar, BorderLayout.NORTH);

		JPanel fondo = new JPanel(new GridBagLayout());
		fondo.setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(430, 360));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

		lblTitulo = new JLabel("Confirmar pedido");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(14));

		lblResumen = new JLabel("Resumen: 1 perfume seleccionado");
		lblResumen.setFont(Tema.fuenteNormal(13));
		lblResumen.setForeground(Tema.TEXTO_MEDIO);
		lblResumen.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblResumen);

		lblTotal = new JLabel("Total: 89.99 EUR");
		lblTotal.setFont(Tema.fuenteNegrita(26));
		lblTotal.setForeground(Tema.MORADO);
		lblTotal.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(Box.createVerticalStrut(6));
		tarjeta.add(lblTotal);
		tarjeta.add(Box.createVerticalStrut(20));

		JPanel formulario = new JPanel(new GridLayout(0, 2, 12, 10));
		formulario.setOpaque(false);
		formulario.add(ComponentesUI.etiquetaFormulario("Forma de pago"));
		comboFormaPago = new JComboBox<String>(Constantes.FORMAS_PAGO);
		formulario.add(comboFormaPago);
		formulario.add(ComponentesUI.etiquetaFormulario("Direccion"));
		txtDireccionEntrega = ComponentesUI.campoTexto("Direccion de entrega");
		formulario.add(txtDireccionEntrega);
		tarjeta.add(formulario);
		tarjeta.add(Box.createVerticalStrut(12));

		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		tarjeta.add(lblError);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		tarjeta.add(lblExito);
		tarjeta.add(Box.createVerticalStrut(10));

		JPanel acciones = new JPanel(new GridLayout(1, 2, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnCancelar = ComponentesUI.botonSecundario("Cancelar");
		btnConfirmarPago = ComponentesUI.botonPrincipal("Confirmar");
		acciones.add(btnCancelar);
		acciones.add(btnConfirmarPago);
		tarjeta.add(acciones);

		fondo.add(tarjeta);
		add(fondo, BorderLayout.CENTER);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JLabel getLblResumen() {
		return lblResumen;
	}

	public JLabel getLblTotal() {
		return lblTotal;
	}

	public JComboBox<String> getComboFormaPago() {
		return comboFormaPago;
	}

	public JTextField getTxtDireccionEntrega() {
		return txtDireccionEntrega;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public JLabel getLblExito() {
		return lblExito;
	}

	public JButton getBtnConfirmarPago() {
		return btnConfirmarPago;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
