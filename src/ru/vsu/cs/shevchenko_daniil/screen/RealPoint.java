package ru.vsu.cs.shevchenko_daniil.screen;

public class RealPoint {
    private double x, y;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public RealPoint minus(RealPoint p) {
        return new RealPoint(getX() - p.getX(), getY() - p.getY());
    }

    public double distanse(RealPoint point) {
        return Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2));
    }

}
