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
 * Panel Catálogo de Perfumes — muestra el listado de perfumes con búsqueda
 * y permite añadir nuevos o editar los existentes mediante un diálogo modal.
 */
public class PerfumesPanel extends JPanel {

    private JTextField searchField;
    private JPanel tableWrapper;
    private List<Perfume> perfumes;

    public PerfumesPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        perfumes = DataStore.getPerfumes();

        add(buildTop(),    BorderLayout.NORTH);
        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Theme.BG_MAIN);
        refreshTable("");
        add(tableWrapper, BorderLayout.CENTER);
    }

    private JPanel buildTop() {
        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JLabel title = new JLabel("CATÁLOGO DE PERFUMES");
        title.setFont(Theme.fontBold(14));
        title.setForeground(Theme.TEXT_DARK);
        top.add(title, BorderLayout.NORTH);

        JPanel searchRow = new JPanel(new BorderLayout(10, 0));
        searchRow.setOpaque(false);
        searchRow.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        searchField = UIComponents.styledField("🔍  Buscar por nombre o marca...");
        searchField.setPreferredSize(new Dimension(0, 36));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refresh(); }
            void refresh() { refreshTable(searchField.getText().trim().toLowerCase()); }
        });

        JButton btnNuevo = UIComponents.outlineButton("＋  Nuevo perfume");
        btnNuevo.setPreferredSize(new Dimension(150, 36));
        btnNuevo.addActionListener(e -> showEditDialog(null, -1));

        searchRow.add(searchField, BorderLayout.CENTER);
        searchRow.add(btnNuevo,    BorderLayout.EAST);
        top.add(searchRow, BorderLayout.SOUTH);
        return top;
    }

    private void refreshTable(String query) {
        tableWrapper.removeAll();

        String[] cols = {"Nombre", "Marca", "Categoría", "Stock", "Acciones"};
        java.util.List<Object[]> rows = new java.util.ArrayList<>();
        for (int i = 0; i < perfumes.size(); i++) {
            Perfume p = perfumes.get(i);
            if (query.isEmpty()
                || p.nombre.toLowerCase().contains(query)
                || p.marca.toLowerCase().contains(query)) {
                rows.add(new Object[]{p.nombre, p.marca, p.categoria,
                    stockLabel(p.stock), i});
            }
        }
        Object[][] data = rows.toArray(new Object[0][]);

        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override
            public Class<?> getColumnClass(int c) {
                return c == 4 ? Integer.class : String.class;
            }
        };
        DashboardPanel.styleTable(table);
        table.setRowHeight(40);

        // Categoría renderer (tag pill)
        table.getColumnModel().getColumn(2).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            String cat = val != null ? val.toString() : "";
            JLabel tag = UIComponents.categoryTag(cat);
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            wrap.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            wrap.add(tag);
            return wrap;
        });

        // Stock renderer with icon and color
        table.getColumnModel().getColumn(3).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            String txt = val != null ? val.toString() : "";
            JLabel lbl = new JLabel(txt);
            lbl.setFont(Theme.fontBold(12));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            int idx = (Integer) tbl.getValueAt(row, 4);
            int stock = perfumes.get(idx).stock;
            lbl.setForeground(stock == 0 ? Theme.DANGER : stock < 5 ? Theme.WARNING : Theme.SUCCESS);
            lbl.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            lbl.setOpaque(true);
            return lbl;
        });

        // Actions column: edit button
        table.getColumnModel().getColumn(4).setCellRenderer((tbl, val, sel, foc, row, col) -> {
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
            btn.setFont(Theme.fontBold(13));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
            wrap.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            wrap.add(btn);
            return wrap;
        });
        table.getColumnModel().getColumn(4).setMaxWidth(80);

        // Click listener for edit button
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 4) {
                    int idx = (Integer) table.getValueAt(row, 4);
                    showEditDialog(perfumes.get(idx), idx);
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new javax.swing.border.LineBorder(Theme.BORDER, 1, true));
        sp.getViewport().setBackground(Color.WHITE);

        tableWrapper.add(sp, BorderLayout.CENTER);
        tableWrapper.revalidate();
        tableWrapper.repaint();
    }

    private String stockLabel(int stock) {
        if (stock == 0) return "✗  Sin stock";
        if (stock < 5)  return "⚠  " + stock + " uds";
        return "✓  " + stock + " uds";
    }

    private void showEditDialog(Perfume p, int idx) {
        boolean isNew = (p == null);
        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this),
            isNew ? "Nuevo perfume" : "Editar perfume",
            java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(400, 380);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        // Title
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Theme.PRIMARY);
        JLabel titleLbl = new JLabel(isNew ? "➕  Nuevo perfume" : "✏  Editar perfume");
        titleLbl.setFont(Theme.fontBold(14));
        titleLbl.setForeground(Color.WHITE);
        header.add(titleLbl);
        dlg.add(header, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        form.setBackground(Theme.BG_MAIN);

        JTextField fNombre   = UIComponents.styledField(isNew ? "Nombre"   : p.nombre);
        JTextField fMarca    = UIComponents.styledField(isNew ? "Marca"    : p.marca);
        JTextField fCateg    = UIComponents.styledField(isNew ? "Categoría": p.categoria);
        JTextField fStock    = UIComponents.styledField(isNew ? "Stock"    : String.valueOf(p.stock));
        JTextField fUbicacion= UIComponents.styledField(isNew ? "Ubicación": p.ubicacion);

        if (!isNew) {
            fNombre.setText(p.nombre);
            fMarca.setText(p.marca);
            fCateg.setText(p.categoria);
            fStock.setText(String.valueOf(p.stock));
            fUbicacion.setText(p.ubicacion);
        }

        String[][] fields = {{"Nombre", ""}, {"Marca", ""}, {"Categoría", ""},
                             {"Stock",  ""}, {"Ubicación", ""}};
        JTextField[] inputs = {fNombre, fMarca, fCateg, fStock, fUbicacion};

        for (int i = 0; i < inputs.length; i++) {
            JLabel lbl = new JLabel(fields[i][0]);
            lbl.setFont(Theme.fontBold(12));
            lbl.setForeground(Theme.TEXT_DARK);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            inputs[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            form.add(lbl);
            form.add(Box.createVerticalStrut(3));
            form.add(inputs[i]);
            form.add(Box.createVerticalStrut(10));
        }

        dlg.add(new JScrollPane(form), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(Theme.BG_MAIN);
        JButton btnCancelar = UIComponents.outlineButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 34));
        btnCancelar.addActionListener(e -> dlg.dispose());
        JButton btnGuardar = UIComponents.primaryButton(isNew ? "Añadir" : "Guardar");
        btnGuardar.setPreferredSize(new Dimension(100, 34));
        btnGuardar.addActionListener(e -> {
            try {
                int stock = Integer.parseInt(fStock.getText().trim());
                if (isNew) {
                    perfumes.add(new Perfume(
                        fNombre.getText(), fMarca.getText(),
                        fCateg.getText(), stock, fUbicacion.getText()));
                } else {
                    p.nombre    = fNombre.getText();
                    p.marca     = fMarca.getText();
                    p.categoria = fCateg.getText();
                    p.stock     = stock;
                    p.ubicacion = fUbicacion.getText();
                }
                dlg.dispose();
                refreshTable(searchField.getText().trim().toLowerCase());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dlg, "El stock debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(btnCancelar);
        btnPanel.add(btnGuardar);
        dlg.add(btnPanel, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
}
