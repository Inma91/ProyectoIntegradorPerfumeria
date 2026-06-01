package view;

import model.DataStore.LineaPedido;
import model.DataStore.PedidoCliente;
import util.Theme;
import util.UIComponents;
import util.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de Pago — permite elegir método de pago (Efectivo / Tarjeta / Bizum)
 * y muestra el resumen del pedido antes de confirmar.
 */
public class PagoPanel extends JPanel {

    private final ClienteFrame frame;
    private PedidoCliente pedidoPendiente;

    // Selección de método
    private String metodoPago = "Efectivo";
    private JPanel metodosWrapper;

    // Resumen
    private JPanel resumenWrapper;

    public PagoPanel(ClienteFrame frame) {
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

        JLabel userLbl = new JLabel(frame.getCliente().nombre);
        userLbl.setFont(Theme.fontBold(13)); userLbl.setForeground(new Color(220,210,255));

        h.add(left,   BorderLayout.WEST);
        h.add(userLbl, BorderLayout.EAST);
        return h;
    }

    // ─── Body ─────────────────────────────────────────────────────────────

    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 24, 0));
        body.setBackground(Theme.BG_MAIN);
        body.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        metodosWrapper = new JPanel();
        metodosWrapper.setLayout(new BoxLayout(metodosWrapper, BoxLayout.Y_AXIS));
        metodosWrapper.setOpaque(false);

        resumenWrapper = new JPanel();
        resumenWrapper.setLayout(new BoxLayout(resumenWrapper, BoxLayout.Y_AXIS));
        resumenWrapper.setOpaque(false);

        body.add(metodosWrapper);
        body.add(resumenWrapper);
        return body;
    }

    // ─── Carga del pedido ─────────────────────────────────────────────────

    public void setPedido(PedidoCliente p) {
        this.pedidoPendiente = p;
        metodoPago = "Efectivo";
        rebuildMetodos();
        rebuildResumen();
    }

    // ─── Métodos de pago ──────────────────────────────────────────────────

    private void rebuildMetodos() {
        metodosWrapper.removeAll();

        JLabel title = new JLabel("MÉTODO DE PAGO");
        title.setFont(Theme.fontBold(12)); title.setForeground(Theme.TEXT_MEDIUM);
        title.setAlignmentX(LEFT_ALIGNMENT);
        metodosWrapper.add(title);
        metodosWrapper.add(Box.createVerticalStrut(12));

        metodosWrapper.add(metodoCard("💳", "Efectivo",  "Pago en caja al recibir el pedido",    new Color(210,245,235)));
        metodosWrapper.add(Box.createVerticalStrut(10));
        metodosWrapper.add(metodoCard("💳", "Tarjeta",   "Débito o crédito en el datáfono",      new Color(210,230,255)));
        metodosWrapper.add(Box.createVerticalStrut(10));
        metodosWrapper.add(metodoCard("📱", "Bizum",     "Transfiere al número de la tienda",    new Color(255,220,230)));
        metodosWrapper.add(Box.createVerticalGlue());

        metodosWrapper.revalidate(); metodosWrapper.repaint();
    }

    private JPanel metodoCard(String iconEmoji, String nombre, String desc, Color iconBg) {
        boolean selected = metodoPago.equals(nombre);

        RoundedPanel card = new RoundedPanel(12, Color.WHITE,
            selected ? Theme.PRIMARY : Theme.BORDER);
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        card.setAlignmentX(LEFT_ALIGNMENT);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (selected) card.setBackground(new Color(246, 242, 255));

        // Icon circle
        RoundedPanel ico = new RoundedPanel(10, iconBg);
        ico.setPreferredSize(new Dimension(42, 42));
        ico.setLayout(new GridBagLayout());
        JLabel icoLbl = new JLabel(iconEmoji);
        icoLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        ico.add(icoLbl);

        // Text
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel nameL = new JLabel(nombre); nameL.setFont(Theme.fontBold(13)); nameL.setForeground(Theme.TEXT_DARK);
        JLabel descL = new JLabel(desc);   descL.setFont(Theme.fontPlain(11)); descL.setForeground(Theme.TEXT_LIGHT);
        info.add(nameL); info.add(descL);

        // Radio indicator
        JPanel radio = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int s = Math.min(getWidth(), getHeight()) - 2;
                int x = (getWidth()-s)/2; int y = (getHeight()-s)/2;
                g2.setColor(selected ? Theme.PRIMARY : Theme.BORDER);
                g2.drawOval(x, y, s, s);
                if (selected) { g2.setColor(Theme.PRIMARY); g2.fillOval(x+4, y+4, s-8, s-8); }
                g2.dispose();
            }
        };
        radio.setOpaque(false);
        radio.setPreferredSize(new Dimension(20, 20));

        card.add(ico,   BorderLayout.WEST);
        card.add(info,  BorderLayout.CENTER);
        card.add(radio, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                metodoPago = nombre;
                rebuildMetodos();
            }
        });
        return card;
    }

    // ─── Resumen del pedido ───────────────────────────────────────────────

    private void rebuildResumen() {
        resumenWrapper.removeAll();

        JLabel title = new JLabel("RESUMEN");
        title.setFont(Theme.fontBold(12)); title.setForeground(Theme.TEXT_MEDIUM);
        title.setAlignmentX(LEFT_ALIGNMENT);
        resumenWrapper.add(title);
        resumenWrapper.add(Box.createVerticalStrut(12));

        RoundedPanel card = new RoundedPanel(12, Color.WHITE, Theme.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        card.setAlignmentX(LEFT_ALIGNMENT);

        if (pedidoPendiente != null) {
            for (LineaPedido l : pedidoPendiente.lineas) {
                JPanel row = rowPanel(l.label(), String.format("%.2f €", l.subtotal()), Theme.TEXT_DARK, false);
                card.add(row); card.add(Box.createVerticalStrut(6));
            }
        }

        card.add(Box.createVerticalStrut(8));
        JSeparator sep = new JSeparator(); sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Theme.BORDER_LIGHT); sep.setAlignmentX(LEFT_ALIGNMENT);
        card.add(sep);
        card.add(Box.createVerticalStrut(8));

        // Unidades / Estado
        int units = pedidoPendiente != null ? pedidoPendiente.unidades() : 0;
        card.add(rowPanel("Unidades", String.valueOf(units), Theme.TEXT_DARK, false));
        card.add(Box.createVerticalStrut(4));

        // Estado en color
        JPanel estadoRow = new JPanel(new BorderLayout());
        estadoRow.setOpaque(false); estadoRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24)); estadoRow.setAlignmentX(LEFT_ALIGNMENT);
        JLabel eK = new JLabel("Estado"); eK.setFont(Theme.fontPlain(12)); eK.setForeground(Theme.TEXT_MEDIUM);
        JLabel eV = new JLabel("Pendiente"); eV.setFont(Theme.fontBold(12)); eV.setForeground(Theme.WARNING);
        estadoRow.add(eK, BorderLayout.WEST); estadoRow.add(eV, BorderLayout.EAST);
        card.add(estadoRow);
        card.add(Box.createVerticalStrut(10));

        sep = new JSeparator(); sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Theme.BORDER_LIGHT); sep.setAlignmentX(LEFT_ALIGNMENT);
        card.add(sep);
        card.add(Box.createVerticalStrut(10));

        // Total
        double total = pedidoPendiente != null ? pedidoPendiente.total() : 0;
        card.add(rowPanel("Total", String.format("%.2f €", total), Theme.PRIMARY, true));
        card.add(Box.createVerticalStrut(4));

        // Ref
        String ref = pedidoPendiente != null ? "Pedido " + pedidoPendiente.ref : "";
        JLabel refLbl = new JLabel(ref); refLbl.setFont(Theme.fontPlain(10));
        refLbl.setForeground(Theme.TEXT_LIGHT); refLbl.setAlignmentX(CENTER_ALIGNMENT);
        card.add(refLbl);
        card.add(Box.createVerticalStrut(14));

        // Confirmar
        JButton btnConfirmar = UIComponents.primaryButton("🔒  Confirmar pago");
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnConfirmar.setAlignmentX(LEFT_ALIGNMENT);
        btnConfirmar.addActionListener(e -> confirmarPago());
        card.add(btnConfirmar);
        card.add(Box.createVerticalStrut(8));

        // Volver al pedido
        JButton btnVolver = UIComponents.outlineButton("Volver al pedido");
        btnVolver.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnVolver.setAlignmentX(LEFT_ALIGNMENT);
        btnVolver.addActionListener(e -> frame.showCarrito());
        card.add(btnVolver);

        resumenWrapper.add(card);
        resumenWrapper.add(Box.createVerticalGlue());
        resumenWrapper.revalidate(); resumenWrapper.repaint();
    }

    private JPanel rowPanel(String key, String val, Color valColor, boolean bold) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false); row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26)); row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel k = new JLabel(key); k.setFont(bold ? Theme.fontBold(14) : Theme.fontPlain(12)); k.setForeground(bold ? Theme.TEXT_DARK : Theme.TEXT_MEDIUM);
        JLabel v = new JLabel(val); v.setFont(bold ? Theme.fontBold(14) : Theme.fontPlain(12)); v.setForeground(valColor);
        row.add(k, BorderLayout.WEST); row.add(v, BorderLayout.EAST);
        return row;
    }

    // ─── Confirmación ─────────────────────────────────────────────────────

    private void confirmarPago() {
        // Consumir el carrito y crear pedido real
        PedidoCliente nuevo = frame.confirmarPedido(metodoPago);
        if (nuevo == null) {
            JOptionPane.showMessageDialog(this, "No hay artículos en el pedido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,
            "✅  Pedido " + nuevo.ref + " confirmado con pago por " + metodoPago + ".\nPuedes ver el estado en 'Mis pedidos'.",
            "Pago realizado", JOptionPane.INFORMATION_MESSAGE);
        frame.showMisPedidos();
    }
}
