package com.goldentale.vistaEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana Gestionar Pedidos — JFrame abierto desde EmpleadoDashboardVista.
 *
 * Muestra todos los pedidos con filtros. Al hacer clic en uno
 * aparece el panel de detalle con las líneas del pedido y
 * el desplegable para cambiar el estado.
 *
 * Equivalente futuro en JavaFX: pedidos_admin.fxml + PedidosAdminController.java
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class GestionarPedidosVista extends JFrame {

    // ── Navbar ────────────────────────────────────────────────────────
    private JPanel panelNavbar;
    private JLabel lblNombreApp;

    /** Cierra este JFrame y vuelve al dashboard. */
    private JButton btnVolver;

    // ── Título ────────────────────────────────────────────────────────
    private JLabel lblTitulo;

    // ── Filtros ───────────────────────────────────────────────────────
    private JPanel panelFiltros;

    /**
     * Filtro por estado de pedido.
     * Valores: "Todos" + Constantes.ESTADOS_PEDIDO
     */
    private JComboBox<String> comboFiltroEstado;

    /** Búsqueda por referencia de pedido o nombre de cliente */
    private JTextField txtBuscar;

    /** Botón para aplicar los filtros y recargar la tabla */
    private JButton btnFiltrar;

    // ── Tabla de pedidos ──────────────────────────────────────────────
    /**
     * Tabla con todos los pedidos.
     * Columnas: Ref. | Cliente | Total | Estado | Fecha
     * Al hacer clic en una fila se muestra panelDetalle.
     * Equivalente en JavaFX: TableView<Pedido>.
     */
    private JTable tablaPedidos;

    /**
     * Modelo de datos de la tabla.
     * Uso: modeloTablaPedidos.addRow(new Object[]{"#1042","Ana García",245.48,"Pendiente","15/05/2025"})
     */
    private DefaultTableModel modeloTablaPedidos;

    /** ScrollPane que envuelve la tabla */
    private JScrollPane scrollTablaPedidos;

    // ── Panel de detalle (aparece al seleccionar un pedido) ───────────
    /**
     * Panel con los datos completos del pedido seleccionado.
     * Invisible por defecto (setVisible(false)).
     * Se muestra al hacer clic en una fila de la tabla.
     */
    private JPanel panelDetalle;

    /** Referencia del pedido seleccionado. Ejemplo: "Pedido #1042" */
    private JLabel lblDetalleRef;

    /** Nombre completo del cliente del pedido seleccionado */
    private JLabel lblDetalleCliente;

    /** Fecha y hora del pedido seleccionado */
    private JLabel lblDetalleFecha;

    // ── Tabla de líneas del pedido ────────────────────────────────────
    /**
     * Tabla con los perfumes incluidos en el pedido seleccionado.
     * Columnas: Perfume | Cantidad | Precio ud. | Subtotal
     */
    private JTable tablaLineas;

    /** Modelo de la tabla de líneas */
    private DefaultTableModel modeloTablaLineas;

    /** ScrollPane que envuelve la tabla de líneas */
    private JScrollPane scrollTablaLineas;

    /** Total del pedido seleccionado */
    private JLabel lblDetalleTotal;

    // ── Cambio de estado ──────────────────────────────────────────────
    private JLabel lblCambiarEstado;

    /**
     * Desplegable para cambiar el estado del pedido seleccionado.
     * Valores: Constantes.ESTADOS_PEDIDO
     * Al confirmar, el controlador llama a PedidoDAO.updateEstado().
     */
    private JComboBox<String> comboCambiarEstado;

    /**
     * Confirma el cambio de estado.
     * El controlador actualiza la base de datos y refresca la tabla.
     */
    private JButton btnConfirmarEstado;

    /**
     * Resultado del cambio — invisible por defecto.
     * Verde: "Estado actualizado correctamente"
     * Rojo:  "Error al actualizar el estado"
     */
    private JLabel lblResultadoCambio;


    // ── Getters ───────────────────────────────────────────────────────
    public JButton getBtnVolver()                   { return btnVolver; }
    public JComboBox<String> getComboFiltroEstado() { return comboFiltroEstado; }
    public JTextField getTxtBuscar()                { return txtBuscar; }
    public JButton getBtnFiltrar()                  { return btnFiltrar; }
    public JTable getTablaPedidos()                 { return tablaPedidos; }
    public DefaultTableModel getModeloTablaPedidos() { return modeloTablaPedidos; }
    public JScrollPane getScrollTablaPedidos()      { return scrollTablaPedidos; }
    public JPanel getPanelDetalle()                 { return panelDetalle; }
    public JLabel getLblDetalleRef()                { return lblDetalleRef; }
    public JLabel getLblDetalleCliente()            { return lblDetalleCliente; }
    public JLabel getLblDetalleFecha()              { return lblDetalleFecha; }
    public JTable getTablaLineas()                  { return tablaLineas; }
    public DefaultTableModel getModeloTablaLineas() { return modeloTablaLineas; }
    public JScrollPane getScrollTablaLineas()       { return scrollTablaLineas; }
    public JLabel getLblDetalleTotal()              { return lblDetalleTotal; }
    public JComboBox<String> getComboCambiarEstado() { return comboCambiarEstado; }
    public JButton getBtnConfirmarEstado()          { return btnConfirmarEstado; }
    public JLabel getLblResultadoCambio()           { return lblResultadoCambio; }
}
