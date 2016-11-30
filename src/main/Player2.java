package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player2 {

    private static int tileX, tileY; //координаты игрока
    private Image player; //изображение игрока
    private int score; //счет
    private static boolean freezed = false; //может ли игрок двигаться или нет
    private Image goldLeft; //изображение кол-ва золота, которое осталось собрать

    //конструктор класса Player2, устанавливающий стартовую позицию, счет, изображение игрока2
    public Player2() {
        score = 0;
        ImageIcon img = new ImageIcon(getClass().getResource("/player2.png"));
        player = img.getImage();
        tileX = Map.getRows() - 2;
        tileY = Map.getRows() - 2;
    }

    //отрисовка игрока
    public void drawPlayer(Graphics g){
        g.drawImage(player, tileX * 32, tileY * 32+32, null);
    }

    //отрисовка монет, оставшихся собрать для игрока 2
    public void drawScore(Graphics g){
        for(int i=0;i<Map.getAmountOfGold()/2-score+1; i++) {
            g.drawImage(goldLeft, Map.getRows()*32-16, i*32+64, null);
        }
    }


    //замораживает игрока
    public static void setFreezed() {
        freezed = true;
    }

    //размораживает игрока
    public static void setNonFreezed() {
        freezed = false;
    }


    public void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
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

    //метор, переопределяющий управление для игрока 2
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(!freezed) {

            if (key == KeyEvent.VK_D) {

                if (Map.getMap(tileY, tileX + 1) == 0 || Map.getMap(tileY, tileX + 1) == 2) move(1, 0);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    System.out.println(score);
                    Map.tookTheGold(tileY, tileX);
                }
            }

            if (key == KeyEvent.VK_A) {
                if (Map.getMap(tileY, tileX - 1) == 0 || Map.getMap(tileY, tileX - 1) == 2) move(-1, 0);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    System.out.println(score);
                    Map.tookTheGold(tileY, tileX);
                }
            }

            if (key == KeyEvent.VK_W) {
                if (Map.getMap(tileY - 1, tileX) == 0 || Map.getMap(tileY - 1, tileX) == 2) move(0, -1);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    System.out.println(score);
                    Map.tookTheGold(tileY, tileX);
                }

            }

            if (key == KeyEvent.VK_S) {
                if (Map.getMap(tileY + 1, tileX) == 0 || Map.getMap(tileY + 1, tileX) == 2) move(0, 1);
                if (Map.getMap(tileY, tileX) == 2) {
                    score++;
                    System.out.println(score);
                    Map.tookTheGold(tileY, tileX);
                }
            }
        }
    }

}
