package ru.vsu.cs.shevchenko_daniil.figures;

import ru.vsu.cs.shevchenko_daniil.screen.RealPoint;

import java.awt.geom.Line2D;

import static java.lang.Double.NaN;

public class CrossPoint {
    private Line line1, line2;
    private boolean intersect;
    private RealPoint intersectPoint;

    public CrossPoint(Line line1, Line line2) {
        this.line1 = line1;
        this.line2 = line2;
        if (line2 != null) {
            intersect = Line2D.linesIntersect(line1.getP1().getX(), line1.getP1().getY(), line1.getP2().getX(), line1.getP2().getY(), line2.getP1().getX(), line2.getP1().getY(), line2.getP2().getX(), line2.getP2().getY());
            intersectPoint = lineIntersect(line1.getP1(), line1.getP2(), line2.getP1(), line2.getP2());
        }
    }

    private RealPoint lineIntersect(RealPoint p0, RealPoint p1, RealPoint p2, RealPoint p3) {
        double A1 = p1.getY() - p0.getY();
        double B1 = p0.getX() - p1.getX();
        double C1 = A1 * p0.getX() + B1 * p0.getY();
        double A2 = p3.getY() - p2.getY();
        double B2 = p2.getX() - p3.getX();
        double C2 = A2 * p2.getX() + B2 * p2.getY();

        double denominator = A1 * B2 - A2 * B1;

        double x = (B2 * C1 - B1 * C2) / denominator;
        double y = (A1 * C2 - A2 * C1) / denominator;
        if (x == NaN)
            return null;
        if (y == NaN)
            return null;
        return new RealPoint(x, y);
    }

    public Line getLine1() {
        return line1;
    }

    public void setLine1(Line line1) {
        this.line1 = line1;
    }

    public Line getLine2() {
        return line2;
    }

    public void setLine2(Line line2) {
        this.line2 = line2;
    }

    public boolean isIntersect() {
        return intersect;
    }

    public void setIntersect(boolean intersect) {
        this.intersect = intersect;
    }

    public RealPoint getIntersectPoint() {
        return intersectPoint;
    }

    public void setIntersectPoint(RealPoint intersectPoint) {
        this.intersectPoint = intersectPoint;
    }
}
