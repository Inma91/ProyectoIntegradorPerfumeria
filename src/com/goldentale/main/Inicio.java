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
 * <p>
 * Inicializa todas las vistas, el controlador y la ventana principal, los
 * conecta entre sí y arranca la interfaz gráfica en el hilo de eventos de
 * Swing.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Inicio {

	/**
	 * Método principal de la aplicación.
	 * <p>
	 * Programa el arranque de la interfaz gráfica en el Event Dispatch Thread
	 * mediante {@link EventQueue#invokeLater}. Dentro del hilo de eventos:
	 * <ol>
	 * <li>Aplica el Look &amp; Feel nativo del sistema operativo.</li>
	 * <li>Instancia la ventana principal y todas las vistas.</li>
	 * <li>Crea el controlador y le inyecta todas las vistas.</li>
	 * <li>Registra el controlador en cada vista.</li>
	 * <li>Añade las vistas al {@code CardLayout} de la ventana principal.</li>
	 * <li>Muestra el sidebar pre-login y la vista de inicio.</li>
	 * </ol>
	 * </p>
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				VPGoldenTale ventana = new VPGoldenTale();

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

				ventana.añadirVistas(panelLogin, panelRegistro, panelCatalogo, panelCarrito, panelMisPedidos, panelPago,
						panelDashboard, panelAnadir, panelModificar, panelStock);

				ventana.mostrarSidebarPreLogin(controlador);
				ventana.mostrarVista(Constantes.VISTA_INICIO);
				ventana.hacerVisible();
			}
		});
	}
}