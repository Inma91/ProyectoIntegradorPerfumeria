package view;

import model.DataStore;
import model.DataStore.*;
import util.Theme;
import util.UIComponents;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel Dashboard — combina el resumen general (Panel Empleado 1)
 * y la tabla de pedidos recientes + accesos rápidos (Panel Empleado 2).
 */
public class DashboardPanel extends JPanel {

    private static JLabel lbl;
	private JScrollPane scroll;
	private JPanel panel;
	private JLabel titleResumen;
	private JPanel statsGrid;
	private JLabel titlePedidos;
	private JLabel titleAccesos;
	private JPanel quickGrid;
	private RoundedPanel card;
	private RoundedPanel iconPanel;
	private JLabel ico;
	private JLabel titleLbl;
	private JLabel descLbl;
	private JTable table;
	private JScrollPane sp;

	public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        scroll = new JScrollPane(buildContent());
        scroll.setBorder(null);
        scroll.setBackground(Theme.BG_MAIN);
        scroll.getViewport().setBackground(Theme.BG_MAIN);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildContent() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.BG_MAIN);

        // ── Resumen General ───────────────────────────────────────────────
        titleResumen = new JLabel("RESUMEN GENERAL");
        titleResumen.setFont(Theme.fontBold(12));
        titleResumen.setForeground(Theme.TEXT_MEDIUM);
        titleResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleResumen);
        panel.add(Box.createVerticalStrut(12));

        statsGrid = new JPanel(new GridLayout(1, 4, 10, 0));
        statsGrid.setOpaque(false);
        statsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsGrid.add(UIComponents.statCard("Pedidos hoy",    "8",  "+3 respecto a ayer",    Theme.TEXT_DARK));
        statsGrid.add(UIComponents.statCard("Pendientes",     "5",  "Requieren atención",    new Color(200, 130, 0)));
        statsGrid.add(UIComponents.statCard("Stock bajo",     "2",  "Menos de 5 uds",        Theme.DANGER));
        statsGrid.add(UIComponents.statCard("Entregados hoy", "3",  "Completados",           Theme.SUCCESS));
        panel.add(statsGrid);
        panel.add(Box.createVerticalStrut(24));

        // ── Pedidos Recientes ─────────────────────────────────────────────
        titlePedidos = new JLabel("PEDIDOS RECIENTES");
        titlePedidos.setFont(Theme.fontBold(12));
        titlePedidos.setForeground(Theme.TEXT_MEDIUM);
        titlePedidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titlePedidos);
        panel.add(Box.createVerticalStrut(10));

        panel.add(buildPedidosTable());
        panel.add(Box.createVerticalStrut(24));

        // ── Accesos Rápidos ───────────────────────────────────────────────
        titleAccesos = new JLabel("ACCESOS RÁPIDOS");
        titleAccesos.setFont(Theme.fontBold(12));
        titleAccesos.setForeground(Theme.TEXT_MEDIUM);
        titleAccesos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleAccesos);
        panel.add(Box.createVerticalStrut(10));

        quickGrid = new JPanel(new GridLayout(2, 2, 12, 12));
        quickGrid.setOpaque(false);
        quickGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        quickGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        quickGrid.add(quickCard("➕", "Añadir perfume",
            "Dar de alta un nuevo producto en el catálogo",
            new Color(237, 230, 255)));
        quickGrid.add(quickCard("🏠", "Consultar stock",
            "Ver disponibilidad y ubicaciones del almacén",
            new Color(210, 245, 235)));
        quickGrid.add(quickCard("🛍", "Gestionar pedidos",
            "Cambiar estados y procesar pedidos pendientes",
            new Color(255, 240, 210)));
        quickGrid.add(quickCard("💳", "Registrar pago",
            "Confirmar el pago de un pedido entregado",
            new Color(210, 245, 235)));

        panel.add(quickGrid);
        return panel;
    }

    private JScrollPane buildPedidosTable() {
        List<Pedido> pedidos = DataStore.getPedidos();

        String[] cols = {"Ref.", "Cliente", "Perfumes", "Total", "Estado"};
        Object[][] data = new Object[pedidos.size()][5];
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            data[i][0] = p.ref;
            data[i][1] = p.cliente;
            data[i][2] = p.perfume;
            data[i][3] = String.format("%.2f €", p.total);
            data[i][4] = p.estado;
        }

        table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        styleTable(table);

        sp = new JScrollPane(table);
        sp.setBorder(new javax.swing.border.LineBorder(Theme.BORDER, 1, true));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    static void styleTable(JTable table) {
        table.setFont(Theme.fontPlain(12));
        table.setRowHeight(34);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(Theme.BORDER_LIGHT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Theme.BG_SELECTED);
        table.setSelectionForeground(Theme.TEXT_DARK);
        table.setFocusable(false);

        table.getTableHeader().setFont(Theme.fontBold(11));
        table.getTableHeader().setBackground(new Color(248, 246, 242));
        table.getTableHeader().setForeground(Theme.TEXT_MEDIUM);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));

        // Estado cell renderer with colored badges
        table.getColumnModel().getColumn(table.getColumnCount()-1).setCellRenderer(
            (tbl, val, sel, foc, row, col) -> {
                String estado = val != null ? val.toString() : "";
                JLabel lbl = new JLabel(estado) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        Color bg; Color fg;
                        switch (estado) {
                            case "Enviado":     bg = new Color(210,245,235); fg = Theme.SUCCESS; break;
                            case "Procesando":  bg = new Color(210,235,255); fg = new Color(30,80,160); break;
                            case "Pendiente":   bg = new Color(255,240,210); fg = new Color(160,100,20); break;
                            default:            bg = Theme.BORDER_LIGHT;     fg = Theme.TEXT_DARK; break;
                        }
                        g2.setColor(sel ? Theme.BG_SELECTED : tbl.getBackground());
                        g2.fillRect(0, 0, getWidth(), getHeight());
                        int pw = fm(g2, estado) + 20;
                        int ph = 22;
                        int px = (getWidth() - pw) / 2;
                        int py = (getHeight() - ph) / 2;
                        g2.setColor(bg);
                        g2.fillRoundRect(px, py, pw, ph, ph, ph);
                        g2.setColor(fg);
                        g2.setFont(getFont());
                        FontMetrics fm = g2.getFontMetrics();
                        g2.drawString(estado, px + (pw - fm.stringWidth(estado))/2,
                            py + (ph + fm.getAscent() - fm.getDescent())/2);
                        g2.dispose();
                    }
                    private int fm(Graphics2D g2, String s) {
                        return g2.getFontMetrics(getFont()).stringWidth(s);
                    }
                };
                lbl.setFont(Theme.fontBold(11));
                lbl.setBackground(sel ? Theme.BG_SELECTED : Color.WHITE);
                lbl.setOpaque(true);
                return lbl;
            }
        );

        // Default cell padding
        table.setDefaultRenderer(Object.class, (tbl, val, sel, foc, row, col) -> {
            lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(Theme.fontPlain(12));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            lbl.setBackground(sel ? Theme.BG_SELECTED : (row % 2 == 0 ? Color.WHITE : new Color(251,249,246)));
            lbl.setForeground(Theme.TEXT_DARK);
            lbl.setOpaque(true);
            return lbl;
        });

        // Re-apply estado renderer on top
        int lastCol = table.getColumnCount()-1;
        table.getColumnModel().getColumn(lastCol).setCellRenderer(
            table.getColumnModel().getColumn(lastCol).getCellRenderer()
        );
    }

    private JPanel quickCard(String icon, String title, String desc, Color iconBg) {
        card = new RoundedPanel(12, Color.WHITE, Theme.BORDER_LIGHT);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        iconPanel = new RoundedPanel(10, iconBg);
        iconPanel.setPreferredSize(new Dimension(40, 40));
        iconPanel.setMaximumSize(new Dimension(40, 40));
        iconPanel.setLayout(new GridBagLayout());
        ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        iconPanel.add(ico);
        card.add(iconPanel);
        card.add(Box.createVerticalStrut(10));

        titleLbl = new JLabel(title);
        titleLbl.setFont(Theme.fontBold(13));
        titleLbl.setForeground(Theme.TEXT_DARK);
        card.add(titleLbl);
        card.add(Box.createVerticalStrut(4));

        descLbl = new JLabel("<html><body style='width:140px'>" + desc + "</body></html>");
        descLbl.setFont(Theme.fontPlain(11));
        descLbl.setForeground(Theme.TEXT_MEDIUM);
        card.add(descLbl);

        return card;
    }
}
