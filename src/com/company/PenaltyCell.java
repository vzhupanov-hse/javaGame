/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для представления клетки типа PenaltyCell
 */
public class PenaltyCell extends Cell {

    public PenaltyCell(String _symbol) {
        super(_symbol);
    }

    /**
     * выводит сообщение, соответствующее данному типу клетки
     * @param coeff процент  баланса игрока, списывающийся при попадании на данную клетку
     */
    public void printMessage(double coeff) {
        System.out.printf("Вы на штрафной клетке. С вас списывается %f часть вашего баланса.  \n", coeff);
    }
}
