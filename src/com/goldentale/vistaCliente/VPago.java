package com.goldentale.vistaCliente;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel del cliente para confirmar el pedido y procesar el pago. Muestra el
 * resumen del carrito (número de perfumes y total), permite elegir la forma
 * de pago, introducir la dirección de entrega y validar internamente los
 * datos antes de devolverlos al controlador para registrar la compra.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
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

	/**
	 * Construye el panel e inicializa todos los componentes visuales.
	 */
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

		JLabel lblTitulo = new JLabel("Confirmar pedido");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(14));

		lblResumen = new JLabel("Resumen: 0 perfumes seleccionados");
		lblResumen.setFont(Tema.fuenteNormal(13));
		lblResumen.setForeground(Tema.TEXTO_MEDIO);
		lblResumen.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblResumen);

		lblTotal = new JLabel("Total: 0.00 EUR");
		lblTotal.setFont(Tema.fuenteNegrita(26));
		lblTotal.setForeground(Tema.MORADO);
		lblTotal.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(Box.createVerticalStrut(6));
		tarjeta.add(lblTotal);
		tarjeta.add(Box.createVerticalStrut(20));

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

		add(tarjeta);
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
	 * Limpia los labels de feedback de error y éxito dejándolos en blanco.
	 */
	public void limpiarFeedback() {
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	/**
	 * Rellena el resumen del pedido y el total en la vista. Es invocado por
	 * el controlador al entrar a la vista de pago con el carrito ya validado.
	 * Adapta el texto a singular o plural según el número de perfumes.
	 *
	 * @param numItems Número de perfumes seleccionados en el carrito.
	 * @param total    Total a pagar en euros.
	 */
	public void mostrarResumen(int numItems, double total) {
		String texto = "Resumen: " + numItems + " perfume" + (numItems == 1 ? "" : "s") + " seleccionado"
				+ (numItems == 1 ? "" : "s");
		lblResumen.setText(texto);
		lblTotal.setText(String.format("Total: %.2f EUR", total));
	}

	/**
	 * Valida los campos de pago (dirección no vacía y de longitud mínima) y
	 * recoge la forma de pago seleccionada en el combo. Si alguna validación
	 * falla, muestra el error en el label de feedback y devuelve {@code null}.
	 *
	 * @return Array donde el índice 0 contiene la forma de pago
	 *         ({@link String}) y el índice 1 la dirección de entrega
	 *         ({@link String}), o {@code null} si la validación falla.
	 */
	public Object[] obtenerDatos() {
		limpiarFeedback();

		String direccion = txtDireccionEntrega.getText().trim();
		if (direccion.isEmpty()) {
			mostrarError("La dirección de entrega no puede estar vacía.");
			return null;
		}
		if (direccion.length() < 5) {
			mostrarError("La dirección parece demasiado corta. Revísala por favor.");
			return null;
		}

		String formaPago = (String) comboFormaPago.getSelectedItem();

		return new Object[] { formaPago, direccion };
	}

	/**
	 * Restablece todos los campos del formulario a su estado inicial, resetea
	 * el combo de forma de pago al primer elemento, reinicia el resumen y el
	 * total y limpia los labels de feedback.
	 */
	public void limpiarFormulario() {
		comboFormaPago.setSelectedIndex(0);
		txtDireccionEntrega.setText("");
		lblResumen.setText("Resumen: 0 perfumes seleccionados");
		lblTotal.setText("Total: 0.00 EUR");
		limpiarFeedback();
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

	/**
	 * Registra el controlador como listener de los botones de la vista.
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnConfirmarPago.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
	}
}