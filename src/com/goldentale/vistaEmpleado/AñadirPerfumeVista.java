package com.goldentale.vistaEmpleado;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana Añadir Perfume — JFrame abierto desde EmpleadoDashboardVista.
 *
 * Formulario para dar de alta un nuevo perfume en el catálogo.
 * Al guardar, el controlador llama a PerfumeDAO.save() y luego
 * a StockDAO.save() con la localización calculada por LocalizacionUtil.
 *
 * Equivalente futuro en JavaFX: catalogo_admin.fxml (modo alta)
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class AñadirPerfumeVista extends JFrame {

    // ── Navbar ────────────────────────────────────────────────────────
    private JPanel panelNavbar;
    private JLabel lblNombreApp;

    /**
     * Cierra este JFrame y vuelve al dashboard.
     * No destruye el dashboard porque sigue abierto detrás.
     */
    private JButton btnVolver;

    // ── Título ────────────────────────────────────────────────────────
    private JLabel lblTitulo;

    // ── Formulario ────────────────────────────────────────────────────
    private JPanel panelFormulario;

    private JLabel lblNombre;
    /** Nombre comercial del perfume. Campo obligatorio. */
    private JTextField txtNombre;

    private JLabel lblMarca;
    /** Marca o casa de perfumería. Campo obligatorio. */
    private JTextField txtMarca;

    private JLabel lblCategoria;
    /**
     * Categoría olfativa. Valores: Constantes.CATEGORIAS_PERFUME
     * Equivalente en JavaFX: ComboBox<String>.
     */
    private JComboBox<String> comboCategoria;

    private JLabel lblPrecio;
    /** Precio en euros. Validar que sea numérico positivo. */
    private JTextField txtPrecio;

    private JLabel lblMl;
    /** Volumen del frasco en mililitros. */
    private JTextField txtMl;

    private JLabel lblPublico;
    /**
     * Público objetivo. Valores: Constantes.PUBLICOS_OBJETIVO
     * Al cambiar, actualiza lblLocalizacion con LocalizacionUtil.asignar().
     */
    private JComboBox<String> comboPublico;

    private JLabel lblLocalizacionTitulo;
    /**
     * Muestra la localización asignada automáticamente.
     * Se actualiza al cambiar comboPublico.
     * Ejemplo: "Estante A — Mujer"
     */
    private JLabel lblLocalizacion;

    private JLabel lblDescripcion;
    /**
     * Notas olfativas y descripción del producto.
     * Campo opcional. Equivalente en JavaFX: TextArea.
     */
    private JTextArea txtDescripcion;
    private JScrollPane scrollDescripcion;

    private JLabel lblStock;
    /** Unidades iniciales a registrar en almacén. */
    private JTextField txtStock;

    // ── Feedback ──────────────────────────────────────────────────────
    /**
     * Etiqueta de error — invisible por defecto.
     * Se muestra si hay campos obligatorios vacíos o datos inválidos.
     */
    private JLabel lblError;

    /**
     * Etiqueta de éxito — invisible por defecto.
     * Se muestra en verde cuando el perfume se guarda correctamente.
     */
    private JLabel lblExito;

    // ── Botones de acción ─────────────────────────────────────────────
    /**
     * Guarda el perfume.
     * El controlador valida los campos, llama a PerfumeDAO.save()
     * y luego a StockDAO.save() con la localización automática.
     */
    private JButton btnGuardar;

    /**
     * Limpia todos los campos del formulario.
     * También resetea lblLocalizacion al texto por defecto.
     */
    private JButton btnLimpiar;


    // ── Getters ───────────────────────────────────────────────────────
    public JButton getBtnVolver()            { return btnVolver; }
    public JTextField getTxtNombre()         { return txtNombre; }
    public JTextField getTxtMarca()          { return txtMarca; }
    public JComboBox<String> getComboCategoria() { return comboCategoria; }
    public JTextField getTxtPrecio()         { return txtPrecio; }
    public JTextField getTxtMl()             { return txtMl; }
    public JComboBox<String> getComboPublico() { return comboPublico; }
    public JLabel getLblLocalizacion()       { return lblLocalizacion; }
    public JTextArea getTxtDescripcion()     { return txtDescripcion; }
    public JTextField getTxtStock()          { return txtStock; }
    public JLabel getLblError()              { return lblError; }
    public JLabel getLblExito()              { return lblExito; }
    public JButton getBtnGuardar()           { return btnGuardar; }
    public JButton getBtnLimpiar()           { return btnLimpiar; }
}
