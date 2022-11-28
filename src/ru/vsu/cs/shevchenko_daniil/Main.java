package ru.vsu.cs.shevchenko_daniil;

import javax.swing.*;

//Написать программу построения точки пересечения двух произвольных отрезков.
//Метод решающий задачу должен принимать две линии и возвращать точку, которую потом можно вывести на экран.
//Предусмотреть ситуацию, когда две линии параллельны/совпадают.
public class Main {

    public static void main(String[] args) {
	MainWindow window = new MainWindow();
    window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    window.setSize(800, 600);
    window.setVisible(true);
    }
}
