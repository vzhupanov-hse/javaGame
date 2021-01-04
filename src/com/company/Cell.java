/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для объединения всех видов клеток
 */
public class Cell {

    private String symbol;

    public Cell(String _symbol) {
        symbol = _symbol;
    }

    /**
     * геттер для переменной symbol
     * @return
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * вывод соответствующего сообщения
     */
    public void printMessage() {}
}
