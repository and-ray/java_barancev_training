package ru.belozerova.lesson2;

public class MySecondProgram {

    public static void main(String[] args) {
// Задание 2.3
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);

        System.out.println("Расстояние между двумя точками с координатами (" + p1.x + ", " + p1.y + ") и (" + p2.x + ", " + p2.y + ") составляет " + distance(p1, p2));

//Задание 2.4
        Point p3 = new Point(0, 1);
        Point p4 = new Point(0, 4);

        System.out.println("Расстояние между двумя точками с координатами (" + p3.x + ", " + p3.y + ") и (" + p4.x + ", " + p4.y + ") составляет " + p4.distanceTo(p3));
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
}
