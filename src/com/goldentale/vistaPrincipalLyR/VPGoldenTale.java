package com.goldentale.vistaPrincipalLyR;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.util.ComponentesUI;
import com.goldentale.model.util.ComponentesUI.PanelRedondeado;
import com.goldentale.model.util.Tema;
import com.goldentale.vistaCliente.VCarritoCompra;
import com.goldentale.vistaCliente.VCatalogoCliente;
import com.goldentale.vistaCliente.VMisPedidos;
import com.goldentale.vistaCliente.VPago;
import com.goldentale.vistaEmpleado.VAniadirPerfume;
import com.goldentale.vistaEmpleado.VEmpleadoDashboard;
import com.goldentale.vistaEmpleado.VModificarPerfume;
import com.goldentale.vistaEmpleado.VStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Ventana principal de Golden Tale. Contiene la navbar superior, un sidebar
 * dinámico que cambia según el estado de sesión (pre-login, cliente o
 * empleado) y un área central con {@link CardLayout} para alternar entre
 * todas las vistas de la aplicación. El panel de inicio (bienvenida) se
 * construye internamente en esta clase, el resto de vistas se añaden vía
 * {@link #añadirVistas}.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 * @see Controlador
 * @see VLogin
 * @see VRegistroUsuario
 */
public class VPGoldenTale extends JFrame {

	private JLabel lblNavNombre;
	private JLabel lblNavEstado;

	private JPanel panelLateral;

	private JButton btnLateralInicio;
	private JButton btnLateralLogin;
	private JButton btnLateralRegistro;

	private JButton btnClienteCatalogo;
	private JButton btnClienteCarrito;
	private JButton btnClienteMisPedidos;
	private JButton btnClienteCerrarSesion;

	private JButton btnEmpleadoDashboard;
	private JButton btnEmpleadoAnadir;
	private JButton btnEmpleadoModificar;
	private JButton btnEmpleadoStock;
	private JButton btnEmpleadoCerrarSesion;

	private JButton btnIniciarSesion;
	private JButton btnRegistrarse;

	private CardLayout cardLayout;
	private JPanel areaCentral;

	/**
	 * Construye la ventana principal con el título de la aplicación e
	 * inicializa todos los componentes (navbar, sidebar y área central con
	 * el panel de inicio).
	 */
	public VPGoldenTale() {
		super(Constantes.TITULO_APLICACION);
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constantes.ANCHURA_APLICACION, Constantes.ALTURA_APLICACION);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Tema.FONDO);

		add(construirNavbar(), BorderLayout.NORTH);

		panelLateral = new JPanel();
		panelLateral.setBackground(Tema.FONDO_LATERAL);
		panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
		panelLateral.setPreferredSize(new Dimension(Constantes.ANCHURA_SIDEBAR, 0));
		panelLateral
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Tema.BORDE),
						BorderFactory.createEmptyBorder(18, 12, 18, 12)));
		add(panelLateral, BorderLayout.WEST);

		cardLayout = new CardLayout();
		areaCentral = new JPanel(cardLayout);
		areaCentral.setBackground(Tema.FONDO);

		areaCentral.add(construirPanelInicio(), Constantes.VISTA_INICIO);

		add(areaCentral, BorderLayout.CENTER);
	}

	private JPanel construirNavbar() {
		JPanel navbar = new JPanel(new BorderLayout());
		navbar.setBackground(Tema.MORADO);
		navbar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		navbar.setPreferredSize(new Dimension(0, 56));

		lblNavNombre = new JLabel(Constantes.TITULO_APLICACION);
		lblNavNombre.setFont(Tema.fuenteNegrita(16));
		lblNavNombre.setForeground(Color.WHITE);

		lblNavEstado = new JLabel("Bienvenido a " + Constantes.TITULO_APLICACION);
		lblNavEstado.setFont(Tema.fuenteNormal(12));
		lblNavEstado.setForeground(new Color(220, 210, 245));
		lblNavEstado.setHorizontalAlignment(SwingConstants.RIGHT);

		navbar.add(lblNavNombre, BorderLayout.WEST);
		navbar.add(lblNavEstado, BorderLayout.EAST);
		return navbar;
	}

	private JPanel construirPanelInicio() {
		JPanel fondo = new JPanel(new GridBagLayout());
		fondo.setBackground(Tema.FONDO);

		PanelRedondeado tarjeta = new PanelRedondeado(18, Color.WHITE, Tema.BORDE);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(520, 340));
		tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

		JLabel lblLogo = new JLabel("🧴");
		lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
		lblLogo.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblLogo);
		tarjeta.add(Box.createVerticalStrut(14));

		JLabel lblNombreApp = new JLabel(Constantes.TITULO_APLICACION);
		lblNombreApp.setFont(Tema.fuenteNegrita(34));
		lblNombreApp.setForeground(Tema.TEXTO_OSCURO);
		lblNombreApp.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblNombreApp);

		JLabel lblEslogan = new JLabel("Tu perfumería de confianza");
		lblEslogan.setFont(Tema.fuenteNormal(16));
		lblEslogan.setForeground(Tema.TEXTO_MEDIO);
		lblEslogan.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblEslogan);
		tarjeta.add(Box.createVerticalStrut(20));

		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(300, 2));
		sep.setForeground(Tema.BORDE);
		tarjeta.add(sep);
		tarjeta.add(Box.createVerticalStrut(24));

		JPanel filaBotones = new JPanel(new GridLayout(1, 2, 12, 0));
		filaBotones.setOpaque(false);
		filaBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
		btnIniciarSesion = ComponentesUI.botonPrincipal("Iniciar sesión");
		btnRegistrarse = ComponentesUI.botonSecundario("Registrarse");
		filaBotones.add(btnIniciarSesion);
		filaBotones.add(btnRegistrarse);
		tarjeta.add(filaBotones);
		tarjeta.add(Box.createVerticalStrut(18));

		JLabel lblNota = new JLabel("Catálogo, pedidos y stock en una sola aplicación");
		lblNota.setFont(Tema.fuenteNormal(11));
		lblNota.setForeground(Tema.TEXTO_CLARO);
		lblNota.setAlignmentX(CENTER_ALIGNMENT);
		tarjeta.add(lblNota);

		fondo.add(tarjeta);
		return fondo;
	}

	/**
	 * Añade todas las vistas de la aplicación al {@link CardLayout} central
	 * usando como clave la constante de vista correspondiente definida en
	 * {@link Constantes}.
	 *
	 * @param panelLogin      Vista de inicio de sesión.
	 * @param panelRegistro   Vista de registro de usuario.
	 * @param panelCatalogo   Vista del catálogo del cliente.
	 * @param panelCarrito    Vista del carrito de la compra.
	 * @param panelMisPedidos Vista del historial de pedidos del cliente.
	 * @param panelPago       Vista de pago.
	 * @param panelDashboard  Vista del dashboard del empleado.
	 * @param panelAnadir     Vista de añadir perfume.
	 * @param panelModificar  Vista de modificar perfume.
	 * @param panelStock      Vista de control de stock.
	 */
	public void añadirVistas(VLogin panelLogin, VRegistroUsuario panelRegistro, VCatalogoCliente panelCatalogo,
			VCarritoCompra panelCarrito, VMisPedidos panelMisPedidos, VPago panelPago,
			VEmpleadoDashboard panelDashboard, VAniadirPerfume panelAnadir, VModificarPerfume panelModificar,
			VStock panelStock) {

		areaCentral.add(panelLogin, Constantes.VISTA_LOGIN);
		areaCentral.add(panelRegistro, Constantes.VISTA_REGISTRO);
		areaCentral.add(panelCatalogo, Constantes.VISTA_CATALOGO);
		areaCentral.add(panelCarrito, Constantes.VISTA_CARRITO);
		areaCentral.add(panelMisPedidos, Constantes.VISTA_MIS_PEDIDOS);
		areaCentral.add(panelPago, Constantes.VISTA_PAGO);
		areaCentral.add(panelDashboard, Constantes.VISTA_DASHBOARD);
		areaCentral.add(panelAnadir, Constantes.VISTA_ANADIR);
		areaCentral.add(panelModificar, Constantes.VISTA_MODIFICAR);
		areaCentral.add(panelStock, Constantes.VISTA_STOCK);
	}

	/**
	 * Registra el controlador como listener de los botones del panel de
	 * inicio (Iniciar sesión y Registrarse).
	 *
	 * @param controlador Controlador que gestionará los eventos.
	 */
	public void setControlador(Controlador controlador) {
		btnIniciarSesion.addActionListener(controlador);
		btnRegistrarse.addActionListener(controlador);
	}

	/**
	 * Construye el sidebar para el estado pre-login con los accesos a Inicio,
	 * Login y Registro, y registra el listener pasado en cada uno de ellos.
	 *
	 * @param listener Listener que recibirá los eventos de los botones del
	 *                 sidebar.
	 */
	public void mostrarSidebarPreLogin(ActionListener listener) {
		panelLateral.removeAll();
		agregarEtiquetaSeccion("MENÚ");

		btnLateralInicio = ComponentesUI.botonSidebar("  Inicio");
		btnLateralLogin = ComponentesUI.botonSidebar("  Iniciar sesión");
		btnLateralRegistro = ComponentesUI.botonSidebar("  Registrarse");

		btnLateralInicio.addActionListener(listener);
		btnLateralLogin.addActionListener(listener);
		btnLateralRegistro.addActionListener(listener);

		panelLateral.add(btnLateralInicio);
		panelLateral.add(Box.createVerticalStrut(6));
		panelLateral.add(btnLateralLogin);
		panelLateral.add(Box.createVerticalStrut(6));
		panelLateral.add(btnLateralRegistro);
		panelLateral.add(Box.createVerticalGlue());

		panelLateral.revalidate();
		panelLateral.repaint();
	}

	/**
	 * Construye el sidebar para un cliente autenticado, mostrando su nombre
	 * en la navbar y los accesos al catálogo, carrito, mis pedidos y cerrar
	 * sesión. Registra el listener pasado en cada botón.
	 *
	 * @param nombreCliente Nombre del cliente a mostrar en la navbar.
	 * @param listener      Listener que recibirá los eventos de los botones.
	 */
	public void mostrarSidebarCliente(String nombreCliente, ActionListener listener) {
		panelLateral.removeAll();
		lblNavEstado.setText(nombreCliente);

		agregarEtiquetaSeccion("MI CUENTA");

		btnClienteCatalogo = ComponentesUI.botonSidebar("  Catálogo");
		btnClienteCarrito = ComponentesUI.botonSidebar("  Mi carrito");
		btnClienteMisPedidos = ComponentesUI.botonSidebar("  Mis pedidos");

		btnClienteCatalogo.addActionListener(listener);
		btnClienteCarrito.addActionListener(listener);
		btnClienteMisPedidos.addActionListener(listener);

		panelLateral.add(btnClienteCatalogo);
		panelLateral.add(Box.createVerticalStrut(6));
		panelLateral.add(btnClienteCarrito);
		panelLateral.add(Box.createVerticalStrut(6));
		panelLateral.add(btnClienteMisPedidos);
		panelLateral.add(Box.createVerticalGlue());

		agregarEtiquetaSeccion("SESIÓN");
		btnClienteCerrarSesion = ComponentesUI.botonSidebar("  Cerrar sesión");
		btnClienteCerrarSesion.addActionListener(listener);
		panelLateral.add(btnClienteCerrarSesion);
		panelLateral.add(Box.createVerticalStrut(8));

		panelLateral.revalidate();
		panelLateral.repaint();
	}

	/**
	 * Construye el sidebar para un empleado autenticado, mostrando su nombre
	 * en la navbar y los accesos al dashboard, añadir/modificar perfume,
	 * control de stock y cerrar sesión. Registra el listener pasado en cada
	 * botón.
	 *
	 * @param nombreEmpleado Nombre del empleado a mostrar en la navbar.
	 * @param listener       Listener que recibirá los eventos de los botones.
	 */
	public void mostrarSidebarEmpleado(String nombreEmpleado, ActionListener listener) {
		panelLateral.removeAll();
		lblNavEstado.setText(nombreEmpleado);

		agregarEtiquetaSeccion("PRINCIPAL");
		btnEmpleadoDashboard = ComponentesUI.botonSidebar("  Dashboard");
		btnEmpleadoDashboard.addActionListener(listener);
		panelLateral.add(btnEmpleadoDashboard);
		panelLateral.add(Box.createVerticalStrut(6));

		agregarEtiquetaSeccion("CATÁLOGO");
		btnEmpleadoAnadir = ComponentesUI.botonSidebar("  Añadir perfume");
		btnEmpleadoModificar = ComponentesUI.botonSidebar("  Modificar stock");
		btnEmpleadoAnadir.addActionListener(listener);
		btnEmpleadoModificar.addActionListener(listener);
		panelLateral.add(btnEmpleadoAnadir);
		panelLateral.add(Box.createVerticalStrut(6));
		panelLateral.add(btnEmpleadoModificar);
		panelLateral.add(Box.createVerticalStrut(6));

		agregarEtiquetaSeccion("ALMACÉN");
		btnEmpleadoStock = ComponentesUI.botonSidebar("  Control stock");
		btnEmpleadoStock.addActionListener(listener);
		panelLateral.add(btnEmpleadoStock);
		panelLateral.add(Box.createVerticalGlue());

		agregarEtiquetaSeccion("SESIÓN");
		btnEmpleadoCerrarSesion = ComponentesUI.botonSidebar("  Cerrar sesión");
		btnEmpleadoCerrarSesion.addActionListener(listener);
		panelLateral.add(btnEmpleadoCerrarSesion);
		panelLateral.add(Box.createVerticalStrut(8));

		panelLateral.revalidate();
		panelLateral.repaint();
	}

	private void agregarEtiquetaSeccion(String texto) {
		JLabel lbl = new JLabel(texto);
		lbl.setFont(Tema.fuenteNegrita(10));
		lbl.setForeground(Tema.TEXTO_CLARO);
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl.setBorder(BorderFactory.createEmptyBorder(10, 4, 4, 0));
		panelLateral.add(lbl);
	}

	/**
	 * Cambia la vista mostrada en el área central a la identificada por la
	 * clave dada.
	 *
	 * @param claveVista Clave de la vista (definida en {@link Constantes}).
	 */
	public void mostrarVista(String claveVista) {
		cardLayout.show(areaCentral, claveVista);
	}

	/**
	 * Hace visible la ventana principal.
	 */
	public void hacerVisible() {
		setVisible(true);
	}

	public JButton getBtnIniciarSesion() {
		return btnIniciarSesion;
	}

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public JButton getBtnLateralInicio() {
		return btnLateralInicio;
	}

	public JButton getBtnLateralLogin() {
		return btnLateralLogin;
	}

	public JButton getBtnLateralRegistro() {
		return btnLateralRegistro;
	}

	public JButton getBtnClienteCatalogo() {
		return btnClienteCatalogo;
	}

	public JButton getBtnClienteCarrito() {
		return btnClienteCarrito;
	}

	public JButton getBtnClienteMisPedidos() {
		return btnClienteMisPedidos;
	}

	public JButton getBtnClienteCerrarSesion() {
		return btnClienteCerrarSesion;
	}

	public JButton getBtnEmpleadoDashboard() {
		return btnEmpleadoDashboard;
	}

	public JButton getBtnEmpleadoAnadir() {
		return btnEmpleadoAnadir;
	}

	public JButton getBtnEmpleadoModificar() {
		return btnEmpleadoModificar;
	}

	public JButton getBtnEmpleadoStock() {
		return btnEmpleadoStock;
	}

	public JButton getBtnEmpleadoCerrarSesion() {
		return btnEmpleadoCerrarSesion;
	}

	public JLabel getLblNavEstado() {
		return lblNavEstado;
	}
}