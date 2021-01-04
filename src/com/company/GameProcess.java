/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * класс для представления реализации игрового процесса
 */
public class GameProcess {
    private int priority;

    public GameProcess() {
        priority = MyRandom.rnd.nextInt(2);
    }

    /**
     * геттер для переменной priority
     * @return порядок ходов
     */
    public int getPriority() {
        return priority;
    }

    /**
     * реализация процесса игры
     * @param player1 первый игрок
     * @param player2 второй игрок
     * @param field игровое поле
     * @param flag указатель, кто из игроков является ботом
     * @param coefficients объект класса Coefficients для получения нужных коэффициентов
     * @param playingField объект класса PlayingField, для вывода игрвоого поля
     * @return возможность продолжения игры
     */
    public boolean game(Player player1, Player player2, ArrayList<Cell> field, int flag, Coefficients coefficients, PlayingField playingField) {
        //передвижение игрока
        int moving = (MyRandom.rnd.nextInt(6) + 1) + (MyRandom.rnd.nextInt(6) + 1);
        player1.changePosition(moving);
        if (player1.getPosition() >= field.size())
            player1.changePosition(-field.size());
        //вывод игрового поля для игрока, не являющегося ботом
        if (flag == 0)
            playingField.printPlayingField(player1);
        Scanner in = new Scanner(System.in);
        //списание средств при попадании на штрафную клетку
        if (field.get(player1.getPosition()).getSymbol() == "%") {
            if (flag == 0)
                ((PenaltyCell) field.get(player1.getPosition())).printMessage(coefficients.getPenaltyCoeff());
            player1.changeCash(-(coefficients.getPenaltyCoeff() * player1.getCash()));
            return nonzeroBalance(player1, player2, flag);
        }
        //реализация всех возможностей при попадании на клетку типа Bank
        if (field.get(player1.getPosition()).getSymbol() == "$") {
            return bankCellProcessing(flag, player1, player2, field, coefficients);
        }
        //реализация всех возможностей при попадании на клетку типа Shop
        if (field.get(player1.getPosition()).getSymbol() == "S") {
            return shopCellProcessing(flag, field, player1, player2);
        }
        if (flag == 0)
            field.get(player1.getPosition()).printMessage();
        //передвижение игрока при попадании на клетку Taxi
        if (field.get(player1.getPosition()).getSymbol() == "T") {
            player1.changePosition(((Taxi) field.get(player1.getPosition())).getTaxiDistance());
            if (player1.getPosition() >= field.size())
                player1.changePosition(-field.size());
            game(player1, player2, field, flag, coefficients, playingField);
        }
        return true;
    }

    /**
     * реализация покупки магазина ботом
     * @param player бот
     * @param field игровое поле
     * @return возможность продолжения игры
     */
    private boolean botUpgradeShop(Player player, ArrayList<Cell> field) {
        if (MyRandom.rnd.nextInt(2) == 0)
            return true;
        else {
            if (player.getCash() >= ((Shop) field.get(player.getPosition())).getPrice() * ((Shop) field.get(player.getPosition())).getImprovementCoeff()) {
                ((Shop) field.get(player.getPosition())).upgrade();
                player.changeCash(-((Shop) field.get(player.getPosition())).getPrice() * ((Shop) field.get(player.getPosition())).getImprovementCoeff());
                System.out.printf("Оппонентом был улучшен магазин %d \n", player.getPosition());
            }
            return true;
        }
    }

    /**
     * реализация возможностей игроков при попадании на клетку типа Shop
     * @param flag показатель, является ли данный игрок ботом
     * @param field игровое поле
     * @param player1 первый игрок
     * @param player2 второй игрок
     * @return возможность продолжения игры
     */
    private boolean shopCellProcessing(int flag, ArrayList<Cell> field, Player player1, Player player2) {
        Scanner in = new Scanner(System.in);
        int res = 0;
        if (flag == 0)
            res = ((Shop) field.get(player1.getPosition())).printMessage(player1);
        boolean result = true;
        switch (res) {
            case 0:
                if (flag == 1)
                    result = botBuyShop(player1, field);
                else
                    result = buyShop(in, field, player1);
                break;
            case 1:
                if (flag == 0)
                    result = upgradeShop(in, player1, field);
                else
                    result = botUpgradeShop(player1, field);
                break;
            case 2:
                player1.changeCash(-((Shop) field.get(player1.getPosition())).getCompensation());
                player2.changeCash(((Shop) field.get(player1.getPosition())).getCompensation());
                if (player1.getCash() >= 0)
                    result = true;
                else
                    result = false;
                break;
        }
        if (flag == 0)
            printStats(player1, player2);
        return result;
    }

    /**
     * реализация возможностей игроков при попадании на клетку типа Bank
     * @param flag показатель, является ли данный игрок ботом
     * @param player1 первый игрок
     * @param player2 второй игрок
     * @param field игровое поле
     * @param coefficients объект класса Coefficients для получения нужных коэффициентов
     * @return возможность продолжения игры
     */
    private boolean bankCellProcessing(int flag, Player player1, Player player2, ArrayList<Cell> field, Coefficients coefficients) {
        if (flag == 0)
            ((Bank) field.get(player1.getPosition())).printMessage(player1);
        if (player1.getDebt() != 0) {
            player1.debtRelief();
            return nonzeroBalance(player1, player2, flag);
        } else {
            if (flag == 0) {
                Scanner in = new Scanner(System.in);
                String res = in.next().toLowerCase();
                if (res.equals("no")) {
                    printStats(player1, player2);
                    return nonzeroBalance(player1, player2, flag);
                }
                gettingCredit(res, coefficients, player1);
                printStats(player1, player2);
            }
        }
        return true;
    }

    /**
     * реализация процесса покупки магазина ботом
     * @param player бот
     * @param field игровое поле
     * @return возможность продолжения игры
     */
    private boolean botBuyShop(Player player, ArrayList<Cell> field) {
        if (MyRandom.rnd.nextInt(2) == 0)
            return true;
        else {
            if (player.getCash() >= ((Shop) field.get(player.getPosition())).getPrice()) {
                ((Shop) field.get(player.getPosition())).buyShop(player);
                player.changeCash(-((Shop) field.get(player.getPosition())).getPrice());
                player.changeSpent(((Shop) field.get(player.getPosition())).getPrice());
                System.out.printf("Оппонентом был приобретен магазин %d \n", player.getPosition());
            }
            return true;
        }
    }

    /**
     * реализация процесса покупки магазина игроком
     * @param in объект класса Scanner, для считывания значений
     * @param field игровое поле
     * @param player игрок, покупающий магазин
     * @return возможность продолжения игры
     */
    private boolean buyShop(Scanner in, ArrayList<Cell> field, Player player) {
        String input = in.next().toLowerCase();
        if (input.equals("no"))
            return true;
        if (input.equals("yes")) {
            if (player.getCash() >= ((Shop) field.get(player.getPosition())).getPrice()) {
                ((Shop) field.get(player.getPosition())).buyShop(player);
                player.changeCash(-((Shop) field.get(player.getPosition())).getPrice());
                player.changeSpent(((Shop) field.get(player.getPosition())).getPrice());
                System.out.printf("Вами был приобретен магазин %d \n", player.getPosition());
            } else
                System.out.println("У вас недостаточно денег!");
            if (player.getCash() >= 0)
                return true;
            else
                return false;
        } else {
            System.out.println("Введено некорректное значение! Попробуйте еще раз:");
            buyShop(in, field, player);
        }
        return true;
    }

    /**
     * проверка, могут ли оба игрока продолжать игру
     * @param player1 первый игрок
     * @param player2 второй игрок
     * @param flag показатель, кто из игроков является ботом
     * @return возможность продолжения игры
     */
    private boolean nonzeroBalance(Player player1, Player player2, int flag) {
        if (player1.getCash() >= 0) {
            if (flag == 0)
                printStats(player1, player2);
            return true;
        } else
            return false;
    }

    /**
     * реализация процесса улучшения магазина
     * @param in объект класса Scanner для считывания значений
     * @param player игрок, который улучшает магазин
     * @param field игровое поле
     * @return возможность продолжения игры
     */
    private boolean upgradeShop(Scanner in, Player player, ArrayList<Cell> field) {
        String input = in.next().toLowerCase();
        if (input.equals("no"))
            return true;
        if (input.equals("yes")) {
            if (player.getCash() >= ((Shop) field.get(player.getPosition())).getPrice() * ((Shop) field.get(player.getPosition())).getImprovementCoeff()) {
                ((Shop) field.get(player.getPosition())).upgrade();
                player.changeCash(-((Shop) field.get(player.getPosition())).getPrice() * ((Shop) field.get(player.getPosition())).getImprovementCoeff());
                System.out.printf("Вами был улучшен магазин %d \n", player.getPosition());
            } else {
                System.out.println("У вас недостаточно денег!");
                return true;
            }
        } else {
            System.out.println("Введено некорректное значение! Попробуйте еще раз:");
            upgradeShop(in, player, field);
        }
        return true;
    }

    /**
     * выводит сводку о состоянии игроков
     * @param player игрок
     * @param bot бот
     */
    public void printStats(Player player, Player bot) {
        if (player.getCash() < 0)
            System.out.printf("Ваш баланс: %d \n", 0);
        else
            System.out.printf("Ваш баланс: %f \n", player.getCash());
        System.out.printf("Ваш долг: %f \n", player.getDebt());
        if (bot.getCash() < 0)
            System.out.printf("Баланс оппонента: %d \n", 0);
        else
            System.out.printf("Баланс оппонента: %f \n", bot.getCash());
        System.out.printf("Долг оппонента: %f \n", bot.getDebt());
    }

    /**
     * реализация процесса получения кредита
     * @param res строковое представление суммы, которую хочет взять игрок
     * @param coefficients объект класса Coefficients для использования нужныхкоэффициентов
     * @param player игрок, который берет кредит
     */
    private void gettingCredit(String res, Coefficients coefficients, Player player) {
        Scanner in = new Scanner(System.in);
        if (tryParseInt(res)) {
            int credit = Integer.parseInt(res);
            if (credit <= coefficients.getCreditCoeff() * player.getSpent()) {
                player.changeCash(credit);
                player.changeDebt(credit * coefficients.getDebtCoeff());
            } else
                System.out.println("Вы не можете взять кредит на данную сумму!");
        } else gettingCredit(in.next(), coefficients, player);
    }

    /**
     * проверка, возможно ли перевести строку в число
     * @param value строковая переменная, которую пытаются перевести в число
     * @return можно ли перевести строку в число
     */
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Ввведите число, пожалуйста!");
            return false;
        }
    }
}
