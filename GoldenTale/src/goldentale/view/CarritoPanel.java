package view;

import model.DataStore;
import model.DataStore.LineaPedido;
import model.DataStore.Perfume;
import model.DataStore;
import util.Theme;
import util.UIComponents;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel Carrito de la Compra (Realizar pedido).
 * Muestra las líneas del pedido, permite ajustar cantidades, eliminar,
 * añadir más perfumes desde un combo y confirmar el pedido.
 */
public class CarritoPanel extends JPanel {

    private final ClienteFrame frame;
    private JPanel  lineasPanel;
    private JLabel  lblTotal, lblUnidades;
    private JLabel  lblResumenLineas;

    public CarritoPanel(ClienteFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(),   BorderLayout.CENTER);
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

        JButton btnVolver = headerBtn("← Volver al catálogo");
        btnVolver.addActionListener(e -> frame.showCatalogo());

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(btnVolver);

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
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 32));
        return btn;
    }

    // ─── Body (dos columnas) ──────────────────────────────────────────────

    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 24, 0));
        body.setBackground(Theme.BG_MAIN);
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        body.add(buildLeft());
        body.add(buildRight());
        return body;
    }

    // ─── Columna izquierda: líneas del pedido ─────────────────────────────

    private JPanel buildLeft() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel("PERFUMES EN EL PEDIDO");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);
        title.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(12));

        lineasPanel = new JPanel();
        lineasPanel.setLayout(new BoxLayout(lineasPanel, BoxLayout.Y_AXIS));
        lineasPanel.setOpaque(false);
        lineasPanel.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(lineasPanel);
        panel.add(Box.createVerticalStrut(12));

        // Añadir perfume
        RoundedPanel addRow = new RoundedPanel(10, Color.WHITE, Theme.BORDER);
        addRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        addRow.setAlignmentX(LEFT_ALIGNMENT);

        String[] nombres = DataStore.getPerfumes().stream()
            .filter(p -> p.stock > 0)
            .map(p -> p.nombre)
            .toArray(String[]::new);
        String[] opciones = new String[nombres.length + 1];
        opciones[0] = "Añadir otro perfume...";
        System.arraycopy(nombres, 0, opciones, 1, nombres.length);
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(Theme.fontPlain(12));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(220, 30));

        JButton btnAdd = UIComponents.outlineButton("＋ Añadir");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnAdd.addActionListener(e -> {
            int idx = combo.getSelectedIndex();
            if (idx > 0) {
                Perfume pf = DataStore.getPerfumes().stream()
                    .filter(p -> p.nombre.equals(combo.getSelectedItem()))
                    .findFirst().orElse(null);
                if (pf != null) { frame.addToCarrito(pf, 1); refresh(); }
            }
        });

        addRow.add(combo); addRow.add(btnAdd);
        panel.add(addRow);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // ─── Columna derecha: resumen ─────────────────────────────────────────

    private JPanel buildRight() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel("RESUMEN DEL PEDIDO");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);
        title.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(12));

        RoundedPanel card = new RoundedPanel(12, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        card.setAlignmentX(LEFT_ALIGNMENT);

        lblResumenLineas = new JLabel("—");
        lblResumenLineas.setFont(Theme.fontPlain(12));
        lblResumenLineas.setForeground(Theme.TEXT_DARK);
        lblResumenLineas.setAlignmentX(LEFT_ALIGNMENT);
        card.add(lblResumenLineas);
        card.add(Box.createVerticalStrut(10));
        card.add(separator());

        lblTotal = summaryRow(card, "Total", "0.00 €", Theme.PRIMARY, true);
        card.add(Box.createVerticalStrut(4));
        lblUnidades = summaryRow(card, "Unidades totales", "0", Theme.TEXT_DARK, false);
        JLabel lblEstado = summaryRow(card, "Estado inicial", "Pendiente", Theme.WARNING, false);

        card.add(Box.createVerticalStrut(16));
        card.add(separator());
        card.add(Box.createVerticalStrut(12));

        JButton btnConfirmar = UIComponents.primaryButton("✓  Confirmar pedido");
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnConfirmar.setAlignmentX(LEFT_ALIGNMENT);
        btnConfirmar.addActionListener(e -> confirmar());
        card.add(btnConfirmar);
        card.add(Box.createVerticalStrut(8));

        JButton btnCancelar = UIComponents.outlineButton("Cancelar");
        btnCancelar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnCancelar.setAlignmentX(LEFT_ALIGNMENT);
        btnCancelar.addActionListener(e -> {
            frame.getCarrito().clear();
            frame.showCatalogo();
        });
        card.add(btnCancelar);

        panel.add(card);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Theme.BORDER_LIGHT);
        return sep;
    }

    private JLabel summaryRow(JPanel parent, String key, String val, Color valColor, boolean bold) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel k = new JLabel(key); k.setFont(bold ? Theme.fontBold(13) : Theme.fontPlain(12));
        k.setForeground(bold ? Theme.TEXT_DARK : Theme.TEXT_MEDIUM);
        JLabel v = new JLabel(val); v.setFont(bold ? Theme.fontBold(13) : Theme.fontPlain(12));
        v.setForeground(valColor);
        row.add(k, BorderLayout.WEST); row.add(v, BorderLayout.EAST);
        parent.add(row);
        return v;
    }

    // ─── Refresh ──────────────────────────────────────────────────────────

    public void refresh() {
        List<LineaPedido> carrito = frame.getCarrito();

        // Rebuild lineas
        lineasPanel.removeAll();
        for (LineaPedido l : carrito) {
            lineasPanel.add(buildLineaCard(l));
            lineasPanel.add(Box.createVerticalStrut(8));
        }
        if (carrito.isEmpty()) {
            JLabel vacio = new JLabel("El pedido está vacío");
            vacio.setFont(Theme.fontPlain(12));
            vacio.setForeground(Theme.TEXT_LIGHT);
            vacio.setAlignmentX(LEFT_ALIGNMENT);
            lineasPanel.add(vacio);
        }
        lineasPanel.revalidate(); lineasPanel.repaint();

        // Update summary
        double total = carrito.stream().mapToDouble(LineaPedido::subtotal).sum();
        int    units = carrito.stream().mapToInt(l -> l.cantidad).sum();
        if (lblTotal    != null) lblTotal.setText(String.format("%.2f €", total));
        if (lblUnidades != null) lblUnidades.setText(String.valueOf(units));
        if (lblResumenLineas != null) {
            if (carrito.isEmpty()) { lblResumenLineas.setText("—"); }
            else {
                StringBuilder sb = new StringBuilder("<html>");
                for (LineaPedido l : carrito) sb.append(l.label()).append("<br>");
                sb.append("</html>");
                lblResumenLineas.setText(sb.toString());
            }
        }
    }

    private RoundedPanel buildLineaCard(LineaPedido l) {
        RoundedPanel card = new RoundedPanel(10, Color.WHITE, Theme.BORDER);
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setAlignmentX(LEFT_ALIGNMENT);

        // Icon
        RoundedPanel ico = new RoundedPanel(8, Theme.BG_SELECTED);
        ico.setPreferredSize(new Dimension(38, 38));
        ico.setLayout(new GridBagLayout());
        JLabel icoLbl = new JLabel("🧴"); icoLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        ico.add(icoLbl);

        // Info
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel nameL = new JLabel(l.perfume.nombre); nameL.setFont(Theme.fontBold(13)); nameL.setForeground(Theme.TEXT_DARK);
        JLabel subL  = new JLabel(l.perfume.marca + " · " + String.format("%.2f € / ud", l.perfume.precio));
        subL.setFont(Theme.fontPlain(11)); subL.setForeground(Theme.TEXT_LIGHT);
        info.add(nameL); info.add(subL);

        // Controls
        JPanel ctrl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        ctrl.setOpaque(false);

        JButton btnMinus = roundBtn("−");
        JLabel  qtyLbl  = new JLabel(String.valueOf(l.cantidad));
        qtyLbl.setFont(Theme.fontBold(13)); qtyLbl.setForeground(Theme.TEXT_DARK);
        JButton btnPlus  = roundBtn("＋");
        JLabel  priceLbl = new JLabel(String.format("%.2f €", l.subtotal()));
        priceLbl.setFont(Theme.fontBold(13)); priceLbl.setForeground(Theme.PRIMARY);
        JButton btnDel   = new JButton("🗑") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(255,230,230) : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(Theme.BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                FontMetrics fm = g2.getFontMetrics(new Font("Segoe UI Emoji", Font.PLAIN, 14));
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btnDel.setPreferredSize(new Dimension(32, 28));
        btnDel.setFocusPainted(false); btnDel.setBorderPainted(false); btnDel.setContentAreaFilled(false);
        btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnMinus.addActionListener(e -> {
            if (l.cantidad > 1) { l.cantidad--; refresh(); }
        });
        btnPlus.addActionListener(e -> { l.cantidad++; refresh(); });
        btnDel.addActionListener(e -> { frame.removeFromCarrito(l); refresh(); });

        ctrl.add(btnMinus); ctrl.add(qtyLbl); ctrl.add(btnPlus);
        ctrl.add(Box.createHorizontalStrut(6));
        ctrl.add(priceLbl);
        ctrl.add(Box.createHorizontalStrut(6));
        ctrl.add(btnDel);

        card.add(ico,  BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);
        card.add(ctrl, BorderLayout.EAST);
        return card;
    }

    private JButton roundBtn(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? Theme.BG_SELECTED : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(Theme.BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.setColor(Theme.TEXT_DARK);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontBold(14));
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(28, 28));
        return btn;
    }

    // ─── Confirmar ────────────────────────────────────────────────────────

    private void confirmar() {
        if (frame.getCarrito().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El pedido está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Crear pedido provisional (sin método de pago aún) y pasar a PagoPanel
        model.DataStore.PedidoCliente provisional = new model.DataStore.PedidoCliente(
            "#???", "", "Pendiente", "");
        provisional.lineas.addAll(frame.getCarrito());
        frame.showPago(provisional);
    }
}
