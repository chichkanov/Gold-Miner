package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class WinScreen {

    public static boolean newGame = false; //началась ли новая игра

    //конструктор класса WinScreen. В нем содержится рещультат игры, время игры, и кнопки: повторить, след. уровень(появляется только при победе) , выход в главное меню, выход из игры
    public WinScreen() {

        for (Window w : Window.getWindows()) {
            w.dispose();
        }

        final JFrame ws = new JFrame("Gold Runner");
        ws.setResizable(false);
        ws.setSize(280, 300);
        ws.setLocationRelativeTo(null);
        ws.getContentPane().setBackground(Color.WHITE);
        ws.setLayout(new GridBagLayout());
        ws.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(0, 2, 10, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        final JPanel win = new JPanel();
        win.setBackground(Color.WHITE);
        JLabel wr = new JLabel();
        wr.setFont(new Font("Serif", Font.BOLD, 20));


        int winSec=(Levels.levelChosen == 1 && Menu.gameMode == 1)?Board.time/100-16:Board.time/100-3;
        int winMsec=Board.time%100;
        if (Board.getWinner() == "You") {
            wr.setText("<html><font color='purple'>Вы победили за "+ winSec+"."+ winMsec +" сек</font></html>");
        } else {

            if (Board.getWinner() == "PlayerPC") {
                wr.setText("<html><font color='purple'>Вы проиграли</font></html>");
            } else {
                wr.setText("<html><font color='purple'>Победил : " + Board.getWinner() +"<br>"+ "за "+winSec +"."+ winMsec+" сек<"+"</font></html>");
            }
        }

        win.add(wr);
        ws.add(win, gbc);

        JButtonFlat startAgain = new JButtonFlat("Повторить", new Color(46, 204, 113));
        gbc.gridy = 1;
        ws.add(startAgain, gbc);
        startAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ws.dispose();
                newGame = true;
                try {
                    new Game();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        //if for next level button
        if ((Board.getWinner() == "You" && Menu.gameMode == 1 && Levels.levelChosen!=24) || (Menu.gameMode == 2 && Levels.levelChosen!=24)) {
            JButtonFlat nextLevel = new JButtonFlat("Следущий уровень", new Color(52, 152, 219));
            gbc.gridy++;
            ws.add(nextLevel, gbc);
            nextLevel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    newGame = true;
                    ws.dispose();
                    Levels.levelChosen++;
                    try {
                        Menu.menu.dispose();
                        new Game();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

        JButtonFlat backInMenu = new JButtonFlat("Меню", new Color(243, 156, 18));
        gbc.gridy++;
        ws.add(backInMenu, gbc);
        backInMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newGame = true;
                ws.dispose();
                new Menu();
            }
        });

        JButtonFlat exit = new JButtonFlat("Выход из игры", new Color(231, 76, 60));
        gbc.gridy++;
        ws.add(exit, gbc);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        ws.setVisible(true);
        ws.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
