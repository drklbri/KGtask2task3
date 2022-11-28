package ru.vsu.cs.shevchenko_daniil.drawers;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private final PixelDrawer pixelDrawer;

    public DDALineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

        if (x2 < x1) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dx = x2 - x1;
        double dy = y2 - y1;

        if (dx != 0) {
            for (int i = 0; i <= dx; i++) {
                pixelDrawer.drawPixel(x1 + i, (int) (y1 + i * dy / dx), Color.black);
            }
        }

        if(y2 < y1){
            int t = x1;
            x1 = x2;
            x2 = t;
            t = y1;
            y1 = y2;
            y2 = t;
        }

        dx = x2 - x1;
        dy = y2 - y1;

        if (dy != 0) {
            if (dy > dx) {
                for (int i = 0; i <= dy; i++) {
                    pixelDrawer.drawPixel((int) (x1 + i * dx / dy), y1 + i, Color.black);
                }
            }
        }
    }
}
