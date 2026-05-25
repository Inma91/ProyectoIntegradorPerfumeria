package com.goldentale.vistaEmpleado;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de Login — JFrame que aparece al arrancar la aplicación.
 *
 * El usuario selecciona si es Cliente o Empleado antes de introducir
 * sus credenciales. Según el rol, el controlador cierra esta ventana
 * y abre la correspondiente (EmpleadoDashboardVista o ClienteVista).
 *
 * Equivalente futuro en JavaFX: login.fxml + LoginController.java
 *
 * @author Bradon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class LoginVista extends JFrame {

    // ── Contenedor principal ──────────────────────────────────────────
    /** Panel raíz con BorderLayout */
    private JPanel panelPrincipal;

    /** Panel superior con el nombre de la app */
    private JPanel panelCabecera;

    /** Panel central con el formulario */
    private JPanel panelFormulario;

    /** Panel inferior con los botones de acción */
    private JPanel panelBotones;

    // ── Cabecera ──────────────────────────────────────────────────────
    /** Etiqueta con el nombre de la aplicación "Golden Tale" */
    private JLabel lblNombreApp;

    /** Etiqueta con el subtítulo "Sistema de inventario" */
    private JLabel lblSubtitulo;

    // ── Selector de rol ───────────────────────────────────────────────
    /** Etiqueta "Acceder como:" */
    private JLabel lblRol;

    /**
     * Panel que contiene los dos botones de rol en GridLayout(1,2).
     * Actúa como selector visual: el controlador resalta el activo
     * cambiando su color de fondo.
     */
    private JPanel panelSelectorRol;

    /**
     * Botón para seleccionar el rol Cliente.
     * Al pulsarlo: se resalta, el otro se desresalta,
     * y el placeholder de txtId cambia a "Ej: C001".
     * Equivalente en JavaFX: ToggleButton dentro de ToggleGroup.
     */
    private JButton btnRolCliente;

    /**
     * Botón para seleccionar el rol Empleado.
     * Al pulsarlo: se resalta, el otro se desresalta,
     * y el placeholder de txtId cambia a "Ej: E001".
     * Equivalente en JavaFX: ToggleButton dentro de ToggleGroup.
     */
    private JButton btnRolEmpleado;

    // ── Campos del formulario ─────────────────────────────────────────
    /** Etiqueta del campo de identificador */
    private JLabel lblId;

    /**
     * Campo de texto para el ID de usuario.
     * El texto de ayuda cambia según el rol seleccionado.
     * Cliente → "Ej: C001" | Empleado → "Ej: E001"
     */
    private JTextField txtId;

    /** Etiqueta del campo de contraseña */
    private JLabel lblPassword;

    /**
     * Campo de contraseña — oculta los caracteres con puntos.
     * Equivalente en JavaFX: PasswordField.
     */
    private JPasswordField txtPassword;

    /**
     * Botón para mostrar u ocultar la contraseña.
     * Alterna entre mostrar y ocultar los caracteres.
     */
    private JButton btnMostrarPassword;

    // ── Acciones ──────────────────────────────────────────────────────
    /**
     * Botón principal de inicio de sesión.
     * El controlador valida contra ClienteDAO o EmpleadoDAO
     * según rolSeleccionado, verifica el hash con HashUtil.verificar(),
     * cierra este JFrame y abre la ventana correspondiente.
     */
    private JButton btnIniciarSesion;

    // ── Feedback ──────────────────────────────────────────────────────
    /**
     * Etiqueta de error — invisible por defecto (setVisible(false)).
     * Se muestra en rojo cuando las credenciales son incorrectas
     * o algún campo está vacío.
     */
    private JLabel lblError;

    // ── Estado interno ────────────────────────────────────────────────
    /**
     * Indica el rol actualmente seleccionado.
     * Valores: Constantes.ROL_CLIENTE o Constantes.ROL_EMPLEADO.
     * Se inicializa a ROL_CLIENTE al abrir la ventana.
     */
    private String rolSeleccionado;


    // ── Getters ───────────────────────────────────────────────────────
    public JPanel getPanelSelectorRol()    { return panelSelectorRol; }
    public JButton getBtnRolCliente()      { return btnRolCliente; }
    public JButton getBtnRolEmpleado()     { return btnRolEmpleado; }
    public JTextField getTxtId()           { return txtId; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnMostrarPassword() { return btnMostrarPassword; }
    public JButton getBtnIniciarSesion()   { return btnIniciarSesion; }
    public JLabel getLblError()            { return lblError; }
    public String getRolSeleccionado()     { return rolSeleccionado; }

    // ── Setters de estado ─────────────────────────────────────────────
    public void setRolSeleccionado(String rol) { this.rolSeleccionado = rol; }
}
