/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для представления клетки типа Taxi
 */
public class Taxi extends Cell {

    private int taxiDistance;

    public Taxi(String _symbol) {
        super(_symbol);
        taxiDistance = MyRandom.rnd.nextInt(5);
        if (taxiDistance < 3)
            taxiDistance += 3;
    }

    /**
     * геттер для переменнй taxiDistance
     * @return дистанцию, на которую перемещается игрок
     */
    public int getTaxiDistance() {
        return taxiDistance;
    }

    /**
     * выводит сообщение, соответствующее данному типу клетки
     */
    public void printMessage() {
        System.out.printf("Вы перенесены вперед на %d клеток \n", taxiDistance);
    }
}
