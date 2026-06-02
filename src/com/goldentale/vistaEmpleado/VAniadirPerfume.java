package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel Añadir Perfume — vista del empleado. Formulario para dar de alta un
 * nuevo perfume. La localización se asigna automáticamente según los ml
 * introducidos.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VAniadirPerfume extends JPanel {

	private JTextField txtNombre;
	private JTextField txtMarca;
	private JComboBox<String> comboCategoria;
	private JTextField txtPrecio;
	private JTextField txtMl;
	private JComboBox<String> comboPublico;
	private JLabel lblLocalizacion;
	private JTextArea txtDescripcion;
	private JTextField txtStock;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnLimpiar;

	public VAniadirPerfume() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(590, 580));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

		// ── Título ────────────────────────────────────────────────────
		JLabel lblTitulo = new JLabel("Añadir nuevo perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		// ── Formulario ────────────────────────────────────────────────
		JPanel formulario = new JPanel(new GridLayout(0, 2, 14, 10));
		formulario.setOpaque(false);

		txtNombre = ComponentesUI.campoTexto("Velvet Rose");
		txtMarca = ComponentesUI.campoTexto("Maison Luxe");
		comboCategoria = new JComboBox<>(Constantes.CATEGORIAS_PERFUME);
		txtPrecio = ComponentesUI.campoTexto("89.99");
		txtMl = ComponentesUI.campoTexto("50");
		comboPublico = new JComboBox<>(Constantes.PUBLICOS_OBJETIVO);
		txtStock = ComponentesUI.campoTexto("12");

		formulario.add(ComponentesUI.etiquetaFormulario("Nombre"));
		formulario.add(txtNombre);
		formulario.add(ComponentesUI.etiquetaFormulario("Marca"));
		formulario.add(txtMarca);
		formulario.add(ComponentesUI.etiquetaFormulario("Categoría"));
		formulario.add(comboCategoria);
		formulario.add(ComponentesUI.etiquetaFormulario("Precio (€)"));
		formulario.add(txtPrecio);
		formulario.add(ComponentesUI.etiquetaFormulario("Mililitros"));
		formulario.add(txtMl);
		formulario.add(ComponentesUI.etiquetaFormulario("Público"));
		formulario.add(comboPublico);
		formulario.add(ComponentesUI.etiquetaFormulario("Stock inicial"));
		formulario.add(txtStock);

		tarjeta.add(formulario);
		tarjeta.add(Box.createVerticalStrut(14));

		// ── Localización automática ───────────────────────────────────
		JLabel lblLocTitulo = ComponentesUI.etiquetaFormulario("Localización automática");
		tarjeta.add(lblLocTitulo);
		tarjeta.add(Box.createVerticalStrut(4));

		lblLocalizacion = new JLabel(Constantes.LOC_PEQUENO + " — pendiente de calcular");
		lblLocalizacion.setFont(Tema.fuenteNegrita(13));
		lblLocalizacion.setForeground(Tema.MORADO);
		tarjeta.add(lblLocalizacion);
		tarjeta.add(Box.createVerticalStrut(12));

		// ── Descripción ───────────────────────────────────────────────
		tarjeta.add(ComponentesUI.etiquetaFormulario("Descripción"));
		tarjeta.add(Box.createVerticalStrut(4));
		txtDescripcion = ComponentesUI.areaTexto();
		JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
		scrollDesc.setPreferredSize(new Dimension(0, 80));
		scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		tarjeta.add(scrollDesc);
		tarjeta.add(Box.createVerticalStrut(10));

		// ── Feedback ──────────────────────────────────────────────────
		lblError = new JLabel(" ");
		lblError.setFont(Tema.fuenteNormal(12));
		lblError.setForeground(Tema.ERROR);
		tarjeta.add(lblError);

		lblExito = new JLabel(" ");
		lblExito.setFont(Tema.fuenteNormal(12));
		lblExito.setForeground(Tema.EXITO);
		tarjeta.add(lblExito);
		tarjeta.add(Box.createVerticalStrut(8));

		// ── Botones ───────────────────────────────────────────────────
		JPanel fila = new JPanel(new GridLayout(1, 2, 10, 0));
		fila.setOpaque(false);
		fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnLimpiar = ComponentesUI.botonSecundario("Limpiar");
		btnGuardar = ComponentesUI.botonPrincipal("Guardar");
		fila.add(btnLimpiar);
		fila.add(btnGuardar);
		tarjeta.add(fila);

		JScrollPane scroll = new JScrollPane(tarjeta);
		scroll.setBorder(null);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		add(scroll);
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

	/**
	 * Calcula y actualiza la etiqueta de localización según los ml introducidos.
	 * Llamar desde el controlador al cambiar txtMl.
	 */
	public void actualizarLocalizacion() {
		try {
			int ml = Integer.parseInt(txtMl.getText().trim());
			if (ml <= Constantes.ML_LIMITE_PEQUENO) {
				lblLocalizacion.setText(Constantes.LOC_PEQUENO);
			} else if (ml <= Constantes.ML_LIMITE_MEDIANO) {
				lblLocalizacion.setText(Constantes.LOC_MEDIANO);
			} else {
				lblLocalizacion.setText(Constantes.LOC_GRANDE);
			}
		} catch (NumberFormatException e) {
			lblLocalizacion.setText("— introduce mililitros válidos");
		}
	}

	public void limpiarFormulario() {
		txtNombre.setText("");
		txtMarca.setText("");
		comboCategoria.setSelectedIndex(0);
		txtPrecio.setText("");
		txtMl.setText("");
		lblLocalizacion.setText(Constantes.LOC_PEQUENO + " — pendiente de calcular");
		txtStock.setText("");
		comboPublico.setSelectedIndex(0);
		txtDescripcion.setText("");
		lblError.setText(" ");
		lblExito.setText(" ");
	}

	// ── Getters ───────────────────────────────────────────────────────

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

	public void setControlador(Controlador controlador) {
		btnGuardar.addActionListener(controlador);
		btnLimpiar.addActionListener(controlador);
	}
}
