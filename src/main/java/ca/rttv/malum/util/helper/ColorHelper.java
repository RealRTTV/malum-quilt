package ca.rttv.malum.util.helper;

import net.minecraft.client.util.ColorUtil;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class ColorHelper {
    public static Color getColor(int decimal) {
        int red = ColorUtil.ARGB32.getRed(decimal);
        int green = ColorUtil.ARGB32.getGreen(decimal);
        int blue = ColorUtil.ARGB32.getBlue(decimal);
        return new Color(red, green, blue);
    }

    public static void RGBToHSV(Color color, float[] hsv) {
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
    }

    public static int getColor(Color color) {
        return ColorUtil.ARGB32.getArgb(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }

    public static int getColor(int r, int g, int b) {
        return ColorUtil.ARGB32.getArgb(255, r, g, b);
    }

    public static int getColor(int r, int g, int b, int a) {
        return ColorUtil.ARGB32.getArgb(a, r, g, b);
    }

    public static int getColor(float r, float g, float b, float a) {
        return ColorUtil.ARGB32.getArgb((int) (a * 255f), (int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
    }

    public static Color colorLerp(float pct, Color brightColor, Color darkColor) {
        pct = MathHelper.clamp(pct, 0, 1);
        int br = brightColor.getRed(), bg = brightColor.getGreen(), bb = brightColor.getBlue();
        int dr = darkColor.getRed(), dg = darkColor.getGreen(), db = darkColor.getBlue();
        int red = (int) MathHelper.lerp(pct, dr, br);
        int green = (int) MathHelper.lerp(pct, dg, bg);
        int blue = (int) MathHelper.lerp(pct, db, bb);
        return new Color(red, green, blue);
    }

    public static Color darker(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        return new Color(Math.max((int) (color.getRed() * FACTOR), 0),
                Math.max((int) (color.getGreen() * FACTOR), 0),
                Math.max((int) (color.getBlue() * FACTOR), 0),
                color.getAlpha());
    }

    public static Color brighter(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        int i = (int) (1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;

        return new Color(Math.min((int) (r / FACTOR), 255),
                Math.min((int) (g / FACTOR), 255),
                Math.min((int) (b / FACTOR), 255),
                alpha);
    }
}
