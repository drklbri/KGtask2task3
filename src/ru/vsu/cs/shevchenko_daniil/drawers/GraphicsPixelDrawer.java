package ru.vsu.cs.shevchenko_daniil.drawers;

import java.awt.*;

public class GraphicsPixelDrawer implements PixelDrawer{

    private final Graphics2D graphics2D;

    public GraphicsPixelDrawer(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillRect(x, y, 1, 1);
    }
}
