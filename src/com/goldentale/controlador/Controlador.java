package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.goldentale.vistaEmpleado.LoginVista;
import com.goldentale.vistaEmpleado.VistaPrincipal;

public class Controlador implements ActionListener {

	private VistaPrincipal vp; 
	private LoginVista lgv;
	
	// Crear atributos que representen los paneles
	//private PRealizarEncuesta pre;

	public Controlador(VistaPrincipal vp, LoginVista lgv) {
		this.vp = vp;
		this.lgv = lgv;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getActionCommand().equals(VistaPrincipal.INGRESAR_EN_APLICACION)) {
			vp.cargarPanel(lgv);
			}
		}
		
	}

}
