/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для представления клетки типа Bank
 */
public class Bank extends Cell {

    public Bank(String _symbol) {
        super(_symbol);
    }

    /**
     * выводит сообщение, соответствующее данному типу клетки
     * @param player игрок, для которого выводится сообщение
     */
    public void printMessage(Player player) {
        if (player.getDebt() == 0)
            System.out.println("Вы в офисе банка. Вы хотите взять кредит? Введите сколько вы хотите получить или слово 'No'");
        else
            System.out.printf("С вас списано %f $ \n", player.getDebt());
    }
}
