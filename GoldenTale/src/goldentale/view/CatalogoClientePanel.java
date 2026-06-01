package view;

import model.DataStore;
import model.DataStore.Perfume;
import util.Theme;
import util.UIComponents;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel Catálogo — vista del cliente.
 * Muestra la tabla de perfumes con búsqueda, filtro de público y categoría.
 * Desde aquí se puede añadir al carrito o ir a Mis Pedidos.
 */
public class CatalogoClientePanel extends JPanel {

    private final ClienteFrame frame;
    private JTextField  searchField;
    private JComboBox<String> filtroPublico;
    private JComboBox<String> filtroCategoria;
    private JPanel      tableWrapper;

    public CatalogoClientePanel(ClienteFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);

        add(buildHeader(),  BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.BG_MAIN);
        body.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        body.add(buildFilters(), BorderLayout.NORTH);

        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Theme.BG_MAIN);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        body.add(tableWrapper, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        refresh();
    }

    // ─── Header ───────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(Theme.PRIMARY);
        h.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        h.setPreferredSize(new Dimension(0, 56));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        JLabel ico  = new JLabel("🧴"); ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        JLabel name = new JLabel("Golden Tale"); name.setFont(Theme.fontBold(16)); name.setForeground(Color.WHITE);
        left.add(ico); left.add(name);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        JLabel userLbl = new JLabel(frame.getCliente().nombre);
        userLbl.setFont(Theme.fontPlain(12));
        userLbl.setForeground(new Color(200, 190, 230));

        JButton btnPedidos = headerBtn("  Mis pedidos");
        btnPedidos.addActionListener(e -> frame.showMisPedidos());
        JButton btnSalir   = headerBtn("  Salir");
        btnSalir.addActionListener(e -> frame.logout());

        right.add(userLbl); right.add(btnPedidos); right.add(btnSalir);
        h.add(left,  BorderLayout.WEST);
        h.add(right, BorderLayout.EAST);
        return h;
    }

    private JButton headerBtn(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(255,255,255,50) : new Color(255,255,255,25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(new Color(200,190,255));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontBold(12));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 32));
        return btn;
    }

    // ─── Filtros ──────────────────────────────────────────────────────────

    private JPanel buildFilters() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        searchField = UIComponents.styledField("  Buscar perfume...");
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        searchField.setAlignmentX(LEFT_ALIGNMENT);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refresh(); }
        });
        p.add(searchField);
        p.add(Box.createVerticalStrut(8));

        filtroPublico    = styledCombo(new String[]{"Todos","Mujer","Hombre","Unisex"});
        filtroCategoria  = styledCombo(new String[]{"Categoría","Floral","Oriental","Cítrico","Acuático","Amaderado"});
        filtroPublico   .setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        filtroCategoria .setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        filtroPublico   .setAlignmentX(LEFT_ALIGNMENT);
        filtroCategoria .setAlignmentX(LEFT_ALIGNMENT);
        filtroPublico   .addActionListener(e -> refresh());
        filtroCategoria .addActionListener(e -> refresh());

        p.add(filtroPublico);
        p.add(Box.createVerticalStrut(8));
        p.add(filtroCategoria);
        return p;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(Theme.fontPlain(12));
        c.setBackground(Color.WHITE);
        return c;
    }

    // ─── Tabla ────────────────────────────────────────────────────────────

    public void refresh() {
        if (tableWrapper == null) return;
        tableWrapper.removeAll();

        String query = searchField != null ? searchField.getText().trim().toLowerCase() : "";
        String pub   = filtroPublico   != null ? (String) filtroPublico.getSelectedItem()   : "Todos";
        String cat   = filtroCategoria != null ? (String) filtroCategoria.getSelectedItem() : "Categoría";

        List<Perfume> src = DataStore.getPerfumes();
        List<Perfume> filtered = new java.util.ArrayList<>();
        for (Perfume pf : src) {
            if (!query.isEmpty() && !pf.nombre.toLowerCase().contains(query)
                    && !pf.marca.toLowerCase().contains(query)) continue;
            if (!pub.equals("Todos") && !pf.publico.equals(pub)) continue;
            if (!cat.equals("Categoría") && !pf.categoria.equals(cat)) continue;
            filtered.add(pf);
        }

        String[] cols = {"Nombre","Marca","Categoría","Público","ml","Precio","Stock","+"};
        Object[][] data = new Object[filtered.size()][8];
        for (int i = 0; i < filtered.size(); i++) {
            Perfume pf = filtered.get(i);
            data[i][0] = pf.nombre;
            data[i][1] = pf.marca;
            data[i][2] = pf.categoria;
            data[i][3] = pf.publico;
            data[i][4] = pf.ml + " ml";
            data[i][5] = String.format("%.2f €", pf.precio);
            data[i][6] = pf.stock;
            data[i][7] = i; // index into filtered
        }

        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        styleClientTable(table);

        // Público tag
        table.getColumnModel().getColumn(3).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            String pub2 = val != null ? val.toString() : "";
            JLabel tag = publicTag(pub2);
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            wrap.setBackground(sel ? Theme.BG_SELECTED : rowBg(row));
            wrap.add(tag);
            return wrap;
        });

        // Stock renderer
        table.getColumnModel().getColumn(6).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            int idx   = (Integer) tbl.getValueAt(row, 7);
            int stock = filtered.get(idx).stock;
            String txt = stock == 0 ? "✗  Sin st..." : stock < 5 ? "⚠  " + stock + " uds" : "✓  " + stock + " uds";
            JLabel lbl = new JLabel(txt);
            lbl.setFont(Theme.fontBold(11));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
            lbl.setForeground(stock == 0 ? Theme.DANGER : stock < 5 ? Theme.WARNING : Theme.SUCCESS);
            lbl.setBackground(sel ? Theme.BG_SELECTED : rowBg(row));
            lbl.setOpaque(true);
            return lbl;
        });

        // Add-to-cart button
        final List<Perfume> fRef = filtered;
        table.getColumnModel().getColumn(7).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            int idx = (Integer) val;
            boolean available = fRef.get(idx).stock > 0;
            JButton btn = UIComponents.primaryButton(available ? "＋" : "—");
            btn.setEnabled(available);
            btn.setFont(Theme.fontBold(13));
            btn.setPreferredSize(new Dimension(36, 26));
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
            wrap.setBackground(sel ? Theme.BG_SELECTED : rowBg(row));
            wrap.add(btn);
            return wrap;
        });
        table.getColumnModel().getColumn(7).setMaxWidth(60);
        table.getColumnModel().getColumn(6).setMaxWidth(90);
        table.getColumnModel().getColumn(4).setMaxWidth(60);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 7) {
                    int idx = (Integer) table.getValueAt(row, 7);
                    Perfume pf = fRef.get(idx);
                    if (pf.stock > 0) {
                        frame.addToCarrito(pf, 1);
                        JOptionPane.showMessageDialog(CatalogoClientePanel.this,
                            "\"" + pf.nombre + "\" añadido al pedido.",
                            "Añadido", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(Theme.BORDER, 1, true));
        sp.getViewport().setBackground(Color.WHITE);
        tableWrapper.add(sp, BorderLayout.CENTER);
        tableWrapper.revalidate();
        tableWrapper.repaint();
    }

    static void styleClientTable(JTable table) {
        table.setFont(Theme.fontPlain(12));
        table.setRowHeight(38);
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
        table.setDefaultRenderer(Object.class, (tbl, val, sel, foc, row, col) -> {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(Theme.fontPlain(12));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            lbl.setBackground(sel ? Theme.BG_SELECTED : rowBg(row));
            lbl.setForeground(Theme.TEXT_DARK);
            lbl.setOpaque(true);
            return lbl;
        });
    }

    static Color rowBg(int row) {
        return row % 2 == 0 ? Color.WHITE : new Color(251, 249, 246);
    }

    static JLabel publicTag(String pub) {
        Color bg, fg;
        switch (pub) {
            case "Mujer":  bg = new Color(255,220,235); fg = new Color(160,50,90);  break;
            case "Hombre": bg = new Color(210,230,255); fg = new Color(40,80,160);  break;
            default:       bg = new Color(230,220,255); fg = Theme.PRIMARY;          break;
        }
        JLabel tag = new JLabel(pub) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tag.setForeground(fg);
        tag.setFont(Theme.fontBold(11));
        tag.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        tag.setOpaque(false);
        return tag;
    }
}
