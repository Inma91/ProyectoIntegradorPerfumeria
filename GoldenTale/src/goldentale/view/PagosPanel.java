package view;

import model.DataStore;
import model.DataStore.*;
import util.Theme;
import util.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel Pagos — lista de pagos con resumen y opción de registrar/confirmar pagos.
 */
public class PagosPanel extends JPanel {

    private List<Pago> pagos;
    private JPanel tableWrapper;
	private JPanel topArea;
	private JPanel panel;
	private JLabel title;
	private JPanel cards;
	private JPanel bar;

    public PagosPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        pagos = DataStore.getPagos();

        topArea = new JPanel();
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.Y_AXIS));
        topArea.setOpaque(false);
        topArea.add(buildSummary());
        topArea.add(Box.createVerticalStrut(20));
        topArea.add(buildTopBar());
        topArea.add(Box.createVerticalStrut(12));
        add(topArea, BorderLayout.NORTH);

        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Theme.BG_MAIN);
        refreshTable();
        add(tableWrapper, BorderLayout.CENTER);
    }

    private JPanel buildSummary() {
        panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        title = new JLabel("RESUMEN DE PAGOS");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);

        long confirmados = pagos.stream().filter(p -> p.estado.equals("Confirmado")).count();
        long pendientes  = pagos.stream().filter(p -> p.estado.equals("Pendiente")).count();
        double total     = pagos.stream().mapToDouble(p -> p.importe).sum();

        cards = new JPanel(new GridLayout(1, 3, 10, 0));
        cards.setOpaque(false);
        cards.add(UIComponents.statCard("Total recaudado",
            String.format("%.0f €", total), "Suma de todos los pagos", Theme.PRIMARY));
        cards.add(UIComponents.statCard("Confirmados",
            String.valueOf(confirmados), "Pagos verificados", Theme.SUCCESS));
        cards.add(UIComponents.statCard("Pendientes",
            String.valueOf(pendientes),  "Por confirmar", Theme.WARNING));

        panel.add(title, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildTopBar() {
        bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);

        JLabel title = new JLabel("HISTORIAL DE PAGOS");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);

        JButton btnRegistrar = UIComponents.primaryButton("＋  Registrar pago");
        btnRegistrar.setPreferredSize(new Dimension(160, 34));
        btnRegistrar.addActionListener(e -> showRegistrarDialog());

        bar.add(title,        BorderLayout.WEST);
        bar.add(btnRegistrar, BorderLayout.EAST);
        return bar;
    }

    private void refreshTable() {
        tableWrapper.removeAll();

        String[] cols = {"Ref.", "Cliente", "Fecha", "Importe", "Estado", "Acción"};
        Object[][] data = new Object[pagos.size()][6];
        for (int i = 0; i < pagos.size(); i++) {
            Pago p = pagos.get(i);
            data[i][0] = p.ref;
            data[i][1] = p.cliente;
            data[i][2] = p.fecha;
            data[i][3] = String.format("%.2f €", p.importe);
            data[i][4] = p.estado;
            data[i][5] = i;
        }

        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        DashboardPanel.styleTable(table);
        table.setRowHeight(38);

        // Estado renderer
        table.getColumnModel().getColumn(4).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            String estado = val != null ? val.toString() : "";
            JLabel lbl = new JLabel(estado) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg; Color fg;
                    if (estado.equals("Confirmado")) { bg = new Color(210,245,235); fg = Theme.SUCCESS; }
                    else { bg = new Color(255,240,210); fg = new Color(160,100,20); }
                    g2.setColor(sel ? Theme.BG_SELECTED : tbl.getBackground());
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    int pw = g2.getFontMetrics(getFont()).stringWidth(estado) + 22;
                    int ph = 22;
                    int px = (getWidth()-pw)/2; int py = (getHeight()-ph)/2;
                    g2.setColor(bg);
                    g2.fillRoundRect(px, py, pw, ph, ph, ph);
                    g2.setColor(fg);
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(estado, px+(pw-fm.stringWidth(estado))/2,
                        py+(ph+fm.getAscent()-fm.getDescent())/2);
                    g2.dispose();
                }
            };
            lbl.setFont(Theme.fontBold(11));
            lbl.setBackground(sel ? Theme.BG_SELECTED : Color.WHITE);
            lbl.setOpaque(true);
            return lbl;
        });

        // Acción: confirmar pago pendiente
        table.getColumnModel().getColumn(5).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            int idx = (Integer) val;
            boolean pendiente = pagos.get(idx).estado.equals("Pendiente");
            JButton btn = UIComponents.primaryButton(pendiente ? "✔ Confirmar" : "—");
            btn.setEnabled(pendiente);
            btn.setFont(Theme.fontBold(11));
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
            wrap.setBackground(sel ? Theme.BG_SELECTED :
                (row%2==0 ? Color.WHITE : new Color(251,249,246)));
            wrap.add(btn);
            return wrap;
        });
        table.getColumnModel().getColumn(5).setMaxWidth(120);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 5) {
                    int idx = (Integer) table.getValueAt(row, 5);
                    Pago p = pagos.get(idx);
                    if (p.estado.equals("Pendiente")) {
                        p.estado = "Confirmado";
                        refreshTable();
                    }
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

    private void showRegistrarDialog() {
        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this),
            "Registrar pago", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(360, 300);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Theme.PRIMARY);
        JLabel hLbl = new JLabel("💳  Registrar nuevo pago");
        hLbl.setFont(Theme.fontBold(13));
        hLbl.setForeground(Color.WHITE);
        header.add(hLbl);
        dlg.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        form.setBackground(Theme.BG_MAIN);

        JTextField fRef     = UIComponents.styledField("Ref. pedido (ej: #1006)");
        JTextField fCliente = UIComponents.styledField("Nombre del cliente");
        JTextField fImporte = UIComponents.styledField("Importe (ej: 89.90)");
        JTextField fFecha   = UIComponents.styledField("Fecha (dd/mm/yyyy)");

        String[][] labels = {{"Referencia",""}, {"Cliente",""}, {"Importe",""}, {"Fecha",""}};
        JTextField[] inputs = {fRef, fCliente, fImporte, fFecha};
        for (int i = 0; i < inputs.length; i++) {
            JLabel lbl = new JLabel(labels[i][0]);
            lbl.setFont(Theme.fontBold(11));
            lbl.setForeground(Theme.TEXT_DARK);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            inputs[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            form.add(lbl);
            form.add(Box.createVerticalStrut(3));
            form.add(inputs[i]);
            form.add(Box.createVerticalStrut(8));
        }
        dlg.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        btnPanel.setBackground(Theme.BG_MAIN);
        JButton btnCancel = UIComponents.outlineButton("Cancelar");
        btnCancel.setPreferredSize(new Dimension(90, 32));
        btnCancel.addActionListener(e -> dlg.dispose());
        JButton btnGuardar = UIComponents.primaryButton("Registrar");
        btnGuardar.setPreferredSize(new Dimension(90, 32));
        btnGuardar.addActionListener(e -> {
            try {
                double imp = Double.parseDouble(fImporte.getText().replace(",",".").trim());
                pagos.add(new Pago(
                    fRef.getText(), fCliente.getText(),
                    fFecha.getText().isEmpty() ? "27/05/2026" : fFecha.getText(),
                    imp, "Pendiente"));
                dlg.dispose();
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dlg, "El importe debe ser un número válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(btnCancel);
        btnPanel.add(btnGuardar);
        dlg.add(btnPanel, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
}
