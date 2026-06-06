package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.InfoPerfumeConStock;
import com.goldentale.model.db.Perfumes;
import com.goldentale.model.db.PerfumesDAO;
import com.goldentale.model.db.Usuario;
import com.goldentale.model.db.UsuarioDAO;
import com.goldentale.model.util.Tema;
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
	private PerfumesDAO perfumesDAO;

	// Perfume que se está editando en la pantalla de Modificar
	private InfoPerfumeConStock perfumeEnEdicion;

	public Controlador(VPGoldenTale ventana) {
		this.ventana = ventana;
		usuarioDAO = new UsuarioDAO();
		perfumesDAO = new PerfumesDAO();
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
				panelRegistro.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_LOGIN);

			} else if (ev.getSource().equals(ventana.getBtnRegistrarse())) {
				panelLogin.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

				// ── Sidebar pre-login ─────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnLateralInicio())) {
				panelLogin.limpiarFormulario();
				panelRegistro.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_INICIO);

			} else if (ev.getSource().equals(ventana.getBtnLateralLogin())) {
				panelRegistro.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_LOGIN);

			} else if (ev.getSource().equals(ventana.getBtnLateralRegistro())) {
				panelLogin.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

				// ── Login ─────────────────────────────────────────────────
			} else if (ev.getSource().equals(panelLogin.getBtnIniciarSesion())) {
				procesarLogin();

			} else if (ev.getSource().equals(panelLogin.getBtnRegistrarse())) {
				panelLogin.limpiarFormulario();
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
				cargarStock();
				ventana.mostrarVista(Constantes.VISTA_STOCK);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoCerrarSesion())) {
				cerrarSesion();

				// ── Dashboard empleado (accesos rápidos) ──────────────────
			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoAnadirPerfume())) {
				ventana.mostrarVista(Constantes.VISTA_ANADIR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoModificarPerfume())) {
				ventana.mostrarVista(Constantes.VISTA_MODIFICAR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoStock())) {
				cargarStock();
				ventana.mostrarVista(Constantes.VISTA_STOCK);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoGestionarPedidos())) {
				// TODO: ventana.mostrarVista(Constantes.VISTA_PEDIDOS_EMPLEADO)

				// ── Botón Filtrar de Stock ────────────────────────────────
			} else if (ev.getSource().equals(panelStock.getBtnFiltrar())) {
				filtrarStock();

				// ── Añadir perfume ────────────────────────────────────────
			} else if (ev.getSource().equals(panelAnadir.getBtnGuardar())) {
				procesarAnadirPerfume();

			} else if (ev.getSource().equals(panelAnadir.getBtnLimpiar())) {
				panelAnadir.limpiarFormulario();

				// ── Modificar perfume ─────────────────────────────────────
			} else if (ev.getSource().equals(panelModificar.getBtnBuscar())) {
				buscarPerfumeParaModificar();

			} else if (ev.getSource().equals(panelModificar.getBtnGuardar())) {
				guardarModificacionPerfume();

			} else if (ev.getSource().equals(panelModificar.getBtnCancelar())) {
				panelModificar.limpiarFormulario();
			}
		}
	}

	// ── Login ─────────────────────────────────────────────────────────

	private void procesarLogin() {
		String email = panelLogin.getTxtEmail().getText().trim();
		String password = new String(panelLogin.getTxtPassword().getPassword()).trim();

		if (email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Por favor, introduce tu correo y contraseña.",
					"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!emailEsValido(email)) {
			JOptionPane.showMessageDialog(ventana, "El formato del correo electrónico no es válido.",
					"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario usuario = usuarioDAO.autenticar(email, password);

		if (usuario == null) {
			JOptionPane.showMessageDialog(ventana, "El usuario o la contraseña son incorrectos.",
					"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);
			panelLogin.getTxtPassword().setText("");
			return;
		}

		Constantes.usuarioAutenticado = usuario;
		panelLogin.limpiarError();

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
		panelLogin.limpiarFormulario();
		ventana.mostrarSidebarPreLogin(this);
		ventana.getLblNavEstado().setText("Bienvenido a " + Constantes.TITULO_APLICACION);
		ventana.mostrarVista(Constantes.VISTA_INICIO);
	}

	// ── Registro ──────────────────────────────────────────────────────

	private void procesarRegistro() {

		// Obtener los datos del formulario (la vista valida internamente)
		Usuario nuevoUsuario = panelRegistro.obtenerDatos();

		// Si la vista detectó un error, ya lo mostró en lblError
		if (nuevoUsuario == null) {
			return;
		}

		String password = new String(panelRegistro.getTxtPassword().getPassword()).trim();
		String confirmPassword = new String(panelRegistro.getTxtConfirmarPassword().getPassword()).trim();

		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(ventana, "Las contraseñas no coinciden.", "Error de registro",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (usuarioDAO.existeEmail(nuevoUsuario.getEmail())) {
			JOptionPane.showMessageDialog(ventana, "Ya existe una cuenta con ese correo electrónico.",
					"Error de registro", JOptionPane.WARNING_MESSAGE);
			return;
		}

		usuarioDAO.registrar(nuevoUsuario);

		JOptionPane.showMessageDialog(ventana, "¡Registro completado correctamente!", "Registro exitoso",
				JOptionPane.INFORMATION_MESSAGE);

		panelRegistro.limpiarFormulario();

		Constantes.usuarioAutenticado = nuevoUsuario;
		ventana.mostrarSidebarCliente(nuevoUsuario.getNombreCompleto(), this);
		ventana.mostrarVista(Constantes.VISTA_CATALOGO);
	}

	// ── Stock (empleado) ──────────────────────────────────────────────

	private void cargarStock() {
		ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoPerfumesConStock();
		pintarTablaYMetricas(lista);
	}

	private void filtrarStock() {
		String nombre = panelStock.getTxtBuscar().getText().trim();
		String ubicacion = (String) panelStock.getComboFiltroUbicacion().getSelectedItem();
		String estado = (String) panelStock.getComboFiltroEstado().getSelectedItem();

		ArrayList<InfoPerfumeConStock> lista;

		if (nombre.isEmpty() && ubicacion.equals("Todas las ubicaciones")) {
			lista = perfumesDAO.getInfoPerfumesConStock();
		} else if (nombre.isEmpty() && !ubicacion.equals("Todas las ubicaciones")) {
			lista = perfumesDAO.getInfoTablaPorUbicacion(ubicacion);
		} else if (!nombre.isEmpty() && ubicacion.equals("Todas las ubicaciones")) {
			lista = perfumesDAO.getInfoTablaPorNombre(nombre);
		} else {
			lista = perfumesDAO.getInfoTablaPorAmbos(nombre, ubicacion);
		}

		ArrayList<InfoPerfumeConStock> listaFiltrada = filtrarPorEstado(lista, estado);
		pintarTablaYMetricas(listaFiltrada);
	}

	private ArrayList<InfoPerfumeConStock> filtrarPorEstado(ArrayList<InfoPerfumeConStock> lista, String estado) {
		if (estado.equals("Todos los estados")) {
			return lista;
		}

		ArrayList<InfoPerfumeConStock> resultado = new ArrayList<InfoPerfumeConStock>();

		for (InfoPerfumeConStock info : lista) {
			int cantidad = info.getStock().getCantidad();
			String estadoCalculado = calcularEstado(cantidad);

			if (estado.equals("Con stock") && (estadoCalculado.equals("OK") || estadoCalculado.equals("Stock bajo"))) {
				resultado.add(info);
			} else if (estado.equals("Stock bajo") && estadoCalculado.equals("Stock bajo")) {
				resultado.add(info);
			} else if (estado.equals("Sin stock") && estadoCalculado.equals("Sin stock")) {
				resultado.add(info);
			}
		}

		return resultado;
	}

	private void pintarTablaYMetricas(ArrayList<InfoPerfumeConStock> lista) {
		Object[][] filas = new Object[lista.size()][4];
		int total = lista.size();
		int bajo = 0;
		int sinStock = 0;

		for (int i = 0; i < lista.size(); i++) {
			InfoPerfumeConStock info = lista.get(i);
			String nombre = info.getPerfume().getNombre();
			String localizacion = info.getStock().getLocalizacion();
			int cantidad = info.getStock().getCantidad();
			String estado = calcularEstado(cantidad);

			filas[i][0] = nombre;
			filas[i][1] = localizacion;
			filas[i][2] = cantidad;
			filas[i][3] = estado;

			if (cantidad == 0) {
				sinStock++;
			} else if (cantidad < Constantes.STOCK_MINIMO_ALERTA) {
				bajo++;
			}
		}

		panelStock.mostrarPerfumesConStock(filas);
		panelStock.actualizarMetricas(total, bajo, sinStock);
	}

	private String calcularEstado(int cantidad) {
		if (cantidad == 0) {
			return "Sin stock";
		} else if (cantidad < Constantes.STOCK_MINIMO_ALERTA) {
			return "Stock bajo";
		} else {
			return "OK";
		}
	}

	// ── Añadir perfume (empleado) ─────────────────────────────────────

	private void procesarAnadirPerfume() {
		Perfumes nuevoPerfume = panelAnadir.obtenerDatos();

		if (nuevoPerfume == null) {
			return;
		}

		InfoPerfumeConStock duplicado = perfumesDAO.buscarPorNombreYMl(nuevoPerfume.getNombre(), nuevoPerfume.getMl());

		if (duplicado != null) {
			panelAnadir.mostrarError("Error: Ya existe un perfume con ese nombre y esos mililitros.");
			return;
		}

		panelAnadir.actualizarLocalizacion();
		String localizacion = panelAnadir.getLblLocalizacion().getText();
		int cantidad = Integer.parseInt(panelAnadir.getTxtStock().getText().trim());

		perfumesDAO.insertar(nuevoPerfume, cantidad, localizacion);

		int respuesta = JOptionPane.showConfirmDialog(ventana, "Perfume añadido correctamente.\n¿Quieres añadir otro?",
				"Perfume guardado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

		panelAnadir.limpiarFormulario();

		if (respuesta == JOptionPane.NO_OPTION) {
			cargarStock();
			ventana.mostrarVista(Constantes.VISTA_STOCK);
		}
	}

	// ── Modificar perfume (empleado) ──────────────────────────────────

	private void buscarPerfumeParaModificar() {
		panelModificar.getLblResultado().setText(" ");
		panelModificar.limpiarFeedback();

		// 1. Delegamos las validaciones de búsqueda a la vista
		Object[] datosBusqueda = panelModificar.obtenerDatosBusqueda();

		// Si la vista devuelve null, significa que falló la validación y ya mostró el
		// error
		if (datosBusqueda == null) {
			panelModificar.getPanelModificacion().setVisible(false);
			return;
		}

		String nombre = (String) datosBusqueda[0];
		int ml = (Integer) datosBusqueda[1];

		// 2. Buscar el perfume en la BBDD
		InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl(nombre, ml);

		if (info == null) {
			panelModificar.mostrarError("No se ha encontrado ningún perfume con esos datos.");
			panelModificar.getPanelModificacion().setVisible(false);
			return;
		}

		// 3. Mostrar panel de modificación indicando éxito
		panelModificar.getLblResultado().setText("¡Perfume encontrado!");
		panelModificar.getLblResultado().setForeground(Tema.EXITO);
		perfumeEnEdicion = info;

		// 4. Mostrar los datos en la vista
		panelModificar.getLblDatosPerfume().setText(info.getPerfume().getNombre() + " - " + info.getPerfume().getMarca()
				+ " (" + info.getPerfume().getMl() + "ml)");
		panelModificar.getLblPrecioActual().setText("Precio actual: " + info.getPerfume().getPrecio() + "€");
		panelModificar.getLblStockActual().setText("Stock actual: " + info.getStock().getCantidad() + " uds");

		// 5. Vaciar los campos de entrada y mostrar el panel de modificación
		panelModificar.getTxtNuevoPrecio().setText("");
		panelModificar.getTxtCantidadAnadir().setText("");
		panelModificar.getPanelModificacion().setVisible(true);
	}

	private void guardarModificacionPerfume() {
		panelModificar.limpiarFeedback();

		if (perfumeEnEdicion == null) {
			panelModificar.mostrarError("Error interno: No hay perfume seleccionado.");
			return;
		}

		int stockActual = perfumeEnEdicion.getStock().getCantidad();

		// 1. Delegamos las validaciones de los nuevos datos a la vista
		Double[] datosModificados = panelModificar.obtenerDatosModificados(stockActual);

		// Si es null, la vista ya ha mostrado sus propios mensajes de error
		if (datosModificados == null) {
			return;
		}

		Double nuevoPrecio = datosModificados[0];
		Integer nuevaCantidad = null;

		// Si en el índice 1 hay un dato, calculamos el stock resultante
		if (datosModificados[1] != null) {
			nuevaCantidad = stockActual + datosModificados[1].intValue();
		}

		// 2. JOption de confirmación con el resumen de los cambios
		String resumen = "¿Confirmas los siguientes cambios?\n\n";
		if (nuevoPrecio != null) {
			resumen += "• Precio: " + perfumeEnEdicion.getPerfume().getPrecio() + "€ → " + nuevoPrecio + "€\n";
		}
		if (nuevaCantidad != null) {
			resumen += "• Stock: " + stockActual + " uds → " + nuevaCantidad + " uds\n";
		}

		int respuesta = JOptionPane.showConfirmDialog(ventana, resumen, "Confirmar modificación",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}

		// 3. Guardar en la BBDD
		int idPerfume = perfumeEnEdicion.getPerfume().getIdPerfume();
		int idStock = perfumeEnEdicion.getStock().getId();
		perfumesDAO.actualizarPrecioYStock(idPerfume, idStock, nuevoPrecio, nuevaCantidad);

		// 4. JOption informativo + limpiar + ir a Stock para ver el cambio
		JOptionPane.showMessageDialog(ventana, "Perfume modificado correctamente.", "Modificación guardada",
				JOptionPane.INFORMATION_MESSAGE);

		panelModificar.limpiarFormulario();
		perfumeEnEdicion = null;
		cargarStock();
		ventana.mostrarVista(Constantes.VISTA_STOCK);
	}

	// ── Utilidades de validación ──────────────────────────────────────

	private boolean emailEsValido(String email) {
		int posArroba = email.indexOf('@');
		int ultimaArroba = email.lastIndexOf('@');
		if (posArroba == -1 || posArroba != ultimaArroba) {
			return false;
		}

		String antes = email.substring(0, posArroba);
		if (antes.isEmpty()) {
			return false;
		}

		String despues = email.substring(posArroba + 1);
		int posPunto = despues.indexOf('.');
		if (posPunto == -1) {
			return false;
		}
		if (posPunto == 0 || posPunto == despues.length() - 1) {
			return false;
		}

		return true;
	}
}