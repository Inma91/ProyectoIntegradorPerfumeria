package com.goldentale.vistaEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana Stock — JFrame abierto desde EmpleadoDashboardVista.
 *
 * Muestra el estado del almacén con métricas, filtros y tabla.
 * Es de solo consulta — la modificación de stock se hace
 * desde ModificarPerfumeVista.
 *
 * Equivalente futuro en JavaFX: stock.fxml + StockController.java
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class StockVista extends JFrame {

    // ── Navbar ────────────────────────────────────────────────────────
    private JPanel panelNavbar;
    private JLabel lblNombreApp;

    /** Cierra este JFrame y vuelve al dashboard. */
    private JButton btnVolver;

    // ── Título ────────────────────────────────────────────────────────
    private JLabel lblTitulo;

    // ── Métricas ──────────────────────────────────────────────────────
    /** Panel con 3 tarjetas en GridLayout(1,3) */
    private JPanel panelMetricas;

    /** Total de productos distintos en catálogo */
    private JPanel tarjetaTotalProductos;
    private JLabel lblTotalProductosTitulo;
    private JLabel lblTotalProductosValor;

    /** Productos con stock menor que Constantes.STOCK_MINIMO_ALERTA */
    private JPanel tarjetaStockBajo;
    private JLabel lblStockBajoTitulo;
    private JLabel lblStockBajoValor;

    /** Productos con stock = 0 */
    private JPanel tarjetaSinStock;
    private JLabel lblSinStockTitulo;
    private JLabel lblSinStockValor;

    // ── Filtros ───────────────────────────────────────────────────────
    private JPanel panelFiltros;

    /** Campo de búsqueda por nombre de perfume */
    private JTextField txtBuscar;

    /**
     * Filtro por ubicación en almacén.
     * Valores: "Todas", Constantes.LOC_MUJER,
     *          Constantes.LOC_HOMBRE, Constantes.LOC_UNISEX
     */
    private JComboBox<String> comboFiltroUbicacion;

    /**
     * Filtro por estado de stock.
     * Valores: "Todos", "Con stock", "Stock bajo", "Sin stock"
     */
    private JComboBox<String> comboFiltroEstado;

    // ── Tabla de stock ────────────────────────────────────────────────
    /**
     * Tabla con el estado del almacén.
     * Columnas: Perfume | Ubicación | Cantidad | Estado
     * Equivalente en JavaFX: TableView<Stock>.
     */
    private JTable tablaStock;

    /**
     * Modelo de datos de la tabla.
     * Uso: modeloTablaStock.addRow(new Object[]{"Velvet Rose","Estante A",12,"OK"})
     */
    private DefaultTableModel modeloTablaStock;

    /** ScrollPane que envuelve la tabla */
    private JScrollPane scrollTablaStock;


    // ── Getters ───────────────────────────────────────────────────────
    public JButton getBtnVolver()                    { return btnVolver; }
    public JLabel getLblTotalProductosValor()        { return lblTotalProductosValor; }
    public JLabel getLblStockBajoValor()             { return lblStockBajoValor; }
    public JLabel getLblSinStockValor()              { return lblSinStockValor; }
    public JTextField getTxtBuscar()                 { return txtBuscar; }
    public JComboBox<String> getComboFiltroUbicacion() { return comboFiltroUbicacion; }
    public JComboBox<String> getComboFiltroEstado()  { return comboFiltroEstado; }
    public JTable getTablaStock()                    { return tablaStock; }
    public DefaultTableModel getModeloTablaStock()   { return modeloTablaStock; }
    public JScrollPane getScrollTablaStock()         { return scrollTablaStock; }
}
