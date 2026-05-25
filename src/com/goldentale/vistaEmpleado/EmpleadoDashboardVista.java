package com.goldentale.vistaEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana Dashboard del Empleado — JFrame principal del empleado.
 *
 * Muestra las métricas del día y la tabla de pedidos recientes.
 * Al hacer clic en un pedido aparece el panel de accesos rápidos
 * con los 4 botones que abren las demás ventanas del empleado.
 *
 * Ventanas que puede abrir:
 *   → AñadirPerfumeVista
 *   → ModificarPerfumeVista
 *   → StockVista
 *   → GestionarPedidosVista
 *
 * Equivalente futuro en JavaFX: dashboard.fxml + DashboardController.java
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class EmpleadoDashboardVista extends JFrame {

    // ── Navbar superior ───────────────────────────────────────────────
    /** Panel superior con fondo morado */
    private JPanel panelNavbar;

    /** Etiqueta "Golden Tale" */
    private JLabel lblNombreApp;

    /** Etiqueta con nombre y cargo del empleado autenticado */
    private JLabel lblNombreEmpleado;

    /**
     * Botón cerrar sesión — abre un JOptionPane de confirmación.
     * Si confirma, limpia Constantes.empleadoAutenticado,
     * cierra este JFrame y abre LoginVista.
     */
    private JButton btnCerrarSesion;

    // ── Métricas ──────────────────────────────────────────────────────
    /** Panel que contiene las 3 tarjetas en GridLayout(1,3) */
    private JPanel panelMetricas;

    /** Tarjeta: pedidos totales del día */
    private JPanel tarjetaPedidosHoy;
    private JLabel lblPedidosHoyTitulo;
    private JLabel lblPedidosHoyValor;

    /** Tarjeta: pedidos en estado Pendiente */
    private JPanel tarjetaPendientes;
    private JLabel lblPendientesTitulo;
    private JLabel lblPendientesValor;

    /** Tarjeta: productos con stock menor que Constantes.STOCK_MINIMO_ALERTA */
    private JPanel tarjetaStockBajo;
    private JLabel lblStockBajoTitulo;
    private JLabel lblStockBajoValor;

    // ── Tabla de pedidos recientes ────────────────────────────────────
    /** Etiqueta de sección */
    private JLabel lblTituloPedidos;

    /**
     * Tabla con los últimos pedidos.
     * Columnas: Ref. | Cliente | Perfumes | Total | Estado
     * Al hacer clic en una fila se muestra panelAccesosRapidos.
     * Equivalente en JavaFX: TableView con setOnMouseClicked.
     */
    private JTable tablaPedidos;

    /**
     * Modelo de datos de la tabla.
     * Uso: modeloTablaPedidos.addRow(new Object[]{"#1042","Ana García",...})
     * Equivalente en JavaFX: ObservableList<Pedido>.
     */
    private DefaultTableModel modeloTablaPedidos;

    /** ScrollPane que envuelve la tabla */
    private JScrollPane scrollTablaPedidos;

    // ── Panel de accesos rápidos ──────────────────────────────────────
    /**
     * Panel que aparece al hacer clic en un pedido de la tabla.
     * Invisible por defecto (setVisible(false)).
     * Contiene los 4 botones de acceso rápido y la referencia del pedido.
     */
    private JPanel panelAccesosRapidos;

    /**
     * Etiqueta que muestra la referencia del pedido seleccionado.
     * Ejemplo: "Pedido #1042 seleccionado"
     */
    private JLabel lblPedidoSeleccionado;

    /**
     * Abre AñadirPerfumeVista.
     * El controlador instancia la vista, le pasa el empleado autenticado
     * y llama a setVisible(true).
     */
    private JButton btnAccesoAñadirPerfume;

    /**
     * Abre ModificarPerfumeVista.
     * El controlador instancia la vista y la muestra.
     * La búsqueda por nombre + ml se hace dentro de esa ventana.
     */
    private JButton btnAccesoModificarPerfume;

    /**
     * Abre StockVista.
     */
    private JButton btnAccesoStock;

    /**
     * Abre GestionarPedidosVista.
     */
    private JButton btnAccesoGestionarPedidos;


    // ── Getters ───────────────────────────────────────────────────────
    public JLabel getLblNombreEmpleado()          { return lblNombreEmpleado; }
    public JButton getBtnCerrarSesion()           { return btnCerrarSesion; }
    public JLabel getLblPedidosHoyValor()         { return lblPedidosHoyValor; }
    public JLabel getLblPendientesValor()         { return lblPendientesValor; }
    public JLabel getLblStockBajoValor()          { return lblStockBajoValor; }
    public JTable getTablaPedidos()               { return tablaPedidos; }
    public DefaultTableModel getModeloTablaPedidos() { return modeloTablaPedidos; }
    public JScrollPane getScrollTablaPedidos()    { return scrollTablaPedidos; }
    public JPanel getPanelAccesosRapidos()        { return panelAccesosRapidos; }
    public JLabel getLblPedidoSeleccionado()      { return lblPedidoSeleccionado; }
    public JButton getBtnAccesoAñadirPerfume()    { return btnAccesoAñadirPerfume; }
    public JButton getBtnAccesoModificarPerfume() { return btnAccesoModificarPerfume; }
    public JButton getBtnAccesoStock()            { return btnAccesoStock; }
    public JButton getBtnAccesoGestionarPedidos() { return btnAccesoGestionarPedidos; }
}
