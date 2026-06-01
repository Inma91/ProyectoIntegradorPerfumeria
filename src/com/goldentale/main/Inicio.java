package com.goldentale.main;

import java.awt.EventQueue;

import com.goldentale.controlador.Controlador;
import com.goldentale.vistaPrincipalLyR.VPGoldenTale;

public class Inicio {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				VPGoldenTale vpgt = new VPGoldenTale();

				Controlador c = new Controlador(vpgt);

				vpgt.setVisible(true);
			}
		});
	}

}