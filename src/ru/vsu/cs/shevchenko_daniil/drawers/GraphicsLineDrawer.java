package ru.vsu.cs.shevchenko_daniil.drawers;

import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {

    private final Graphics2D graphics2D;

    public GraphicsLineDrawer(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics2D.drawLine(x1, y1, x2, y2);
    }
}
