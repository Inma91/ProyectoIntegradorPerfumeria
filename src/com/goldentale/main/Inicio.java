package com.goldentale.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.vistaPrincipalLyR.VLogin;
import com.goldentale.vistaPrincipalLyR.VPGoldenTale;
import com.goldentale.vistaPrincipalLyR.VRegistroUsuario;
import com.goldentale.vistaCliente.VCarritoCompra;
import com.goldentale.vistaCliente.VCatalogoCliente;
import com.goldentale.vistaCliente.VMisPedidos;
import com.goldentale.vistaCliente.VPago;
import com.goldentale.vistaEmpleado.VAniadirPerfume;
import com.goldentale.vistaEmpleado.VEmpleadoDashboard;
import com.goldentale.vistaEmpleado.VModificarPerfume;
import com.goldentale.vistaEmpleado.VStock;

/**
 * Punto de entrada de la aplicación Golden Tale.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Inicio {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Ventana principal (incluye el panel de inicio internamente)
				VPGoldenTale ventana = new VPGoldenTale();

				// Vistas
				VLogin panelLogin = new VLogin();
				VRegistroUsuario panelRegistro = new VRegistroUsuario();
				VCatalogoCliente panelCatalogo = new VCatalogoCliente();
				VCarritoCompra panelCarrito = new VCarritoCompra();
				VMisPedidos panelMisPedidos = new VMisPedidos();
				VPago panelPago = new VPago();
				VEmpleadoDashboard panelDashboard = new VEmpleadoDashboard();
				VAniadirPerfume panelAnadir = new VAniadirPerfume();
				VModificarPerfume panelModificar = new VModificarPerfume();
				VStock panelStock = new VStock();

				// Controlador
				Controlador controlador = new Controlador(ventana);
				controlador.setPanelLogin(panelLogin);
				controlador.setPanelRegistro(panelRegistro);
				controlador.setPanelCatalogo(panelCatalogo);
				controlador.setPanelCarrito(panelCarrito);
				controlador.setPanelMisPedidos(panelMisPedidos);
				controlador.setPanelPago(panelPago);
				controlador.setPanelDashboard(panelDashboard);
				controlador.setPanelAnadir(panelAnadir);
				controlador.setPanelModificar(panelModificar);
				controlador.setPanelStock(panelStock);

				// setControlador en cada vista
				ventana.setControlador(controlador);
				panelLogin.setControlador(controlador);
				panelRegistro.setControlador(controlador);
				panelCatalogo.setControlador(controlador);
				panelCarrito.setControlador(controlador);
				panelMisPedidos.setControlador(controlador);
				panelPago.setControlador(controlador);
				panelDashboard.setControlador(controlador);
				panelAnadir.setControlador(controlador);
				panelModificar.setControlador(controlador);
				panelStock.setControlador(controlador);

				// Añadir vistas al CardLayout
				ventana.añadirVistas(panelLogin, panelRegistro, panelCatalogo, panelCarrito, panelMisPedidos, panelPago,
						panelDashboard, panelAnadir, panelModificar, panelStock);

				// Sidebar inicial y vista de inicio
				ventana.mostrarSidebarPreLogin(controlador);
				ventana.mostrarVista(Constantes.VISTA_INICIO);
				ventana.hacerVisible();
			}
		});
	}
}