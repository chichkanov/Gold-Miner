package main;

import java.util.Random;

public class MazeGenerator {

    static final int fullfill = 100; //плотность стен от 0 до 100
    static final int wallshort = 60; //длина стен от 0 до 100
    int size; //размер поля
    int startx, starty; //начальная клетка генерации, (0,0) по умолчанию
    Random rand = new Random(); //объект класса Random
    int[][] m = new int[size + 1][size + 1]; //результативный массив
    int[][] r = new int[2][size / 2 * size / 2]; //массив, содержащий рандомные значения стенок, которые затем помещаются в массив m

    int h = 0;

    //получение случайного поля
    public int[][] getMaze(int size) {

        this.size = size;

        m = new int[size + 1][size + 1];
        r = new int[2][size / 2 * size / 2];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                m[i][j] = 0;

        for (int i = 0; i <= size; i++) {
            m[0][i] = 1;
            m[size][i] = 1;

            m[i][0] = 1;
            m[i][size] = 1;
        }

        initrandom();

        while (getrandom()) {

            if (m[starty][startx] == 1)
                continue;
            if (rand.nextInt(100) > fullfill)
                continue;
            int sx = 0, sy = 0;
            do {
                sx = rand.nextInt(3) - 1;
                sy = rand.nextInt(3) - 1;
            } while (sx == 0 && sy == 0 || sx != 0 && sy != 0);
            while (m[starty][startx] == 0) {
                if (rand.nextInt(100) > wallshort) {
                    m[starty][startx] = 1;
                    break;
                }
                m[starty][startx] = 1;
                startx += sx;
                starty += sy;
                m[starty][startx] = 1;
                startx += sx;
                starty += sy;
            }
        }

        for (int i = 0; i <= size; i++) {
            m[0][i] = 7;
            m[i][0] = 7;
            m[size][i] = 7;
            m[i][size] = 7;
        }

        for (int i = 2; i <= size - 1; i++) {
            for (int j = 2; j <= size - 1; j++) {

            }
        }

        m[1][1] = 0;
        m[size - 1][size - 1] = 0;
        return m;
    }

    //инициализация массива рандомных значений
    private void initrandom() {
        int j = 0;

        for (int y = 2; y < size; y += 2)
            for (int x = 2; x < size; x += 2) {
                r[0][j] = x;
                r[1][j] = y;
                j++;
            }
        h = j - 1;
    }

    //получить рандомное значение
    private boolean getrandom() {

        int i = (new Random()).nextInt(h);

        startx = r[0][i];
        starty = r[1][i];

        r[0][i] = r[0][h];
        r[1][i] = r[1][h];

        return (--h) != 0;

    }
}
