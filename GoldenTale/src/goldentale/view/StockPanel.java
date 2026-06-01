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
 * Panel Control de Stock — resumen de almacén, filtros y tabla
 * con barra de stock visual y botón de actualización.
 */
public class StockPanel extends JPanel {

    private JTextField searchField;
    private JComboBox<String> filtroUbicacion;
    private JComboBox<String> filtroEstado;
    private JPanel tableWrapper;
    private List<Perfume> perfumes;

    public StockPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        perfumes = DataStore.getPerfumes();

        JPanel topArea = new JPanel();
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.Y_AXIS));
        topArea.setOpaque(false);
        topArea.add(buildSummary());
        topArea.add(Box.createVerticalStrut(20));
        topArea.add(buildFilters());
        topArea.add(Box.createVerticalStrut(12));
        add(topArea, BorderLayout.NORTH);

        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Theme.BG_MAIN);
        refreshTable();
        add(tableWrapper, BorderLayout.CENTER);
    }

    private JPanel buildSummary() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JLabel title = new JLabel("RESUMEN DE ALMACÉN");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);

        long sinStock   = perfumes.stream().filter(p -> p.stock == 0).count();
        long stockBajo  = perfumes.stream().filter(p -> p.stock > 0 && p.stock < 5).count();

        JPanel cards = new JPanel(new GridLayout(1, 3, 10, 0));
        cards.setOpaque(false);
        cards.add(UIComponents.statCard("Total productos",
            String.valueOf(perfumes.size()), "En catálogo", Theme.TEXT_DARK));
        cards.add(UIComponents.statCard("Stock bajo",
            String.valueOf(stockBajo), "Menos de 5 uds", Theme.WARNING));
        cards.add(UIComponents.statCard("Sin stock",
            String.valueOf(sinStock),  "Requieren reposición", Theme.DANGER));

        panel.add(title, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFilters() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel sectionLbl = new JLabel("ESTADO DEL ALMACÉN");
        sectionLbl.setFont(Theme.fontBold(12));
        sectionLbl.setForeground(Theme.TEXT_MEDIUM);
        sectionLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sectionLbl);
        panel.add(Box.createVerticalStrut(10));

        searchField = UIComponents.styledField("🔍  Buscar perfume...");
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { refreshTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { refreshTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
        });
        panel.add(searchField);
        panel.add(Box.createVerticalStrut(8));

        filtroUbicacion = styledCombo(new String[]{
            "Todas las ubicaciones","Estante A","Estante B","Estante C","Estante D"});
        filtroEstado = styledCombo(new String[]{
            "Todos los estados","Con stock","Stock bajo","Sin stock"});

        filtroUbicacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        filtroEstado   .setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        filtroUbicacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        filtroEstado   .setAlignmentX(Component.LEFT_ALIGNMENT);
        filtroUbicacion.addActionListener(e -> refreshTable());
        filtroEstado   .addActionListener(e -> refreshTable());

        panel.add(filtroUbicacion);
        panel.add(Box.createVerticalStrut(8));
        panel.add(filtroEstado);
        return panel;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(Theme.fontPlain(12));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private void refreshTable() {
        tableWrapper.removeAll();

        String query    = searchField != null ? searchField.getText().trim().toLowerCase() : "";
        String ubicFil  = filtroUbicacion != null ? (String) filtroUbicacion.getSelectedItem() : "Todas las ubicaciones";
        String estadFil = filtroEstado    != null ? (String) filtroEstado.getSelectedItem()    : "Todos los estados";

        String[] cols = {"Perfume", "Ubicación", "Stock", "Actualizar"};
        java.util.List<Perfume> filtered = new java.util.ArrayList<>();

        for (Perfume p : perfumes) {
            if (!query.isEmpty() && !p.nombre.toLowerCase().contains(query)) continue;
            if (!ubicFil.equals("Todas las ubicaciones") && !p.ubicacion.equals(ubicFil)) continue;
            if (!estadFil.equals("Todos los estados")) {
                if (estadFil.equals("Con stock")  && p.stock < 5)   continue;
                if (estadFil.equals("Stock bajo")  && !(p.stock > 0 && p.stock < 5)) continue;
                if (estadFil.equals("Sin stock")   && p.stock != 0) continue;
            }
            filtered.add(p);
        }

        Object[][] data = new Object[filtered.size()][4];
        for (int i = 0; i < filtered.size(); i++) {
            Perfume p = filtered.get(i);
            data[i][0] = p.nombre;
            data[i][1] = p.ubicacion;
            data[i][2] = p.stock;
            data[i][3] = "✏";
        }

        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        DashboardPanel.styleTable(table);
        table.setRowHeight(44);

        // Ubicación renderer — pill badge
        table.getColumnModel().getColumn(1).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            String loc = val != null ? val.toString() : "";
            JLabel lbl = new JLabel("📦  " + loc);
            lbl.setFont(Theme.fontBold(11));
            lbl.setForeground(new Color(80, 60, 140));
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
            lbl.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            return lbl;
        });

        // Stock renderer — mini progress bar
        table.getColumnModel().getColumn(2).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            int stock = val instanceof Integer ? (Integer) val : 0;
            JPanel wrap = new JPanel(new BorderLayout(8, 0)) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                }
            };
            wrap.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            wrap.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

            // Bar
            JPanel barBg = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Theme.BORDER_LIGHT);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                    int w = Math.min((int)((stock / 20.0) * getWidth()), getWidth());
                    Color c = stock == 0 ? Theme.DANGER : stock < 5 ? Theme.WARNING : Theme.SUCCESS;
                    g2.setColor(c);
                    g2.fillRoundRect(0, 0, w, getHeight(), getHeight(), getHeight());
                    g2.dispose();
                }
            };
            barBg.setOpaque(false);
            barBg.setPreferredSize(new Dimension(0, 8));

            JLabel stkLbl = new JLabel(stock + " uds");
            stkLbl.setFont(Theme.fontBold(12));
            stkLbl.setForeground(stock == 0 ? Theme.DANGER : stock < 5 ? Theme.WARNING : Theme.SUCCESS);
            stkLbl.setPreferredSize(new Dimension(50, 14));

            wrap.add(barBg,  BorderLayout.CENTER);
            wrap.add(stkLbl, BorderLayout.EAST);
            return wrap;
        });

        // Actualizar button
        table.getColumnModel().getColumn(3).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            JButton btn = new JButton("✏") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(240,235,255));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.setColor(Theme.PRIMARY);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2,
                        (getHeight()+fm.getAscent()-fm.getDescent())/2);
                    g2.dispose();
                }
            };
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
            wrap.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            wrap.add(btn);
            return wrap;
        });
        table.getColumnModel().getColumn(3).setMaxWidth(80);

        // Click on update button
        final java.util.List<Perfume> fRef = filtered;
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 3) showUpdateStock(fRef.get(row));
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new javax.swing.border.LineBorder(Theme.BORDER, 1, true));
        sp.getViewport().setBackground(Color.WHITE);
        tableWrapper.add(sp, BorderLayout.CENTER);
        tableWrapper.revalidate();
        tableWrapper.repaint();
    }

    private void showUpdateStock(Perfume p) {
        String input = JOptionPane.showInputDialog(this,
            "Nuevo stock para \"" + p.nombre + "\":",
            String.valueOf(p.stock));
        if (input == null) return;
        try {
            p.stock = Integer.parseInt(input.trim());
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un número válido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
