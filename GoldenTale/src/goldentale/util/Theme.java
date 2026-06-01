package util;

import java.awt.*;

public class Theme {
    // Colors matching Golden Tale design
    public static final Color PRIMARY       = new Color(94, 60, 171);   // purple header
    public static final Color PRIMARY_LIGHT = new Color(115, 80, 195);
    public static final Color PRIMARY_TEXT  = Color.WHITE;

    public static final Color BG_MAIN       = new Color(246, 244, 240); // warm off-white
    public static final Color BG_SIDEBAR    = new Color(252, 251, 249);
    public static final Color BG_CARD       = Color.WHITE;
    public static final Color BG_SELECTED   = new Color(237, 230, 255); // light purple selection

    public static final Color TEXT_DARK     = new Color(30, 25, 20);
    public static final Color TEXT_MEDIUM   = new Color(100, 90, 80);
    public static final Color TEXT_LIGHT    = new Color(150, 140, 130);

    public static final Color BORDER        = new Color(220, 215, 210);
    public static final Color BORDER_LIGHT  = new Color(235, 230, 225);

    public static final Color SUCCESS       = new Color(34, 139, 34);
    public static final Color WARNING       = new Color(200, 130, 0);
    public static final Color DANGER        = new Color(180, 50, 50);
    public static final Color INFO          = new Color(94, 60, 171);

    // Tag colors
    public static final Color TAG_FLORAL_BG   = new Color(255, 220, 230);
    public static final Color TAG_FLORAL_FG   = new Color(160, 60, 90);
    public static final Color TAG_ORIENTAL_BG = new Color(255, 235, 200);
    public static final Color TAG_ORIENTAL_FG = new Color(160, 100, 30);
    public static final Color TAG_CITRUS_BG   = new Color(220, 255, 210);
    public static final Color TAG_CITRUS_FG   = new Color(60, 130, 60);
    public static final Color TAG_AQUATIC_BG  = new Color(210, 240, 255);
    public static final Color TAG_AQUATIC_FG  = new Color(30, 100, 160);
    public static final Color TAG_WOOD_BG     = new Color(225, 215, 200);
    public static final Color TAG_WOOD_FG     = new Color(100, 70, 40);

    // Fonts
    public static Font fontBold(int size) {
        return new Font("Segoe UI", Font.BOLD, size);
    }
    public static Font fontPlain(int size) {
        return new Font("Segoe UI", Font.PLAIN, size);
    }
    public static Font fontSemiBold(int size) {
        return new Font("Segoe UI Semibold", Font.BOLD, size);
    }
}
