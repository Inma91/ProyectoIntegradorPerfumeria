package com.goldentale.vistaEmpleado;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana Modificar Perfume — JFrame abierto desde EmpleadoDashboardVista.
 *
 * Tiene dos fases:
 *   1. Búsqueda: el empleado introduce nombre y ml para verificar
 *      si el perfume existe en la base de datos.
 *   2. Modificación: si existe, aparece el formulario donde SOLO
 *      se puede modificar la cantidad de stock (se suma al existente).
 *
 * Equivalente futuro en JavaFX: catalogo_admin.fxml (modo edición)
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class ModificarPerfumeVista extends JFrame {

    // ── Navbar ────────────────────────────────────────────────────────
    private JPanel panelNavbar;
    private JLabel lblNombreApp;

    /** Cierra este JFrame y vuelve al dashboard. */
    private JButton btnVolver;

    // ── Título ────────────────────────────────────────────────────────
    private JLabel lblTitulo;

    // ── FASE 1: Panel de búsqueda (siempre visible) ───────────────────
    private JPanel panelBusqueda;
    private JLabel lblBusquedaTitulo;

    private JLabel lblBuscarNombre;
    /** Nombre del perfume a buscar. */
    private JTextField txtBuscarNombre;

    private JLabel lblBuscarMl;
    /** Mililitros del perfume a buscar. */
    private JTextField txtBuscarMl;

    /**
     * Botón que lanza la búsqueda.
     * El controlador llama a PerfumeDAO.findByNombreYMl()
     * y según el resultado muestra lblResultado y panelModificacion.
     */
    private JButton btnBuscar;

    /**
     * Resultado de la búsqueda — invisible por defecto.
     * Verde: "Perfume encontrado: Velvet Rose 50ml"
     * Rojo:  "No se encontró ningún perfume con esos datos"
     */
    private JLabel lblResultado;

    // ── FASE 2: Panel de modificación (invisible hasta encontrar) ─────
    /**
     * Panel que aparece solo cuando la búsqueda tiene resultado.
     * setVisible(false) por defecto.
     * setVisible(true) cuando PerfumeDAO.findByNombreYMl() devuelve resultado.
     */
    private JPanel panelModificacion;

    /**
     * Muestra los datos del perfume encontrado en modo lectura.
     * Ejemplo: "Velvet Rose | Maison Luxe | Floral | 50ml | Estante A"
     */
    private JLabel lblDatosPerfume;

    /** Etiqueta que muestra el stock actual */
    private JLabel lblStockActual;

    private JLabel lblCantidadAñadir;
    /**
     * Unidades a añadir al stock existente.
     * El controlador NO sobreescribe — suma al valor actual.
     * Ejemplo: stock actual = 8, se añaden 10 → nuevo stock = 18.
     */
    private JTextField txtCantidadAñadir;

    /**
     * Preview del resultado antes de confirmar.
     * Ejemplo: "Stock actual: 8 uds → tras añadir: 18 uds"
     * Se actualiza en tiempo real con un DocumentListener.
     */
    private JLabel lblPreview;

    /**
     * Error del formulario de modificación — invisible por defecto.
     * Se muestra si la cantidad está vacía o no es un número positivo.
     */
    private JLabel lblError;

    /**
     * Éxito — invisible por defecto.
     * Se muestra en verde al guardar correctamente.
     */
    private JLabel lblExito;

    /**
     * Guarda el cambio de stock.
     * El controlador llama a StockDAO.actualizarCantidad() sumando
     * txtCantidadAñadir al stock actual del perfume encontrado.
     */
    private JButton btnGuardar;

    /**
     * Cancela y oculta el panelModificacion.
     * También limpia txtBuscarNombre, txtBuscarMl y lblResultado.
     */
    private JButton btnCancelar;


    // ── Getters ───────────────────────────────────────────────────────
    public JButton getBtnVolver()          { return btnVolver; }
    public JTextField getTxtBuscarNombre() { return txtBuscarNombre; }
    public JTextField getTxtBuscarMl()     { return txtBuscarMl; }
    public JButton getBtnBuscar()          { return btnBuscar; }
    public JLabel getLblResultado()        { return lblResultado; }
    public JPanel getPanelModificacion()   { return panelModificacion; }
    public JLabel getLblDatosPerfume()     { return lblDatosPerfume; }
    public JLabel getLblStockActual()      { return lblStockActual; }
    public JTextField getTxtCantidadAñadir() { return txtCantidadAñadir; }
    public JLabel getLblPreview()          { return lblPreview; }
    public JLabel getLblError()            { return lblError; }
    public JLabel getLblExito()            { return lblExito; }
    public JButton getBtnGuardar()         { return btnGuardar; }
    public JButton getBtnCancelar()        { return btnCancelar; }
}
