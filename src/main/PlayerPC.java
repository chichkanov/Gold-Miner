package main;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PlayerPC {

    private int tileX, tileY; //координаты Х и У
    private Image playerPC; //изображение игрокаПК
    private static Image goldLeft; //кол-во монет, оставшихся собрать
    private static int score; //счет игрокаПК
    private int time; //счетчик времени
    private int speed; //скорость игрокаПК
    private boolean freezed = false; //заморожен или нет

    //замораживает игрокаПК
    public void setFreezed() {
        freezed = true;
    }

    //размораживает игрокаПК
    public void setNonFreezed() {
        freezed = false;
    }

    //конструктор класса PlayerPC, инициализирующий счет, изображение, скорость, счетчик времени
    public PlayerPC(int x, int y, int speed) {
        this.speed = speed;
        score = 0;
        time = 0;
        ImageIcon img = new ImageIcon(getClass().getResource("/playerPC.png"));
        playerPC = img.getImage();

        tileX = x;
        tileY = y;

        img = new ImageIcon(getClass().getResource("/goldLeft.png"));
        goldLeft = img.getImage();
    }

    //отрисовка оставшихся монет
    public static void drawScore(Graphics g) {
        for (int i = 0; i < Map.getAmountOfGold() / 2 - score + 1; i++) {
            g.drawImage(goldLeft, Map.getRows() * 32 - 16, i * 32 + 64, null);
        }
    }

    //отрисовка игрокаПК
    public void drawPlayerPC(Graphics g) {
        g.drawImage(playerPC, tileX * 32, tileY * 32 + 32, null);
    }

    //возвращает счет
    public static int getScore() {
        return score;
    }

    //возвращает координату Х
    public int getTileX() {
        return tileX;
    }

    //возвращает координату У
    public int getTileY() {
        return tileY;
    }

    //проверка движения вверх
    public boolean canMoveUp() {
        return Map.getMap(tileY - 1, tileX) == 0 || Map.getMap(tileY - 1, tileX) == 2 ? true : false;
    }

    //проверка движения вправо
    public boolean canMoveRight() {
        return Map.getMap(tileY, tileX + 1) == 0 || Map.getMap(tileY, tileX + 1) == 2 ? true : false;
    }

    //проверка движения вниз
    public boolean canMoveDown() {
        return Map.getMap(tileY + 1, tileX) == 0 || Map.getMap(tileY + 1, tileX) == 2 ? true : false;
    }

    //проверка движения влево
    public boolean canMoveLeft() {
        return Map.getMap(tileY, tileX - 1) == 0 || Map.getMap(tileY, tileX - 1) == 2 ? true : false;
    }

    //проверка движения в направлении r
    public boolean canMove(int r) {

        if (r == 0) {
            return canMoveUp() ? true : false;
        }
        if (r == 1) {
            return canMoveLeft() ? true : false;
        }
        if (r == 2) {
            return canMoveDown() ? true : false;
        }
        if (r == 3) {
            return canMoveRight() ? true : false;
        }

        return false;
    }

    Random rand = new Random();

    //подобрать золото
    public void tookTheGold(){
        if (Map.getMap(tileY, tileX) == 2) {
            score++;
            Map.tookTheGold(tileY, tileX);
        }
    }

    //случайное движение
    public void doRandMove() {
        r = rand.nextInt(4);
        switch(r){
            case 0:{
                if(canMoveUp() && Map.map[tileY-1][tileX]!=9){
                    tileY--;
                    break;
                }
            }
            case 1:{
                if(canMoveLeft() && Map.map[tileY][tileX-1]!=9){
                    tileX--;
                    break;
                }
            }
            case 2:{
                if(canMoveDown() && Map.map[tileY+1][tileX]!=9){
                    tileY++;
                    break;
                }
            }
            case 3:{
                if(canMoveRight() && Map.map[tileY][tileX+1]!=9){
                    tileX++;
                    break;
                }

            }
        }
    }

    int r;
    int stepCount = 0;
    boolean stupid = false;
    int stupidCounter=0;
    int prevX=-1,prevY=-1;

    //передвижение
    public void move() {
        if (!freezed) {
            time++;
            //интеллект становится глупым

            if (time % speed == 0) {
                if(prevX!=-1 && prevY!=-1)Map.map[prevY][prevX]=0;
                if (!stupid) {
                    boolean step = false;


                    if (Player.tileX < tileX && canMoveLeft() && !step && Map.map[tileY][tileX-1]!=9) {
                        tileX--;
                        stepCount++;
                        step = true;

                    }


                    if (Player.tileX > tileX && canMoveRight() && !step && Map.map[tileY][tileX+1]!=9) {
                        step = true;
                        stepCount++;
                        tileX++;
                    }


                    if (Player.tileY > tileY && canMoveDown() && !step && Map.map[tileY+1][tileX]!=9) {
                        step = true;
                        stepCount++;
                        tileY++;
                    }


                    if (Player.tileY < tileY && canMoveUp() && !step && Map.map[tileY-1][tileX-1]!=9) {
                        step = true;
                        stepCount++;
                        tileY--;
                    }

                    if (!step) {
                        doRandMove();
                    }

                    //if((time/speed)%20 == 0)stupid=true;
                    tookTheGold();

                    Map.map[tileY][tileX]=9;
                    prevX=tileX;
                    prevY=tileY;
                }
                else {
                    doRandMove();
                    stupidCounter++;
                    if(stupidCounter==2)stupid=false;
                    tookTheGold();
                }
            }
        }
    }


}
