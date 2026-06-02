package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de Pago — vista del cliente. Permite elegir forma de pago y dirección
 * de entrega, y muestra el resumen del pedido antes de confirmar.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VPago extends JPanel {

	private JLabel lblResumen;
	private JLabel lblTotal;
	private JComboBox<String> comboFormaPago;
	private JTextField txtDireccionEntrega;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnConfirmarPago;
	private JButton btnCancelar;

	public VPago() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(430, 370));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

		// ── Título ────────────────────────────────────────────────────
		JLabel lblTitulo = new JLabel("Confirmar pedido");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(14));

		// ── Resumen y total ───────────────────────────────────────────
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

		// ── Formulario ────────────────────────────────────────────────
		JPanel formulario = new JPanel(new GridLayout(0, 2, 12, 10));
		formulario.setOpaque(false);

		formulario.add(ComponentesUI.etiquetaFormulario("Forma de pago"));
		comboFormaPago = new JComboBox<>(Constantes.FORMAS_PAGO);
		formulario.add(comboFormaPago);

		formulario.add(ComponentesUI.etiquetaFormulario("Dirección de entrega"));
		txtDireccionEntrega = ComponentesUI.campoTexto("Calle y número");
		formulario.add(txtDireccionEntrega);

		tarjeta.add(formulario);
		tarjeta.add(Box.createVerticalStrut(12));

		// ── Feedback ──────────────────────────────────────────────────
		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		tarjeta.add(lblError);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		tarjeta.add(lblExito);
		tarjeta.add(Box.createVerticalStrut(10));

		// ── Botones ───────────────────────────────────────────────────
		JPanel acciones = new JPanel(new GridLayout(1, 2, 10, 0));
		acciones.setOpaque(false);
		acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnCancelar = ComponentesUI.botonSecundario("Cancelar");
		btnConfirmarPago = ComponentesUI.botonPrincipal("Confirmar");
		acciones.add(btnCancelar);
		acciones.add(btnConfirmarPago);
		tarjeta.add(acciones);

		add(tarjeta);
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

	public void limpiarFeedback() {
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	// ── Getters ───────────────────────────────────────────────────────

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
	
	public void setControlador(Controlador controlador) {
	    btnConfirmarPago.addActionListener(controlador);
	    btnCancelar.addActionListener(controlador);
	}
}
