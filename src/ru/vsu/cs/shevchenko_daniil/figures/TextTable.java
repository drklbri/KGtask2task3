package ru.vsu.cs.shevchenko_daniil.figures;

public class TextTable {
    private double x, y;
    private String text;
    private int sizeX, sizeY;

    public TextTable(double x, double y) {
        this.x = x;
        this.y = y;
        text = "Координаты точки:\nx: " + String.valueOf(x) + "\ny: " + String.valueOf(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}
