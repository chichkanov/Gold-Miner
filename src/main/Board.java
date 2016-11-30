package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    public static boolean win = false; //переменная, сигнализирующая об окончании игры
    private static String winner; //победитель игры
    public static int time = 0; // счетчик времени
    private Timer timer; // таймер
    private Map map; // карта
    private Player player; // игрок
    int enemiesCount = 1; // кол-во ПК игроков (1 по умолчанию)
    ArrayList<PlayerPC> enemies; // массив ПК игроков
    private Player2 player2; // 2 игрок для мультиплеера


    public static String getWinner() {
        return winner;
    }

    //конструктор класса Board, в котором инициализируются основные переменные
    public Board() {
        Map.setAmountOfGold(0);
        time=0;
        map = new Map();
        player = new Player();
        enemies = new ArrayList<PlayerPC>(enemiesCount);
        Player.setFreezed();

        if (Menu.gameMode == 1) {
            int speed = 50 - Levels.levelChosen -7;
            if(Levels.levelChosen<10)speed=30;
            if(Levels.levelChosen>=10 || Levels.levelChosen<15)speed=25;
            if(Levels.levelChosen>=15 || Levels.levelChosen<20)speed=23;
            if(Levels.levelChosen>=20)speed=20;

            if (Levels.levelChosen != 1) {
                enemies.add(new PlayerPC(Map.getRows() - 2, Map.getRows() - 2, speed));
                enemies.add(new PlayerPC(Map.getRows() - 2, 1, speed));
                if (Levels.levelChosen >= 10) enemies.add(new PlayerPC(1, Map.getRows() - 2, speed));
            } else {
                enemies.add(new PlayerPC(Map.getRows() - 2, Map.getRows() - 2, speed));

            }

            for (PlayerPC enemie : enemies) enemie.setFreezed();


        }
        if (Menu.gameMode == 2) {
            player2 = new Player2();
            Player2.setFreezed();
        }

        if (WinScreen.newGame == true) {
            win = false;
        }

        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        timer = new Timer(10, this);
        timer.start();
    }

    //action listner
    public void actionPerformed(ActionEvent e) {

        if (!win) repaint();

        if (win) {

            if(Menu.gameMode==1 && winner=="You" && Levels.levelChosen==Levels.lastOpenedLevel){
                Levels.openLevel();
            }

            JOptionPane optionPane = new JOptionPane();
            String winText=winner=="You"?"Вы выиграли":(winner=="PlayerPC"?"Вы проиграли":winner+" победил");
            optionPane.showMessageDialog(null,  new JLabel(winText,JLabel.CENTER), "Gold Runner", JOptionPane.PLAIN_MESSAGE);

            try {
                Thread.sleep(200);            // wait N ms after game
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            timer.stop();
            new WinScreen();
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            if (Menu.gameMode == 1) {
                player.keyPressed(e);

            }

            if (Menu.gameMode == 2) {
                player.keyPressed(e);
                player2.keyPressed(e);
            }

        }

        public void keyReleased(KeyEvent e) {

        }
    }

    Font myFont = new Font("Century Gothic", Font.BOLD, 16); //шрифт для игры
    Font myFont2 = new Font("Arial", Font.BOLD, 13); //шрифт для таймера и туториала

    //метод рисует кастомный прямоугольник
    public void drawRect(Graphics g, int sizeX, int sizeY) {
        g.setColor(new Color(.3f, .3f, .3f, .4f));
        g.fillRect(65, 60, sizeX, sizeY);
        g.setColor(Color.WHITE);
    }

    //отображение на экране обучения в первом уровне


    int enterCount = 0;
    public void drawTutorial(Graphics g) {

        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {

                    enterCount++;
                    System.out.println("ENTER key pressed "+(enterCount));
                }
            }
        });

        g.setFont(myFont2);
        g.setColor(Color.WHITE);
        if (time / 100 < 2) {
            g.drawString("← Это вы", 70, 85);
        } else {
            if (time / 100 < 6) {
                drawRect(g, 140, 90);
                g.drawString("Ваша цель-собрать", 70, 80);
                g.drawString("нужное количество", 70, 100);
                g.drawString("монет, которое", 70, 120);
                g.drawString("отмечено слева", 70, 140);
            } else {
                if (time / 100 < 9) {
                    drawRect(g, 140, 70);
                    g.drawString("Управление", 70, 80);
                    g.drawString("осуществляется на", 70, 100);
                    g.drawString("срелочках", 70, 120);
                } else {
                    if (time / 100 < 11) {
                        g.drawString("Это ваш враг →", 60, 215);
                    } else {
                        if (time / 100 < 13) {
                            drawRect(g, 140, 70);
                            g.drawString("Избегайте", 70, 80);
                            g.drawString("встречи с ним,", 70, 100);
                            g.drawString("т.к. он убьет вас", 70, 120);
                        } else {
                            if (time / 100 < 16) {
                                drawRect(g, 140, 90);
                                g.drawString("Поторапливайтесь,", 70, 80);
                                g.drawString("ведь он может", 70, 100);
                                g.drawString("собрать монеты", 70, 120);
                                g.drawString("быстрее вас", 70, 140);
                            } else {
                                Player.setNonFreezed();
                                enemies.get(0).setNonFreezed();
                            }
                        }
                    }
                }
            }
        }

    }

    //отображение таймера перед игрой
    public void drawTimer(Graphics g) {
        if(time / 100 <=2) {
            g.setColor(new Color(.3f, .3f, .3f, .4f));
            g.fillOval(Map.getRows() * 16-60, Map.getRows() * 16-28, 120, 120);
        }
        myFont2 = new Font("Arial", Font.BOLD, 60);
        g.setColor(Color.WHITE);
        g.setFont(myFont2);
        if (time / 100 == 0) {
            g.drawString("3", Map.getRows() * 16 - 15, Map.getRows() * 16 + 50);
        } else {
            if (time / 100 == 1) {
                g.drawString("2", Map.getRows() * 16 - 15, Map.getRows() * 16 + 50);
            } else {
                if (time / 100 == 2) {
                    g.drawString("1", Map.getRows() * 16 - 15, Map.getRows() * 16 + 50);
                } else {
                    Player.setNonFreezed();
                    for (PlayerPC enemie : enemies) enemie.setNonFreezed();
                    Player2.setNonFreezed();
                }
            }
        }
    }

    //отображения времени в игре
    public void drawTimeInGame(Graphics g) {
        int delay = (Levels.levelChosen == 1 && Menu.gameMode == 1)?16:3;
        if(time/100>=delay) {
            myFont2 = new Font("Arial", Font.BOLD, 13);
            g.setColor(Color.WHITE);
            g.setFont(myFont2);
            String a = (Levels.levelChosen == 1 && Menu.gameMode == 1)?(Integer.toString(time / 100-16)):(Integer.toString(time / 100-3));
            String b = Integer.toString(time % 100);
            g.drawString(a + "." + b, Map.getRows() * 16 - 13, Map.getRows() * 32 + 22);
        }
    }

    //**************************************
    //Game loop    *************************
    //**************************************

    //Game loop: обработка данных игры в реальном времени и отображение на экране
    public void paint(Graphics g) {

        super.paint(g);

        if (!win) {
            time++;
            map.drawMap(g);
            drawTimeInGame(g);
            player.drawScore(g);
            //game mode for 1 player
            if (Menu.gameMode == 1) {

                if (Levels.levelChosen == 1) {
                    drawTutorial(g);
                } else {
                    drawTimer(g);
                }

                //scoreUI
                g.setColor(Color.WHITE);
                g.setFont(myFont);
                g.drawString("You  " + player.getScore(), Map.getRows() * 16 - 70, 23);
                g.drawString("vs", Map.getRows() * 16 - 7, 23);
                g.drawString(PlayerPC.getScore() + "  PC", Map.getRows() * 16 + 28, 23);

                PlayerPC.drawScore(g);

                //if player staying on gold-repaint this square
                if (Map.getMap(player.getTileY(), player.getTileX()) == 2) {
                    g.drawImage(map.getGrass(), player.getTileX() * 32, player.getTileY() * 32, null);
                    Map.tookTheGold(player.getTileY(), player.getTileX());
                }
                for (PlayerPC enemie : enemies) {
                    if (Map.getMap(enemie.getTileY(), enemie.getTileX()) == 2) {
                        g.drawImage(map.getGrass(), enemie.getTileX() * 32, enemie.getTileY() * 32, null);
                        Map.tookTheGold(enemie.getTileY(), enemie.getTileX());
                    }
                }

                player.drawPlayer(g);

                //PCmove
                for (PlayerPC enemie : enemies) {
                    enemie.move();
                    enemie.drawPlayerPC(g);
                    if (PlayerPC.getScore() == map.getAmountOfGold() / 2 + 1 || (player.getTileX() == enemie.getTileX() && player.getTileY() == enemie.getTileY())) {
                        win = true;
                        winner = "PlayerPC";
                    }
                }


                if (player.getScore() == map.getAmountOfGold() / 2 +1 ) {
                    win = true;
                    winner = "You";
                }


            } else {

                drawTimer(g);
                //scoreUI
                g.setColor(Color.WHITE);
                Font myFont = new Font("Century Gothic", Font.BOLD, 16);
                g.setFont(myFont);
                g.drawString("Player1  " + player.getScore(), Map.getRows() * 16 - 90, 23);
                g.drawString("vs", Map.getRows() * 16 - 7, 23);
                g.drawString(player2.getScore() + "  Player2", Map.getRows() * 16 + 30, 23);
                player2.drawScore(g);

                //if player staying on gold-repaint this square
                if (Map.getMap(player.getTileY(), player.getTileX()) == 2) {
                    g.drawImage(map.getGrass(), player.getTileX() * 32, player.getTileY() * 32, null);
                    Map.tookTheGold(player.getTileY(), player.getTileX());
                }

                if (Map.getMap(player2.getTileY(), player2.getTileX()) == 2) {
                    g.drawImage(map.getGrass(), player2.getTileX() * 32, player2.getTileY() * 32, null);
                    Map.tookTheGold(player2.getTileY(), player2.getTileX());
                }

                player.drawPlayer(g);

                player2.drawPlayer(g);

                if (player.getScore() == map.getAmountOfGold() / 2 + 1) {
                    win = true;
                    winner = "Player 1";
                }

                if (player2.getScore() == map.getAmountOfGold() / 2 + 1) {
                    win = true;
                    winner = "Player 2";
                }

            }
        }


    }



}
