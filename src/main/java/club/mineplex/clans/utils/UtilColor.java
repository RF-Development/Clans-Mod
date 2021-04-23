package club.mineplex.clans.utils;

import java.awt.*;

public class UtilColor {

    public static int getChromaColor() {
        return Color.HSBtoRGB((float)((System.currentTimeMillis() - (3 * 10) - (3 * 10)) % 2000L) / 2000.0F, 0.8F, 0.8F);
    }

}
