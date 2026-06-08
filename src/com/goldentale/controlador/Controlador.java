package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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
 * <p>
 * Implementa {@link ActionListener} y actúa como punto central de gestión de
 * eventos de la interfaz. Coordina la navegación entre vistas, la lógica de
 * negocio del cliente (catálogo, carrito, pedidos, pago) y las operaciones del
 * empleado (stock, añadir y modificar perfumes).
 * </p>
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

	/** Perfume que se está editando en la pantalla de Modificar. */
	private InfoPerfumeConStock perfumeEnEdicion;

	/** Líneas del carrito del cliente con sesión activa, mantenidas en memoria. */
	private ArrayList<CarritoCompra> carritoActivo = new ArrayList<CarritoCompra>();

	/**
	 * Crea el controlador e inicializa los DAOs.
	 *
	 * @param ventana ventana principal de la aplicación
	 */
	public Controlador(VPGoldenTale ventana) {
		this.ventana = ventana;
		usuarioDAO = new UsuarioDAO();
		perfumesDAO = new PerfumesDAO();
		pedidosDAO = new PedidosDAO();
	}

	// ── Setters ───────────────────────────────────────────────────────

	/**
	 * Establece el panel de inicio de sesión.
	 *
	 * @param panelLogin panel de login
	 */
	public void setPanelLogin(VLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	/**
	 * Establece el panel de registro de usuario.
	 *
	 * @param panelRegistro panel de registro
	 */
	public void setPanelRegistro(VRegistroUsuario panelRegistro) {
		this.panelRegistro = panelRegistro;
	}

	/**
	 * Establece el panel del catálogo de cliente.
	 *
	 * @param panelCatalogo panel del catálogo
	 */
	public void setPanelCatalogo(VCatalogoCliente panelCatalogo) {
		this.panelCatalogo = panelCatalogo;
	}

	/**
	 * Establece el panel del carrito de compra.
	 *
	 * @param panelCarrito panel del carrito
	 */
	public void setPanelCarrito(VCarritoCompra panelCarrito) {
		this.panelCarrito = panelCarrito;
	}

	/**
	 * Establece el panel de mis pedidos.
	 *
	 * @param panelMisPedidos panel de pedidos del cliente
	 */
	public void setPanelMisPedidos(VMisPedidos panelMisPedidos) {
		this.panelMisPedidos = panelMisPedidos;
	}

	/**
	 * Establece el panel de pago.
	 *
	 * @param panelPago panel de pago
	 */
	public void setPanelPago(VPago panelPago) {
		this.panelPago = panelPago;
	}

	/**
	 * Establece el panel del dashboard del empleado.
	 *
	 * @param panelDashboard panel del dashboard
	 */
	public void setPanelDashboard(VEmpleadoDashboard panelDashboard) {
		this.panelDashboard = panelDashboard;
	}

	/**
	 * Establece el panel de añadir perfume.
	 *
	 * @param panelAnadir panel de alta de perfume
	 */
	public void setPanelAnadir(VAniadirPerfume panelAnadir) {
		this.panelAnadir = panelAnadir;
	}

	/**
	 * Establece el panel de modificar perfume.
	 *
	 * @param panelModificar panel de modificación de perfume
	 */
	public void setPanelModificar(VModificarPerfume panelModificar) {
		this.panelModificar = panelModificar;
	}

	/**
	 * Establece el panel de gestión de stock.
	 *
	 * @param panelStock panel de stock
	 */
	public void setPanelStock(VStock panelStock) {
		this.panelStock = panelStock;
	}

	// ── Dispatcher ────────────────────────────────────────────────────

	/**
	 * Punto de entrada de todos los eventos de botón de la aplicación.
	 * <p>
	 * Identifica el origen del evento y delega en el método privado
	 * correspondiente. Cubre los flujos de: inicio, login, registro, sidebar de
	 * cliente y empleado, catálogo, carrito, pedidos, pago, stock, alta y
	 * modificación de perfumes.
	 * </p>
	 *
	 * @param ev evento de acción generado por la interfaz
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {

		if (ev.getSource() instanceof JButton) {

			if (ev.getSource().equals(ventana.getBtnIniciarSesion())) {
				panelRegistro.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_LOGIN);

			} else if (ev.getSource().equals(ventana.getBtnRegistrarse())) {
				panelLogin.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

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

			} else if (ev.getSource().equals(panelLogin.getBtnIniciarSesion())) {
				procesarLogin();

			} else if (ev.getSource().equals(panelLogin.getBtnRegistrarse())) {
				panelLogin.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_REGISTRO);

			} else if (ev.getSource().equals(panelLogin.getBtnMostrarPassword())) {
				mostrarPasswordLogin();

			} else if (ev.getSource().equals(panelRegistro.getBtnRegistrar())) {
				procesarRegistro();

			} else if (ev.getSource().equals(panelRegistro.getBtnLimpiar())) {
				panelRegistro.limpiarFormulario();

			} else if (ev.getSource().equals(panelRegistro.getBtnMostrarPassword())) {
				mostrarPasswordRegistro();

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

			} else if (ev.getSource().equals(panelCatalogo.getBtnFiltrar())) {
				filtrarCatalogoCliente();

			} else if (ev.getSource().equals(panelCatalogo.getBtnAnadirCarrito())) {
				anadirAlCarrito();

			} else if (ev.getSource().equals(panelCatalogo.getBtnMisPedidos())) {
				cargarMisPedidos();
				ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

			} else if (ev.getSource().equals(panelCarrito.getBtnEliminarLinea())) {
				eliminarLineaCarrito();

			} else if (ev.getSource().equals(panelCarrito.getBtnVaciarCarrito())) {
				vaciarCarritoCompleto();

			} else if (ev.getSource().equals(panelCarrito.getBtnFinalizarCompra())) {
				finalizarCompraCarrito();

			} else if (ev.getSource().equals(panelMisPedidos.getBtnVerDetalle())) {
				mostrarDetallePedido();

			} else if (ev.getSource().equals(panelMisPedidos.getBtnFiltrar())) {
				filtrarMisPedidos();

			} else if (ev.getSource().equals(panelPago.getBtnConfirmarPago())) {
				procesarPago();

			} else if (ev.getSource().equals(panelPago.getBtnCancelar())) {
				panelPago.limpiarFormulario();
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(ventana.getBtnEmpleadoDashboard())) {
				panelAnadir.limpiarFormulario();
				panelModificar.limpiarFormulario();
				panelStock.limpiarFiltros();
				perfumeEnEdicion = null;
				cargarMetricasDashboard();
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

			} else if (ev.getSource().equals(panelStock.getBtnFiltrar())) {
				filtrarStock();

			} else if (ev.getSource().equals(panelAnadir.getBtnGuardar())) {
				procesarAnadirPerfume();

			} else if (ev.getSource().equals(panelAnadir.getBtnLimpiar())) {
				panelAnadir.limpiarFormulario();

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

	/**
	 * Valida las credenciales introducidas en el formulario de login y, si son
	 * correctas, redirige al usuario a su vista según su rol (cliente o empleado).
	 * <p>
	 * Muestra mensajes de error si el email o la contraseña están vacíos, el
	 * formato del email no es válido o las credenciales no coinciden con ningún
	 * usuario registrado.
	 * </p>
	 */
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
			cargarMetricasDashboard();
			ventana.mostrarVista(Constantes.VISTA_DASHBOARD);
		}
	}

	// ── Cerrar sesión ─────────────────────────────────────────────────

	/**
	 * Solicita confirmación al usuario y, si acepta, cierra la sesión activa.
	 * <p>
	 * Limpia todos los formularios y vistas, vacía el carrito en memoria,
	 * restablece el sidebar al estado pre-login y navega a la vista de inicio.
	 * </p>
	 */
	private void cerrarSesion() {
		int confirm = JOptionPane.showConfirmDialog(ventana, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
				JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		panelLogin.limpiarFormulario();
		panelRegistro.limpiarFormulario();
		panelAnadir.limpiarFormulario();
		panelModificar.limpiarFormulario();
		panelStock.limpiarFiltros();
		panelPago.limpiarFormulario();
		panelMisPedidos.limpiarVista();

		perfumeEnEdicion = null;
		carritoActivo.clear();

		Constantes.usuarioAutenticado = null;
		ventana.mostrarSidebarPreLogin(this);
		ventana.getLblNavEstado().setText("Bienvenido a " + Constantes.TITULO_APLICACION);
		ventana.mostrarVista(Constantes.VISTA_INICIO);
	}

	// ── Registro ──────────────────────────────────────────────────────

	/**
	 * Procesa el formulario de registro de un nuevo cliente.
	 * <p>
	 * Delega la validación de campos a la vista. Comprueba además que las
	 * contraseñas coincidan y que el correo no esté ya registrado. Si todo es
	 * correcto, persiste el usuario, inicia sesión automáticamente y navega al
	 * catálogo.
	 * </p>
	 */
	private void procesarRegistro() {
		Usuario nuevoUsuario = panelRegistro.obtenerDatos();

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

	/**
	 * Carga todos los perfumes con su stock desde la base de datos y los muestra en
	 * la vista de stock junto con las métricas calculadas.
	 */
	private void cargarStock() {
		ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoPerfumesConStock();
		pintarTablaYMetricas(lista);
	}

	/**
	 * Aplica los filtros de nombre, ubicación y estado seleccionados en la vista de
	 * stock y actualiza la tabla con los resultados obtenidos.
	 */
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

	/**
	 * Filtra una lista de perfumes con stock según el estado de disponibilidad.
	 *
	 * @param lista  lista original de perfumes con stock
	 * @param estado estado por el que filtrar: {@code "Todos los estados"},
	 *               {@code "Con stock"}, {@code "Stock bajo"} o {@code "Sin stock"}
	 * @return lista filtrada según el estado indicado
	 */
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

	/**
	 * Construye la matriz de filas para la tabla de stock y actualiza las métricas
	 * del panel (total de referencias, artículos con stock bajo y artículos sin
	 * stock).
	 *
	 * @param lista lista de perfumes con stock a representar
	 */
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

	/**
	 * Calcula la etiqueta de estado de stock a partir de una cantidad numérica.
	 *
	 * @param cantidad unidades disponibles en almacén
	 * @return {@code "Sin stock"} si es 0, {@code "Stock bajo"} si está por debajo
	 *         del umbral definido en {@link Constantes#STOCK_MINIMO_ALERTA}, o
	 *         {@code "OK"} en caso contrario
	 */
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

	/**
	 * Recoge los datos del formulario de alta de perfume, comprueba que no exista
	 * un duplicado (mismo nombre y mililitros) y, si la validación es correcta,
	 * persiste el nuevo perfume con su stock inicial.
	 * <p>
	 * Tras guardar, pregunta al empleado si desea añadir otro perfume. En caso
	 * negativo, navega automáticamente a la vista de stock.
	 * </p>
	 */
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

	/**
	 * Busca en la base de datos el perfume cuyo nombre y mililitros coincidan con
	 * los introducidos en el formulario de búsqueda.
	 * <p>
	 * Si se encuentra, muestra los datos actuales del perfume y hace visible el
	 * panel de modificación. Si no, informa al usuario y oculta dicho panel.
	 * </p>
	 */
	private void buscarPerfumeParaModificar() {
		panelModificar.getLblResultado().setText(" ");
		panelModificar.limpiarFeedback();

		Object[] datosBusqueda = panelModificar.obtenerDatosBusqueda();

		if (datosBusqueda == null) {
			panelModificar.getPanelModificacion().setVisible(false);
			return;
		}

		String nombre = (String) datosBusqueda[0];
		int ml = (Integer) datosBusqueda[1];

		InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl(nombre, ml);

		if (info == null) {
			panelModificar.getLblResultado().setText("No se ha encontrado ningún perfume con esos datos.");
			panelModificar.getLblResultado().setForeground(Tema.ERROR);
			panelModificar.getPanelModificacion().setVisible(false);
			return;
		}

		panelModificar.getLblResultado().setText("¡Perfume encontrado!");
		panelModificar.getLblResultado().setForeground(Tema.EXITO);
		perfumeEnEdicion = info;

		panelModificar.getLblDatosPerfume().setText(info.getPerfume().getNombre() + " - " + info.getPerfume().getMarca()
				+ " (" + info.getPerfume().getMl() + "ml)");
		panelModificar.getLblPrecioActual().setText("Precio actual: " + info.getPerfume().getPrecio() + "€");
		panelModificar.getLblStockActual().setText("Stock actual: " + info.getStock().getCantidad() + " uds");

		panelModificar.getTxtNuevoPrecio().setText("");
		panelModificar.getTxtCantidadAnadir().setText("");
		panelModificar.getPanelModificacion().setVisible(true);
	}

	/**
	 * Valida y persiste los cambios de precio y/o stock sobre el perfume
	 * actualmente en edición ({@link #perfumeEnEdicion}).
	 * <p>
	 * Requiere que al menos uno de los dos campos esté relleno. Impide que el stock
	 * resultante sea negativo. Muestra un resumen de los cambios al empleado antes
	 * de confirmar la operación y, tras guardar, navega a la vista de stock.
	 * </p>
	 */
	private void guardarModificacionPerfume() {
		panelModificar.limpiarFeedback();

		if (perfumeEnEdicion == null) {
			panelModificar.mostrarError("Error interno: No hay perfume seleccionado.");
			return;
		}

		int stockActual = perfumeEnEdicion.getStock().getCantidad();

		Double nuevoPrecio = panelModificar.obtenerNuevoPrecio();
		if (panelModificar.tieneError()) {
			return;
		}

		Integer cantidadASumar = panelModificar.obtenerCantidadASumar(stockActual);
		if (panelModificar.tieneError()) {
			return;
		}

		if (nuevoPrecio == null && cantidadASumar == null) {
			panelModificar.getLblResultado().setText("Rellena al menos un campo para modificar.");
			panelModificar.getLblResultado().setForeground(Tema.ERROR);
			return;
		}

		Integer nuevaCantidad = null;
		if (cantidadASumar != null) {
			nuevaCantidad = stockActual + cantidadASumar;
		}

		if (nuevaCantidad != null && nuevaCantidad < 0) {
			panelModificar.mostrarError("El stock final no puede ser negativo.");
			return;
		}

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

		int idPerfume = perfumeEnEdicion.getPerfume().getIdPerfume();
		int idStock = perfumeEnEdicion.getStock().getId();
		perfumesDAO.actualizarPrecioYStock(idPerfume, idStock, nuevoPrecio, nuevaCantidad);

		JOptionPane.showMessageDialog(ventana, "Perfume modificado correctamente.", "Modificación guardada",
				JOptionPane.INFORMATION_MESSAGE);

		panelModificar.limpiarFormulario();
		perfumeEnEdicion = null;
		cargarStock();
		ventana.mostrarVista(Constantes.VISTA_STOCK);
	}

	// ── Utilidades de validación ──────────────────────────────────────

	/**
	 * Comprueba que una cadena tenga formato de correo electrónico válido.
	 * <p>
	 * Verifica que exista exactamente un símbolo {@code @}, que haya texto antes de
	 * él, y que la parte del dominio contenga un punto que no sea ni el primer ni
	 * el último carácter.
	 * </p>
	 *
	 * @param email cadena a validar
	 * @return {@code true} si el formato es válido; {@code false} en caso contrario
	 */
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

	// ── Catálogo cliente ──────────────────────────────────────────────

	/**
	 * Carga el catálogo completo de perfumes disponibles y lo muestra en la vista
	 * del cliente. El stock numérico se convierte en disponibilidad comercial
	 * ("Disponible" / "Agotado").
	 */
	private void cargarCatalogoCliente() {
		ArrayList<InfoPerfumeConStock> listaPerfumes = perfumesDAO.getInfoPerfumesConStock();
		Object[][] filas = construirFilasCatalogo(listaPerfumes);
		panelCatalogo.mostrarCatalogo(filas);
	}

	/**
	 * Aplica los filtros de nombre y categoría seleccionados en la vista del
	 * catálogo y actualiza la tabla con los resultados.
	 */
	private void filtrarCatalogoCliente() {
		String nombre = panelCatalogo.getTxtBuscar().getText().trim();

		if (nombre.equals("Buscar perfume o marca...")) {
			nombre = "";
		}

		String categoria = (String) panelCatalogo.getComboCategoria().getSelectedItem();

		ArrayList<InfoPerfumeConStock> lista;

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

		Object[][] filas = construirFilasCatalogo(lista);
		panelCatalogo.mostrarCatalogo(filas);
	}

	/**
	 * Construye la matriz de filas del catálogo a partir de una lista de perfumes
	 * con stock. Convierte el stock numérico en "Disponible"/"Agotado" para que el
	 * cliente no vea las unidades reales del almacén.
	 *
	 * @param lista lista de perfumes con información de stock
	 * @return matriz de filas lista para ser mostrada en la tabla del catálogo
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

	// ── Carrito ───────────────────────────────────────────────────────

	/**
	 * Gestiona la acción de añadir un perfume al carrito desde el catálogo.
	 * <p>
	 * Verifica que haya una fila seleccionada, que el perfume tenga stock
	 * disponible y que la cantidad solicitada por el cliente sea válida y no supere
	 * el stock real. Si el perfume ya estaba en el carrito, acumula la cantidad.
	 * </p>
	 */
	private void anadirAlCarrito() {
		int filaSeleccionada = panelCatalogo.getTablaCatalogo().getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Por favor, selecciona un perfume del catálogo.",
					"Ningún producto seleccionado", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String nombre = (String) panelCatalogo.getTablaCatalogo().getValueAt(filaSeleccionada, 0);
		int ml = Integer.parseInt(panelCatalogo.getTablaCatalogo().getValueAt(filaSeleccionada, 4).toString());

		InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl(nombre, ml);

		if (info == null) {
			JOptionPane.showMessageDialog(ventana, "Error al recuperar la información del producto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int stockDisponible = info.getStock().getCantidad();

		if (stockDisponible <= 0) {
			JOptionPane.showMessageDialog(ventana, "Lo sentimos, este perfume está temporalmente agotado.", "Sin stock",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String inputCantidad = JOptionPane.showInputDialog(ventana,
				"¿Cuántas unidades de '" + info.getPerfume().getNombre() + "' deseas añadir al carrito?",
				"Añadir al carrito", JOptionPane.QUESTION_MESSAGE);

		if (inputCantidad == null) {
			return;
		}

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

		if (cantidadElegida > stockDisponible) {
			JOptionPane.showMessageDialog(ventana,
					"No es posible añadir esa cantidad. Solo quedan " + stockDisponible + " unidades en stock.",
					"Stock insuficiente", JOptionPane.WARNING_MESSAGE);
			return;
		}

		boolean existeEnCarrito = false;
		for (CarritoCompra item : carritoActivo) {
			if (item.getPerfume().getIdPerfume() == info.getPerfume().getIdPerfume()) {
				int nuevaCantidadTotal = item.getCantidad() + cantidadElegida;

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

		if (!existeEnCarrito) {
			int idCarritoTemporal = carritoActivo.size() + 1;
			CarritoCompra nuevaLinea = new CarritoCompra(idCarritoTemporal, Constantes.usuarioAutenticado,
					info.getPerfume(), cantidadElegida, info.getPerfume().getPrecio());
			carritoActivo.add(nuevaLinea);
		}

		JOptionPane.showMessageDialog(ventana, "Producto añadido con éxito", "Producto añadido",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Lee las líneas del carrito en memoria ({@link #carritoActivo}), calcula el
	 * subtotal de cada línea y el total global, y actualiza la vista del carrito.
	 */
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

	/**
	 * Elimina del carrito la línea seleccionada por el cliente, previa
	 * confirmación. Refresca la vista del carrito tras la eliminación.
	 */
	private void eliminarLineaCarrito() {
		int filaSeleccionada = panelCarrito.getTablaCarrito().getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Por favor, selecciona el perfume que deseas quitar del carrito.",
					"Ninguna línea seleccionada", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmacion = JOptionPane.showConfirmDialog(ventana,
				"¿Estás seguro de que deseas eliminar este perfume de tu carrito?", "Eliminar producto",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			carritoActivo.remove(filaSeleccionada);

			JOptionPane.showMessageDialog(ventana, "El perfume ha sido eliminado del carrito.", "Producto eliminado",
					JOptionPane.INFORMATION_MESSAGE);

			cargarCarritoCliente();
		}
	}

	/**
	 * Vacía por completo el carrito activo, previa confirmación del cliente.
	 * Refresca la vista del carrito para mostrar el estado vacío.
	 */
	private void vaciarCarritoCompleto() {
		if (carritoActivo.isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Tu carrito ya está vacío.", "Carrito vacío",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int confirmacion = JOptionPane.showConfirmDialog(ventana,
				"¿Estás seguro de que deseas eliminar todos los productos de tu carrito?", "Vaciar carrito",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			carritoActivo.clear();

			JOptionPane.showMessageDialog(ventana, "El carrito ha sido vaciado correctamente.", "Carrito vaciado",
					JOptionPane.INFORMATION_MESSAGE);

			cargarCarritoCliente();
		}
	}

	/**
	 * Prepara la vista de pago con el resumen del carrito (número de artículos y
	 * total) y navega a dicha vista. Impide continuar si el carrito está vacío.
	 */
	private void finalizarCompraCarrito() {
		if (carritoActivo.isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "No puedes finalizar la compra porque tu carrito está vacío.",
					"Carrito vacío", JOptionPane.WARNING_MESSAGE);
			return;
		}

		double totalPrecioCarrito = 0;
		int numItems = 0;
		for (CarritoCompra item : carritoActivo) {
			totalPrecioCarrito += item.getCantidad() * item.getPrecioUnidad();
			numItems += item.getCantidad();
		}

		panelPago.limpiarFormulario();
		panelPago.mostrarResumen(numItems, totalPrecioCarrito);

		ventana.mostrarVista(Constantes.VISTA_PAGO);
	}

	// ── Pago ──────────────────────────────────────────────────────────

	/**
	 * Procesa el pago del carrito activo.
	 * <p>
	 * Recoge los datos del formulario de pago, revalida el stock real de cada línea
	 * antes de confirmar, y persiste el pedido en la base de datos a través del
	 * DAO. Si la operación es correcta, vacía el carrito y vuelve al catálogo; si
	 * falla, informa al usuario sin perder el carrito.
	 * </p>
	 */
	private void procesarPago() {
		Object[] datosPago = panelPago.obtenerDatos();

		if (datosPago == null) {
			return;
		}

		String formaPago = (String) datosPago[0];
		String direccionEntrega = (String) datosPago[1];

		double totalPrecioCarrito = 0;
		for (CarritoCompra item : carritoActivo) {
			totalPrecioCarrito += item.getCantidad() * item.getPrecioUnidad();
		}

		String mensaje = "¿Confirmas el pago de " + String.format("%.2f EUR", totalPrecioCarrito) + " con " + formaPago
				+ "?\n\nDirección de entrega:\n" + direccionEntrega;

		int respuesta = JOptionPane.showConfirmDialog(ventana, mensaje, "Confirmar pago", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}

		int idUsuario = Constantes.usuarioAutenticado.getIdUsuario();

		for (CarritoCompra item : carritoActivo) {
			int stockReal = perfumesDAO.obtenerStockDisponible(item.getPerfume().getIdPerfume());

			if (item.getCantidad() > stockReal) {
				JOptionPane.showMessageDialog(ventana,
						"El perfume '" + item.getPerfume().getNombre() + "' ya no tiene suficiente stock disponible.\n"
								+ "Stock actual: " + stockReal + " unidades.",
						"Stock insuficiente", JOptionPane.WARNING_MESSAGE);

				cargarCatalogoCliente();
				ventana.mostrarVista(Constantes.VISTA_CATALOGO);
				return;
			}
		}

		boolean exitoBBDD = pedidosDAO.insertarPedido(idUsuario, totalPrecioCarrito, formaPago, direccionEntrega,
				carritoActivo);

		if (exitoBBDD) {
			JOptionPane.showMessageDialog(ventana,
					"¡Tu compra ha sido procesada con éxito!\nGracias por confiar en Golden Tale.", "Compra finalizada",
					JOptionPane.INFORMATION_MESSAGE);

			carritoActivo.clear();
			panelPago.limpiarFormulario();

			cargarCatalogoCliente();
			ventana.mostrarVista(Constantes.VISTA_CATALOGO);

		} else {
			JOptionPane.showMessageDialog(ventana,
					"Hubo un problema al procesar tu pedido.\nTu carrito sigue intacto, puedes intentarlo de nuevo.",
					"Error en la compra", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ── Mis pedidos (cliente) ─────────────────────────────────────────

	/**
	 * Carga todos los pedidos del cliente autenticado en la vista de Mis pedidos.
	 * Restablece el filtro de estado a su valor por defecto y limpia el panel de
	 * detalle.
	 */
	private void cargarMisPedidos() {
		panelMisPedidos.getComboFiltroEstado().setSelectedIndex(0);

		int idUsuario = Constantes.usuarioAutenticado.getIdUsuario();
		ArrayList<Pedido> lista = pedidosDAO.getPedidosPorUsuario(idUsuario);

		Object[][] filas = construirFilasPedidos(lista);
		panelMisPedidos.mostrarPedidos(filas);

		panelMisPedidos.mostrarDetalle(0, new Object[0][4]);
		panelMisPedidos.getLblPedidoSeleccionado().setText("Detalle del pedido seleccionado");
	}

	/**
	 * Filtra los pedidos del cliente según el estado seleccionado en el combo de la
	 * vista. Limpia el panel de detalle al cambiar el filtro.
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

		panelMisPedidos.mostrarDetalle(0, new Object[0][4]);
		panelMisPedidos.getLblPedidoSeleccionado().setText("Detalle del pedido seleccionado");
	}

	/**
	 * Construye la matriz de filas de la tabla de pedidos a partir de una lista de
	 * pedidos. Las columnas siguen el orden de {@code Constantes.COLS_MIS_PEDIDOS}:
	 * Ref, Fecha, Estado, Total.
	 *
	 * @param lista lista de pedidos a representar
	 * @return matriz de filas lista para ser mostrada en la tabla de pedidos
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
	 * Muestra el detalle (líneas) del pedido seleccionado en la tabla principal de
	 * Mis pedidos. Extrae el identificador del pedido del campo "Ref." y consulta
	 * las líneas asociadas en la base de datos.
	 */
	private void mostrarDetallePedido() {
		int filaSeleccionada = panelMisPedidos.getTablaPedidos().getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Selecciona un pedido para ver su detalle.",
					"Ningún pedido seleccionado", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String referencia = (String) panelMisPedidos.getTablaPedidos().getValueAt(filaSeleccionada, 0);
		int idPedido = Integer.parseInt(referencia.substring(1));

		ArrayList<LineaPedido> lineas = pedidosDAO.getLineasPorPedido(idPedido);

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

	/**
	 * Alterna la visibilidad de la contraseña en el formulario de login y actualiza
	 * el texto del botón entre "Mostrar" y "Ocultar".
	 */
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

	/**
	 * Alterna la visibilidad de los campos de contraseña y confirmación en el
	 * formulario de registro, y actualiza el texto del botón entre "Mostrar" y
	 * "Ocultar".
	 */
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

	/**
	 * Carga y actualiza las tres métricas del dashboard del empleado consultando la
	 * base de datos en tiempo real.
	 * <p>
	 * Recupera el número de pedidos realizados hoy, los pedidos en estado pendiente
	 * y los perfumes con stock bajo, y los vuelca en las tarjetas de resumen del
	 * panel {@link VEmpleadoDashboard}.
	 * </p>
	 */
	private void cargarMetricasDashboard() {
		int pedidosHoy = pedidosDAO.contarPedidosHoy();
		int pendientes = pedidosDAO.contarPedidosPendientes();
		int stockBajo = perfumesDAO.contarStockBajo();

		panelDashboard.getLblPedidosHoyValor().setText(String.valueOf(pedidosHoy));
		panelDashboard.getLblPendientesValor().setText(String.valueOf(pendientes));
		panelDashboard.getLblStockBajoValor().setText(String.valueOf(stockBajo));
	}
}