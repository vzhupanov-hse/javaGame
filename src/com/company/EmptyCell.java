/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для представления клетки типа EmptyCell
 */
public class EmptyCell extends Cell {

    public EmptyCell(String _symbol) {
        super(_symbol);
    }

    /**
     * выводит сообщение, соответствующее данному типу клетки
     */
    public void printMessage() {
        System.out.println("Just relax there");
    }
}
