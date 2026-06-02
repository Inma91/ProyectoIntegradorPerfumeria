package com.goldentale.vistaEmpleado;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;

import javax.swing.*;
import java.awt.*;

/**
 * Panel Modificar Perfume (stock) — vista del empleado. Permite buscar un
 * perfume por nombre y ml, y añadir stock.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class VModificarPerfume extends JPanel {

	// ── Búsqueda ──────────────────────────────────────────────────────
	private JTextField txtBuscarNombre;
	private JTextField txtBuscarMl;
	private JButton btnBuscar;
	private JLabel lblResultado;

	// ── Panel de modificación (oculto hasta encontrar el perfume) ─────
	private JPanel panelModificacion;
	private JLabel lblDatosPerfume;
	private JLabel lblStockActual;
	private JTextField txtCantidadAnadir;
	private JLabel lblPreview;
	private JLabel lblError;
	private JLabel lblExito;
	private JButton btnGuardar;
	private JButton btnCancelar;

	public VModificarPerfume() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setLayout(new GridBagLayout());
		setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(16, Color.WHITE, Tema.BORDE);
		tarjeta.setPreferredSize(new Dimension(560, 480));
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

		// ── Título ────────────────────────────────────────────────────
		JLabel lblTitulo = new JLabel("Modificar stock de perfume");
		lblTitulo.setFont(Tema.fuenteNegrita(22));
		lblTitulo.setForeground(Tema.TEXTO_OSCURO);
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createVerticalStrut(18));

		// ── Búsqueda ──────────────────────────────────────────────────
		JLabel lblBusquedaTitulo = new JLabel("Búsqueda del perfume");
		lblBusquedaTitulo.setFont(Tema.fuenteNegrita(12));
		lblBusquedaTitulo.setForeground(Tema.TEXTO_MEDIO);
		tarjeta.add(lblBusquedaTitulo);
		tarjeta.add(Box.createVerticalStrut(8));

		JPanel panelBusqueda = new JPanel(new GridLayout(0, 2, 12, 10));
		panelBusqueda.setOpaque(false);
		txtBuscarNombre = ComponentesUI.campoTexto("Velvet Rose");
		txtBuscarMl = ComponentesUI.campoTexto("50");
		btnBuscar = ComponentesUI.botonPrincipal("Buscar");
		panelBusqueda.add(ComponentesUI.etiquetaFormulario("Nombre"));
		panelBusqueda.add(txtBuscarNombre);
		panelBusqueda.add(ComponentesUI.etiquetaFormulario("Mililitros"));
		panelBusqueda.add(txtBuscarMl);
		panelBusqueda.add(new JLabel(""));
		panelBusqueda.add(btnBuscar);
		tarjeta.add(panelBusqueda);
		tarjeta.add(Box.createVerticalStrut(10));

		lblResultado = new JLabel(" ");
		lblResultado.setFont(Tema.fuenteNormal(12));
		lblResultado.setForeground(Tema.MORADO);
		tarjeta.add(lblResultado);
		tarjeta.add(Box.createVerticalStrut(14));

		// ── Panel de modificación (oculto inicialmente) ───────────────
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

		txtCantidadAnadir = ComponentesUI.campoTexto("10");

		lblPreview = new JLabel("Stock actual: 0 uds → tras añadir: 0 uds");
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
		panelModificacion.add(ComponentesUI.etiquetaFormulario("Cantidad a añadir"));
		panelModificacion.add(Box.createVerticalStrut(4));
		panelModificacion.add(txtCantidadAnadir);
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

	public JTextField getTxtCantidadAnadir() {
		return txtCantidadAnadir;
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
	
	public void setControlador(Controlador controlador) {
	    btnBuscar.addActionListener(controlador);
	    btnGuardar.addActionListener(controlador);
	    btnCancelar.addActionListener(controlador);
	}
}
