/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * Класс служит для представления всех параметров игрока и управления ими
 */
public class Player {

    private int position;
    private double cash;
    private double debt;
    private double spent;

    public Player(int money) {
        position = 0;
        cash = money;
        debt = 0;
        spent = 0;
    }

    /**
     * геттер для переменной spent
     * @return количество потраченных денег
     */
    public double getSpent() {
        return spent;
    }

    /**
     * меняет количество потраченных денег
     * @param difference новые затраты
     */
    public void changeSpent(double difference) {
        spent += difference;
    }

    /**
     * геттер для переменной debt
     * @return размер долга
     */
    public double getDebt() {
        return debt;
    }

    /**
     * изменяет размер долга
     * @param change новый долг
     */
    public void changeDebt(double change) {
        debt += change;
    }

    /**
     * изменяет баланс игрока
     * @param change размер изменения
     */
    public void changeCash(double change) {
        cash += change;
    }

    /**
     * геттер для переменной cash
     * @return баланс
     */
    public double getCash() {
        return cash;
    }

    /**
     * геттер для переменной position
     * @return текущую позицию игрока
     */
    public int getPosition() {
        return position;
    }

    /**
     * изменяет текущую позицию игрока
     * @param difference расстояние, на которое перемещается игрок
     */
    public void changePosition(int difference) {
        position += difference;
    }

    /**
     * производит списание долга с баланса
     */
    public void debtRelief() {
        cash -= debt;
        debt = 0;
    }
}
