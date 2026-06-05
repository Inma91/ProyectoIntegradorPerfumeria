package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.Usuario;
import com.goldentale.model.db.UsuarioDAO;
import com.goldentale.vistaPrincipalLyR.VPGoldenTale;
import com.goldentale.vistaPrincipalLyR.VLogin;
import com.goldentale.vistaPrincipalLyR.VRegistroUsuario;
import com.goldentale.vistaCliente.VCatalogoCliente;
import com.goldentale.vistaCliente.VCarritoCompra;
import com.goldentale.vistaCliente.VMisPedidos;
import com.goldentale.vistaCliente.VPago;
import com.goldentale.vistaEmpleado.VEmpleadoDashboard;
import com.goldentale.vistaEmpleado.VAniadirPerfume;
import com.goldentale.vistaEmpleado.VModificarPerfume;
import com.goldentale.vistaEmpleado.VStock;

/**
 * Controlador principal de Golden Tale.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Controlador implements ActionListener {

	private VPGoldenTale ventana;
	private VLogin panelLogin;
	private VRegistroUsuario panelRegistro;

	// Vistas cliente
	private VCatalogoCliente panelCatalogo;
	private VCarritoCompra panelCarrito;
	private VMisPedidos panelMisPedidos;
	private VPago panelPago;

	// Vistas empleado
	private VEmpleadoDashboard panelDashboard;
	private VAniadirPerfume panelAnadir;
	private VModificarPerfume panelModificar;
	private VStock panelStock;

	// DAOs
	private UsuarioDAO usuarioDAO;

	public Controlador(VPGoldenTale ventana) {
		this.ventana = ventana;
		usuarioDAO = new UsuarioDAO();
	}

	// ── Setters ───────────────────────────────────────────────────────

	public void setPanelLogin(VLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	public void setPanelRegistro(VRegistroUsuario panelRegistro) {
		this.panelRegistro = panelRegistro;
	}

	public void setPanelCatalogo(VCatalogoCliente panelCatalogo) {
		this.panelCatalogo = panelCatalogo;
	}

	public void setPanelCarrito(VCarritoCompra panelCarrito) {
		this.panelCarrito = panelCarrito;
	}

	public void setPanelMisPedidos(VMisPedidos panelMisPedidos) {
		this.panelMisPedidos = panelMisPedidos;
	}

	public void setPanelPago(VPago panelPago) {
		this.panelPago = panelPago;
	}

	public void setPanelDashboard(VEmpleadoDashboard panelDashboard) {
		this.panelDashboard = panelDashboard;
	}

	public void setPanelAnadir(VAniadirPerfume panelAnadir) {
		this.panelAnadir = panelAnadir;
	}

	public void setPanelModificar(VModificarPerfume panelModificar) {
		this.panelModificar = panelModificar;
	}

	public void setPanelStock(VStock panelStock) {
		this.panelStock = panelStock;
	}

	// ── Dispatcher ────────────────────────────────────────────────────

	@Override
	public void actionPerformed(ActionEvent ev) {

		if (ev.getSource() instanceof JButton) {

			// ── Panel inicio (botones dentro de VPGoldenTale) ─────────
			if (ev.getSource().equals(ventana.getBtnIniciarSesion())) {
				ventana.mostrarVista(Constantes.VISTA_LOGIN);

			} else if (ev.getSource().equals(ventana.getBtnRegistrarse())) {
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

				// ── Sidebar pre-login ─────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnLateralInicio())) {
				ventana.mostrarVista(Constantes.VISTA_INICIO);

			} else if (ev.getSource().equals(ventana.getBtnLateralLogin())) {
				ventana.mostrarVista(Constantes.VISTA_LOGIN);

			} else if (ev.getSource().equals(ventana.getBtnLateralRegistro())) {
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

				// ── Login ─────────────────────────────────────────────────
			} else if (ev.getSource().equals(panelLogin.getBtnIniciarSesion())) {
				procesarLogin();

			} else if (ev.getSource().equals(panelLogin.getBtnRegistrarse())) {
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

			} else if (ev.getSource().equals(panelLogin.getBtnMostrarPassword())) {
				// TODO: mostrar/ocultar contraseña

				// ── Registro ──────────────────────────────────────────────
			} else if (ev.getSource().equals(panelRegistro.getBtnRegistrar())) {
				procesarRegistro();

			} else if (ev.getSource().equals(panelRegistro.getBtnLimpiar())) {
				panelRegistro.limpiarFormulario();

			} else if (ev.getSource().equals(panelRegistro.getBtnMostrarPassword())) {
				// TODO: mostrar/ocultar contraseña registro

				// ── Sidebar cliente ───────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnClienteCatalogo())) {
				ventana.mostrarVista(Constantes.VISTA_CATALOGO);

			} else if (ev.getSource().equals(ventana.getBtnClienteCarrito())) {
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(ventana.getBtnClienteMisPedidos())) {
				ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

			} else if (ev.getSource().equals(ventana.getBtnClienteCerrarSesion())) {
				cerrarSesion();

				// ── Catálogo cliente ──────────────────────────────────────
			} else if (ev.getSource().equals(panelCatalogo.getBtnAnadirCarrito())) {
				// TODO: anadirAlCarrito()

			} else if (ev.getSource().equals(panelCatalogo.getBtnVerCarrito())) {
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(panelCatalogo.getBtnMisPedidos())) {
				ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

				// ── Carrito ───────────────────────────────────────────────
			} else if (ev.getSource().equals(panelCarrito.getBtnEliminarLinea())) {
				// TODO: eliminarLineaCarrito()

			} else if (ev.getSource().equals(panelCarrito.getBtnVaciarCarrito())) {
				// TODO: vaciarCarrito()

			} else if (ev.getSource().equals(panelCarrito.getBtnFinalizarCompra())) {
				ventana.mostrarVista(Constantes.VISTA_PAGO);

				// ── Mis pedidos ───────────────────────────────────────────
			} else if (ev.getSource().equals(panelMisPedidos.getBtnVerDetalle())) {
				// TODO: mostrarDetallePedido()

				// ── Pago ──────────────────────────────────────────────────
			} else if (ev.getSource().equals(panelPago.getBtnConfirmarPago())) {
				// TODO: procesarPago()

			} else if (ev.getSource().equals(panelPago.getBtnCancelar())) {
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

				// ── Sidebar empleado ──────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnEmpleadoDashboard())) {
				ventana.mostrarVista(Constantes.VISTA_DASHBOARD);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoAnadir())) {
				ventana.mostrarVista(Constantes.VISTA_ANADIR);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoModificar())) {
				ventana.mostrarVista(Constantes.VISTA_MODIFICAR);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoStock())) {
				ventana.mostrarVista(Constantes.VISTA_STOCK);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoCerrarSesion())) {
				cerrarSesion();

				// ── Dashboard empleado (accesos rápidos) ──────────────────
			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoAnadirPerfume())) {
				ventana.mostrarVista(Constantes.VISTA_ANADIR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoModificarPerfume())) {
				ventana.mostrarVista(Constantes.VISTA_MODIFICAR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoStock())) {
				ventana.mostrarVista(Constantes.VISTA_STOCK);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoGestionarPedidos())) {
				// TODO: ventana.mostrarVista(Constantes.VISTA_PEDIDOS_EMPLEADO)

				// ── Añadir perfume ────────────────────────────────────────
			} else if (ev.getSource().equals(panelAnadir.getBtnGuardar())) {
				// TODO: guardarPerfume()

			} else if (ev.getSource().equals(panelAnadir.getBtnLimpiar())) {
				panelAnadir.limpiarFormulario();

				// ── Modificar perfume ─────────────────────────────────────
			} else if (ev.getSource().equals(panelModificar.getBtnBuscar())) {
				// TODO: buscarPerfume()

			} else if (ev.getSource().equals(panelModificar.getBtnGuardar())) {
				// TODO: guardarStockModificado()

			} else if (ev.getSource().equals(panelModificar.getBtnCancelar())) {
				panelModificar.getPanelModificacion().setVisible(false);
				panelModificar.getLblResultado().setText(" ");
			}
		}
	}

	// ── Login ─────────────────────────────────────────────────────────

	private void procesarLogin() {
		String email = panelLogin.getTxtEmail().getText().trim();
		String password = new String(panelLogin.getTxtPassword().getPassword()).trim();

		// Tiene que haber algo escrito en los campos
		if (email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(ventana,
					"Por favor, introduce tu correo y contraseña.",
					"Error de inicio de sesión",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Comprobar si está en la BBDD
		Usuario usuario = usuarioDAO.autenticar(email, password);

		// Login fallido salta JOptionPane de advertencia
		if (usuario == null) {
			JOptionPane.showMessageDialog(ventana,
					"El usuario o la contraseña son incorrectos.",
					"Error de inicio de sesión",
					JOptionPane.WARNING_MESSAGE);
			panelLogin.getTxtPassword().setText("");
			return;
		}

		// Login correcto: limpiar errores y guardar usuario en sesión
		Constantes.usuarioAutenticado = usuario;
		panelLogin.limpiarError();

		// Rol (Cliente o Empleado)
		if (Constantes.ROL_CLIENTE.equals(usuario.getRol())) {
			ventana.mostrarSidebarCliente(usuario.getNombreCompleto(), this);
			ventana.mostrarVista(Constantes.VISTA_CATALOGO);
		} else {
			ventana.mostrarSidebarEmpleado(usuario.getNombreCompleto(), this);
			ventana.mostrarVista(Constantes.VISTA_DASHBOARD);
		}
	}

	// ── Cerrar sesión ─────────────────────────────────────────────────

	private void cerrarSesion() {
		int confirm = JOptionPane.showConfirmDialog(ventana, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
				JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		Constantes.usuarioAutenticado = null;
		ventana.mostrarSidebarPreLogin(this);
		ventana.getLblNavEstado().setText("Bienvenido a " + Constantes.TITULO_APLICACION);
		ventana.mostrarVista(Constantes.VISTA_INICIO);
	}

	// ── Registro ──────────────────────────────────────────────────────

	private void procesarRegistro() {

		// TODO: validaciones (formato email, longitud contraseña, teléfono...) → otro compañero

		// Obtener los datos del formulario
		Usuario nuevoUsuario = panelRegistro.obtenerDatos();
		String password = new String(panelRegistro.getTxtPassword().getPassword()).trim();
		String confirmPassword = new String(panelRegistro.getTxtConfirmarPassword().getPassword()).trim();

		// Comprobar que las dos contraseñas coinciden
		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(ventana,
					"Las contraseñas no coinciden.",
					"Error de registro",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Comprobar que el email no esté ya registrado
		if (usuarioDAO.existeEmail(nuevoUsuario.getEmail())) {
			JOptionPane.showMessageDialog(ventana,
					"Ya existe una cuenta con ese correo electrónico.",
					"Error de registro",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Guardar en la BBDD
		usuarioDAO.registrar(nuevoUsuario);

		// Mensaje informativo de éxito
		JOptionPane.showMessageDialog(ventana,
				"¡Registro completado correctamente!",
				"Registro exitoso",
				JOptionPane.INFORMATION_MESSAGE);

		// Limpiar el formulario para la próxima vez
		panelRegistro.limpiarFormulario();

		// Auto-login: meter al usuario en la sesión y llevarlo al catálogo
		Constantes.usuarioAutenticado = nuevoUsuario;
		ventana.mostrarSidebarCliente(nuevoUsuario.getNombreCompleto(), this);
		ventana.mostrarVista(Constantes.VISTA_CATALOGO);
	}
}