/*
 * @author <a href="mailto:vzhupanov@edu.hse.ru" Vyacheslav Zhupanov</a>
 */

package com.company;

import java.util.ArrayList;

/**
 * класс для представления игрового поля
 */
public class PlayingField {

    private int width;
    private int height;
    private int size;
    private ArrayList<Cell> field;

    public PlayingField(int width, int height) {
        if (width < 6 || width > 30 || height < 6 || width > 30)
            throw new IllegalArgumentException("Недопустимые параметры поля!");
        this.width = width;
        this.height = height;
        size = width * 2 + 2 * (height - 2) - 1;
        field = new ArrayList<Cell>();
        generatePlayingField();
    }

    /**
     * выводит игровое поле в формате квадрата
     * @param player игрок, для которого отображается игровое поле
     */
    public void printPlayingField(Player player) {
        System.out.println();
        for (int i = 0; i < width; i++) {
            outputShop(field.get(i), player);
        }
        System.out.println();
        String space = generateSpace();
        for (int i = 0; i < height - 2; i++) {
            outputShop(field.get(size - i), player);
            System.out.print(space);
            outputShop(field.get(width + i), player);
            System.out.println();
        }
        for (int i = 0; i < width; i++) {
            outputShop(field.get(size - (height - 2) - i), player);
        }
        System.out.println();
    }

    /**
     * геттер для списка клеток
     * @return список-игровое поле
     */
    public ArrayList<Cell> getField() {
        return field;
    }

    /**
     * генерация нужного количества пробелов для вывода игрового пол квадратом
     * @return строку из пробелов
     */
    private String generateSpace() {
        String space = "  ";
        for (int i = 0; i < width - 3; i++) {
            space += "  ";
        }
        return space;
    }

    /**
     * выводит символвсоответствии с типом клетки
     * @param cell клетка, которая подается на вывод
     * @param player игрок, для которого выводится игровое поле
     */
    private void outputShop(Cell cell, Player player) {
        if (cell.getSymbol() == "S")
            if (((Shop) cell).isFree())
                System.out.print("S" + " ");
            else if (((Shop) cell).getOwner() == player)
                System.out.print("M" + " ");
            else
                System.out.print("O" + " ");
        else
            System.out.print(cell.getSymbol() + " ");
    }

    /**
     * генерация списка-игрового поля
     */
    private void generatePlayingField() {
        Cell[] line1 = createLine(width - 1);
        for (int i = 0; i < line1.length; i++) {
            field.add(line1[i]);
        }
        Cell[] line2 = createLine(height - 1);
        for (int i = 0; i < line2.length; i++) {
            field.add(line2[i]);
        }
        Cell[] line3 = createLine(width - 1);
        for (int i = 0; i < line3.length; i++) {
            field.add(line3[i]);
        }
        Cell[] line4 = createLine(height - 1);
        for (int i = 0; i < line4.length; i++) {
            field.add(line4[i]);
        }
    }

    /**
     * генерация одной линии игрового поля
     * @param size длина линии
     * @return массив клеток
     */
    private Cell[] createLine(int size) {
        Cell[] line = new Cell[size];
        line[0] = new EmptyCell("E");
        int bank_position = MyRandom.rnd.nextInt(size - 1) + 1;
        line[bank_position] = new Bank("$");
        generateCells(size, line, 0);
        generateCells(size, line, 1);
        for (int i = 1; i < size; i++) {
            if (line[i] == null)
                line[i] = new Shop("S");
        }
        return line;
    }

    /**
     * генерация клеток типа Taxi и PenaltyCell
     * @param size размер линии, на которой будет расположена клетка
     * @param line линия, на которой будет расположена клетка
     * @param flag флаг для определения какого типа клеткагенерируется
     */
    private void generateCells(int size, Cell[] line, int flag) {
        for (int i = 0; i < MyRandom.rnd.nextInt(3); i++) {
            while (true) {
                int position = MyRandom.rnd.nextInt(size - 1) + 1;
                if (line[position] == null) {
                    if (flag == 0)
                        line[position] = new Taxi("T");
                    else
                        line[position] = new PenaltyCell("%");
                    break;
                }
            }
        }
    }
}

