package main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Map {
    /*
     * Map :
	 * 0-can go
	 * 1-wall
	 * 2-gold
	 * 7-border 
	 */
    private static int rows; //размер поля
    protected static int map[][];  //игровая карта
    private BufferedReader in;  //требуется для ввода поля из файла
    private static int amountOfGold = 0;  //количество монет на поле
    private Image grass, wall, gold, edge, menubar;  //графическая составляющая игры

    //метод устанавливает кол-во золота на карте, он требуется для инициализации значения золота при новом уровне
    public static void setAmountOfGold(int a){
        amountOfGold = a;
    }

    //возвращает размер поля
    public static int getRows() {
        return rows;
    }

    //возвращает поле
    public static int[][] getMap() {
        return map;
    }

    //возвращает значение поля с координатами х,у
    public static int getMap(int x, int y) {
        return (x >= 0 && x < rows && y >= 0 && y < rows) ? map[x][y] : 0;
    }

    //возвращает картинку травы
    public Image getGrass() {
        return grass;
    }

    //возвращает кол-во монет
    public static int getAmountOfGold() {
        return amountOfGold;
    }

    //изменяет значения поля, если игрок подобрал монету
    public static void tookTheGold(int x, int y) {
        map[x][y] = 0;
    }

    //метод, рисующий игровое поле
    public void drawMap(Graphics g){
        for (int y = 0; y < rows; y++) {
                g.drawImage(menubar, y * 32, 0, null);
        }
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < rows; x++) {

                if(map[x][y] == 0 || map[x][y] == 9 ){
                    g.drawImage(grass, y * 32, x * 32 + 32 , null);
                }

                if (map[x][y] == 1) {
                    g.drawImage(wall, y * 32, x * 32 + 32, null);
                }

                if (map[x][y] == 2) {
                    g.drawImage(gold, y * 32, x * 32 + 32, null);
                }

                if (map[x][y] == 7) {
                    g.drawImage(edge, y * 32, x * 32 + 32, null);
                }
            }
        }
    }

    public static void printMap(){
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < rows; x++) {
                System.out.print(map[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //конструктор класса Map, где происходит инициализация уровня
    public Map() {

        ImageIcon img = new ImageIcon(getClass().getResource("/field.png"));
        grass = img.getImage();
        img = new ImageIcon(getClass().getResource("/wall.png"));
        wall = img.getImage();
        img = new ImageIcon(getClass().getResource("/gold.png"));
        gold = img.getImage();
        img = new ImageIcon(getClass().getResource("/edge.png"));
        edge = img.getImage();
        img = new ImageIcon(getClass().getResource("/menubar.png"));
        menubar = img.getImage();

        try {
            if (Levels.levelChosen != 0) {
                in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/levels/location1/lvl" + Levels.levelChosen + ".txt")));
            } else {
                if (Levels.levelChosen == 0) {
                    rows = 11;
                    /*
                    rows = 16 + (int)(Math.random() * ((30 - 16) + 1));
                    if(rows%2 == 0)rows--;
                    */
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
        }

        if (Levels.levelChosen != 0) {
            try {
                String tmp = in.readLine();
                rows = Integer.parseInt(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map = new int[rows][rows];

        //init non random map

            for (int i = 0; i < rows; i++) {
                try {
                    String tmp = in.readLine();
                    String parts[] = tmp.split(" ");


                    for (int j = 0; j < parts.length; j++) {
                        map[i][j] = Integer.parseInt(parts[j]);
                        if (map[i][j] == 2) amountOfGold++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}

/*
            MazeGenerator mz = new MazeGenerator();
            map = mz.getMaze(rows - 1);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
 */