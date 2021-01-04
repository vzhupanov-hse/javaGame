/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для представления клетки типа Shop
 */
public class Shop extends Cell {

    private boolean freeShop = true;
    private double compensationCoeff;
    private double improvementCoeff;
    private int price;
    private double compensation;
    private Player owner;

    /**
     * инициализация всех нужных переменных
     * @param _symbol символ обозначения клетки
     */
    public Shop(String _symbol) {
        super(_symbol);
        compensationCoeff = MyRandom.rnd.nextDouble();
        if (compensationCoeff < 0.1)
            compensationCoeff += 0.1;
        improvementCoeff = MyRandom.rnd.nextInt(2);
        if (improvementCoeff != 2)
            improvementCoeff += MyRandom.rnd.nextDouble();
        if (improvementCoeff < 0.1)
            improvementCoeff += 0.1;
        price = MyRandom.rnd.nextInt(500);
        if (price < 50)
            price += 50;
        compensation = MyRandom.rnd.nextDouble();
        if (compensation < 0.5)
            compensation += 0.5;
        if (compensation > 0.9)
            compensation -= 0.09;
        compensation *= price;
    }

    /**
     * проверяет, есть ли у магазина владелец
     * @return информацию, есть ли у магазина владелец
     */
    public boolean isFree() {
        return freeShop;
    }

    /**
     * реализует процесс покупки магазина
     * @param new_owner игрок, который покупает магазин
     */
    public void buyShop(Player new_owner) {
        freeShop = false;
        owner = new_owner;
    }

    /**
     * реализует процесс улучшения магазина
     */
    public void upgrade() {
        price += price * improvementCoeff;
        compensation += compensationCoeff * compensation;
    }

    /**
     * геттер для переменной improvementCoeff
     * @return коэффицент, на который умножается цена магазина при улучшении
     */
    public double getImprovementCoeff() {
        return improvementCoeff;
    }

    /**
     * геттер для переменной price
     * @return цену магазина
     */
    public int getPrice() {
        return price;
    }

    /**
     * геттер для переменной compensation
     * @return компенсацию игрока, не являющегося владельцем магазина
     */
    public double getCompensation() {
        return compensation;
    }

    /**
     * геттер для переменной owner
     * @return игрока- владельца магазина
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * выводит сообщение, соответствующее данному типу клетки
     * @param player игрок, для которого выводится сообщение
     */
    public int printMessage(Player player) {
        if (isFree()) {
            System.out.printf("Вы находитесь в магазинe. У этого магазина нет владельца. Хотите купить его за %d $? Введите 'Yes', если вы согласны, или 'No' в противном случае. \n", this.price);
            return 0;
        } else if (owner == player) {
            System.out.printf("Вы в своем магазине. Хотите улучшить его за %f $? Введите 'Yes', если вы согласны, или 'No' в противном случае. \n", this.price * this.improvementCoeff);
            return 1;
        }
        else {
            System.out.printf("Вы в чужом магазине. Вы должны заплатить %f. \n", this.compensation);
            return 2;
        }
    }
}
