package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.goldentale.vistaPrincipalLyR.VLogin;
import com.goldentale.vistaPrincipalLyR.VPGoldenTale;
import com.goldentale.vistaPrincipalLyR.VRegistroUsuario;

/**
 * Controlador de la aplicación Golden Tale.
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Controlador implements ActionListener {

	private VPGoldenTale vpgt;

	public Controlador(VPGoldenTale vpgt) {
		this.vpgt = vpgt;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {

		if (vpgt != null) {

			if (ev.getSource().equals(vpgt.getBtnInicio()) || ev.getSource().equals(vpgt.getBtnLateralLogin())) {

				VLogin login = new VLogin();
				login.setVisible(true);

			} else if (ev.getSource().equals(vpgt.getBtnIrRegistro())
					|| ev.getSource().equals(vpgt.getBtnLateralRegistro())) {

				VRegistroUsuario registro = new VRegistroUsuario();
				registro.setVisible(true);

			} else if (ev.getSource().equals(vpgt.getBtnLateralInicio())) {

				vpgt.getLblNavEstado().setText("Ya estas en la pantalla principal");
			}
		}
	}
}