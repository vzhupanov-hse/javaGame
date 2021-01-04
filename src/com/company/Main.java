/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int height = 0, width = 0, money = 0;
        //обработка параметров из командной строки
        if (args.length == 3) {
            height = tryParseInt(args[0]);
            width = tryParseInt(args[1]);
            money = tryParseInt(args[2]);
        }
        //процесс игры
        if (!(height < 6 || height > 30 || width < 6 || width > 30 || money < 500 || money > 1500)) {
            PlayingField field = new PlayingField(width, height);
            Player player = new Player(money);
            Player bot = new Player(money);
            System.out.println();
            field.printPlayingField(player);
            Coefficients coefficients = new Coefficients();
            coefficients.printCoefficients();
            GameProcess game = new GameProcess();
            int priority = game.getPriority();
            if (priority == 0) {
                gamePriority(game, player, bot, field, coefficients, 0);
            } else {
                gamePriority(game, bot, player, field, coefficients, 1);
            }
            //вывод результатов игры
            if (player.getCash() <= 0)
                System.out.println("Вы проиграли!");
            else
                System.out.println("Вы выиграли!");
        } else
            System.out.println("Введены некорректные данные!");
    }

    /**
     * осуществляет процесс игры
     *
     * @param game         объекткласса GameProcess, c помощью которого осуществляется процесс игры
     * @param player1      Игрок
     * @param player2      Бот
     * @param field        игровое поле
     * @param coefficients коэффициенты, заданные до начала игры
     * @param priority     очередь ходов игроков
     */
    public static void gamePriority(GameProcess game, Player player1, Player player2, PlayingField field, Coefficients coefficients, int priority) {
        while (game.game(player1, player2, field.getField(), 0, coefficients, field))
            if (!game.game(player2, player1, field.getField(), 1, coefficients, field))
                break;
    }

    /**
     * переводит строковую переменную в int
     *
     * @param value строковое значение числа
     * @return числовое представление строки
     */
    public static int tryParseInt(String value) {
        try {
            int res = Integer.parseInt(value);
            return res;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
