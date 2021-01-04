/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

/**
 * класс для генерации и хранения коэффициентов, генерирующихся в начале игры
 */
public class Coefficients {

    private double penaltyCoeff;
    private double debtCoeff;
    private double creditCoeff;

    /**
     * генерация неизменяемых коэффициентов для игры
     */
    public Coefficients() {
        penaltyCoeff = MyRandom.rnd.nextDouble();
        while (penaltyCoeff > 0.1)
            penaltyCoeff -= 0.1;
        if (penaltyCoeff < 0.01)
            penaltyCoeff += 0.01;
        debtCoeff = createDebtCoeff();
        creditCoeff = MyRandom.rnd.nextDouble();
        while (creditCoeff > 0.2)
            creditCoeff -= 0.1;
        if (penaltyCoeff < 0.002)
            penaltyCoeff += 0.002;
    }

    /**
     * генерация коэффициента, на который умножается взятая у банка сумма, для определения долга
     * @return коэффициент, который умножается взятая у банка сумма
     */
    private double createDebtCoeff() {
        int whole = MyRandom.rnd.nextInt(3);
        if (whole == 0)
            whole++;
        if (whole == 3)
            return whole;
        return whole + MyRandom.rnd.nextDouble();
    }

    /**
     * геттер для переменной debtCoeff
     * @return коэффициент, который умножается взятая у банка сумма
     */
    public double getDebtCoeff() {
        return debtCoeff;
    }

    /**
     * геттер для переменной penaltyCoeff
     * @return коэффициент, который умножается баланс для взыскания, при попадании на штрафную клетку
     */
    public double getPenaltyCoeff() {
        return penaltyCoeff;
    }

    /**
     * геттер для переменной creditCoeff
     * @return коэффициент, который умножается валанс, для определения максимальной суммы кредита
     */
    public double getCreditCoeff() {
        return creditCoeff;
    }

    /**
     * выводит значения всех коэффициентов
     */
    public void printCoefficients(){
        System.out.printf("Вы можете взять кредит на сумму не более чем, %f умноженное на сумму потраченных денег на покупки и улучшения", creditCoeff);
        System.out.println();
        System.out.printf("Кредитная ставка: %f", debtCoeff);
        System.out.println();
        System.out.printf("На штрафной клетке вы потеряете %f часть ваших денег", penaltyCoeff);
        System.out.println();
    }
}
