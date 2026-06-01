package view;

import model.DataStore;
import model.DataStore.Cliente;
import util.Theme;
import util.UIComponents;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pantalla de inicio de sesión de Golden Tale.
 * Selección de rol (Cliente / Empleado) y validación de credenciales.
 *
 * Credenciales de prueba:
 *   Cliente:   C001 / 1234   (Ana García)
 *              C002 / 1234   (Miguel Torres)
 *   Empleado:  E001 / 1234   (Roberto Iglesias)
 */
public class LoginFrame extends JFrame {

    private JTextField     fieldId;
    private JPasswordField fieldPassword;
    private boolean        modoEmpleado = false;

    private JButton btnCliente;
    private JButton btnEmpleado;

    public LoginFrame() {
        setTitle("Golden Tale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(420, 530);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG_MAIN);
        setLayout(new GridBagLayout());
        add(buildCard());
    }

    // ─── Tarjeta central ──────────────────────────────────────────────────

    private JPanel buildCard() {
        RoundedPanel card = new RoundedPanel(16, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 28, 40));
        card.setPreferredSize(new Dimension(340, 460));

        // Logo
        JPanel logoWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoWrap.setOpaque(false);
        RoundedPanel circle = new RoundedPanel(50, new Color(237, 230, 255));
        circle.setPreferredSize(new Dimension(62, 62));
        circle.setLayout(new GridBagLayout());
        JLabel icoLbl = new JLabel("🧴");
        icoLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        circle.add(icoLbl);
        logoWrap.add(circle);
        card.add(logoWrap);
        card.add(Box.createVerticalStrut(10));

        // Título
        JLabel title = new JLabel("Golden Tale");
        title.setFont(Theme.fontBold(22));
        title.setForeground(Theme.TEXT_DARK);
        title.setAlignmentX(CENTER_ALIGNMENT);
        card.add(title);

        JLabel subtitle = new JLabel("Sistema de inventario");
        subtitle.setFont(Theme.fontPlain(12));
        subtitle.setForeground(Theme.TEXT_LIGHT);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        card.add(subtitle);
        card.add(Box.createVerticalStrut(22));

        // ── Selector de rol ───────────────────────────────────────────────
        JPanel roleRow = new JPanel(new GridLayout(1, 2, 6, 0));
        roleRow.setOpaque(false);
        roleRow.setMaximumSize(new Dimension(260, 38));

        btnCliente  = roleBtn("  Cliente",  true);
        btnEmpleado = roleBtn("  Empleado", false);
        btnCliente .addActionListener(e -> setModo(false));
        btnEmpleado.addActionListener(e -> setModo(true));
        roleRow.add(btnCliente);
        roleRow.add(btnEmpleado);

        JPanel roleWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roleWrap.setOpaque(false);
        roleWrap.add(roleRow);
        card.add(roleWrap);
        card.add(Box.createVerticalStrut(20));

        // ── Campos ────────────────────────────────────────────────────────
        JPanel fields = new JPanel();
        fields.setOpaque(false);
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.setAlignmentX(CENTER_ALIGNMENT);
        fields.setMaximumSize(new Dimension(260, 130));

        JLabel lblId = new JLabel("ID de usuario");
        lblId.setFont(Theme.fontBold(12));
        lblId.setForeground(Theme.TEXT_DARK);
        lblId.setAlignmentX(LEFT_ALIGNMENT);
        fields.add(lblId);
        fields.add(Box.createVerticalStrut(4));

        fieldId = UIComponents.styledField("Ej: C001");
        fieldId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        fieldId.setAlignmentX(LEFT_ALIGNMENT);
        fields.add(fieldId);
        fields.add(Box.createVerticalStrut(12));

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(Theme.fontBold(12));
        lblPass.setForeground(Theme.TEXT_DARK);
        lblPass.setAlignmentX(LEFT_ALIGNMENT);
        fields.add(lblPass);
        fields.add(Box.createVerticalStrut(4));

        fieldPassword = UIComponents.styledPassword("••••••••");
        fieldPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        fieldPassword.setAlignmentX(LEFT_ALIGNMENT);
        fields.add(fieldPassword);

        card.add(fields);
        card.add(Box.createVerticalStrut(22));

        // ── Botón iniciar sesión ──────────────────────────────────────────
        JButton btnLogin = UIComponents.primaryButton("Iniciar sesión");
        btnLogin.setMaximumSize(new Dimension(260, 42));
        btnLogin.setAlignmentX(CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> doLogin());
        card.add(btnLogin);

        // Enter en cualquier campo
        ActionListener enterAction = e -> doLogin();
        fieldId.addActionListener(enterAction);
        fieldPassword.addActionListener(enterAction);

        // ── Pista de credenciales ─────────────────────────────────────────
        card.add(Box.createVerticalStrut(16));
        JLabel hint = new JLabel(
            "<html><center><font color='#aaaaaa' size='2'>"
            + "Cliente: C001&nbsp;/&nbsp;1234 &nbsp;·&nbsp; Empleado: E001&nbsp;/&nbsp;1234"
            + "</font></center></html>");
        hint.setAlignmentX(CENTER_ALIGNMENT);
        card.add(hint);

        return card;
    }

    // ─── Botón de rol ─────────────────────────────────────────────────────

    private JButton roleBtn(String text, boolean initialSelected) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean sel = Boolean.TRUE.equals(getClientProperty("selected"));
                g2.setColor(sel ? Theme.BG_SELECTED : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(sel ? Theme.PRIMARY : Theme.BORDER);
                g2.setStroke(new BasicStroke(sel ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.setColor(sel ? Theme.PRIMARY : Theme.TEXT_DARK);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.putClientProperty("selected", initialSelected);
        btn.setFont(Theme.fontBold(12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(127, 36));
        return btn;
    }

    // ─── Cambio de modo ───────────────────────────────────────────────────

    private void setModo(boolean empleado) {
        modoEmpleado = empleado;
        btnCliente .putClientProperty("selected", !empleado);
        btnEmpleado.putClientProperty("selected",  empleado);
        btnCliente .repaint();
        btnEmpleado.repaint();
    }

    // ─── Lógica de login ──────────────────────────────────────────────────

    private void doLogin() {
        String id   = fieldId.getText().trim();
        String pass = new String(fieldPassword.getPassword()).trim();

        // Validación de campos vacíos
        if (id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, introduce tu ID y contraseña.",
                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modoEmpleado) {
            // ── Login empleado ────────────────────────────────────────────
            if (id.equals("E001") && pass.equals("1234")) {
                dispose();
                new MainFrame("Roberto Iglesias", "Gerente").setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas.\n\nEmpleado de prueba: E001 / 1234",
                    "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                fieldPassword.setText("");
                fieldPassword.requestFocus();
            }
        } else {
            // ── Login cliente ─────────────────────────────────────────────
            Cliente cliente = DataStore.findCliente(id, pass);
            if (cliente != null) {
                dispose();
                new ClienteFrame(cliente).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas.\n\nClientes de prueba:\n  C001 / 1234  (Ana García)\n  C002 / 1234  (Miguel Torres)",
                    "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                fieldPassword.setText("");
                fieldPassword.requestFocus();
            }
        }
    }
}
