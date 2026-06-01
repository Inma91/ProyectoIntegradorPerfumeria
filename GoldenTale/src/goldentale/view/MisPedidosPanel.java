package view;

import model.DataStore.LineaPedido;
import model.DataStore.PedidoCliente;
import util.Theme;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel Mis Pedidos — historial del cliente con filtros por estado
 * y panel de detalle a la derecha al seleccionar un pedido.
 */
public class MisPedidosPanel extends JPanel {

    private final ClienteFrame frame;
    private JPanel   listPanel;
    private JPanel   detailPanel;
    private String   filtroActivo = "Todos";

    public MisPedidosPanel(ClienteFrame frame) {
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

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JButton btnCat  = headerBtn("← Catálogo"); btnCat.addActionListener(e -> frame.showCatalogo());
        JButton btnSalir= headerBtn(" Salir");    btnSalir.addActionListener(e -> frame.logout());
        right.add(btnCat); right.add(btnSalir);

        h.add(left, BorderLayout.WEST); h.add(right, BorderLayout.EAST);
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
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    // ─── Body: dos columnas ───────────────────────────────────────────────

    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 16, 0));
        body.setBackground(Theme.BG_MAIN);
        body.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        body.add(buildLeft());
        body.add(buildDetailArea());
        return body;
    }

    // ─── Izquierda: filtros + lista ───────────────────────────────────────

    private JPanel buildLeft() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel title = new JLabel("MIS PEDIDOS");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);
        title.setAlignmentX(LEFT_ALIGNMENT);
        p.add(title);
        p.add(Box.createVerticalStrut(10));

        // Filter chips
        JPanel chips = new JPanel(new GridLayout(2, 3, 6, 6));
        chips.setOpaque(false);
        chips.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));
        chips.setAlignmentX(LEFT_ALIGNMENT);
        String[] filtros = {"Todos","Pendiente","Procesando","Enviado","Entregado","Cancelado"};
        for (String f : filtros) chips.add(filterChip(f));
        p.add(chips);
        p.add(Box.createVerticalStrut(12));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        listPanel.setAlignmentX(LEFT_ALIGNMENT);

        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBorder(null);
        sp.setBackground(Theme.BG_MAIN);
        sp.getViewport().setBackground(Theme.BG_MAIN);
        sp.getVerticalScrollBar().setUnitIncrement(12);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        p.add(sp);
        return p;
    }

    private JButton filterChip(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean active = filtroActivo.equals(text);
                g2.setColor(active ? Theme.BG_SELECTED : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(active ? Theme.PRIMARY : Theme.BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.setColor(active ? Theme.PRIMARY : Theme.TEXT_DARK);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontBold(12));
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> { filtroActivo = text; refresh(); btn.getParent().repaint(); });
        return btn;
    }

    // ─── Derecha: detalle vacío por defecto ───────────────────────────────

    private JPanel buildDetailArea() {
        detailPanel = new JPanel(new BorderLayout());
        detailPanel.setOpaque(false);
        showEmptyDetail();
        return detailPanel;
    }

    private void showEmptyDetail() {
        detailPanel.removeAll();
        JLabel title = new JLabel("DETALLE");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);

        RoundedPanel card = new RoundedPanel(12, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel ico = new JLabel("📄"); ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        ico.setAlignmentX(CENTER_ALIGNMENT);
        JLabel msg = new JLabel("<html><center>Selecciona un pedido<br>para ver el detalle</center></html>");
        msg.setFont(Theme.fontPlain(12));
        msg.setForeground(Theme.TEXT_LIGHT);
        msg.setAlignmentX(CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(ico); card.add(Box.createVerticalStrut(10)); card.add(msg);
        card.add(Box.createVerticalGlue());

        JPanel wrap = new JPanel(new BorderLayout(0, 10));
        wrap.setOpaque(false);
        wrap.add(title, BorderLayout.NORTH);
        wrap.add(card,  BorderLayout.CENTER);
        detailPanel.add(wrap, BorderLayout.CENTER);
        detailPanel.revalidate(); detailPanel.repaint();
    }

    private void showDetail(PedidoCliente pedido) {
        detailPanel.removeAll();

        JLabel title = new JLabel("DETALLE");
        title.setFont(Theme.fontBold(12));
        title.setForeground(Theme.TEXT_MEDIUM);

        RoundedPanel card = new RoundedPanel(12, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // Header
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setOpaque(false);
        hdr.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        hdr.setAlignmentX(LEFT_ALIGNMENT);
        JLabel refLbl = new JLabel(pedido.ref); refLbl.setFont(Theme.fontBold(16)); refLbl.setForeground(Theme.TEXT_DARK);
        JLabel estLbl = estadoBadge(pedido.estado);
        hdr.add(refLbl, BorderLayout.WEST); hdr.add(estLbl, BorderLayout.EAST);
        card.add(hdr);
        card.add(Box.createVerticalStrut(4));

        JLabel fechaLbl = new JLabel(pedido.fecha);
        fechaLbl.setFont(Theme.fontPlain(11)); fechaLbl.setForeground(Theme.TEXT_LIGHT);
        fechaLbl.setAlignmentX(LEFT_ALIGNMENT);
        card.add(fechaLbl);
        card.add(Box.createVerticalStrut(14));

        // Líneas
        for (LineaPedido l : pedido.lineas) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
            row.setAlignmentX(LEFT_ALIGNMENT);
            JLabel k = new JLabel(l.label()); k.setFont(Theme.fontPlain(12)); k.setForeground(Theme.TEXT_DARK);
            JLabel v = new JLabel(String.format("%.2f €", l.subtotal())); v.setFont(Theme.fontPlain(12)); v.setForeground(Theme.TEXT_DARK);
            row.add(k, BorderLayout.WEST); row.add(v, BorderLayout.EAST);
            card.add(row);
            card.add(Box.createVerticalStrut(4));
        }

        card.add(Box.createVerticalStrut(8));
        JSeparator sep = new JSeparator(); sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); sep.setForeground(Theme.BORDER_LIGHT);
        sep.setAlignmentX(LEFT_ALIGNMENT);
        card.add(sep);
        card.add(Box.createVerticalStrut(8));

        // Total
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false); totalRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26)); totalRow.setAlignmentX(LEFT_ALIGNMENT);
        JLabel tk = new JLabel("Total"); tk.setFont(Theme.fontBold(14)); tk.setForeground(Theme.TEXT_DARK);
        JLabel tv = new JLabel(String.format("%.2f €", pedido.total())); tv.setFont(Theme.fontBold(14)); tv.setForeground(Theme.PRIMARY);
        totalRow.add(tk, BorderLayout.WEST); totalRow.add(tv, BorderLayout.EAST);
        card.add(totalRow);
        card.add(Box.createVerticalStrut(6));

        // Unidades & método
        detailRow(card, "Unidades",      String.valueOf(pedido.unidades()));
        detailRow(card, "Método de pago", pedido.metodoPago.isEmpty() ? "—" : pedido.metodoPago);
        card.add(Box.createVerticalGlue());

        JPanel wrap = new JPanel(new BorderLayout(0, 10));
        wrap.setOpaque(false);
        wrap.add(title, BorderLayout.NORTH);
        wrap.add(card,  BorderLayout.CENTER);
        detailPanel.add(wrap, BorderLayout.CENTER);
        detailPanel.revalidate(); detailPanel.repaint();
    }

    private void detailRow(JPanel parent, String key, String val) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false); row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22)); row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel k = new JLabel(key);   k.setFont(Theme.fontPlain(12)); k.setForeground(Theme.TEXT_MEDIUM);
        JLabel v = new JLabel(val);   v.setFont(Theme.fontPlain(12)); v.setForeground(Theme.TEXT_DARK);
        row.add(k, BorderLayout.WEST); row.add(v, BorderLayout.EAST);
        parent.add(row); parent.add(Box.createVerticalStrut(4));
    }

    // ─── Refresh ──────────────────────────────────────────────────────────

    public void refresh() {
        listPanel.removeAll();
        List<PedidoCliente> pedidos = frame.getCliente().pedidos;
        boolean any = false;
        for (PedidoCliente p : pedidos) {
            if (!filtroActivo.equals("Todos") && !p.estado.equals(filtroActivo)) continue;
            listPanel.add(buildPedidoCard(p));
            listPanel.add(Box.createVerticalStrut(8));
            any = true;
        }
        if (!any) {
            JLabel empty = new JLabel("No hay pedidos en este estado.");
            empty.setFont(Theme.fontPlain(12)); empty.setForeground(Theme.TEXT_LIGHT);
            empty.setAlignmentX(LEFT_ALIGNMENT);
            listPanel.add(empty);
        }
        listPanel.revalidate(); listPanel.repaint();
        showEmptyDetail();
    }

    private JPanel buildPedidoCard(PedidoCliente p) {
        RoundedPanel card = new RoundedPanel(10, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setAlignmentX(LEFT_ALIGNMENT);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel row1 = new JPanel(new BorderLayout()); row1.setOpaque(false); row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24)); row1.setAlignmentX(LEFT_ALIGNMENT);
        JLabel refL = new JLabel(p.ref); refL.setFont(Theme.fontBold(13)); refL.setForeground(Theme.TEXT_DARK);
        row1.add(refL, BorderLayout.WEST); row1.add(estadoBadge(p.estado), BorderLayout.EAST);
        card.add(row1); card.add(Box.createVerticalStrut(4));

        JPanel row2 = new JPanel(new BorderLayout()); row2.setOpaque(false); row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20)); row2.setAlignmentX(LEFT_ALIGNMENT);
        JLabel itemsL = new JLabel(p.resumenLineas()); itemsL.setFont(Theme.fontPlain(11)); itemsL.setForeground(Theme.TEXT_MEDIUM);
        JLabel totalL = new JLabel(String.format("%.2f €", p.total())); totalL.setFont(Theme.fontBold(12)); totalL.setForeground(Theme.PRIMARY);
        row2.add(itemsL, BorderLayout.WEST); row2.add(totalL, BorderLayout.EAST);
        card.add(row2); card.add(Box.createVerticalStrut(2));

        JLabel fechaL = new JLabel(p.fecha); fechaL.setFont(Theme.fontPlain(10)); fechaL.setForeground(Theme.TEXT_LIGHT); fechaL.setAlignmentX(LEFT_ALIGNMENT);
        card.add(fechaL);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { showDetail(p); }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                ((RoundedPanel)card).setBorder(BorderFactory.createCompoundBorder(
                    new javax.swing.border.LineBorder(Theme.PRIMARY, 1, true),
                    BorderFactory.createEmptyBorder(11,13,11,13)));
                card.repaint();
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(12,14,12,14)); card.repaint();
            }
        });
        return card;
    }

    private JLabel estadoBadge(String estado) {
        Color bg, fg;
        switch (estado) {
            case "Pendiente":  bg = new Color(255,240,210); fg = new Color(160,100,20); break;
            case "Procesando": bg = new Color(210,235,255); fg = new Color(40,80,160);  break;
            case "Enviado":    bg = new Color(210,245,235); fg = Theme.SUCCESS;          break;
            case "Entregado":  bg = new Color(220,255,220); fg = new Color(20,120,20);  break;
            case "Cancelado":  bg = new Color(255,220,220); fg = Theme.DANGER;           break;
            default:           bg = Theme.BORDER_LIGHT;     fg = Theme.TEXT_DARK;        break;
        }
        String icon = "";
        switch (estado) {
            case "Pendiente":  icon = "⏱ "; break;
            case "Procesando": icon = "⚙ "; break;
            case "Enviado":    icon = "🚚 "; break;
            case "Entregado":  icon = "✓ ";  break;
            case "Cancelado":  icon = "✗ ";  break;
        }
        final Color fbg = bg; final Color ffg = fg; final String fullText = icon + estado;
        JLabel lbl = new JLabel(fullText) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fbg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setFont(Theme.fontBold(11)); lbl.setForeground(ffg);
        lbl.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8)); lbl.setOpaque(false);
        return lbl;
    }
}
