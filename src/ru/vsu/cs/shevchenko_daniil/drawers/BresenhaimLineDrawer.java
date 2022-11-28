package ru.vsu.cs.shevchenko_daniil.drawers;

import java.awt.*;

public class BresenhaimLineDrawer implements LineDrawer {

    private PixelDrawer pixelDrawer;

    public BresenhaimLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    private int sign(int x) {
        return Integer.compare(x, 0);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                pixelDrawer.drawPixel(x, y, Color.black);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                pixelDrawer.drawPixel(x, y, Color.black);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
}
