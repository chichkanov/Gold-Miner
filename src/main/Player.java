package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    protected static int tileX, tileY; //координаты игрока
    private Image player; //изображение игрока
    private int score; //счет
    private static boolean freezed = false; //может ли игрок двигаться или нет
    private Image goldLeft; //изображение кол-ва золота, которое осталось собрать

    //замораживает игрока
    public static void setFreezed() {
        freezed = true;
    }

    //размораживает игрока
    public static void setNonFreezed() {
        freezed = false;
    }

    //конструктор класса Player устанавливает изображение, стартовую позицию, нач. счет
    public Player() {
        score = 0;
        ImageIcon img = new ImageIcon(getClass().getResource("/player.png"));
        player = img.getImage();
        tileX = 1;
        tileY = 1;

        img = new ImageIcon(getClass().getResource("/goldLeft.png"));
        goldLeft = img.getImage();
    }

    //отрисовка игрока
    public void drawPlayer(Graphics g){
        g.drawImage(player, tileX * 32, tileY * 32+32, null);
    }

    //отрисовка кол-ва монет, необходимых собрать
    public void drawScore(Graphics g){
        for(int i=0;i<Map.getAmountOfGold()/2-score+1; i++) {
            g.drawImage(goldLeft, -7, i*32+64, null);
        }
    }

    //возвращает счет
    public  int getScore() {
        return score;
    }

    //возвращает координату Х
    public  int getTileX() {
        return tileX;
    }

    //возвращает координату У
    public  int getTileY() {
        return tileY;
    }

    //перемещение игрока, осуществляемое путем изменения координат Х и У
    public void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
    }

    //слушатель событий, отвечающий за управление игре
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(!freezed) {

            if (key == KeyEvent.VK_RIGHT) {

                if (Map.getMap(tileY, tileX + 1) == 0 || Map.getMap(tileY, tileX + 1) == 2) move(1, 0);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    Map.tookTheGold(tileY, tileX);
                }
            }

            if (key == KeyEvent.VK_LEFT) {
                if (Map.getMap(tileY, tileX - 1) == 0 || Map.getMap(tileY, tileX - 1) == 2) move(-1, 0);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    Map.tookTheGold(tileY, tileX);
                }
            }

            if (key == KeyEvent.VK_UP) {
                if (Map.getMap(tileY - 1, tileX) == 0 || Map.getMap(tileY - 1, tileX) == 2) move(0, -1);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    Map.tookTheGold(tileY, tileX);
                }

            }

            if (key == KeyEvent.VK_DOWN) {
                if (Map.getMap(tileY + 1, tileX) == 0 || Map.getMap(tileY + 1, tileX) == 2) move(0, 1);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    Map.tookTheGold(tileY, tileX);
                }
            }
        }
    }

}
