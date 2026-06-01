package view;

import model.DataStore;
import model.DataStore.*;
import util.Theme;
import util.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel Pedidos — lista completa de pedidos con filtro por estado
 * y posibilidad de cambiar el estado de cada pedido.
 */
public class PedidosPanel extends JPanel {

    private static final String[] ESTADOS = {"Todos", "Pendiente", "Procesando", "Enviado", "Entregado"};
    private JComboBox<String> filtroEstado;
    private JPanel tableWrapper;

    public PedidosPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        add(buildTop(),    BorderLayout.NORTH);
        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Theme.BG_MAIN);
        refreshTable("Todos");
        add(tableWrapper, BorderLayout.CENTER);
    }

    private JPanel buildTop() {
        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JLabel title = new JLabel("GESTIÓN DE PEDIDOS");
        title.setFont(Theme.fontBold(14));
        title.setForeground(Theme.TEXT_DARK);
        top.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        JLabel lbl = new JLabel("Filtrar por estado:");
        lbl.setFont(Theme.fontPlain(12));
        lbl.setForeground(Theme.TEXT_MEDIUM);

        filtroEstado = new JComboBox<>(ESTADOS);
        filtroEstado.setFont(Theme.fontPlain(12));
        filtroEstado.setBackground(Color.WHITE);
        filtroEstado.setPreferredSize(new Dimension(150, 32));
        filtroEstado.addActionListener(e ->
            refreshTable((String) filtroEstado.getSelectedItem()));

        right.add(lbl);
        right.add(filtroEstado);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private void refreshTable(String filtro) {
        tableWrapper.removeAll();

        List<Pedido> pedidos = DataStore.getPedidos();

        String[] cols = {"Ref.", "Cliente", "Perfume", "Total", "Estado", "Acción"};

        java.util.List<Object[]> rows = new java.util.ArrayList<>();
        for (Pedido p : pedidos) {
            if (filtro.equals("Todos") || p.estado.equals(filtro)) {
                rows.add(new Object[]{
                    p.ref, p.cliente, p.perfume,
                    String.format("%.2f €", p.total), p.estado, "▶ Ver"
                });
            }
        }

        Object[][] data = rows.toArray(new Object[0][]);

        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return c == 4; }
        };
        DashboardPanel.styleTable(table);
        table.setRowHeight(38);

        // Estado as combobox for editing
        JComboBox<String> estadoCombo = new JComboBox<>(
            new String[]{"Pendiente","Procesando","Enviado","Entregado"});
        estadoCombo.setFont(Theme.fontPlain(11));
        table.getColumnModel().getColumn(4).setCellEditor(
            new DefaultCellEditor(estadoCombo));

        // Acción button column
        table.getColumnModel().getColumn(5).setCellRenderer((tbl, val, sel, foc, row, col) -> {
            JButton btn = UIComponents.outlineButton("▶ Ver detalle");
            btn.setFont(Theme.fontPlain(11));
            return btn;
        });
        table.getColumnModel().getColumn(5).setMaxWidth(110);
        table.getColumnModel().getColumn(4).setMaxWidth(130);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new javax.swing.border.LineBorder(Theme.BORDER, 1, true));
        sp.getViewport().setBackground(Color.WHITE);

        tableWrapper.add(sp, BorderLayout.CENTER);
        tableWrapper.revalidate();
        tableWrapper.repaint();
    }
}
