package ru.vsu.cs.shevchenko_daniil;

import ru.vsu.cs.shevchenko_daniil.figures.CrossPoint;
import ru.vsu.cs.shevchenko_daniil.figures.Line;
import ru.vsu.cs.shevchenko_daniil.screen.RealPoint;
import ru.vsu.cs.shevchenko_daniil.screen.ScreenConverter;
import ru.vsu.cs.shevchenko_daniil.screen.ScreenPoint;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private java.util.List<Line> allLines = new ArrayList<>();
    private java.util.List<CrossPoint> allCrossPoints = new ArrayList<>();
    private CrossPoint crossPoint;
    private CrossPoint chosenCrossPoint;
    private Line currentLine;
    private ScreenConverter sc;
    private Line editingLine = null;
    private Line ox, oy;

    public DrawPanel() {
        sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        ox = new Line(new RealPoint(-1, 0), new RealPoint(1, 0));
        oy = new Line(new RealPoint(0, -1), new RealPoint(0, 1));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics origG) {
        sc.setSw(getWidth());
        sc.setSh(getHeight());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.black);
        drawLine(g, sc, ox);
        drawLine(g, sc, oy);
        for (Line l : allLines) {
            drawLine(g, sc, l);
        }
        if (currentLine != null) {
            g.setColor(Color.red);
            drawLine(g, sc, currentLine);
        }
        if (editingLine != null) {
            g.setColor(new Color(0x06C506));
            drawLine(g, sc, editingLine);
        }

        if (crossPoint != null) {
            g.setColor(Color.black);
            for (CrossPoint point : allCrossPoints)
                drawCrossPoint(g, sc, point.getIntersectPoint());
        }

        g.setColor(Color.darkGray);
        g.fillRect(getWidth() * 8 / 10, 0, getWidth() / 5 + 10, getHeight() / 6);
        if (chosenCrossPoint != null) {
            g.setColor(Color.white);
            g.drawString("Точка пересечения:", getWidth() * 8 / 10, getHeight() / 50);
            ScreenPoint point = sc.r2s(chosenCrossPoint.getIntersectPoint());
            g.drawString("X:" + String.valueOf(chosenCrossPoint.getIntersectPoint().getX()).substring(0, 5), getWidth() * 8 / 10, getHeight() / 20);
            g.drawString("Y:" + String.valueOf(chosenCrossPoint.getIntersectPoint().getY()).substring(0, 5), getWidth() * 8 / 10, getHeight() / 12);
        }
        origG.drawImage(bi, 0, 0, null);
        g.dispose();
    }

    private static void drawCrossPoint(Graphics2D g, ScreenConverter sc, RealPoint crossPoint) {
        ScreenPoint point = sc.r2s(crossPoint);
        g.fillOval(point.getC() - 10, point.getR() - 10, 20, 20);
        g.setColor(Color.blue);
        g.fillOval(point.getC() - 5, point.getR() - 5, 10, 10);
        g.setColor(Color.black);
    }

    private static void drawLine(Graphics2D g, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        g.drawLine(p1.getC(), p1.getR(), p2.getC(), p2.getR());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            RealPoint clickP = sc.s2r(new ScreenPoint(e.getX(), e.getY()));
            chosenCrossPoint = new CrossPoint(null, null);
            chosenCrossPoint.setIntersectPoint(new RealPoint(99999999, 99999999));
            for (CrossPoint point : allCrossPoints) {
                RealPoint rpPoint = point.getIntersectPoint();
                if (rpPoint.distanse(clickP) < clickP.distanse(chosenCrossPoint.getIntersectPoint()))
                    chosenCrossPoint.setIntersectPoint(clickP);
            }

        }
    }

    private ScreenPoint previousPoint = null;

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            previousPoint = new ScreenPoint(e.getX(), e.getY());
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (editingLine == null) {
                Line x = findLine(sc, allLines, new ScreenPoint(e.getX(), e.getY()), 50);
                if (x != null) {
                    editingLine = x;
                } else {
                    RealPoint p = sc.s2r(new ScreenPoint(e.getX(), e.getY()));
                    currentLine = new Line(p, p);
                }
            } else {
                if (closeToLine(sc, editingLine, new ScreenPoint(e.getX(), e.getY()), 50)) {
                    /**/
                }
            }
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            previousPoint = null;
        } else if (SwingUtilities.isLeftMouseButton(e)) {

            if (currentLine != null) {
                currentLine.setP2(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                for (Line line : allLines) {
                    crossPoint = new CrossPoint(line, currentLine);
                    if (crossPoint.isIntersect())
                        allCrossPoints.add(crossPoint);
                }
                allLines.add(currentLine);
                currentLine = null;
            }
            if (editingLine != null) {
                allCrossPoints.removeIf(point -> point.getLine1() == editingLine || point.getLine2() == editingLine);
                for (Line line : allLines) {
                    crossPoint = new CrossPoint(line, editingLine);
                    if (crossPoint.isIntersect())
                        allCrossPoints.add(crossPoint);
                }
                editingLine = null;

            }
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            if (editingLine != null) {
                allLines.remove(editingLine);
                editingLine = null;
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            ScreenPoint curPoint = new ScreenPoint(e.getX(), e.getY());
            RealPoint p1 = sc.s2r(curPoint);
            RealPoint p2 = sc.s2r(previousPoint);
            RealPoint delta = p2.minus(p1);
            sc.moveCorner(delta);
            previousPoint = curPoint;
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (currentLine != null)
                currentLine.setP2(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
            else if (editingLine != null) {
                if (nearestPoint(sc.s2r(new ScreenPoint(e.getX(), e.getY())), editingLine)) {
                    editingLine.setP1(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                } else {
                    editingLine.setP2(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private static final double SCAPE_STEP = 0.05;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        double scale = 1;
        double coef = 1 + SCAPE_STEP * (rotation < 0 ? -1 : 1);
        for (int i = Math.abs(rotation); i > 0; i--) {
            scale *= coef;
        }
        sc.changeScale(scale);
        repaint();
    }


    private static boolean closeToLine(ScreenConverter sc, Line l, ScreenPoint p, int eps) {
        ScreenPoint a = sc.r2s(l.getP1());
        ScreenPoint b = sc.r2s(l.getP2());
        return isNear(a, p, eps) || isNear(b, p, eps) || distanceToLine(a, b, p) < eps && p.getC() > Math.min(a.getC(), b.getC()) && isPointInRect(a, b, p);
    }

    private static boolean isPointInRect(ScreenPoint pr1, ScreenPoint pr2, ScreenPoint cp) {
        return cp.getC() >= Math.min(pr1.getC(), pr2.getC()) &&
                cp.getC() <= Math.max(pr1.getC(), pr2.getC()) &&
                cp.getR() >= Math.min(pr1.getR(), pr2.getR()) &&
                cp.getR() <= Math.max(pr1.getR(), pr2.getR());
    }

    private static boolean isNear(ScreenPoint p1, ScreenPoint p2, int eps) {
        int dx = p1.getC() - p2.getC();
        int dy = p1.getR() - p2.getR();
        int d2 = dx * dx + dy * dy;
        return d2 < eps;
    }

    private static double distanceToLine(ScreenPoint lp1, ScreenPoint lp2, ScreenPoint cp) {
        double a = lp2.getR() - lp1.getR();
        double b = -(lp2.getC() - lp1.getC());

        double e = -cp.getC() * b + cp.getR() * a;
        double f = a * lp1.getC() - b * lp1.getR();

        double y = (a * e - b * f) / (a * a + b * b);
        double x = (a * y - e) / b;

        return Math.sqrt((cp.getC() - x) * (cp.getC() - x) + (cp.getR() - y) * (cp.getR()) - y);
    }

    private static Line findLine(ScreenConverter sc, java.util.List<Line> lines, ScreenPoint searchPoint, int eps) {
        for (Line l : lines) {
            if (closeToLine(sc, l, searchPoint, eps)) {
                return l;
            }
        }
        return null;
    }


    private static boolean nearestPoint(RealPoint click, Line line) {
        RealPoint sp1 = line.getP1();
        RealPoint sp2 = line.getP2();
        double dist1 = Math.sqrt(Math.pow((sp1.getY() - click.getY()), 2) + Math.pow((sp1.getX() - click.getX()), 2));
        double dist2 = Math.sqrt(Math.pow((sp2.getY() - click.getY()), 2) + Math.pow((sp2.getX() - click.getX()), 2));
        if (dist1 < dist2)
            return true;
        return false;
    }


}
