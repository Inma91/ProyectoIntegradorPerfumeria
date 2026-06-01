package util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class UIComponents {

    /** Rounded panel with optional background */
    public static class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;
        private Color borderColor;

        public RoundedPanel(int radius, Color bg) {
            this(radius, bg, null);
        }
        public RoundedPanel(int radius, Color bg, Color borderColor) {
            this.radius = radius;
            this.bg = bg;
            this.borderColor = borderColor;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth()-1, getHeight()-1, radius, radius));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Styled button matching the design */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(Theme.PRIMARY_LIGHT);
                else if (getModel().isRollover()) g2.setColor(Theme.PRIMARY_LIGHT);
                else g2.setColor(Theme.PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontBold(13));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton outlineButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) g2.setColor(new Color(240,235,255));
                else g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Theme.BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.setColor(Theme.TEXT_DARK);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(Theme.fontBold(13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Styled text field */
    public static JTextField styledField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g3 = (Graphics2D) g.create();
                    g3.setColor(Theme.TEXT_LIGHT);
                    g3.setFont(getFont());
                    Insets ins = getInsets();
                    FontMetrics fm = g3.getFontMetrics();
                    g3.drawString(placeholder, ins.left + 2, ins.top + fm.getAscent());
                    g3.dispose();
                }
            }
        };
        field.setFont(Theme.fontPlain(13));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Theme.BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setOpaque(false);
        return field;
    }

    public static JPasswordField styledPassword(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(Theme.fontPlain(13));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Theme.BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setOpaque(false);
        return field;
    }

    /** Stat card for dashboard */
    public static JPanel statCard(String label, String value, String sub, Color valueColor) {
        RoundedPanel card = new RoundedPanel(12, Color.WHITE, Theme.BORDER_LIGHT);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JLabel lbl = new JLabel(label);
        lbl.setFont(Theme.fontPlain(11));
        lbl.setForeground(Theme.TEXT_MEDIUM);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(Theme.fontBold(26));
        val.setForeground(valueColor != null ? valueColor : Theme.TEXT_DARK);
        val.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(Theme.fontPlain(10));
        subLbl.setForeground(Theme.TEXT_LIGHT);
        subLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lbl);
        card.add(Box.createVerticalStrut(4));
        card.add(val);
        card.add(Box.createVerticalStrut(2));
        card.add(subLbl);
        return card;
    }

    /** Category tag pill */
    public static JLabel categoryTag(String text) {
        JLabel tag = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color[] colors = getTagColors(getText());
                g2.setColor(colors[0]);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        Color[] colors = getTagColors(text);
        tag.setForeground(colors[1]);
        tag.setFont(Theme.fontBold(11));
        tag.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        tag.setOpaque(false);
        return tag;
    }

    private static Color[] getTagColors(String text) {
        switch (text.toLowerCase()) {
            case "floral":    return new Color[]{Theme.TAG_FLORAL_BG,   Theme.TAG_FLORAL_FG};
            case "oriental":  return new Color[]{Theme.TAG_ORIENTAL_BG, Theme.TAG_ORIENTAL_FG};
            case "cítrico":
            case "citrico":   return new Color[]{Theme.TAG_CITRUS_BG,   Theme.TAG_CITRUS_FG};
            case "acuático":
            case "acuatico":  return new Color[]{Theme.TAG_AQUATIC_BG,  Theme.TAG_AQUATIC_FG};
            case "amaderado": return new Color[]{Theme.TAG_WOOD_BG,     Theme.TAG_WOOD_FG};
            default:          return new Color[]{Theme.BG_SELECTED,     Theme.PRIMARY};
        }
    }

    /** Section title label */
    public static JLabel sectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.fontBold(12));
        lbl.setForeground(Theme.TEXT_LIGHT);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        return lbl;
    }
}
