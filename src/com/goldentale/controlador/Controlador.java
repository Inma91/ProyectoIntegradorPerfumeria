package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.CarritoCompra;
import com.goldentale.model.db.InfoPerfumeConStock;
import com.goldentale.model.db.LineaPedido;
import com.goldentale.model.db.Pedido;
import com.goldentale.model.db.PedidosDAO;
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
	private boolean passwordLoginVisible = false;
	private boolean passwordRegistroVisible = false;

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
	private PedidosDAO pedidosDAO;

	// Perfume que se está editando en la pantalla de Modificar
	private InfoPerfumeConStock perfumeEnEdicion;

	// Líneas del carrito del cliente que tiene la sesión iniciada (en memoria)
	private ArrayList<CarritoCompra> carritoActivo = new ArrayList<CarritoCompra>();

	public Controlador(VPGoldenTale ventana) {
		this.ventana = ventana;
		usuarioDAO = new UsuarioDAO();
		perfumesDAO = new PerfumesDAO();
		pedidosDAO = new PedidosDAO();
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
				mostrarPasswordLogin();

				// ── Registro ──────────────────────────────────────────────
			} else if (ev.getSource().equals(panelRegistro.getBtnRegistrar())) {
				procesarRegistro();

			} else if (ev.getSource().equals(panelRegistro.getBtnLimpiar())) {
				panelRegistro.limpiarFormulario();

			} else if (ev.getSource().equals(panelRegistro.getBtnMostrarPassword())) {
				mostrarPasswordRegistro();

				// ── Sidebar cliente ───────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnClienteCatalogo())) {
				cargarCatalogoCliente();
				ventana.mostrarVista(Constantes.VISTA_CATALOGO);

			} else if (ev.getSource().equals(panelCatalogo.getBtnVerCarrito())) {
				cargarCarritoCliente();
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(ventana.getBtnClienteMisPedidos())) {
			    cargarMisPedidos();
			    ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

			} else if (ev.getSource().equals(ventana.getBtnClienteCerrarSesion())) {
				cerrarSesion();

				// ── Catálogo cliente ──────────────────────────────────────

			} else if (ev.getSource().equals(panelCatalogo.getBtnFiltrar())) {
				filtrarCatalogoCliente();

			} else if (ev.getSource().equals(panelCatalogo.getBtnAnadirCarrito())) {
				anadirAlCarrito();

			} else if (ev.getSource().equals(panelCatalogo.getBtnMisPedidos())) {
			    cargarMisPedidos();
			    ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

				// ── Carrito ───────────────────────────────────────────────
			} else if (ev.getSource().equals(panelCarrito.getBtnEliminarLinea())) {
				eliminarLineaCarrito();

			} else if (ev.getSource().equals(panelCarrito.getBtnVaciarCarrito())) {
				vaciarCarritoCompleto();

			} else if (ev.getSource().equals(panelCarrito.getBtnFinalizarCompra())) {
				finalizarCompraCarrito();

				// ── Mis pedidos ───────────────────────────────────────────
			} else if (ev.getSource().equals(panelMisPedidos.getBtnVerDetalle())) {
			    mostrarDetallePedido();

			} else if (ev.getSource().equals(panelMisPedidos.getBtnFiltrar())) {
			    filtrarMisPedidos();

				// ── Pago ──────────────────────────────────────────────────
			} else if (ev.getSource().equals(panelPago.getBtnConfirmarPago())) {
				procesarPago();

			} else if (ev.getSource().equals(panelPago.getBtnCancelar())) {
				panelPago.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

				// ── Sidebar empleado ──────────────────────────────────────
			} else if (ev.getSource().equals(ventana.getBtnEmpleadoDashboard())) {
				panelAnadir.limpiarFormulario();
				panelModificar.limpiarFormulario();
				panelStock.limpiarFiltros();
				perfumeEnEdicion = null;
				ventana.mostrarVista(Constantes.VISTA_DASHBOARD);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoAnadir())) {
				panelModificar.limpiarFormulario();
				panelStock.limpiarFiltros();
				perfumeEnEdicion = null;
				ventana.mostrarVista(Constantes.VISTA_ANADIR);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoModificar())) {
				panelAnadir.limpiarFormulario();
				panelStock.limpiarFiltros();
				ventana.mostrarVista(Constantes.VISTA_MODIFICAR);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoStock())) {
				panelAnadir.limpiarFormulario();
				panelModificar.limpiarFormulario();
				panelStock.limpiarFiltros();
				perfumeEnEdicion = null;
				cargarStock();
				ventana.mostrarVista(Constantes.VISTA_STOCK);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoCerrarSesion())) {
				cerrarSesion();

				// ── Dashboard empleado (accesos rápidos) ──────────────────
			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoAnadirPerfume())) {
				panelModificar.limpiarFormulario();
				panelStock.limpiarFiltros();
				perfumeEnEdicion = null;
				ventana.mostrarVista(Constantes.VISTA_ANADIR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoModificarPerfume())) {
				panelAnadir.limpiarFormulario();
				panelStock.limpiarFiltros();
				ventana.mostrarVista(Constantes.VISTA_MODIFICAR);

			} else if (ev.getSource().equals(panelDashboard.getBtnAccesoStock())) {
				panelAnadir.limpiarFormulario();
				panelModificar.limpiarFormulario();
				perfumeEnEdicion = null;
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
			cargarCatalogoCliente();
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

		// Limpiar todas las vistas para que la próxima sesión empiece desde cero
		panelLogin.limpiarFormulario();
		panelRegistro.limpiarFormulario();
		panelAnadir.limpiarFormulario();
		panelModificar.limpiarFormulario();
		panelStock.limpiarFiltros();
		panelPago.limpiarFormulario();
		panelMisPedidos.limpiarVista();

		// Resetear el atributo del controlador
		perfumeEnEdicion = null;
		carritoActivo.clear();

		// Cerrar sesión y volver al inicio
		Constantes.usuarioAutenticado = null;
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
		cargarCatalogoCliente();
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
			panelModificar.getLblResultado().setText("No se ha encontrado ningún perfume con esos datos.");
			panelModificar.getLblResultado().setForeground(Tema.ERROR);
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

		// 1. Validar precio
		Double nuevoPrecio = panelModificar.obtenerNuevoPrecio();
		if (panelModificar.tieneError()) {
			return;
		}

		// 2. Validar cantidad
		Integer cantidadASumar = panelModificar.obtenerCantidadASumar(stockActual);
		if (panelModificar.tieneError()) {
			return;
		}

		// 3. Si los dos son null (los dos campos vacíos), no hay nada que modificar
		if (nuevoPrecio == null && cantidadASumar == null) {
			panelModificar.getLblResultado().setText("Rellena al menos un campo para modificar.");
			panelModificar.getLblResultado().setForeground(Tema.ERROR);
			return;
		}

		// 4. Calcular la cantidad final si hay que modificar el stock
		Integer nuevaCantidad = null;
		if (cantidadASumar != null) {
			nuevaCantidad = stockActual + cantidadASumar;
		}
		
		if (nuevaCantidad != null && nuevaCantidad < 0) {

			panelModificar.mostrarError(
					"El stock final no puede ser negativo.");

			return;
		}

		// 5. JOption de confirmación con el resumen de los cambios
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

		// 6. Guardar en la BBDD
		int idPerfume = perfumeEnEdicion.getPerfume().getIdPerfume();
		int idStock = perfumeEnEdicion.getStock().getId();
		perfumesDAO.actualizarPrecioYStock(idPerfume, idStock, nuevoPrecio, nuevaCantidad);

		// 7. JOption informativo + limpiar + ir a Stock
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

	// CLIENTES
	// ── Catálogo Cliente (Mostrando disponibilidad comercial en vez de stock
	// numérico) ──

	private void cargarCatalogoCliente() {
		ArrayList<InfoPerfumeConStock> listaPerfumes = perfumesDAO.getInfoPerfumesConStock();
		Object[][] filas = construirFilasCatalogo(listaPerfumes);
		panelCatalogo.mostrarCatalogo(filas);
	}

	// ── Filtrar Catálogo Cliente (Búsqueda parcial y Categorías) ──────

	private void filtrarCatalogoCliente() {
		// 1. Capturar los valores de los filtros desde la vista
		String nombre = panelCatalogo.getTxtBuscar().getText().trim();

		// Si el usuario no ha escrito nada o se mantiene el texto por defecto, lo
		// tratamos como vacío
		if (nombre.equals("Buscar perfume o marca...")) {
			nombre = "";
		}

		String categoria = (String) panelCatalogo.getComboCategoria().getSelectedItem();

		ArrayList<InfoPerfumeConStock> lista;

		// 2. Evaluar combinaciones según el texto y el combo
		boolean sinNombre = nombre.isEmpty();
		boolean todasCategorias = categoria.equals("Todas las categorías");

		if (sinNombre && todasCategorias) {
			lista = perfumesDAO.getInfoPerfumesConStock();
		} else if (sinNombre && !todasCategorias) {
			lista = perfumesDAO.getInfoCatalogoPorCategoria(categoria);
		} else if (!sinNombre && todasCategorias) {
			lista = perfumesDAO.getInfoTablaPorNombre(nombre);
		} else {
			lista = perfumesDAO.getInfoCatalogoPorNombreYCategoria(nombre, categoria);
		}

		// 3. Pintar los resultados
		Object[][] filas = construirFilasCatalogo(lista);
		panelCatalogo.mostrarCatalogo(filas);
	}

	/**
	 * Construye la matriz de filas del catálogo a partir de una lista de perfumes
	 * con stock. Convierte el stock numérico en "Disponible"/"Agotado" para que el
	 * cliente no vea las unidades reales del almacén.
	 */
	private Object[][] construirFilasCatalogo(ArrayList<InfoPerfumeConStock> lista) {
		Object[][] filas = new Object[lista.size()][7];

		for (int i = 0; i < lista.size(); i++) {
			InfoPerfumeConStock info = lista.get(i);
			int cantidadStock = info.getStock().getCantidad();
			String disponibilidad = (cantidadStock > 0) ? "Disponible" : "Agotado";

			filas[i][0] = info.getPerfume().getNombre();
			filas[i][1] = info.getPerfume().getMarca();
			filas[i][2] = info.getPerfume().getCategoria();
			filas[i][3] = info.getPerfume().getPublico();
			filas[i][4] = info.getPerfume().getMl();
			filas[i][5] = info.getPerfume().getPrecio();
			filas[i][6] = disponibilidad;
		}

		return filas;
	}

	// ── Acción: Añadir al carrito desde el Catálogo ───────────────────

	private void anadirAlCarrito() {
		// 1. Obtener la fila seleccionada en la tabla del catálogo
		int filaSeleccionada = panelCatalogo.getTablaCatalogo().getSelectedRow();

		// Si no hay ninguna fila seleccionada, avisamos al usuario y paramos
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Por favor, selecciona un perfume del catálogo.",
					"Ningún producto seleccionado", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 2. Recuperar los datos clave de la fila para identificar el perfume en la
		// BBDD
		// Según vuestro modelo: Nombre está en la col 0, y los mililitros (ml) en la
		// col 4
		String nombre = (String) panelCatalogo.getTablaCatalogo().getValueAt(filaSeleccionada, 0);
		int ml = Integer.parseInt(panelCatalogo.getTablaCatalogo().getValueAt(filaSeleccionada, 4).toString());

		// 3. Buscar el perfume completo con su stock real en la BBDD a través de
		// vuestro DAO
		InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl(nombre, ml);

		if (info == null) {
			JOptionPane.showMessageDialog(ventana, "Error al recuperar la información del producto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int stockDisponible = info.getStock().getCantidad();

		// Si el producto está agotado en almacén, no permitimos continuar
		if (stockDisponible <= 0) {
			JOptionPane.showMessageDialog(ventana, "Lo sentimos, este perfume está temporalmente agotado.", "Sin stock",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 4. Preguntar al cliente cuántas unidades desea comprar
		String inputCantidad = JOptionPane.showInputDialog(ventana,
				"¿Cuántas unidades de '" + info.getPerfume().getNombre() + "' deseas añadir al carrito?",
				"Añadir al carrito", JOptionPane.QUESTION_MESSAGE);

		// Si el usuario cancela o cierra el diálogo, salimos sin hacer nada
		if (inputCantidad == null) {
			return;
		}

		// 5. Validar que la cantidad introducida sea un número entero coherente
		int cantidadElegida;
		try {
			cantidadElegida = Integer.parseInt(inputCantidad.trim());
			if (cantidadElegida <= 0) {
				JOptionPane.showMessageDialog(ventana, "La cantidad debe ser un número mayor que 0.",
						"Cantidad no válida", JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ventana, "Por favor, introduce un número entero válido.",
					"Cantidad no válida", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 6. Verificar si hay suficiente stock físico en el almacén de la BBDD
		if (cantidadElegida > stockDisponible) {
			JOptionPane.showMessageDialog(ventana,
					"No es posible añadir esa cantidad. Solo quedan " + stockDisponible + " unidades en stock.",
					"Stock insuficiente", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 7. Añadir el producto al carrito activo en memoria (carritoActivo)
		// Comprobamos primero si este perfume ya estaba en el carrito para sumarle la
		// cantidad
		boolean existeEnCarrito = false;
		for (CarritoCompra item : carritoActivo) {
			if (item.getPerfume().getIdPerfume() == info.getPerfume().getIdPerfume()) {
				int nuevaCantidadTotal = item.getCantidad() + cantidadElegida;

				// Validamos que la suma de lo que ya tenía más lo nuevo no supere el stock real
				if (nuevaCantidadTotal > stockDisponible) {
					JOptionPane.showMessageDialog(ventana, "Lo sentimos, no hay suficiente stock de este producto",
							"Stock insuficiente", JOptionPane.WARNING_MESSAGE);
					return;
				}

				item.setCantidad(nuevaCantidadTotal);
				existeEnCarrito = true;
				break;
			}
		}

		// Si es la primera vez que añade este perfume en esta sesión, creamos una nueva
		// línea
		if (!existeEnCarrito) {
			// ID temporal en memoria basado en el tamaño actual de la lista
			int idCarritoTemporal = carritoActivo.size() + 1;

			CarritoCompra nuevaLinea = new CarritoCompra(idCarritoTemporal, Constantes.usuarioAutenticado,
					info.getPerfume(), cantidadElegida, info.getPerfume().getPrecio());
			carritoActivo.add(nuevaLinea);
		}

		// 8. Confirmar el éxito de la operación al cliente
		JOptionPane.showMessageDialog(ventana, "Producto añadido con éxito", "Producto añadido",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// ── Cargar los datos reales en la vista del Carrito de la Compra ──
	private void cargarCarritoCliente() {
		Object[][] filas = new Object[carritoActivo.size()][4];
		double totalPrecioCarrito = 0;

		for (int i = 0; i < carritoActivo.size(); i++) {
			CarritoCompra item = carritoActivo.get(i);
			double precioUnitario = item.getPrecioUnidad();
			double subtotal = item.getCantidad() * precioUnitario;
			totalPrecioCarrito += subtotal;

			filas[i][0] = item.getPerfume().getNombre();
			filas[i][1] = String.valueOf(item.getCantidad());
			filas[i][2] = String.format("%.2f EUR", precioUnitario);
			filas[i][3] = String.format("%.2f EUR", subtotal);
		}

		panelCarrito.mostrarCarrito(filas, totalPrecioCarrito);
	}

	// ── Acción: Eliminar la línea seleccionada del Carrito ─────────────

	private void eliminarLineaCarrito() {
		// 1. Obtener la fila seleccionada en la tabla del carrito
		int filaSeleccionada = panelCarrito.getTablaCarrito().getSelectedRow();

		// Si el usuario no ha seleccionado ninguna fila, le avisamos
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Por favor, selecciona el perfume que deseas quitar del carrito.",
					"Ninguna línea seleccionada", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 2. Confirmar si el usuario está seguro de querer eliminar el producto
		int confirmacion = JOptionPane.showConfirmDialog(ventana,
				"¿Estás seguro de que deseas eliminar este perfume de tu carrito?", "Eliminar producto",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			// 3. Eliminar el elemento de la lista en memoria (carritoActivo)
			// Como la fila de la tabla coincide exactamente con la posición en la lista,
			// borramos por índice
			carritoActivo.remove(filaSeleccionada);

			// 4. Avisar al usuario con un mensaje limpio
			JOptionPane.showMessageDialog(ventana, "El perfume ha sido eliminado del carrito.", "Producto eliminado",
					JOptionPane.INFORMATION_MESSAGE);

			// 5. Refrescar la pantalla llamando al método que ya creamos antes
			// Esto vacía la tabla, vuelve a leer la lista actualizada y recalcula el total
			// automáticamente
			cargarCarritoCliente();
		}
	}

	// ── Acción: Vaciar por completo el Carrito de Compra ───────────────
	private void vaciarCarritoCompleto() {
		// 1. Si el carrito ya está vacío, no hace falta hacer nada
		if (carritoActivo.isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Tu carrito ya está vacío.", "Carrito vacío",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// 2. Pedir confirmación al usuario para evitar descuidos
		int confirmacion = JOptionPane.showConfirmDialog(ventana,
				"¿Estás seguro de que deseas eliminar todos los productos de tu carrito?", "Vaciar carrito",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			// 3. Borrar todos los elementos de la lista en memoria
			carritoActivo.clear();

			// 4. Avisar al usuario con un mensaje limpio
			JOptionPane.showMessageDialog(ventana, "El carrito ha sido vaciado correctamente.", "Carrito vaciado",
					JOptionPane.INFORMATION_MESSAGE);

			// 5. Refrescar la pantalla para que la tabla se limpie y el total vuelva a
			// 0.0EUR
			cargarCarritoCliente();
		}
	}


	// ── Acción: Finalizar la compra y navegar a la vista de Pago ──────

		private void finalizarCompraCarrito() {
			// 1. Verificar si el carrito está vacío antes de continuar
			if (carritoActivo.isEmpty()) {
				JOptionPane.showMessageDialog(ventana, "No puedes finalizar la compra porque tu carrito está vacío.",
						"Carrito vacío", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// 2. Calcular el total acumulado
			double totalPrecioCarrito = 0;
			int numItems = 0;
			for (CarritoCompra item : carritoActivo) {
				totalPrecioCarrito += item.getCantidad() * item.getPrecioUnidad();
				numItems += item.getCantidad();
			}

			// 3. Rellenar el resumen y el total en la vista de Pago
			panelPago.limpiarFormulario();
			panelPago.mostrarResumen(numItems, totalPrecioCarrito);

			// 4. Navegar a la vista de Pago
			ventana.mostrarVista(Constantes.VISTA_PAGO);
		}
		
	// ── Acción: Procesar el pago y registrar la compra en la BBDD ─────

		private void procesarPago() {
			// 1. Validar los datos del formulario (la vista valida internamente)
			Object[] datosPago = panelPago.obtenerDatos();

			if (datosPago == null) {
				return; // La vista ya mostró su error
			}

			String formaPago = (String) datosPago[0];
			String direccionEntrega = (String) datosPago[1];

			// 2. Recalcular el total (por si acaso, aunque ya lo tenemos en la vista)
			double totalPrecioCarrito = 0;
			for (CarritoCompra item : carritoActivo) {
				totalPrecioCarrito += item.getCantidad() * item.getPrecioUnidad();
			}

			// 3. JOption de confirmación con el resumen
			String mensaje = "¿Confirmas el pago de " + String.format("%.2f EUR", totalPrecioCarrito) + " con " + formaPago
					+ "?\n\nDirección de entrega:\n" + direccionEntrega;

			int respuesta = JOptionPane.showConfirmDialog(ventana, mensaje, "Confirmar pago", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (respuesta != JOptionPane.YES_OPTION) {
				return;
			}

			// 4. Guardar el pedido en la BBDD a través del DAO
			int idUsuario = Constantes.usuarioAutenticado.getIdUsuario();
			
			// Revalidar stock real antes de procesar el pedido
			for (CarritoCompra item : carritoActivo) {

				int stockReal = perfumesDAO.obtenerStockDisponible(
						item.getPerfume().getIdPerfume());

				if (item.getCantidad() > stockReal) {

					JOptionPane.showMessageDialog(
							ventana,
							"El perfume '" + item.getPerfume().getNombre()
									+ "' ya no tiene suficiente stock disponible.\n"
									+ "Stock actual: " + stockReal + " unidades.",
							"Stock insuficiente",
							JOptionPane.WARNING_MESSAGE);

					cargarCatalogoCliente();
					return;
				}
			}
			
			boolean exitoBBDD = pedidosDAO.insertarPedido(idUsuario, totalPrecioCarrito, formaPago, direccionEntrega,
					carritoActivo);

			if (exitoBBDD) {
				// 5. Si todo fue bien
				JOptionPane.showMessageDialog(ventana,
						"¡Tu compra ha sido procesada con éxito!\nGracias por confiar en Golden Tale.", "Compra finalizada",
						JOptionPane.INFORMATION_MESSAGE);

				// Vaciamos el carrito y limpiamos la vista de pago
				carritoActivo.clear();
				panelPago.limpiarFormulario();

				// Refrescamos el catálogo (el stock ha cambiado) y volvemos a él
				cargarCatalogoCliente();
				ventana.mostrarVista(Constantes.VISTA_CATALOGO);

			} else {
				// 6. Si hubo un error técnico
				JOptionPane.showMessageDialog(ventana,
						"Hubo un problema al procesar tu pedido.\nTu carrito sigue intacto, puedes intentarlo de nuevo.",
						"Error en la compra", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// ── Mis pedidos (cliente) ─────────────────────────────────────────

		/**
		 * Carga todos los pedidos del cliente autenticado en la vista de Mis pedidos.
		 */
		private void cargarMisPedidos() {
			// Resetear filtro al estado por defecto
			panelMisPedidos.getComboFiltroEstado().setSelectedIndex(0);

			int idUsuario = Constantes.usuarioAutenticado.getIdUsuario();
			ArrayList<Pedido> lista = pedidosDAO.getPedidosPorUsuario(idUsuario);

			Object[][] filas = construirFilasPedidos(lista);
			panelMisPedidos.mostrarPedidos(filas);

			// También limpiamos la tabla de detalle, por si venimos de una sesión anterior
			panelMisPedidos.mostrarDetalle(0, new Object[0][4]);
			panelMisPedidos.getLblPedidoSeleccionado().setText("Detalle del pedido seleccionado");
		}

		/**
		 * Filtra los pedidos del cliente según el estado seleccionado en el combo.
		 */
		private void filtrarMisPedidos() {
			String estado = (String) panelMisPedidos.getComboFiltroEstado().getSelectedItem();
			int idUsuario = Constantes.usuarioAutenticado.getIdUsuario();

			ArrayList<Pedido> lista;
			if (estado.equals("Todos")) {
				lista = pedidosDAO.getPedidosPorUsuario(idUsuario);
			} else {
				lista = pedidosDAO.getPedidosPorUsuarioYEstado(idUsuario, estado);
			}

			Object[][] filas = construirFilasPedidos(lista);
			panelMisPedidos.mostrarPedidos(filas);

			// Limpiar el detalle al cambiar el filtro
			panelMisPedidos.mostrarDetalle(0, new Object[0][4]);
			panelMisPedidos.getLblPedidoSeleccionado().setText("Detalle del pedido seleccionado");
		}

		/**
		 * Construye la matriz de filas de la tabla de pedidos a partir de una lista
		 * de pedidos. Las columnas siguen el orden de Constantes.COLS_MIS_PEDIDOS:
		 * Ref, Fecha, Estado, Total.
		 */
		private Object[][] construirFilasPedidos(ArrayList<Pedido> lista) {
			Object[][] filas = new Object[lista.size()][4];

			for (int i = 0; i < lista.size(); i++) {
				Pedido p = lista.get(i);
				filas[i][0] = "#" + p.getIdPedido();
				filas[i][1] = p.getFecha();
				filas[i][2] = p.getEstado();
				filas[i][3] = String.format("%.2f EUR", p.getTotal());
			}

			return filas;
		}

		/**
		 * Muestra el detalle (líneas) del pedido seleccionado en la tabla principal.
		 */
		private void mostrarDetallePedido() {
			int filaSeleccionada = panelMisPedidos.getTablaPedidos().getSelectedRow();

			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(ventana, "Selecciona un pedido para ver su detalle.",
						"Ningún pedido seleccionado", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Extraer el id del pedido de la columna "Ref." (formato "#123")
			String referencia = (String) panelMisPedidos.getTablaPedidos().getValueAt(filaSeleccionada, 0);
			int idPedido = Integer.parseInt(referencia.substring(1)); // quitamos el '#'

			// Recuperar las líneas del pedido desde la BBDD
			ArrayList<LineaPedido> lineas = pedidosDAO.getLineasPorPedido(idPedido);

			// Construir las filas y pintar en la vista
			Object[][] filas = new Object[lineas.size()][4];
			for (int i = 0; i < lineas.size(); i++) {
				LineaPedido linea = lineas.get(i);
				filas[i][0] = linea.getPerfume().getNombre() + " (" + linea.getPerfume().getMl() + "ml)";
				filas[i][1] = linea.getCantidad();
				filas[i][2] = String.format("%.2f EUR", linea.getPrecioUnitario());
				filas[i][3] = String.format("%.2f EUR", linea.getSubtotal());
			}

			panelMisPedidos.mostrarDetalle(idPedido, filas);
		}
  
  // ── Mostrar/ocultar contraseña ────────────────────────────────────

	private void mostrarPasswordLogin() {
		passwordLoginVisible = !passwordLoginVisible;

		if (passwordLoginVisible) {
			panelLogin.getTxtPassword().setEchoChar('\0');
			panelLogin.getBtnMostrarPassword().setText("Ocultar");
		} else {
			panelLogin.getTxtPassword().setEchoChar('•');
			panelLogin.getBtnMostrarPassword().setText("Mostrar");
		}
	}

	private void mostrarPasswordRegistro() {
		passwordRegistroVisible = !passwordRegistroVisible;

		if (passwordRegistroVisible) {
			panelRegistro.getTxtPassword().setEchoChar('\0');
			panelRegistro.getTxtConfirmarPassword().setEchoChar('\0');
			panelRegistro.getBtnMostrarPassword().setText("Ocultar");
		} else {
			panelRegistro.getTxtPassword().setEchoChar('•');
			panelRegistro.getTxtConfirmarPassword().setEchoChar('•');
			panelRegistro.getBtnMostrarPassword().setText("Mostrar");
		}
	}
}