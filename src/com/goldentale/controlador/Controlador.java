package com.goldentale.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
				ventana.mostrarVista(Constantes.VISTA_CATALOGO);

			} else if (ev.getSource().equals(ventana.getBtnClienteCarrito())) {
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(ventana.getBtnClienteMisPedidos())) {
				ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

			} else if (ev.getSource().equals(ventana.getBtnClienteCerrarSesion())) {
				cerrarSesion();

				// ── Catálogo cliente ──────────────────────────────────────
			} else if (ev.getSource().equals(panelCatalogo.getBtnAnadirCarrito())) {
				  anadirAlCarrito();

			} else if (ev.getSource().equals(panelCatalogo.getBtnVerCarrito())) {
				ventana.mostrarVista(Constantes.VISTA_CARRITO);

			} else if (ev.getSource().equals(panelCatalogo.getBtnMisPedidos())) {
				ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);

				// ── Carrito ───────────────────────────────────────────────
			} else if (ev.getSource().equals(panelCarrito.getBtnEliminarLinea())) {
				eliminarLineaCarrito();

			} else if (ev.getSource().equals(panelCarrito.getBtnVaciarCarrito())) {
				  vaciarCarrito();

			} else if (ev.getSource().equals(panelCarrito.getBtnFinalizarCompra())) {
				ventana.mostrarVista(Constantes.VISTA_PAGO);

				// ── Mis pedidos ───────────────────────────────────────────
			} else if (ev.getSource().equals(panelMisPedidos.getBtnVerDetalle())) {
				mostrarDetallePedido();

				// ── Pago ──────────────────────────────────────────────────
			} else if (ev.getSource().equals(panelPago.getBtnConfirmarPago())) {
				procesarPago();

			} else if (ev.getSource().equals(panelPago.getBtnCancelar())) {
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

	    // Resetear el atributo del controlador
	    perfumeEnEdicion = null;

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
	
	private void anadirAlCarrito() {
	    int fila = panelCatalogo.getTablaCatalogo().getSelectedRow();
	    if (fila < 0) {
	        JOptionPane.showMessageDialog(ventana,
	                "Selecciona un perfume del catálogo primero.",
	                "Sin selección", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    String nombre    = panelCatalogo.getModeloTablaCatalogo().getValueAt(fila, 0).toString();
	    String precio    = panelCatalogo.getModeloTablaCatalogo().getValueAt(fila, 5).toString();
	    String stockStr  = panelCatalogo.getModeloTablaCatalogo().getValueAt(fila, 6).toString();

	    if (stockStr.equals("0")) {
	        JOptionPane.showMessageDialog(ventana,
	                "Este perfume no tiene stock disponible.",
	                "Sin stock", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    // Comprobar si ya está en el carrito y sumar cantidad
	    DefaultTableModel modelo = panelCarrito.getModeloTablaCarrito();
	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        if (modelo.getValueAt(i, 0).toString().equals(nombre)) {
	            int cantidadActual = Integer.parseInt(modelo.getValueAt(i, 1).toString());
	            int nuevaCantidad  = cantidadActual + 1;
	            double precioUd    = Double.parseDouble(precio);
	            double subtotal    = precioUd * nuevaCantidad;
	            modelo.setValueAt(String.valueOf(nuevaCantidad), i, 1);
	            modelo.setValueAt(String.format("%.2f EUR", subtotal), i, 3);
	            actualizarTotalCarrito();
	            JOptionPane.showMessageDialog(ventana,
	                    "\"" + nombre + "\" actualizado en el carrito.",
	                    "Carrito", JOptionPane.INFORMATION_MESSAGE);
	            return;
	        }
	    }

	    // Añadir nueva línea
	    String precioFormato = String.format("%.2f EUR", Double.parseDouble(precio));
	    modelo.addRow(new Object[]{ nombre, "1", precioFormato, precioFormato });
	    actualizarTotalCarrito();

	    JOptionPane.showMessageDialog(ventana,
	            "\"" + nombre + "\" añadido al carrito.",
	            "Carrito", JOptionPane.INFORMATION_MESSAGE);
	}

	private void actualizarTotalCarrito() {
	    double total = 0;
	    DefaultTableModel modelo = panelCarrito.getModeloTablaCarrito();
	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        try {
	            String sub = modelo.getValueAt(i, 3).toString().replace(" EUR", "").trim();
	            total += Double.parseDouble(sub);
	        } catch (NumberFormatException ignored) {}
	    }
	    panelCarrito.getLblTotal().setText(String.format("Total: %.2f EUR", total));
	    panelPago.getLblTotal().setText(String.format("%.2f EUR", total));
	    panelPago.getLblResumen().setText("Resumen: " + modelo.getRowCount() + " artículo(s)");
	}
	
	
	private void eliminarLineaCarrito() {
	    int fila = panelCarrito.getTablaCarrito().getSelectedRow();
	    if (fila < 0) {
	        JOptionPane.showMessageDialog(ventana,
	                "Selecciona una línea del carrito para eliminar.",
	                "Sin selección", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    panelCarrito.getModeloTablaCarrito().removeRow(fila);
	    actualizarTotalCarrito();
	}
	
	
	private void vaciarCarrito() {
	    if (panelCarrito.getModeloTablaCarrito().getRowCount() == 0) {
	        JOptionPane.showMessageDialog(ventana,
	                "El carrito ya está vacío.",
	                "Carrito vacío", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
	    int confirm = JOptionPane.showConfirmDialog(ventana,
	            "¿Seguro que quieres vaciar el carrito?",
	            "Vaciar carrito", JOptionPane.YES_NO_OPTION);
	    if (confirm != JOptionPane.YES_OPTION) return;

	    panelCarrito.getModeloTablaCarrito().setRowCount(0);
	    panelCarrito.getLblTotal().setText("Total: 0.00 EUR");
	    panelPago.getLblTotal().setText("0.00 EUR");
	    panelPago.getLblResumen().setText("Resumen: 0 artículo(s)");
	}
	
	private void mostrarDetallePedido() {
	    int fila = panelMisPedidos.getTablaPedidos().getSelectedRow();
	    if (fila < 0) {
	        JOptionPane.showMessageDialog(ventana,
	                "Selecciona un pedido para ver su detalle.",
	                "Sin selección", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    String ref    = panelMisPedidos.getModeloTablaPedidos().getValueAt(fila, 0).toString();
	    String fecha  = panelMisPedidos.getModeloTablaPedidos().getValueAt(fila, 1).toString();
	    String estado = panelMisPedidos.getModeloTablaPedidos().getValueAt(fila, 2).toString();
	    String total  = panelMisPedidos.getModeloTablaPedidos().getValueAt(fila, 3).toString();

	    panelMisPedidos.getLblPedidoSeleccionado()
	            .setText("Pedido " + ref + "  |  " + fecha + "  |  " + estado + "  |  " + total);

	    // TODO: cargar líneas reales desde BD
	}
	
	private void procesarPago() {
	    // 1. Comprobar que el carrito no esté vacío
	    if (panelCarrito.getModeloTablaCarrito().getRowCount() == 0) {
	        panelPago.mostrarError("El carrito está vacío, añade perfumes antes de confirmar.");
	        return;
	    }

	    // 2. Comprobar que se ha introducido la dirección de entrega
	    String direccion = panelPago.getTxtDireccionEntrega().getText().trim();
	    if (direccion.isEmpty()) {
	        panelPago.mostrarError("Introduce la dirección de entrega.");
	        return;
	    }

	    // 3. Recoger la forma de pago
	    String formaPago = panelPago.getComboFormaPago().getSelectedItem().toString();

	    // 4. Confirmación
	    int confirm = JOptionPane.showConfirmDialog(ventana,
	            "¿Confirmas el pedido?\n\nForma de pago: " + formaPago
	            + "\nDirección: " + direccion
	            + "\n" + panelPago.getLblTotal().getText(),
	            "Confirmar pedido", JOptionPane.YES_NO_OPTION);
	    if (confirm != JOptionPane.YES_OPTION) return;

	    // TODO:  el pedido en BD
	    // TODO: guardar el pago en BD
	    // TODO: actualizar stock en BD por cada línea del carrito

	    // 5. Limpiar carrito y mostrar mensaje de éxito
	    panelCarrito.getModeloTablaCarrito().setRowCount(0);
	    panelCarrito.getLblTotal().setText("Total: 0.00 EUR");
	    panelPago.getLblTotal().setText("0.00 EUR");
	    panelPago.getLblResumen().setText("Resumen: 0 artículo(s)");
	    panelPago.getTxtDireccionEntrega().setText("");
	    panelPago.limpiarFeedback();

	    JOptionPane.showMessageDialog(ventana,
	            "Pedido confirmado correctamente.\nPuedes ver el estado en 'Mis pedidos'.",
	            "Pedido realizado", JOptionPane.INFORMATION_MESSAGE);

	    ventana.mostrarVista(Constantes.VISTA_MIS_PEDIDOS);
	}
}