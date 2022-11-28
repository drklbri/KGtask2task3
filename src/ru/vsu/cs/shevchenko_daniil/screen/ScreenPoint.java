package ru.vsu.cs.shevchenko_daniil.screen;

public class ScreenPoint {
    private final int r;
    private final int c;

    public ScreenPoint(int c, int r) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public double distanse(ScreenPoint point) {
        return Math.sqrt(Math.pow(point.getC() - c, 2) + Math.pow(point.getR() - r, 2));
    }

    public int getC() {
        return c;
    }
}
