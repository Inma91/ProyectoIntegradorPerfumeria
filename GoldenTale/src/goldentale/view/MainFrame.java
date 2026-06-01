package view;

import util.Theme;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Marco principal de la aplicación para empleados.
 * Contiene la barra superior, la barra lateral de navegación y el área de contenido.
 * Cada sección se carga como panel intercambiable.
 */
public class MainFrame extends JFrame {

    private String nombreUsuario;
    private String rolUsuario;

    private JPanel contentArea;
    private CardLayout cardLayout;

    // Vista activa
    private String vistaActual = "dashboard";

    // Botones del sidebar
    private JButton btnDashboard;
    private JButton btnPedidos;
    private JButton btnPerfumes;
    private JButton btnStock;
    private JButton btnPagos;
	private JPanel main;
	private JComponent header;
	private JPanel logoPanel;
	private JLabel logoIco;
	private JComponent logoText;
	private JPanel rightPanel;
	private JLabel userLbl;
	private JLabel roleBadge;
	private JButton btnSalir;

    public MainFrame(String nombre, String rol) {
        this.nombreUsuario = nombre;
        this.rolUsuario    = rol;

        setTitle("Golden Tale — Panel empleado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(800, 560));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        add(buildHeader(), BorderLayout.NORTH);

        // Main area
        main = new JPanel(new BorderLayout());
        main.setBackground(Theme.BG_MAIN);
        main.add(buildSidebar(), BorderLayout.WEST);
        main.add(buildContent(), BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);

        // Cargar dashboard por defecto
        showView("dashboard");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Header
    // ──────────────────────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        header = new JPanel(new BorderLayout());
        header.setBackground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        header.setPreferredSize(new Dimension(0, 58));

        // Logo
        logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        logoPanel.setOpaque(false);
        logoIco = new JLabel("🧴");
        logoIco.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        logoText = new JLabel("Golden Tale");
        logoText.setFont(Theme.fontBold(16));
        logoText.setForeground(Color.WHITE);
        logoPanel.add(logoIco);
        logoPanel.add(logoText);

        // Right: user info + logout
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        userLbl = new JLabel(" " + nombreUsuario);
        userLbl.setFont(Theme.fontPlain(12));
        userLbl.setForeground(new Color(200, 190, 230));

        // Role badge
        roleBadge = new JLabel(rolUsuario) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(120, 90, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        roleBadge.setFont(Theme.fontBold(11));
        roleBadge.setForeground(Color.WHITE);
        roleBadge.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        roleBadge.setOpaque(false);

        btnSalir = new JButton("  Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()
                    ? new Color(255, 255, 255, 40) : new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(new Color(200, 190, 255));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btnSalir.setFont(Theme.fontBold(12));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(90, 32));
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        rightPanel.add(userLbl);
        rightPanel.add(roleBadge);
        rightPanel.add(Box.createHorizontalStrut(6));
        rightPanel.add(btnSalir);

        header.add(logoPanel,  BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        // Vertical center alignment hack
        header.add(Box.createVerticalGlue(), BorderLayout.CENTER);

        return (JPanel) header;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Sidebar
    // ──────────────────────────────────────────────────────────────────────────

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.BG_SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 12, 16, 12)
        ));
        sidebar.setPreferredSize(new Dimension(200, 0));

        // PRINCIPAL
        sidebar.add(sectionLabel("PRINCIPAL"));
        btnDashboard = navButton("  Dashboard",  "dashboard");
        btnPedidos   = navButton("  Pedidos",    "pedidos");
        sidebar.add(btnDashboard);
        sidebar.add(btnPedidos);

        sidebar.add(Box.createVerticalStrut(8));

        // CATÁLOGO
        sidebar.add(sectionLabel("CATÁLOGO"));
        btnPerfumes  = navButton("  Perfumes",   "perfumes");
        btnStock     = navButton("  Stock",       "stock");
        sidebar.add(btnPerfumes);
        sidebar.add(btnStock);

        sidebar.add(Box.createVerticalStrut(8));

        // GESTIÓN
        sidebar.add(sectionLabel("GESTIÓN"));
        btnPagos     = navButton("  Pagos",       "pagos");
        sidebar.add(btnPagos);

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.fontBold(10));
        lbl.setForeground(Theme.TEXT_LIGHT);
        lbl.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton navButton(String text, String viewKey) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean active = vistaActual.equals(viewKey);
                if (active) {
                    g2.setColor(Theme.BG_SELECTED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(230, 225, 220));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.setColor(active ? Theme.PRIMARY : Theme.TEXT_DARK);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), 12, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontPlain(13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btn.setPreferredSize(new Dimension(176, 36));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> showView(viewKey));
        return btn;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Content area (CardLayout)
    // ──────────────────────────────────────────────────────────────────────────

    private JPanel buildContent() {
        cardLayout  = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(Theme.BG_MAIN);

        contentArea.add(new DashboardPanel(),  "dashboard");
        contentArea.add(new PedidosPanel(),    "pedidos");
        contentArea.add(new PerfumesPanel(),   "perfumes");
        contentArea.add(new StockPanel(),      "stock");
        contentArea.add(new PagosPanel(),      "pagos");

        return contentArea;
    }

    private void showView(String key) {
        vistaActual = key;
        cardLayout.show(contentArea, key);
        // Repaint sidebar buttons to update selection
        repaintSidebar();
    }

    private void repaintSidebar() {
        btnDashboard.repaint();
        btnPedidos.repaint();
        btnPerfumes.repaint();
        btnStock.repaint();
        btnPagos.repaint();
    }
}
